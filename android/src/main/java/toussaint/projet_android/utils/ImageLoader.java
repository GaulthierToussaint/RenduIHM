package toussaint.projet_android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoader extends AsyncTask<Void, Void, Bitmap> {

    private String URL;
    private Context context;
    private ImageView imageView;

    public ImageLoader(ImageView img, Context context, String Url) {
        URL = Url;
        ImageLoader.this.context = context;
        imageView = img;
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected Bitmap doInBackground(Void... arg0) {

        int path = context.getResources().getIdentifier(URL, "drawable", "toussaint.projet_android");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap source = BitmapFactory.decodeResource(context.getResources(), path, options);
        return source;
    }

    @Override
    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
    }
}