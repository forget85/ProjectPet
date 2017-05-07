package com.example.forget.projectpet;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MainActivityPresenter mainActivityPresenter = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
           return mainActivityPresenter.onNavigationItemSelected(getFragmentManager(), item);
        }

    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initView();

        readData();
        mainActivityPresenter.addFragment(getFragmentManager(), new ListFragment());
    }

    private void initView(){
        ListView drawerLeftMenuListView = (ListView) findViewById(R.id.drawer_list_view_left);
        ListView drawerRightMenuListView = (ListView) findViewById(R.id.drawer_list_view_right);

        drawerLeftMenuListView.addHeaderView(getLayoutInflater().inflate(R.layout.left_drawer_menu_header, null, false));
        drawerLeftMenuListView.addFooterView(getLayoutInflater().inflate(R.layout.left_drawer_menu_footer, null, false));

        drawerRightMenuListView.addHeaderView(getLayoutInflater().inflate(R.layout.right_drawer_menu_header, null, false));
        //drawerRightMenuListView.addFooterView(getLayoutInflater().inflate(R.layout.right_drawer_menu_footer, null, false));

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainActivityPresenter = new MainActivityPresenter(this, getWindow().getDecorView());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return mainActivityPresenter.onCreateOptionsMenu(menu);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mainActivityPresenter.onPostCreate(savedInstanceState);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mainActivityPresenter.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (mainActivityPresenter.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<ListItem> getListItems(){
        return mainActivityPresenter.getListItems();
    }

    private void readData() {
        try {
            ReadDataTask readDataTask = new ReadDataTask();
            readDataTask.execute("http://13.124.70.76/products");
        }catch (Exception exception){
            System.out.println(exception.toString());
        }
    }

    private class ReadDataTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            try{
                return getData(params[0]);
            }catch (Exception exception){
                return "fail";
            }
        }

        protected void onPostExecute(String string) {
            super.onPostExecute(string);
        }

        String getData(String urlString){
            try {
                URL url = new URL(urlString);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
                try {
                    JSONArray jsonArray = (JSONArray) JSONValue.parseWithException(inputStreamReader);
                    mainActivityPresenter.setJSONArray(jsonArray);
                } catch (ParseException parseException) {
                }
            }catch (IOException exception){
                return "fail";
            }
            return "Success";
        }
    }
}
