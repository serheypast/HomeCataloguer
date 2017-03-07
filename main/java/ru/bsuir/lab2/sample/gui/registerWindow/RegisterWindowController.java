package ru.bsuir.lab2.sample.gui.registerWindow;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ru.bsuir.lab2.sample.dataBase.authenticationDataBaseDriver.AuthenticationDataBaseDriver;
import ru.bsuir.lab2.sample.encrypt.EncryptPassword;
import ru.bsuir.lab2.sample.gui.loginWindow.LoginWindow;


/**
 * Created by Сергей on 05.03.2017.
 */
public class RegisterWindowController {

    /**
     * Set some Action for button OK
     * - if data is correctly create user with login some login and password
     * - if login is not a free then get error message
     * @param registerWindow
     */
    public static void setOnActionButtonOk(final RegisterWindow registerWindow){
        registerWindow.buttonOk.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if(checkField(registerWindow)){
                    if(AuthenticationDataBaseDriver.checkLogin(registerWindow.loginField.getText())){
                        String password = registerWindow.passwordField_1.getText();
                        password = EncryptPassword.encryptString(password);

                        AuthenticationDataBaseDriver.addUser(registerWindow.loginField.getText(),password);
                        registerWindow.stage.setScene((new LoginWindow(registerWindow.stage).getScene()));

                    }
                    else {
                        registerWindow.errorText.setText("User with this name already exists");
                    }
                } else {
                    registerWindow.errorText.setText("Your entered data is incorrect");
                }
            }
        });
    }

    /**
     *  Set some Action for button Back
     *  -set LoginWindow scene
     * @param registerWindow
     */
    public static void setOnActionButtonBack(final RegisterWindow registerWindow){
        registerWindow.buttonBack.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                registerWindow.stage.setScene((new LoginWindow(registerWindow.stage).getScene()));
            }
        });


    }

    /**
     * Check login in data base
     * @param registerWindow that look some field
     * @return true if user exist
     */
    private static boolean checkField(RegisterWindow registerWindow){
        if (!registerWindow.loginField.getText().equals("")   &&
                (registerWindow.passwordField_1.getText().equals(registerWindow.passwordField_2.getText()) &&
                !registerWindow.passwordField_1.getText().equals(""))){
            return true;
        }
        else{
            return false;
        }
    }
}
