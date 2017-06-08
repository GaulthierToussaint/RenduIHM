package fr.polytech.ihm.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Magasin {

    private StringProperty nom;
    private StringProperty adresse;
    private StringProperty horaire;
    private StringProperty tel;
    private StringProperty email;

    public Magasin(String nom, String adresse, String horaire, String tel, String email){
        this.nom = new SimpleStringProperty(nom);
        this.adresse = new SimpleStringProperty(adresse);
        this.horaire = new SimpleStringProperty(horaire);
        this.tel = new SimpleStringProperty(tel);
        this.email = new SimpleStringProperty(email);
    }

    public String getNom(){
        return nom.get();
    }

    public String getAdresse(){
        return adresse.get();
    }

    public String getHoraire(){
        return horaire.get();
    }

    public String getTel(){
        return tel.get();
    }

    public String getEmail(){
        return email.get();
    }
}
