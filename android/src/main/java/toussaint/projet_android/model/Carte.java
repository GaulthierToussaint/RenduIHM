package toussaint.projet_android.model;

public class Carte {

    private String valeur;
    private String path;

    public Carte(String val, String pa){
        valeur = val;
        path = pa;
    }

    public String getValeur(){
        return valeur;
    }

    public String getPath(){
        return path;
    }
}
