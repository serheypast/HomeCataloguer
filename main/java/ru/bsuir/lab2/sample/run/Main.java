package ru.bsuir.lab2.sample.run;



import javafx.application.Application;
import javafx.stage.Stage;
import ru.bsuir.lab2.sample.dataBase.DataBaseDriver;
import ru.bsuir.lab2.sample.gui.loginWindow.LoginWindow;


public class Main extends Application {
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        primaryStage.setTitle("Home Catologuer");
        DataBaseDriver.Conn();
        primaryStage.setScene((new LoginWindow(primaryStage)).getScene());
        primaryStage.show();

    }

    public static void main(String[] args)  {
        launch(args);
    }
}
