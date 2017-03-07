package ru.bsuir.lab2.sample.gui.mainWindow;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.bsuir.lab2.sample.models.*;


/**
 * Created by Сергей on 01.03.2017.
 */
public class MainWindow {
    protected Stage stage;
    protected Scene scene;
    protected AnchorPane anchorPane;
    protected Label homeLabel;
    protected Label searchLabel;
    protected ComboBox<String> comboBox;
    protected TableView<CatalogFile> tableView;
    protected Button buttonAdd;
    protected Button buttonDelete;
    protected TextField searchField;
    protected Button buttonSearch;
    protected Hyperlink hyperlinkInOut;
    protected User user;
    protected Spinner<Integer> spinner;
    protected Label pageLabel;
    protected Label userNameLabel;


    /**
     * Constructor that initialize and set action on field
     * @param stage  super Stage that allow swap scene
     * @param user allow use role system
     */
    public MainWindow(Stage stage,User user) {
        this.user = user;
        this.stage = stage;
        setScene();
    }

    /**
     * Get field scene
     * @return field scene that save information about window
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * The method that set field scene
     */
    private void setScene(){
        setAnchorPane();
        scene = new Scene(anchorPane,620,500);
    }

    /**
     * The method that set field anchorPane
     * and its main children
     */
    private void setAnchorPane() {
        anchorPane = new AnchorPane();
        setButtonAdd();
        setTreeTableView();
        setComboBox();
        setHomeLabel();
        setSearchLabel();
        setTextField();
        setButtonSearch();
        setHyperlink();
        setSpinner();
        setButtonDelete();
        setUserNameLabel();
        setPageLabel();
    }

    /**
     * The set some properties and actions for field(object Spinner<Integer>) spinner
     * and set in some position in anchorPane
     */
    private void setSpinner(){
        spinner = new Spinner<Integer>();
        spinner.setAccessibleText("Page: ");
        spinner.setLayoutX(509);
        spinner.setLayoutY(443);
        spinner.setPrefWidth(60);
        spinner.setPrefHeight(25);

        MainWindowController.setSpinnerValue(this);
        MainWindowController.setOnActionChangeSpinner(this);
        anchorPane.getChildren().add(spinner);
    }

    /**
     * The set some properties and actions for field(object button) buttonSearch
     *
     */
    private  void setButtonSearch() {
        buttonSearch = new Button("Search");
        buttonSearch.setLayoutY(31);
        buttonSearch.setLayoutX(434);
        MainWindowController.setOnSearchBotton(this);
        anchorPane.getChildren().add(buttonSearch);
    }

    /**
     * The set some properties for field(object TextField) searchField
     */
    private  void setTextField() {
        searchField = new TextField();
        searchField.setLayoutX(265);
        searchField.setLayoutY(31);

        anchorPane.getChildren().add(searchField);
    }

    /**
     * The set some properties for field(object Label) userNameLabel
     */
    private void setUserNameLabel(){
        userNameLabel = new Label(user.getLogin());
        if (user.getRole() == 1) {
            userNameLabel.setFont(new Font(20));
            userNameLabel.setTextFill(Color.DARKRED);
        }
        userNameLabel.setLayoutX(527);
        userNameLabel.setLayoutY(23);
        userNameLabel.setPrefWidth(74);
        userNameLabel.setPrefHeight(17);

        anchorPane.getChildren().add(userNameLabel);
    }

    /**
     * The set some properties for field(object Label) pageLabel
     */
    private void setPageLabel(){
        pageLabel = new Label("Page");
        pageLabel.setLayoutX(475);
        pageLabel.setLayoutY(447);
        pageLabel.setPrefWidth(34);
        pageLabel.setPrefHeight(17);

        anchorPane.getChildren().add(pageLabel);
    }

    /**
     * The set some properties for field(object Label) homeLabel
     */
    private  void setHomeLabel() {
        homeLabel = new Label("Home Cataloguer");
        homeLabel.setPrefHeight(57.0);
        homeLabel.setPrefWidth(149.0);
        homeLabel.setStyle("12");
        homeLabel.setFont(new Font("Bodoni MT Italic",19));
        homeLabel.setLayoutX(11);
        homeLabel.setLayoutY(11);
        anchorPane.getChildren().add(homeLabel);
    }

    /**
     * The set some properties for field(object Label) searchLabel
     */
    private  void setSearchLabel() {
        searchLabel = new Label("Search");
        searchLabel.setLayoutX(202);
        searchLabel.setLayoutY(35);
        searchLabel.setPrefWidth(47);
        searchLabel.setPrefHeight(17);

        anchorPane.getChildren().add(searchLabel);
    }

    /**
     * The set some properties and actions for field(object ComboBox) comboBox
     */
    private  void setComboBox() {
        comboBox = new ComboBox<String>();

        for(Files files : Files.values()) {
            comboBox.getItems().add(files.getName());
        }

        comboBox.setValue(Files.Music.getName());
        comboBox.setLayoutX(15.0);
        comboBox.setLayoutY(88.0);
        comboBox.setPrefHeight(25);
        comboBox.setPrefWidth(173);
        MainWindowController.setTable(this,1);
        MainWindowController.setOnActionChangeComboBoxValue(this);
        anchorPane.getChildren().add(comboBox);
    }

    /**
     * The set some properties and actions for field(object TableView) tableView
     */
    private  void setTreeTableView() {
        tableView = new TableView<CatalogFile>();
        tableView.setLayoutY(88);
        tableView.setLayoutX(202);
        tableView.setPrefHeight(333);
        tableView.setPrefWidth(400);

        TableColumn tableColumnId =  new TableColumn("№");
        tableColumnId.setPrefWidth(25);
        TableColumn tableColumnName =  new TableColumn("Name");
        tableColumnName.setPrefWidth(135);
        tableColumnName.setCellValueFactory(new PropertyValueFactory<CatalogFile,String>("name"));
        TableColumn tableColumnType =  new TableColumn("Type");
        tableColumnType.setPrefWidth(56);
        tableColumnType.setCellValueFactory(new PropertyValueFactory<CatalogFile,String>("type"));
        TableColumn tableColumnSize =  new TableColumn("Size(MB)");
        tableColumnSize.setPrefWidth(56);
        tableColumnSize.setCellValueFactory(new PropertyValueFactory<CatalogFile,Double>("size"));
        TableColumn tableColumnPath =  new TableColumn("Path to file");
        tableColumnPath.setCellValueFactory(new PropertyValueFactory<CatalogFile,Double>("path"));
        tableColumnPath.setPrefWidth(143);

        tableView.getColumns().addAll(
                tableColumnId,
                tableColumnName,
                tableColumnSize,
                tableColumnType,
                tableColumnPath);
        MainWindowController.setCatalogList();
        MainWindowController.setDoubleMouseClickActionTable(tableView);

        anchorPane.getChildren().add(tableView);
    }

    /**
     * The set some properties and actions for field(object button) buttonAdd
     */
    private  void setButtonAdd() {
        if(user.getRole() !=0) {
            buttonAdd = new Button("Add");
            buttonAdd.setLayoutY(430);
            buttonAdd.setLayoutX(202);
            buttonAdd.setPrefHeight(50);
            buttonAdd.setPrefWidth(94);
            MainWindowController.setOnActionButtonAdd(this);

            anchorPane.getChildren().add(buttonAdd);
        }
    }

    /**
     * The set some properties and actions for field(object hyperlink) hyperlinkOnOut
     */
    private  void setHyperlink() {

        hyperlinkInOut = new Hyperlink();
        if(user.getRole() == 0){
            hyperlinkInOut.setText("Sign In");
        }else{
            hyperlinkInOut.setText("Log out");
        }
        hyperlinkInOut.setLayoutX(525);
        hyperlinkInOut.setLayoutY(52);
        hyperlinkInOut.setPrefHeight(23);
        hyperlinkInOut.setPrefWidth(50);

        MainWindowController.setOnActionHyperLing(this);
        anchorPane.getChildren().add(hyperlinkInOut);
    }

    /**
     * The set some properties and actions for field(object button) buttonDelet
     */
    private void setButtonDelete() {
        if(user.getRole() !=0) {
            buttonDelete = new Button("Delete");
            buttonDelete.setLayoutY(430);
            buttonDelete.setLayoutX(340);
            buttonDelete.setPrefHeight(50);
            buttonDelete.setPrefWidth(94);

            MainWindowController.setOnActionDeleteButton(this);
            anchorPane.getChildren().add(buttonDelete);
        }
    }
}
