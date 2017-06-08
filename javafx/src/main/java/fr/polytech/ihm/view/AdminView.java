package fr.polytech.ihm.view;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminView extends Application {

    private static final String fxmlFileMenu = "/fxml/menu.fxml";
    private static final String fxmlFileAdmin = "/fxml/admin.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {

        SvgImageLoaderFactory.install();

        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource(fxmlFileAdmin));

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource(fxmlFileMenu));
        BorderPane root = new BorderPane();
        root.setLeft(menuLoader.load());
        root.setCenter(homeLoader.load());
        Scene scene = new Scene(root,1366, 716);
        scene.getStylesheets().add("/styles/styles.css");
        primaryStage.setTitle("To Be or to Have");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
