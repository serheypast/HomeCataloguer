package ru.bsuir.lab2.sample.dataBase.mainWindowDataBaseDriver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.bsuir.lab2.sample.dataBase.DataBaseDriver;
import ru.bsuir.lab2.sample.models.CatalogFile;
import ru.bsuir.lab2.sample.models.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Сергей on 05.03.2017.
 */
public class MainWindowDataBaseDriver {
    private static final int DATABASE_NAME = 1;
    private static final int DATABASE_SIZE = 2;
    private static final int DATABASE_TYPE = 3;
    private static final int DATABASE_PATH_TO_RESOURCE = 4;
    public static final int MAX_ELEMENTS_PAGE = 12;

    /**
     * Adding pattern file in table depending in @param table
     * @param catalogFile info about file(adding file) in computer directory
     * @param table Name table in DataBase
     */
    public static void addFile(CatalogFile catalogFile, String table){
        try {
            DataBaseDriver.statmt.execute("INSERT INTO '"+table+"' ('Name', 'Size', 'Type', 'PathToResource')" +
                    " VALUES ('"+catalogFile.getName()+"', "+catalogFile.getSize()+", '"+catalogFile.getType()+"', '"+ catalogFile.getPath()+"'); ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update info about user when him add file in cataloguer
     * @param user full information about user
     */
    public static void updateUserAfterAddingFile(User user) {
        try {
            String sql = "UPDATE Users SET lastUpdate = ? "
                    + "WHERE Login = ?";

            PreparedStatement preparedStatement = DataBaseDriver.conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getInfAboutUpd());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete some value in a table in DataBase
     * @param catalogFile  info about file(remove file) in computer directory
     * @param table table in DataBase in which be remove
     */
    public static void deleteCatalogFile(CatalogFile catalogFile,String table){
        try {
            String sql = "DELETE FROM "+table+" WHERE Name = ? and PathToResource = ?;";
            PreparedStatement preparedStatement  = DataBaseDriver.conn.prepareStatement(sql);
            preparedStatement.setString(1,catalogFile.getName());
            preparedStatement.setString(2,catalogFile.getPath());
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method that takes values from a table
     * @param table name of Table
     * @return List of Lise values
     */
    public static ObservableList<ObservableList<CatalogFile>> getCatalogFile(String table){
        final ObservableList<ObservableList<CatalogFile>> data = FXCollections.observableArrayList();
        try {
            DataBaseDriver.resSet = DataBaseDriver.statmt.executeQuery("SELECT * FROM "+table+"");
            boolean isNext = DataBaseDriver.resSet.next();
            while(isNext){
                final ObservableList<CatalogFile> dataPage = FXCollections.observableArrayList();
                for(int i = 0; i < MAX_ELEMENTS_PAGE && isNext;i++) {
                    CatalogFile catalogFile = new CatalogFile();
                    catalogFile.setName(DataBaseDriver.resSet.getString(DATABASE_NAME));
                    catalogFile.setSize(DataBaseDriver.resSet.getDouble(DATABASE_SIZE));
                    catalogFile.setType(DataBaseDriver.resSet.getString(DATABASE_TYPE));
                    catalogFile.setPath(DataBaseDriver.resSet.getString(DATABASE_PATH_TO_RESOURCE));
                    dataPage.add(catalogFile);
                    isNext = DataBaseDriver.resSet.next();
                }
                data.add(dataPage);
            }
            if (data.size() == 0){
                final ObservableList<CatalogFile> dataPage = FXCollections.observableArrayList();
                data.add(dataPage);
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
