package com.example.natalya.task;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapters.ListAdapter;
import bean.User;
import db.DBSource;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://raw.githubusercontent.com/volodymyryatsykiv/TechnicalTask/master/users.json";
    private List<User> userList;
    private ListView listView;
    private ListAdapter listAdapter;
    DBSource ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ds =  ((AppContext)this.getApplication()).getDataSource();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(R.layout.bar);
        actionBar.setDisplayShowCustomEnabled(true);
        userList = new ArrayList<>();
        userList = ds.getAllRecords();
        listView = (ListView)findViewById(R.id.ListView);
        listAdapter = new ListAdapter(this, R.layout.list_row, userList);
        listView.setAdapter(listAdapter);
        if (userList.isEmpty())
        {
            new RecipeLoaderAsyncTask().execute(URL);
        }
    }

    public class RecipeLoaderAsyncTask extends AsyncTask<String, Void, List<User>> {

        @Override
        public List<User> doInBackground(String... params) {
            String url = params[0];
            String result = loadData(url);
            userList = parseToList(result, User[].class);
            return userList;
        }

        @Override
        public void onPostExecute(List<User> users) {
            listAdapter.addAll(users);
            for (User u: users
                 ) {
                ds.createRecord(u.idUrl, u.nameUrl, u.pictureUrl, u.titleUrl, u.textUrl);
            }
        }

        private String loadData(String url) {
            DefaultHttpClient httpClient = new DefaultHttpClient((new BasicHttpParams()));
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-type", "application/json");
            InputStream inputStream = null;
            String result = null;

            try {
                org.apache.http.HttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                try {
                    if (inputStream != null) inputStream.close();
                } catch (Exception f) {
                }
            }
            return result;
        }
    }
    private static <T> List<T> parseToList(String s, Class<T[]> clazz) throws JsonSyntaxException {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr);
    }

}
