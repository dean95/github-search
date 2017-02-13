package com.example.dean.githubapp.utilities;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.dean.githubapp.RepositorySearchActivity;
import com.example.dean.githubapp.data.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    final static String GITHUB_BASE_URL = "https://api.github.com/search/repositories";
    final static String PARAM_QUERY = "q";
    final static String PARAM_SORT = "sort";


    private NetworkUtils() {

    }

    public static List<Repository> fetchRepositoryData(URL requestUrl) {
        String jsonResponse = "";

        try {
            jsonResponse = makeHttpRequest(requestUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Probem making HTTP request", e);
        }

        List<Repository> repositoryList = extractRepos(jsonResponse);

        return repositoryList;
    }

    public static List<Repository> extractRepos(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        List<Repository> repositoryList = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(jsonResponse);

            JSONArray items = root.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject currentRepo = items.getJSONObject(i);

                String name = currentRepo.getString("name");
                int watchers = currentRepo.getInt("watchers");
                int forks = currentRepo.getInt("forks_count");
                int issues = currentRepo.getInt("open_issues_count");
                String language = currentRepo.getString("language");
                String description = currentRepo.getString("description");
                String datePublished = currentRepo.getString("pushed_at");
                String dateUpdated = currentRepo.getString("updated_at");

                JSONObject owner = currentRepo.getJSONObject("owner");
                String ownerName = owner.getString("login");
                String ownerAvatar = owner.getString("avatar_url");

                Repository repository = new Repository(name, ownerName, ownerAvatar, watchers,
                        forks, issues, language, description, datePublished, dateUpdated);

                repositoryList.add(repository);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing repository JSON result", e);
        }

        return repositoryList;
    }

    public static URL buildUrl(String githubSearchQuery, String sortBy) {
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, githubSearchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL", e);
        }

        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the repository JSON results", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream,
                    Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }
}
