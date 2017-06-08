package toussaint.projet_android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import toussaint.projet_android.R;
import toussaint.projet_android.model.Produit;
import toussaint.projet_android.utils.ImageLoader;
import toussaint.projet_android.utils.Utils;

public class CustomAdapterProduit extends android.widget.ArrayAdapter<Produit>{

    public CustomAdapterProduit(@NonNull Context context, @LayoutRes int resource, @NonNull List<Produit> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Produit produit = getItem(position);
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_produit,null);
            holder = new ViewHolder();
            holder.image = (ImageView)convertView.findViewById(R.id.imageViewProduit);
            holder.textPrix = (TextView) convertView.findViewById(R.id.labelProduit);
            holder.textProduit = (TextView) convertView.findViewById(R.id.labelPrix);
            convertView.setTag(holder);
            if(Utils.getCurrentTheme().equals("night")){
                RelativeLayout relativeLayout = (RelativeLayout)convertView.findViewById(R.id.relativeLayout);
                relativeLayout.setBackgroundColor(Color.BLACK);
            }
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        ImageView img = (ImageView)convertView.findViewById(R.id.imageViewProduit);
        ImageLoader imageLoader = new ImageLoader(img,getContext(),produit.getSrcImage());
        imageLoader.execute();

        holder.textProduit.setText(produit.getTitre());
        holder.textPrix.setText(produit.getPrice()+" €");
        if(Utils.getCurrentTheme().equals("default")){
            holder.textPrix.setTextColor(Color.parseColor("#FF0000"));
        }
        else if(Utils.getCurrentTheme().equals("night")){
            holder.textPrix.setTextColor(Color.parseColor("#E7AD0F"));
        }
        holder.textPrix.setTypeface(null, Typeface.BOLD);

        return convertView;
    }

    static class ViewHolder {
        ImageView image;
        TextView textProduit;
        TextView textPrix;
    }
}
