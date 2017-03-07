package ru.bsuir.lab2.sample.dataBase.authenticationDataBaseDriver;


import ru.bsuir.lab2.sample.dataBase.DataBaseDriver;
import ru.bsuir.lab2.sample.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Сергей on 05.03.2017.
 */
public class AuthenticationDataBaseDriver {
    /** Default access during registration */
    private static final int DEFAULT_USER = 2;

    /** Default value about last Update in Cataloguer */
    private static final String DEFAULT_LAST_UPDATE = "0";

    /**
     * find info about user(by reference param)
     * @param login String login (user name)
     * @param password String password (password for authentication)
     * @return object User where be kept info about him
     */
    public static User checkUser(String login, String password){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Users where Login = ? and Password = ?";
        try{
            preparedStatement = DataBaseDriver.conn.prepareStatement(query);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                User user = new User();
                user.setLogin(login);
                user.setRole(resultSet.getInt("Role"));
                user.setInfAboutUpd(resultSet.getString("LastUpdate"));
                preparedStatement.close();
                return  user;
            }else {
                preparedStatement.close();
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Check for the existence of the user in the database
     * @param login String login (user name)
     * @return true - if the user exist, false if not
     */
    public static boolean checkLogin(String login){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Users where Login = ?";
        try{
            preparedStatement = DataBaseDriver.conn.prepareStatement(query);
            preparedStatement.setString(1,login);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                preparedStatement.close();
                return  false;
            }else {
                preparedStatement.close();
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Add user in DataBase (sqlite)
     * @param login String login (user name)
     * @param password String password (password for authentication)
     */
    public static void addUser(String login,String password){
        try {
            DataBaseDriver.statmt.execute("INSERT INTO 'Users' ('Login', 'Password', 'Role', 'lastUpdate')" +
                    " VALUES ('"+login+"', '"+password+"', "+ DEFAULT_USER +", '"+DEFAULT_LAST_UPDATE+"'); ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}
