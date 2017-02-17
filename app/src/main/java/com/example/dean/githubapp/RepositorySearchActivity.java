package com.example.dean.githubapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dean.githubapp.data.Repository;
import com.example.dean.githubapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RepositorySearchActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Repository>>,
        RepositoryAdapter.ListItemClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String REPOSITORY_EXTRA = "repository_extra";

    private static final int LOADER_ID = 1;
    private static final String SEARCH_QUERY_URL_EXTRA = "query";

    private EditText mSearchBoxEditText;
    private Button mSearchButton;
    private RecyclerView mRepositoriesRecycler;
    private RepositoryAdapter mAdapter;

    private List<Repository> mRepositoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_search);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_query);
        mSearchButton = (Button) findViewById(R.id.btn_search);
        mRepositoriesRecycler = (RecyclerView) findViewById(R.id.rv_repositories);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRepositoriesRecycler.setLayoutManager(layoutManager);
        mRepositoriesRecycler.setHasFixedSize(true);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeGithubSearchQuery();
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public Loader<List<Repository>> onCreateLoader(int i, final Bundle bundle) {
        return new AsyncTaskLoader<List<Repository>>(this) {
            List<Repository> repositoryData;

            @Override
            protected void onStartLoading() {
                if (bundle == null) {
                    return;
                }

                if (repositoryData != null) {
                    deliverResult(repositoryData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<Repository> loadInBackground() {
                String searchQueryUrlString = bundle.getString(SEARCH_QUERY_URL_EXTRA);

                if (TextUtils.isEmpty(searchQueryUrlString)) {
                    return null;
                }

                try {
                    URL githubUrl = new URL(searchQueryUrlString);
                    return NetworkUtils.fetchRepositoryData(githubUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(List<Repository> data) {
                repositoryData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Repository>> loader, List<Repository> repositoryList) {
        if (repositoryList == null) {
            return;
        }

        mRepositoryList = repositoryList;

        mAdapter = new RepositoryAdapter(repositoryList, this);
        mRepositoriesRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Repository>> loader) {

    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {
        Intent intent = new Intent(this, RepositoryDetailActivity.class);
        intent.putExtra(REPOSITORY_EXTRA, mRepositoryList.get(clickedItemIndex));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sort_by_key))) {
            makeGithubSearchQuery();
        }
    }

    private void makeGithubSearchQuery() {
        String query = mSearchBoxEditText.getText().toString();
        String sortBy = loadSortByFromPreferences();
        URL githubRequestUrl = NetworkUtils.buildUrl(query, sortBy);

        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, githubRequestUrl.toString());

        LoaderManager loaderManager = getLoaderManager();
        Loader<List<Repository>> githubSearchLoader = loaderManager.getLoader(LOADER_ID);

        if (githubSearchLoader == null) {
            loaderManager.initLoader(LOADER_ID, queryBundle, RepositorySearchActivity.this);
        } else {
            loaderManager.restartLoader(LOADER_ID, queryBundle, RepositorySearchActivity.this);
        }
    }

    private String loadSortByFromPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String sortBy = sharedPreferences.getString(
                getString(R.string.pref_sort_by_key),
                getString(R.string.pref_sort_by_stars_value)
        );

        return sortBy;
    }
}
