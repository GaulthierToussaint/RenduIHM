package toussaint.projet_android.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import toussaint.projet_android.R;
import toussaint.projet_android.model.Carte;

public class CustomAdapterCarte extends android.widget.ArrayAdapter<Carte>{

    public CustomAdapterCarte(@NonNull Context context, @LayoutRes int resource, @NonNull List<Carte> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.adapter_carte,null);
        }
        Carte carte = getItem(position);

        TextView labelDate = (TextView) convertView.findViewById(R.id.labelContext);
        TextView labelCategorie = (TextView) convertView.findViewById(R.id.labelReduc);
        labelDate.setText("Carte Cadeau");
        labelCategorie.setText(carte.getValeur());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        int id;
        Context context = imageView.getContext();
        id = context.getResources().getIdentifier(carte.getPath(), "drawable", context.getPackageName());
        imageView.setImageResource(id);

        return convertView;
    }
}



