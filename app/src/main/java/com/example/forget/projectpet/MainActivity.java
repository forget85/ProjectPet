package com.example.forget.projectpet;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    MainActivityPresenter mainActivityPresenter = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
           return mainActivityPresenter.onNavigationItemSelected(getSupportFragmentManager(), item);
        }

    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initView();

        mainActivityPresenter.addFragment(getSupportFragmentManager(), new ListFragment());
    }

    private void initView(){
        ListView drawerLeftMenuListView = (ListView) findViewById(R.id.drawer_list_view_left);
        ListView drawerRightMenuListView = (ListView) findViewById(R.id.drawer_list_view_right);

        drawerLeftMenuListView.addHeaderView(getLayoutInflater().inflate(R.layout.left_drawer_menu_header, null, false));
        drawerLeftMenuListView.addFooterView(getLayoutInflater().inflate(R.layout.left_drawer_menu_footer, null, false));

        drawerRightMenuListView.addHeaderView(getLayoutInflater().inflate(R.layout.right_drawer_menu_header, null, false));
        //drawerRightMenuListView.addFooterView(getLayoutInflater().inflate(R.layout.right_drawer_menu_footer, null, false));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mainActivityPresenter = new MainActivityPresenter(this, drawerLayout, drawerLeftMenuListView, drawerRightMenuListView);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
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

}
