package toussaint.projet_android.adapter;

import android.content.Context;
import android.graphics.Color;
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
import toussaint.projet_android.model.Parrainage;
import toussaint.projet_android.utils.Utils;

public class CustomAdapterParrainage extends android.widget.ArrayAdapter<Parrainage>{

    public CustomAdapterParrainage(@NonNull Context context, @LayoutRes int resource, @NonNull List<Parrainage> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.adapter_parrainage,null);
        }
        if(Utils.getCurrentTheme().equals("night")){
            RelativeLayout relativeLayout = (RelativeLayout)convertView.findViewById(R.id.relativeLayout);
            relativeLayout.setBackgroundColor(Color.BLACK);
        }
        Parrainage parrainage = getItem(position);

        TextView labelContext = (TextView) convertView.findViewById(R.id.labelContext);
        TextView labelReduc = (TextView) convertView.findViewById(R.id.labelReduc);
        labelContext.setText(parrainage.getContext());
        labelReduc.setText(parrainage.getReduction());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        Context context = imageView.getContext();

        int id;
        if(Utils.getCurrentTheme().equals("night")){
            id = context.getResources().getIdentifier(parrainage.getPath()+"_night", "drawable", context.getPackageName());
        }
        else{
            id = context.getResources().getIdentifier(parrainage.getPath(), "drawable", context.getPackageName());
        }

        imageView.setImageResource(id);

        return convertView;
    }
}
