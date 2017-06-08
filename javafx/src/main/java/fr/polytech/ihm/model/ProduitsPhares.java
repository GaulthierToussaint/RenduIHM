package fr.polytech.ihm.model;

/**
 * Created by user on 14/03/2017.
 */
public class ProduitsPhares {

    private String srcImage ;
    private String labelTitleFr;
    private String labelDescriFr;
    private String labelTitleEn;
    private String labelDescriEn;

    public ProduitsPhares(String srcImage, String labelTitleFr, String labelDescriFr, String labelTitleEn, String labelDescriEn) {
        this.srcImage = srcImage;
        this.labelTitleFr = labelTitleFr;
        this.labelDescriFr = labelDescriFr;
        this.labelTitleEn = labelTitleEn;
        this.labelDescriEn = labelDescriEn;
    }

    public String getSrcImage() {
        return srcImage;
    }

    public String getLabelTitleFr() {
        return labelTitleFr;
    }

    public String getLabelDescriFr() {
        return labelDescriFr;
    }

    public String getLabelTitleEn() {
        return labelTitleEn;
    }

    public String getLabelDescriEn() {
        return labelDescriEn;
    }

    public String getLabelDescri(String language) {
        if(language.equals("french")){
            return getLabelDescriFr();
        }
        if(language.equals("english")){
            return getLabelDescriEn();
        }
        return getLabelDescriFr();
    }

    public String getLabelTitle(String language) {
        if(language.equals("french")){
            return getLabelTitleFr();
        }
        if(language.equals("english")){
            return getLabelTitleEn();
        }
        return getLabelTitleFr();
    }

}
