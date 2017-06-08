package toussaint.projet_android.model;

public class Parrainage {

    private String context;
    private String path;
    private String reduction;

    public Parrainage(String con, String pa, String reduc){
        context = con;
        path = pa;
        reduction = reduc;
    }

    public String getContext() {
        return context;
    }

    public String getReduction() {
        return reduction;
    }

    public String getPath(){
        return path;
    }
}
