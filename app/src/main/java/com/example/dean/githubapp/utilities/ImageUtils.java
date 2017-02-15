package com.example.dean.githubapp.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUtils {

    private static final String LOG_TAG = ImageUtils.class.getSimpleName();

    private ImageUtils() {

    }

    private static Bitmap getImage(String url) {
        URL imageUrl = null;

        try {
            imageUrl = new URL(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem with image URL", e);
        }

        Bitmap image = null;
        try {
            image = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with decoding image", e);
        }

        return image;
    }

    public static class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        ImageView image;

        public DownloadImageAsyncTask(ImageView imageView) {
            image = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            return getImage(urls[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
        }
    }
}
