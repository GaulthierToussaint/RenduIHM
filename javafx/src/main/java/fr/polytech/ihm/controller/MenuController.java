package fr.polytech.ihm.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuController {

    private static final String fxmlFileHome = "/fxml/home.fxml";
    private static final String fxmlFileMenu = "/fxml/menu.fxml";
    private static final String fxmlFileSale = "/fxml/promotions.fxml";
    private static final String fxmlFileContact = "/fxml/contact.fxml";
    private static final String fxmlFileDescr = "/fxml/descript_enseigne.fxml";
    private static final String fxmlFileGallery = "/fxml/gallery.fxml";

    @FXML
    private VBox menuBar ;

    @FXML
    public void home(Event event) throws IOException {

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();

        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource(fxmlFileHome));
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource(fxmlFileMenu));

        BorderPane root = new BorderPane();
        root.setCenter(homeLoader.load());
        root.setLeft(menuLoader.load());

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/styles.css");
        scene.getStylesheets().add("/styles/styleButtonHome.css");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void sales(Event event) throws Exception {

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();

        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource(fxmlFileSale));
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource(fxmlFileMenu));

        BorderPane root = new BorderPane();
        root.setCenter(homeLoader.load());
        root.setLeft(menuLoader.load());

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/styles.css");
        scene.getStylesheets().add("/styles/styleButtonSales.css");
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    public void map(Event event) throws IOException {

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();

        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource(fxmlFileContact));
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource(fxmlFileMenu));

        BorderPane root = new BorderPane();
        root.setCenter(homeLoader.load());
        root.setLeft(menuLoader.load());

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/styles.css");
        scene.getStylesheets().add("/styles/styleButtonContact.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void description(Event event) throws IOException {

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();

        FXMLLoader descriLoader = new FXMLLoader(getClass().getResource(fxmlFileDescr));
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource(fxmlFileMenu));

        BorderPane root = new BorderPane();
        root.setCenter(descriLoader.load());
        root.setLeft(menuLoader.load());

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/styles.css");
        scene.getStylesheets().add("/styles/styleButtonDescri.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void gallery(Event event) throws IOException {

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();

        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource(fxmlFileGallery));
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource(fxmlFileMenu));

        BorderPane root = new BorderPane();
        root.setCenter(homeLoader.load());
        root.setLeft(menuLoader.load());

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/styles.css");
        scene.getStylesheets().add("/styles/styleButtonGallery.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void exit() {
        menuBar.getScene().getWindow().hide();
    }

}
