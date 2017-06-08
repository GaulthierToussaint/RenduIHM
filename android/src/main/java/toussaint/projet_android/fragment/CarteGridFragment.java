package toussaint.projet_android.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import toussaint.projet_android.adapter.CustomAdapterCarte;
import toussaint.projet_android.R;
import toussaint.projet_android.model.Carte;
import toussaint.projet_android.utils.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CarteGridFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private View rootView;

    public CarteGridFragment(){
    }

    public CarteGridFragment newInstance(int sectionNumber) {
        CarteGridFragment fragment = new CarteGridFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_carte, container, false);

        List<Carte> list = new ArrayList<>();

        InputStream is;
        try {
            is = getActivity().getAssets().open("cartes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String myJson = new String(buffer, "UTF-8");
            JSONObject fichier = new JSONObject(myJson);
            JSONArray cartes = fichier.getJSONArray("cartes");
            for(int i = 0 ; i<cartes.length() ; i++){
                JSONObject cart = cartes.getJSONObject(i);
                list.add(new Carte(cart.getString("val"),cart.getString("path")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new CustomAdapterCarte(this.getContext(),android.R.layout.simple_list_item_1,list);

        GridView gridView = (GridView) rootView.findViewById(R.id.section_label_grid_view);
        gridView.setAdapter(adapter);

        Button button = (Button)rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                QrScanner();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }
    public void QrScanner(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity()).forSupportFragment(CarteGridFragment.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt(getString(R.string.text_scanner));
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.affiche_qrcode));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

        int value = Utils.getSolde();
        value = value + 10;
        Utils.setSolde(value);

    }


}
