package fr.polytech.ihm.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.web.WebView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class DescriController {

    @FXML
    private WebView chartShopOpening;

    @FXML
    private LineChart chartSales;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private NumberAxis xAxis;

    @FXML
    public void initialize() throws FileNotFoundException {
        final URL urlShopOpening = getClass().getResource("/html/GooglePie.html");
        chartShopOpening.getEngine().load(urlShopOpening.toExternalForm());

        String contentOfJSON = new Scanner(new File(System.getProperty("user.dir")+"/signboard/src/main/resources/json/CA.json")).useDelimiter("\\Z").next();
        JSONObject jsonObject = new JSONObject(contentOfJSON);
        JSONArray listItems = jsonObject.getJSONArray("liste");

        xAxis.setLabel("Années");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(listItems.getJSONObject(0).getInt("année"));
        xAxis.setUpperBound(listItems.getJSONObject(listItems.length()-1).getInt("année"));
        xAxis.setTickUnit(1);

        chartSales.setTitle("Chiffre d'affaires en France");
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Millions d'€");

        for(int i = 0 ; i<listItems.length() ; i++){
            series.getData().add(new XYChart.Data<>(listItems.getJSONObject(i).getInt("année"),listItems.getJSONObject(i).getInt("CA") ));
        }

        chartSales.getData().add(series);
    }
}
