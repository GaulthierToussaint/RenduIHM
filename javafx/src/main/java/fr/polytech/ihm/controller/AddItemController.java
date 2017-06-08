package fr.polytech.ihm.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AddItemController {

    @FXML
    private TextField srcImage;

    @FXML
    private TextField srcPromo;

    @FXML
    private TextField title;

    @FXML
    private TextField descri;

    @FXML
    private TextField ancienPrix;

    @FXML
    private TextField newPrix;

    @FXML
    private TextField categorie;

    @FXML
    private Text erreur;

    @FXML
    public void addItem(Event evt) throws IOException {
        if(!srcImage.getText().isEmpty() && !srcPromo.getText().isEmpty() && !title.getText().isEmpty() && !descri.getText().isEmpty() && !ancienPrix.getText().isEmpty() && !newPrix.getText().isEmpty() && !categorie.getText().isEmpty()){
            String contentOfJSON = new Scanner(new File(System.getProperty("user.dir")+"/signboard/src/main/resources/json/items.json")).useDelimiter("\\Z").next();
            JSONObject total = new JSONObject(contentOfJSON);
            JSONObject object = new JSONObject();
            object.put("srcImage",srcImage.getText());
            object.put("srcPromo",srcPromo.getText());
            object.put("labelTitle",title.getText());
            object.put("labelDescri",descri.getText());
            object.put("oldPrice",ancienPrix.getText());
            object.put("newPrice",newPrix.getText());
            object.put("categorie",categorie.getText());
            total.getJSONArray("liste").put(object);
            FileWriter writer = new FileWriter(System.getProperty("user.dir")+"/signboard/src/main/resources/json/items.json");

            writer.write(total.toString(4));
            writer.close();

            Node source = (Node)  evt.getSource();
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        }
        else{
            erreur.setText("Veuillez remplir tous les champs");
            erreur.setFill(Color.RED);
        }
    }
}
