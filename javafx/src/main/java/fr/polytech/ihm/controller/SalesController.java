package fr.polytech.ihm.controller;

import fr.polytech.ihm.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SalesController {

    @FXML
    private ListView<Item> listView;

    @FXML
    private BorderPane mainBorderPane;

    private ObservableList<Item> myObservableList;

    private List<Item> myList;

    private boolean isFilteringBook = false;
    private boolean isFilteringDvd = false;
    private boolean isFilteringHighTech = false;

    @FXML
    public void triPromotion(){
        int compteurItemVisible = 0;
        boolean tab_en_ordre = false;
        for(int i = 0 ; i<myObservableList.size() ; i++){
            if(myObservableList.get(i).isVisible() == true){
                compteurItemVisible++;
            }
        }

        while(!tab_en_ordre){
            tab_en_ordre = true;
            for(int i=0 ; i < compteurItemVisible-1 ; i++) {
                if(Integer.valueOf(myObservableList.get(i).getSrcPromo().substring(17,18)) < Integer.valueOf(myObservableList.get(i+1).getSrcPromo().substring(17,18))) {
                    Item item = myObservableList.get(i);
                    myObservableList.remove(i);
                    myObservableList.add(i+1,item);
                    tab_en_ordre = false;
                }
            }
            compteurItemVisible--;
        }
        listView.setItems(myObservableList);
        setCellfactory();
    }

    @FXML
    public void triPrix(){
        int compteurItemVisible = 0;
        boolean tab_en_ordre = false;
        for(int i = 0 ; i<myObservableList.size() ; i++){
            if(myObservableList.get(i).isVisible() == true){
                compteurItemVisible++;
            }
        }

        while(!tab_en_ordre){
            tab_en_ordre = true;
            for(int i=0 ; i < compteurItemVisible-1 ; i++) {
                double prix1 = Double.valueOf(myObservableList.get(i).getNewPrice().substring(0,4));
                double prix2 = Double.valueOf(myObservableList.get(i+1).getNewPrice().substring(0,4));
                if(prix1 > prix2) {
                    Item item = myObservableList.get(i);
                    myObservableList.remove(i);
                    myObservableList.add(i+1,item);
                    tab_en_ordre = false;
                }
            }
            compteurItemVisible--;
        }
        listView.setItems(myObservableList);
        setCellfactory();
    }

    @FXML
    public void initialize() throws IOException {
        initializeList();
        listView.setItems(myObservableList);
        setCellfactory();
        mainBorderPane.setCenter(listView);

    }

    private void initializeList() throws FileNotFoundException {
        myList = new ArrayList<>();
        String contentOfJSON = new Scanner(new File(System.getProperty("user.dir")+"/signboard/src/main/resources/json/items.json")).useDelimiter("\\Z").next();
        JSONObject jsonObject = new JSONObject(contentOfJSON);
        JSONArray listItems = jsonObject.getJSONArray("liste");
        for(int i = 0 ; i<listItems.length() ; i++){
            JSONObject obj = listItems.getJSONObject(i);
            Item item = new Item(obj.getString("srcImage"),obj.getString("srcPromo"),obj.getString("labelTitle"),obj.getString("labelDescri"),obj.getString("oldPrice"),obj.getString("newPrice"),obj.getString("categorie"));
            myList.add(item);
        }
        listView = new ListView<>();
        myObservableList = FXCollections.observableList(myList);
    }

    @FXML
    public void filtreLivres() throws FileNotFoundException {
        if(!isFilteringBook){
            isFilteringBook = true;
        }
        else{
            isFilteringBook = false;
        }
        filtre(isFilteringBook,isFilteringDvd,isFilteringHighTech);
    }

    @FXML
    public void filtreDvds(){
        if(!isFilteringDvd){
            isFilteringDvd = true;
        }
        else{
            isFilteringDvd = false;
        }
        filtre(isFilteringBook,isFilteringDvd,isFilteringHighTech);
    }

    @FXML
    public void filtreHighTech(){
        if(!isFilteringHighTech){
            isFilteringHighTech = true;
        }
        else{
            isFilteringHighTech = false;
        }
        filtre(isFilteringBook,isFilteringDvd,isFilteringHighTech);
    }

    @FXML void filtre(boolean filtreLivre, boolean filtreDvd, boolean filtreHighTech){
        if(!filtreLivre && !filtreDvd && !filtreHighTech){
            displayAll();
            listView.setItems(myObservableList);
            setCellfactory();
            return;
        }
        for(int i = 0 ; i<myObservableList.size() ; i++){
            myObservableList.get(i).setVisible(false);
            if(filtreLivre){
                if(myObservableList.get(i).getCategorie().equals("livre")){
                    myObservableList.get(i).setVisible(true);
                }
            }
            if(filtreDvd){
                if(myObservableList.get(i).getCategorie().equals("dvd")){
                    myObservableList.get(i).setVisible(true);
                }
            }
            if(filtreHighTech){
                if(myObservableList.get(i).getCategorie().equals("highTech")){
                    myObservableList.get(i).setVisible(true);
                }
            }
        }

        for(int i = 0 ; i<myObservableList.size() ; i++){
            if(myObservableList.get(i).isVisible()){
                Item item = myObservableList.get(i);
                myObservableList.remove(i);
                myObservableList.add(0,item);
            }
        }

        listView.setItems(myObservableList);
        setCellfactory();
    }


    private void setCellfactory(){
        listView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>(){
            @Override
            public ListCell<Item> call(ListView<Item> p) {

                ListCell<Item> cell = new ListCell<Item>(){

                    @Override
                    protected void updateItem(Item t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            HBox hbox = new HBox();
                            HBox hbox2 = new HBox();
                            HBox hbox3 = new HBox();

                            hbox2.setAlignment(Pos.CENTER_LEFT);
                            hbox2.setPrefWidth(700);
                            hbox2.setPrefHeight(135.0);
                            hbox3.setAlignment(Pos.CENTER_RIGHT);

                            ImageView imageViewPromo = new ImageView();
                            ImageView imageViewSrc = new ImageView();
                            Image imagePromo = new Image(t.getSrcPromo());
                            Image imageSrc = new Image(t.getSrcImage());
                            imageViewPromo.setImage(imagePromo);
                            imageViewSrc.setImage(imageSrc);
                            imageViewPromo.setFitHeight(87.5);
                            imageViewPromo.setFitWidth(125.0);
                            imageViewSrc.setFitHeight(87.5);
                            imageViewSrc.setFitWidth(125.0);
                            imageViewSrc.setTranslateX(30);
                            imageViewPromo.setTranslateX(30.0);

                            Text labelTitle = new Text();
                            Text labelDescri = new Text();
                            labelTitle.setText(t.getLabelTitle());
                            labelDescri.setText(t.getLabelDescri());
                            labelTitle.setTranslateX(0.0);
                            labelDescri.setTranslateX(5.0);
                            labelTitle.setStyle("-fx-font-weight: bold");

                            Text oldPrice = new Text();
                            Text newPrice = new Text();
                            oldPrice.setText(t.getOldPrice());
                            newPrice.setText(t.getNewPrice());
                            oldPrice.setTranslateX(0);
                            newPrice.setTranslateX(5);
                            oldPrice.setFill(Color.RED);
                            newPrice.setFill(Color.GREEN);
                            newPrice.setStyle("-fx-font-size: 18px ;-fx-font-weight: bold");
                            oldPrice.setStrikethrough(true);

                            hbox2.setTranslateX(50.0);

                            hbox.getChildren().add(imageViewSrc);
                            hbox.getChildren().add(imageViewPromo);
                            hbox2.getChildren().add(labelTitle);
                            hbox2.getChildren().add(labelDescri);
                            hbox.getChildren().add(hbox2);
                            hbox3.getChildren().add(oldPrice);
                            hbox3.getChildren().add(newPrice);
                            hbox.getChildren().add(hbox3);

                            hbox.setAlignment(Pos.CENTER_LEFT);
                            hbox.setPrefHeight(135.0);
                            hbox.setPrefWidth(1000.0);

                            if(t.isVisible()){
                                setGraphic(hbox);
                            }
                        }
                    }

                };
                return cell;
            }
        });
    }

    private void displayAll(){
        for(int i = 0 ; i<myObservableList.size() ; i++){
            myObservableList.get(i).setVisible(true);
        }
    }

}
