package com.example.dean.githubapp;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dean.githubapp.data.Repository;
import com.example.dean.githubapp.utilities.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RepositoryDetailActivity extends AppCompatActivity {

    private TextView mOwnerTextView;
    private TextView mDescriptionTextView;
    private TextView mLanguageTextView;
    private TextView mDateCreatedTextView;
    private TextView mDateModifiedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_detail);

        mOwnerTextView = (TextView) findViewById(R.id.tv_owner_name);
        mDescriptionTextView = (TextView) findViewById(R.id.tv_description);
        mLanguageTextView = (TextView) findViewById(R.id.tv_language);
        mDateCreatedTextView = (TextView) findViewById(R.id.tv_date_created);
        mDateModifiedTextView = (TextView) findViewById(R.id.tv_date_modified);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent.hasExtra(RepositorySearchActivity.REPOSITORY_EXTRA)) {
            Repository repository = (Repository)
                    intent.getSerializableExtra(RepositorySearchActivity.REPOSITORY_EXTRA);

            mOwnerTextView.setText(repository.getName());
            mDescriptionTextView.setText(repository.getDescription());
            mLanguageTextView.setText(repository.getLanguage());
            mDateCreatedTextView.setText(DateUtils.formatDate(repository.getDatePublished()) + " "
                                + DateUtils.formatTime(repository.getDatePublished()));
            mDateModifiedTextView.setText(DateUtils.formatDate(repository.getDateModified()) + " "
                                + DateUtils.formatTime(repository.getDateModified()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
