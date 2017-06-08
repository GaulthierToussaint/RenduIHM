package toussaint.projet_android.model;

public class Produit {

    private String srcImage;
    private String titre;
    private String price;
    private String categorie;

    public Produit(String srcImage, String titre, String  price, String categorie){
        this.srcImage = srcImage;
        this.titre = titre;
        this.price = price;
        this.categorie = categorie;
    }

    public String getSrcImage() {
        return srcImage;
    }

    public String getTitre() {
        return titre;
    }

    public String getPrice() {
        return price;
    }

    public String getCategorie() {
        return categorie;
    }
}
