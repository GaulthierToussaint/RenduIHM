package toussaint.projet_android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import toussaint.projet_android.R;
import toussaint.projet_android.adapter.CustomAdapterProduit;
import toussaint.projet_android.model.Produit;
import toussaint.projet_android.utils.Utils;

public class CompareFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private GridView grid;
    private List<Produit> listTampon = new ArrayList<>();
    private Spinner spinnerRegion;

    public CompareFragment(){}

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public CompareFragment newInstance(int sectionNumber) {
        CompareFragment fragment = new CompareFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_compare, container, false);
        spinnerRegion = (Spinner) rootView.findViewById(R.id.spinnerTri);

        final List<Produit> list = new ArrayList<>();

        InputStream is;
        try {
            is = getActivity().getAssets().open("articles.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String myJson = new String(buffer, "UTF-8");
            JSONObject fichier = new JSONObject(myJson);
            JSONArray articles = fichier.getJSONArray("articles");
            for(int i = 0 ; i<articles.length() ; i++){
                JSONObject art = articles.getJSONObject(i);
                list.add(new Produit(art.getString("srcImage"),art.getString("titre"),art.getString("price"),art.getString("categorie")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        spinnerRegion.setBackgroundResource(R.drawable.spinner);
        spinnerRegion.setSelection(0,false);
        grid = (GridView)rootView.findViewById(R.id.gridViewProduit);
        listTampon.clear();
        listTampon.addAll(list);
        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String valueTri = parent.getItemAtPosition(pos).toString();
                switch (valueTri){
                    case "Prix croissant":
                        for(int j = 0 ; j<listTampon.size() ; j++){
                            for(int i = 0 ; i<listTampon.size()-1 ; i++){
                                if(Integer.parseInt(listTampon.get(i).getPrice())>Integer.parseInt(listTampon.get(i+1).getPrice())){
                                    Collections.swap(listTampon,i, i+1);
                                }
                            }
                        }
                        ListAdapter adapterProduit = new CustomAdapterProduit(parent.getContext(),android.R.layout.simple_list_item_1,listTampon);
                        grid.setAdapter(adapterProduit);
                        break;
                    case "Prix décroissant":
                        for(int j = 0 ; j<listTampon.size() ; j++){
                            for(int i = 0 ; i<listTampon.size()-1 ; i++){
                                if(Integer.parseInt(listTampon.get(i).getPrice())<Integer.parseInt(listTampon.get(i+1).getPrice())){
                                    Collections.swap(listTampon,i, i+1);
                                }
                            }
                        }
                        adapterProduit = new CustomAdapterProduit(parent.getContext(),android.R.layout.simple_list_item_1,listTampon);
                        grid.setAdapter(adapterProduit);
                        break;
                    case "Promotion":
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.array_tri, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerRegion.setAdapter(adapter);

        Spinner spinnerCategorie = (Spinner) rootView.findViewById(R.id.spinnerCategorie);
        spinnerCategorie.setBackgroundResource(R.drawable.spinner);
        spinnerCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String value = parent.getItemAtPosition(pos).toString();
                if(!value.equals("Tout")){
                    listTampon.clear();
                    listTampon.addAll(list);
                    for(int j = listTampon.size()-1 ; j>=0 ; j--){
                        if(!listTampon.get(j).getCategorie().equals(value)){
                            listTampon.remove(j);
                        }
                    }
                    ListAdapter adapterProduit = new CustomAdapterProduit(parent.getContext(),android.R.layout.simple_list_item_1,listTampon);
                    grid.setAdapter(adapterProduit);
                    spinnerRegion.setSelection(0);
                }
                else{
                    listTampon.clear();
                    listTampon.addAll(list);
                    ListAdapter adapterProduit = new CustomAdapterProduit(parent.getContext(),android.R.layout.simple_list_item_1,listTampon);
                    grid.setAdapter(adapterProduit);
                    spinnerRegion.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        ArrayAdapter<CharSequence> adapterCategorie = ArrayAdapter.createFromResource(this.getContext(),
                R.array.array_categorie, android.R.layout.simple_spinner_item);
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerCategorie.setAdapter(adapterCategorie);

        if(Utils.getCurrentTheme().equals("night")){
            spinnerRegion.setBackgroundResource(R.drawable.spinner_dark);
            spinnerCategorie.setBackgroundResource(R.drawable.spinner_dark);
        }

        ListAdapter adapterProduit = new CustomAdapterProduit(this.getContext(),android.R.layout.simple_list_item_1,list);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridViewProduit);
        gridView.setAdapter(adapterProduit);

        return rootView;
    }
}
