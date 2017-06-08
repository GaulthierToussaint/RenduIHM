package fr.polytech.ihm.controller;

import fr.polytech.ihm.model.ProduitsPhares;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GalleryController {

    private List<ProduitsPhares> listProduct;

    @FXML
    private ImageView imageGallery;
    private int cpt = 0;

    @FXML
    private Label titleGallery;

    @FXML
    private Label descriGallery;

    @FXML
    private MenuButton menuLanguage;

    private String currentLanguage;


    @FXML
    public void initialize() throws FileNotFoundException {
        currentLanguage = "french";
        menuLanguage.setStyle("-fx-background-image:url(images/langues/drapeauFrancePetit.png)");
        listProduct = new ArrayList<>();
        String contentOfJSON = new Scanner(new File(System.getProperty("user.dir")+"/signboard/src/main/resources/json/ProduitsPhares.json")).useDelimiter("\\Z").next();
        JSONObject jsonObject = new JSONObject(contentOfJSON);
        JSONArray listItems = jsonObject.getJSONArray("liste");
        for(int i = 0 ; i<listItems.length() ; i++){
            JSONObject obj = listItems.getJSONObject(i);
            ProduitsPhares item = new ProduitsPhares(obj.getString("srcImage"),obj.getString("labelTitleFr"),obj.getString("labelDescriFr"),obj.getString("labelTitleEn"),obj.getString("labelDescriEn"));
            listProduct.add(item);
        }
        imageGallery.setImage(new Image(listProduct.get(cpt).getSrcImage()));
        titleGallery.setText(listProduct.get(cpt).getLabelTitle(currentLanguage));
        descriGallery.setText(listProduct.get(cpt).getLabelDescri(currentLanguage));

    }

    @FXML
    public void nextImage() {
        cpt++;
        if (cpt == listProduct.size())
            cpt = 0;
        imageGallery.setImage(new Image(listProduct.get(cpt).getSrcImage()));
        titleGallery.setText(listProduct.get(cpt).getLabelTitle(currentLanguage));
        descriGallery.setText(listProduct.get(cpt).getLabelDescri(currentLanguage));
    }

    @FXML
    public void beforeImage() {
        cpt--;
        if (cpt < 0)
            cpt = listProduct.size() - 1;
        imageGallery.setImage(new Image(listProduct.get(cpt).getSrcImage()));
        titleGallery.setText(listProduct.get(cpt).getLabelTitle(currentLanguage));
        descriGallery.setText(listProduct.get(cpt).getLabelDescri(currentLanguage));
    }

    @FXML
    public void changeLanguage(Event evt){
        MenuItem item = (MenuItem) evt.getSource();
        if(item.getId().equals("french")){
            currentLanguage = "french";
            menuLanguage.setStyle("-fx-background-image:url(images/langues/drapeauFrancePetit.png)");
            imageGallery.setImage(new Image(listProduct.get(cpt).getSrcImage()));
            titleGallery.setText(listProduct.get(cpt).getLabelTitle(currentLanguage));
            descriGallery.setText(listProduct.get(cpt).getLabelDescri(currentLanguage));
        }
        if(item.getId().equals("english")){
            currentLanguage = "english";
            menuLanguage.setStyle("-fx-background-image:url(images/langues/drapeauRUPetit.png)");
            imageGallery.setImage(new Image(listProduct.get(cpt).getSrcImage()));
            titleGallery.setText(listProduct.get(cpt).getLabelTitle(currentLanguage));
            descriGallery.setText(listProduct.get(cpt).getLabelDescri(currentLanguage));
        }
        currentLanguage = item.getId();
    }
}



