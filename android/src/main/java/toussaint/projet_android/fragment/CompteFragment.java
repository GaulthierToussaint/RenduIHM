package toussaint.projet_android.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import toussaint.projet_android.R;
import toussaint.projet_android.adapter.CustomAdapterParrainage;
import toussaint.projet_android.model.Parrainage;
import toussaint.projet_android.model.Produit;
import toussaint.projet_android.utils.Utils;

public class CompteFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public CompteFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public CompteFragment newInstance(int sectionNumber) {
        CompteFragment fragment = new CompteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_compte, container, false);
        CheckBox checkBox = (CheckBox)rootView.findViewById(R.id.checkBox);
        Switch switchButton = (Switch)rootView.findViewById(R.id.switch1);
        TextView textViewConnectionValue = (TextView)rootView.findViewById(R.id.textViewConnectionValue);
        TextView textViewPoints = (TextView)rootView.findViewById(R.id.textViewPoints);
        TextView textView3 = (TextView)rootView.findViewById(R.id.textView3);
        TextView textSolde = (TextView)rootView.findViewById(R.id.textSolde);

        InputStream is;
        try {
            is = getActivity().getAssets().open("compte.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String myJson = new String(buffer, "UTF-8");
            JSONObject fichier = new JSONObject(myJson);
            textViewConnectionValue.setText(fichier.getString("identifiant"));
            textViewPoints.setText(fichier.getString("points"));
            textView3.setText(fichier.getString("parrainage"));
            if (Utils.getSolde() == 0 ){
                Utils.setSolde(Integer.parseInt(fichier.getString("solde").substring(0,2)));
            }
            textSolde.setText(Utils.getSolde()+" €");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(Utils.getChangeAutomatic()){
            switchButton.setChecked(true);
        }
        else{
            switchButton.setChecked(false);
        }

        if(Utils.getNotification()){
            checkBox.setChecked(true);
        }
        else{
            checkBox.setChecked(false);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Utils.setNotification(true);
                }
                else{
                    Utils.setNotification(false);
                }
            }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == false){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getString(R.string.choix_theme));
                    builder.setPositiveButton("Par défaut", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if(!Utils.getCurrentTheme().equals("default")){
                                Utils.changeToTheme(getActivity(), Utils.THEME_DEFAULT);
                                Utils.setCurrentThemeDefault();
                            }
                            Utils.setChangeAutomatic(false);

                        }
                    });
                    builder.setNegativeButton("Mode nuit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if(!Utils.getCurrentTheme().equals("night")){
                                Utils.setCurrentThemeNight();
                                Utils.changeToTheme(getActivity(), Utils.THEME_BLACK);
                            }
                            Utils.setChangeAutomatic(false);
                        }
                    });
                    builder.show();
                }
                else{
                    Utils.setChangeAutomatic(true);
                }
            }
        });

        if(Utils.getCurrentTheme().equals("night")){
            TextView textViewConnection = (TextView)rootView.findViewById(R.id.textViewConnectionValue) ;
            TextView textViewParrainage = (TextView)rootView.findViewById(R.id.textView3);


            int colorJaune = Color.parseColor("#E7AD0F");
            int colorGris = Color.parseColor("#1E1E1E");

            checkBox.setBackgroundColor(colorGris);
            switchButton.setBackgroundColor(colorGris);
            textViewParrainage.setTextColor(colorJaune);
            textViewPoints.setTextColor(colorJaune);
            textViewConnection.setTextColor(colorJaune);
            textSolde.setTextColor(colorJaune);

        }

        Button buttonParrain = (Button)rootView.findViewById(R.id.buttonParrain);
        Button buttonDeconnction = (Button)rootView.findViewById(R.id.buttonDeconnction);
        buttonDeconnction.setOnClickListener(onClickListener);
        buttonParrain.setOnClickListener(onClickListener);
        checkBox.setOnClickListener(onClickListener);

        Button buttonInvite = (Button)rootView.findViewById(R.id.buttonInvite);
        buttonInvite.setOnClickListener(onClickListener);

        return rootView;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(final View v) {
            switch(v.getId()){
                case R.id.buttonParrain:
                    GridView gridView = new GridView(v.getContext());
                    List<Parrainage> list = new ArrayList<>();

                    InputStream is;
                    try {
                        is = getActivity().getAssets().open("parrainage.json");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        String myJson = new String(buffer, "UTF-8");
                        JSONObject fichier = new JSONObject(myJson);
                        JSONArray parrainages = fichier.getJSONArray("parrainages");
                        for(int i = 0 ; i<parrainages.length() ; i++){
                            JSONObject par = parrainages.getJSONObject(i);
                            list.add(new Parrainage(par.getString("context"),par.getString("path"),par.getString("reduction")));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ListAdapter adapter = new CustomAdapterParrainage(v.getContext(),android.R.layout.simple_list_item_1,list);
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}
                    });
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(gridView);
                    builder.setTitle("Programme de parrainage");
                    builder.setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {} });
                    builder.show();
                    break;
                case R.id.checkBox:
                    CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
                    if(!checkBox.isChecked()){
                        builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle(getString(R.string.choix_notification));
                        builder.setNegativeButton("J'ai compris", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {}
                        });
                        builder.show();
                    }
                    break;
                case R.id.buttonDeconnction:
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(v.getContext());
                    builder2.setTitle("Se connecter");

                    builder2.setView(R.layout.connexion);

                    builder2.setPositiveButton("Connexion", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    builder2.show();
                    break;
                case R.id.buttonInvite:
                    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.notif_mail_1));
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.notif_mail_2));

                    Intent chooserIntent = Intent.createChooser(shareIntent, "Envoyer via");
                    chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(chooserIntent);
                    break;
            }

        }
    };
}
