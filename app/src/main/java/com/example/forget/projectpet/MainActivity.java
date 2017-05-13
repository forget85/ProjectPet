package com.example.forget.projectpet;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements InvalidataOptionMenuListener, MenuItemCheckListener{
    MainActivityPresenter mainActivityPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initPresenter();
    }

    private void initView(){
        ListView drawerLeftMenuListView = (ListView) findViewById(R.id.drawer_list_view_left);
        ListView drawerRightMenuListView = (ListView) findViewById(R.id.drawer_list_view_right);

        drawerLeftMenuListView.addHeaderView(getLayoutInflater().inflate(R.layout.left_drawer_menu_header, null, false));
        drawerLeftMenuListView.addFooterView(getLayoutInflater().inflate(R.layout.left_drawer_menu_footer, null, false));

        drawerRightMenuListView.addHeaderView(getLayoutInflater().inflate(R.layout.right_drawer_menu_header, null, false));

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return mainActivityPresenter.onNavigationItemSelected(item);
            }
        });
    }

    private void initPresenter(){
        mainActivityPresenter = new MainActivityPresenter(this, getWindow().getDecorView(), getFragmentManager(), getSupportActionBar());
        mainActivityPresenter.setInvalidataOptionMenuListener(this);
        mainActivityPresenter.setMenuItemCheckListener(this);
        mainActivityPresenter.readData();
        mainActivityPresenter.addFragment("listFragment", false, false);
    }

    protected void onDestroy() {
        super.onDestroy();
        mainActivityPresenter.onDestroy();
        mainActivityPresenter = null;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
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

    public void openItemWebView(String itemUrl){
        getSupportActionBar().hide();
        mainActivityPresenter.openItemWebView(getFragmentManager(), itemUrl);
    }

    public void onBackPressed() {
        if(!mainActivityPresenter.onBackPressed(getFragmentManager())) {
            super.onBackPressed();
            mainActivityPresenter.postProcessAfterBackPressed(getFragmentManager(), getSupportActionBar());
        }
    }

    public void reloadItems(){
        mainActivityPresenter.readData();
    }

    public void onInvalidateOptionsMenu() {
        invalidateOptionsMenu();
    }

    public void onCheckItem(int menuID) {
        if(menuID == -1)
            return;

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        MenuItem menuItem = navigationView.getMenu().findItem(menuID);
        if(menuItem != null){
            menuItem.setChecked(true);
        }
    }
}
