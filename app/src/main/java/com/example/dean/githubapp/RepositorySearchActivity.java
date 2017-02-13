package com.example.dean.githubapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dean.githubapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class RepositorySearchActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;
    private Button mSearchButton;
    private TextView mDisplayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_search);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_query);
        mSearchButton = (Button) findViewById(R.id.btn_search);
        mDisplayTextView = (TextView) findViewById(R.id.tv_response);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = mSearchBoxEditText.getText().toString();
                URL url = NetworkUtils.buildUrl(query, "stars");

                new FetchDataAsyncTask().execute(url);
            }
        });
    }

    private class FetchDataAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL[] urls) {
            try {
                return NetworkUtils.makeHttpRequest(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            mDisplayTextView.setText(s);
        }
    }
}
