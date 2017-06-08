package toussaint.projet_android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import toussaint.projet_android.R;
import toussaint.projet_android.model.Carte;
import toussaint.projet_android.utils.Utils;

public class HomeFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView listTwitter;

    public HomeFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        TextView valuePoints = (TextView) rootView.findViewById(R.id.valuePoints);
        TextView valuePromo = (TextView) rootView.findViewById(R.id.valuePromo);
        TextView connectionValue = (TextView) rootView.findViewById(R.id.connectionValue);
        TextView valueSolde = (TextView) rootView.findViewById(R.id.valueSolde);


        InputStream is;
        try {
            is = getActivity().getAssets().open("compte.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String myJson = new String(buffer, "UTF-8");
            JSONObject fichier = new JSONObject(myJson);
            connectionValue.setText(fichier.getString("identifiant"));
            valuePoints.setText(fichier.getString("points"));
            valuePromo.setText(fichier.getString("parrainage"));
            if (Utils.getSolde() == 0 ){
                Utils.setSolde(Integer.parseInt(fichier.getString("solde").substring(0,2)));
            }
            valueSolde.setText(Utils.getSolde()+" €");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int colorRouge = Color.parseColor("#ff0000");
        int colorJaune = Color.parseColor("#E7AD0F");

        if(Utils.getCurrentTheme().equals("default")){
            connectionValue.setTextColor(colorRouge);
            valuePoints.setTextColor(colorRouge);
            valuePromo.setTextColor(colorRouge);
            valueSolde.setTextColor(colorRouge);
        }
        else if(Utils.getCurrentTheme().equals("night")){
            connectionValue.setTextColor(colorJaune);
            valuePoints.setTextColor(colorJaune);
            valuePromo.setTextColor(colorJaune);
            valueSolde.setTextColor(colorJaune);
        }

        final UserTimeline userTimeline = new UserTimeline.Builder().screenName("Fnac").build();
        final TweetTimelineListAdapter adapter;

        if(Utils.getCurrentTheme().equals("night")){
            adapter = new TweetTimelineListAdapter.Builder(this.getContext()).setViewStyle(R.style.tw__TweetDarkStyle).setTimeline(userTimeline).build();
        }
        else {
            adapter = new TweetTimelineListAdapter.Builder(this.getContext()).setTimeline(userTimeline).build();
        }

        listTwitter = (ListView)rootView.findViewById(R.id.listTwitter);
        listTwitter.setAdapter(adapter);

        return rootView;
    }
}
