package com.example.dean.githubapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dean.githubapp.data.Repository;

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

        Intent intent = getIntent();
        if (intent.hasExtra(RepositorySearchActivity.REPOSITORY_EXTRA)) {
            Repository repository = (Repository)
                    intent.getSerializableExtra(RepositorySearchActivity.REPOSITORY_EXTRA);

            mOwnerTextView.setText(repository.getName());
            mDescriptionTextView.setText(repository.getDescription());
            mLanguageTextView.setText(repository.getLanguage());
            mDateCreatedTextView.setText(repository.getDatePublished());
            mDateModifiedTextView.setText(repository.getDateModified());
        }
    }
}
