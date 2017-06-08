package fr.polytech.ihm.view;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class  HomeView extends Application {

    private static final String fxmlFileHome = "/fxml/home.fxml";
    private static final String fxmlFileMenu = "/fxml/menu.fxml";

    @FXML
    private Image img;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        SvgImageLoaderFactory.install();

        BorderPane root = new BorderPane();
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource(fxmlFileHome));
        root.setCenter(homeLoader.load());

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource(fxmlFileMenu));
        root.setLeft(menuLoader.load());

        Scene scene = new Scene(root, 1366, 716);
        scene.getStylesheets().add("/styles/styles.css");
        scene.getStylesheets().add("/styles/styleButtonHome.css");
        primaryStage.setTitle("To Be or to Have");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
