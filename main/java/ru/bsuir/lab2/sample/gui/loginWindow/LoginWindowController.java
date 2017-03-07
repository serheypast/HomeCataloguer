package ru.bsuir.lab2.sample.gui.loginWindow;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import ru.bsuir.lab2.sample.dataBase.authenticationDataBaseDriver.AuthenticationDataBaseDriver;
import ru.bsuir.lab2.sample.encrypt.EncryptPassword;
import ru.bsuir.lab2.sample.gui.mainWindow.MainWindow;
import ru.bsuir.lab2.sample.gui.registerWindow.RegisterWindow;
import ru.bsuir.lab2.sample.models.User;


/**
 * Created by Сергей on 05.03.2017.
 */
public class LoginWindowController {

    /**
     * Set action in Buttuon Sign In
     * - check correct input data
     * - if data is correctly set MainWidnod scene
     * @param loginWindow object LoginWidnow for set some Actions
     */
    public static void setOnActionButtonSignIn(final LoginWindow loginWindow){
        loginWindow.buttonSignIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User user = AuthenticationDataBaseDriver.checkUser(
                        loginWindow.textField.getText(),
                        EncryptPassword.encryptString(loginWindow.passwordField.getText()));

                if(user != null){
                    loginWindow.stage.setScene((new MainWindow(loginWindow.stage,user)).getScene());
                }else{
                    final Text actiontarget = new Text();
                    loginWindow.grid.add(actiontarget, 1, 6);
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("User with that name or password does not exist!");
                }
            }
        });
    }

    /**
     *Set action in Buttuon Register
     * - set RegisterWindow scene
     * @param loginWindow object LoginWidnow for set some Actions
     */
    public static void setOnActionButtonRegister(final LoginWindow loginWindow){

        loginWindow.buttonRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginWindow.stage.setScene((new RegisterWindow(loginWindow.stage)).getScene());
            }
        });
    }

    /**
     * *Set action in Buttuon sign how a Guest
     * - set some Limitations in next scene
     * - set MainWindow scene with some limitations
     * @param loginWindow object LoginWidnow for set some Actions
     */
    public static void setOnActionButtonGuest(final LoginWindow loginWindow){

        loginWindow.buttonGuest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User user = new User("Guest","",0);
                loginWindow.stage.setScene((new MainWindow(loginWindow.stage,user)).getScene());

            }
        });
    }

}
