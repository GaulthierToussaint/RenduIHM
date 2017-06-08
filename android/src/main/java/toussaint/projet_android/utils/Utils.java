package toussaint.projet_android.utils;

import android.app.Activity;
import android.content.Intent;
import toussaint.projet_android.R;

public class Utils {

    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_BLACK = 1;
    private static String currentTheme = "default";
    private static boolean changeAutomatic = true;
    private static boolean notification = true;
    private static int solde = 0;
    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme){
        sTheme = theme;
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        activity.finish();
    }
    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity){
        switch (sTheme){
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_BLACK:
                activity.setTheme(R.style.AppThemeNight);
                break;
        }
    }

    public static String getCurrentTheme(){
        return currentTheme;
    }

    public static void setCurrentThemeNight(){
        currentTheme = "night";
    }

    public static void setCurrentThemeDefault(){
        currentTheme = "default";
    }

    public static boolean getChangeAutomatic(){
        return changeAutomatic;
    }

    public static void setChangeAutomatic(boolean input){
        changeAutomatic = input;
    }

    public static boolean getNotification(){
        return notification;
    }

    public static void setNotification(boolean b){
        notification = b;
    }

    public static void setSolde(int i){solde = i;}

    public static int getSolde(){
        return solde;
    }

}