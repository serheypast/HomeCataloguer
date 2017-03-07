package ru.bsuir.lab2.sample.gui.mainWindow;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import ru.bsuir.lab2.sample.dataBase.mainWindowDataBaseDriver.MainWindowDataBaseDriver;
import ru.bsuir.lab2.sample.gui.loginWindow.LoginWindow;
import ru.bsuir.lab2.sample.models.*;
import ru.bsuir.lab2.sample.searchPatter.SearchPattern;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Сергей on 05.03.2017.
 */


public class MainWindowController {
    private static ObservableList<ObservableList<CatalogFile>> musicList;
    private static ObservableList<ObservableList<CatalogFile>> filmsList;
    private static ObservableList<ObservableList<CatalogFile>> booksList;
    private static ObservableList<ObservableList<CatalogFile>> documentsList;
    private static ObservableList<ObservableList<CatalogFile>> currentList;
    private static final int DEFAULT_PAGE_VALUE = 1;
    private static Desktop desktop = Desktop.getDesktop();
    private static FileChooser fileChooser = new FileChooser();
    private static int isSearch = 0;

    /**
     * Set mouse action button Add
     * @param mainWindow look some field in this scene
     */
    public static void setOnActionButtonAdd(final MainWindow mainWindow){

        mainWindow.buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isSearch == 0) {
                    fileChooser.setInitialDirectory(new File("D:\\"));
                    setExtensionFilters(mainWindow.comboBox.getValue());
                    File file = fileChooser.showOpenDialog(mainWindow.stage);
                    if (file != null) {
                        User user = mainWindow.user;
                        CatalogFile catalogFile = new CatalogFile();
                        catalogFile.setName(file.getName());
                        catalogFile.setPath(file.getPath());
                        catalogFile.setSize((file.length() / 1024.0) / 1024.0);
                        if ((user.getRole() == 1) || isUserAdd(user, catalogFile.getSize())) {
                            MainWindowDataBaseDriver.addFile(catalogFile, mainWindow.comboBox.getValue());
                            MainWindowDataBaseDriver.updateUserAfterAddingFile(user);
                            addCatalogFile(catalogFile, mainWindow);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText("You have exceeded the limit of 10 MB per day. The file will not be added!");
                            alert.showAndWait();
                        }
                    }
                }
            }
        });
    }

    /**
     * After action in button Add
     * - add file in DB
     * - add file in list and tableView
     * @param catalogFile this file we add from computer directory
     * @param mainWindow allow look some field in this scene
     */
    private static void addCatalogFile(CatalogFile catalogFile, MainWindow mainWindow){
        int index = currentList.size();
        int sizeOfEndPage = currentList.get(index - 1).size();
        if (sizeOfEndPage == MainWindowDataBaseDriver.MAX_ELEMENTS_PAGE){
            final ObservableList<CatalogFile> dataPage = FXCollections.observableArrayList();
            dataPage.add(catalogFile);
            currentList.add(dataPage);
            setSpinnerValue(mainWindow);
        }else{
            currentList.get(index - 1).add(catalogFile);
        }

    }

    /**
     * Check 10 MB limits add file
     * @param user  - who add file
     * @param size - size file
     * @return - (false)Limit exceeded or not(tree)
     */
    private static boolean isUserAdd(User user,Double size){
        String lastUpdate = user.getInfAboutUpd();
        String currentData;
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        currentData = formatForDateNow.format(dateNow);

        if(!lastUpdate.equals("0")){
            String regular = "(\\w+[.]\\w+)";
            String sizeOfLastUpdate = SearchPattern.findStringRegular(regular,lastUpdate);
            regular = "(\\w+[.]\\w+[.]\\w+)";
            String dataOfLastUpdate = SearchPattern.findStringRegular(regular,lastUpdate);

            if(currentData.equals(dataOfLastUpdate)) {
                Double sizeLastUpd = Double.parseDouble(sizeOfLastUpdate);
                size =size + sizeLastUpd;
            }
        }
        user.setInfAboutUpd(String.valueOf(size) + " " + currentData);
        if(size > 10.0){
            return false;
        }
        return true;
    }


    /**
     * set information about file in list of DataBase
     */
    public static void setCatalogList(){
        musicList = MainWindowDataBaseDriver.getCatalogFile(Files.Music.getName());
        filmsList = MainWindowDataBaseDriver.getCatalogFile(Files.Films.getName());
        booksList = MainWindowDataBaseDriver.getCatalogFile(Files.Books.getName());
        documentsList = MainWindowDataBaseDriver.getCatalogFile(Files.Documents.getName());
    }

    /**
     * Set Data in tableView
     * @param mainWindow allow look some field in this scene
     * @param indexPage look page
     */
    public static void setTable(MainWindow mainWindow,int indexPage){

        switch (mainWindow.comboBox.getValue()) {
            case "Music":{
                currentList = musicList;
                break;
            }
            case "Films":{
                currentList = filmsList;
                break;
            }
            case "Books":{
                currentList = booksList;
                break;
            }
            case "Documents":{
                currentList = documentsList;
                break;
            }
            default:{

                break;
            }
        }
        mainWindow.tableView.setItems(currentList.get(indexPage - 1));

    }

    /**
     * set action Double click
     * - open file
     * @param tableView
     */
    public static void setDoubleMouseClickActionTable(final TableView<CatalogFile> tableView){
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2){
                    openFile(tableView.getSelectionModel().getSelectedItem().getPath());
                }
            }
        });

    }

    /**
     * open file @param path
     * @param path -path to file
     */
    private  static void openFile(String path) {
        try {
            File file = new File(path);
            desktop.open(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * set Action if comboBox change
     * @param mainWindow  allow look some field in this scene
     */
    public static void setOnActionChangeComboBoxValue(final MainWindow mainWindow){
        mainWindow.comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isSearch = 0;
                setTable(mainWindow,DEFAULT_PAGE_VALUE);
                setSpinnerValue(mainWindow);
            }
        });
    }

    /**
     * Set some action if button delete pressed
     * @param mainWindow allow look some field in this scene
     */
    public static void setOnActionDeleteButton(final MainWindow mainWindow){
        mainWindow.buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CatalogFile deleteCatalogFile = mainWindow.tableView.getSelectionModel().getSelectedItem();
                if(deleteCatalogFile != null && isSearch == 0){
                    mainWindow.tableView.getItems().remove(deleteCatalogFile);
                    balanceList(mainWindow);
                    MainWindowDataBaseDriver.deleteCatalogFile(deleteCatalogFile,mainWindow.comboBox.getValue());
                }
            }
        });
    }

    /**
     * balance Table view before delete data in table
     * @param mainWindow allow look some field in this scene
     */
    private static void balanceList(MainWindow mainWindow){
        int indexRemove = mainWindow.spinner.getValue();
        int indexLastPage = currentList.size();
        if (!(indexLastPage == indexRemove)){
            int sizeElementsInLastPage = currentList.get(indexLastPage - 1).size();
            CatalogFile catalogFile = currentList.get(indexLastPage - 1).get(sizeElementsInLastPage -1);
            currentList.get(indexRemove - 1).add(catalogFile);
            currentList.get(indexLastPage - 1).remove(sizeElementsInLastPage - 1);
            if(sizeElementsInLastPage == 1){
                currentList.remove(indexLastPage - 1);
            }
            setSpinnerValue(mainWindow);
        }
    }

    /**
     * Set some action before click in Search Button
     * @param mainWindow allow look some field in this scene
     */
    public static void setOnSearchBotton(final MainWindow mainWindow){
        mainWindow.buttonSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setOnActionSearchButton(mainWindow);
                isSearch = 1;
            }
        });
    }

    /**
     * Set table after request search
     * @param mainWindow allow look some field in this scene
     */
    private static void setOnActionSearchButton(MainWindow mainWindow){
        String searchText = mainWindow.searchField.getText();
        if(!searchText.equals("")) {
            String pattern = searchText;
            final ObservableList<ObservableList<CatalogFile>> list = FXCollections.observableArrayList();
            final ObservableList<CatalogFile> subList = FXCollections.observableArrayList();
            list.add(subList);
            for (ObservableList<CatalogFile> catalogFiles : currentList) {
                for (CatalogFile catalogFile : catalogFiles) {
                    if (!SearchPattern.findStringRegular(pattern, catalogFile.getName()).equals("")) {
                        int sizeList = list.size() - 1;
                        int sizeOfSubList = list.get(sizeList).size();
                        if (sizeOfSubList == MainWindowDataBaseDriver.MAX_ELEMENTS_PAGE) {
                            final ObservableList<CatalogFile> newSubList = FXCollections.observableArrayList();
                            newSubList.add(catalogFile);
                            list.add(newSubList);
                        } else {
                            list.get(sizeList).add(catalogFile);
                        }
                    }
                }
            }
            currentList = list;
            mainWindow.tableView.setItems(currentList.get(DEFAULT_PAGE_VALUE - 1));
            setSpinnerValue(mainWindow);

        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("You did not complete the search field!");
            alert.showAndWait();
        }
    }

    /**
     * set spinnet value(Page value) after spinner change
     * @param mainWindow allow look some field in this scene
     */
    public static void setSpinnerValue(MainWindow mainWindow){
        Spinner<Integer> spinner = mainWindow.spinner;
        int initialValue = 1;
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        initialValue,
                        currentList.size(),
                        initialValue);
        spinner.setValueFactory(valueFactory);
    }

    /**
     * Set action if spinner value is change
     * @param mainWindow allow look some field in this scene
     */
    public static void setOnActionChangeSpinner(final MainWindow mainWindow){
        final Spinner<Integer> spinner = mainWindow.spinner;
        spinner.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int currentValue = spinner.getValue();
                mainWindow.tableView.setItems(currentList.get(currentValue - 1));
            }
        });
    }


    /**
     * set Some Extension Filters for open file
     * @param fileType name of type of file
     */
    private static void setExtensionFilters(String fileType){
        switch (fileType){
            case "Music":
                setExtensionFiltersForMusic();
                break;
            case "Films":
                setExtensionFiltersForFilms();
                break;
            case "Books":
                setExtensionFiltersForBooks();
                break;
            case "Documents":
                setExtensionFiltersForDocuments();
                break;
            default:
                break;
        }
    }

    /**
     * set Extension Filters for music
     */
    private static void setExtensionFiltersForMusic(){
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3", "*.mp3"),
                new FileChooser.ExtensionFilter("All", "*.*")
        );
    }

    /**
     * set Extension Filters for music
     */
    private static void setExtensionFiltersForFilms(){
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("AVI", "*.avi"),
                new FileChooser.ExtensionFilter("MP4", "*.mp4"),
                new FileChooser.ExtensionFilter("All", "*.*")
        );
    }
    /**
     * set Extension Filters for music
     */
    private static void setExtensionFiltersForBooks(){
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("DJVU", "*.djvu"),
                new FileChooser.ExtensionFilter("All", "*.*")
        );
    }
    /**
     * set Extension Filters for music
     */
    private static void setExtensionFiltersForDocuments(){
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("DOC", "*.doc"),
                new FileChooser.ExtensionFilter("DOCX", "*.docx"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("All", "*.*")
        );
    }

    /**
     * set action if hyperlink is pressed
     * @param mainWindow allow look some field in this scene
     */
    public static void setOnActionHyperLing(final MainWindow mainWindow){
        mainWindow.hyperlinkInOut.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainWindow.stage.setScene(new LoginWindow(mainWindow.stage).getScene());
            }
        });
    }

}
