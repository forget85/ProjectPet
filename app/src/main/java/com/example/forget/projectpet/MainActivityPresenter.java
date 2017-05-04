package com.example.forget.projectpet;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.simple.JSONObject;

public class MainActivityPresenter {
    private int lastMenuItemID = R.id.navigation_home;
    private DrawerLayout drawerLayout = null;
    private ActionBarDrawerToggle actionBarDrawerToggle = null;
    private MainActivityModel mainActivityModel = new MainActivityModel();
    private ListView drawerLeftMenuListView = null;
    private ListView drawerRightMenuListView = null;
    private View lastDrawerView = null;

    private Context context;

    MainActivityPresenter(Context _context, View view) {
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        drawerLeftMenuListView = (ListView) view.findViewById(R.id.drawer_list_view_left);
        drawerRightMenuListView = (ListView) view.findViewById(R.id.drawer_list_view_right);

        context = _context;

        actionBarDrawerToggle = new ActionBarDrawerToggle((MainActivity)context, drawerLayout, 0, 0) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                lastDrawerView = drawerView;
            }

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            public void onDrawerStateChanged(int newState) {
                if(lastDrawerView != null && newState == DrawerLayout.STATE_SETTLING){
                    if(drawerLayout.isDrawerOpen(lastDrawerView)){
                        drawerLayout.closeDrawer(lastDrawerView);
                        lastDrawerView = null;
                    }
                }

                super.onDrawerStateChanged(newState);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        drawerLeftMenuListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String menuString = (String) drawerLeftMenuListView.getItemAtPosition(position);
                Toast.makeText(context, menuString, Toast.LENGTH_SHORT).show();
            }
        });

        drawerRightMenuListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String menuString = (String) drawerRightMenuListView.getItemAtPosition(position);
                Toast.makeText(context, menuString, Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<String> leftAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mainActivityModel.getCategoryList());
        drawerLeftMenuListView.setAdapter(leftAdapter);

        ArrayAdapter<String> rightAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mainActivityModel.getFilterList());
        drawerRightMenuListView.setAdapter(rightAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        boolean bDisplayHomeAsUpEnabled = true;
        boolean bVisibleSearchItem = true;
        boolean bVisibleFilterItem = true;

        switch (lastMenuItemID) {
            case R.id.navigation_home:
                bDisplayHomeAsUpEnabled = true;
                bVisibleSearchItem = true;
                bVisibleFilterItem = true;
                break;
            case R.id.navigation_like:
                bDisplayHomeAsUpEnabled = false;
                bVisibleSearchItem = true;
                bVisibleFilterItem = false;
                break;
            case R.id.navigation_search:
                bDisplayHomeAsUpEnabled = false;
                bVisibleSearchItem = true;
                bVisibleFilterItem = false;
                break;
            case R.id.navigation_setting:
                bDisplayHomeAsUpEnabled = false;
                bVisibleSearchItem = false;
                bVisibleFilterItem = false;
                break;
        }

        MainActivity mainActivity = (MainActivity)context;
        mainActivity.getMenuInflater().inflate(R.menu.action_bar, menu);
        ActionBar actionBar = mainActivity.getSupportActionBar();
        MenuItem searchMenuItem = menu.findItem(R.id.search_menu);
        MenuItem filterMenuItem = menu.findItem(R.id.filter_menu);

        actionBar.setDisplayHomeAsUpEnabled(bDisplayHomeAsUpEnabled);
        searchMenuItem.setVisible(bVisibleSearchItem);
        filterMenuItem.setVisible(bVisibleFilterItem);

        return true;
    }

    public void addFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void changeFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        MainActivity mainActivity = (MainActivity)context;
        mainActivity.invalidateOptionsMenu();
    }

    public boolean onNavigationItemSelected(FragmentManager fragmentManager, @NonNull MenuItem item) {
        if (lastMenuItemID == item.getItemId())
            return false;

        lastMenuItemID = item.getItemId();
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new ListFragment();
                item.setChecked(true);
                break;
            case R.id.navigation_like:
                fragment = new LikeFragment();
                item.setChecked(true);
                break;
            case R.id.navigation_search:
                fragment = new SearchFragment();
                item.setChecked(true);
                break;
            case R.id.navigation_setting:
                fragment = new SettingFragment();
                item.setChecked(true);
                break;
        }

        if (fragment != null) {
            changeFragment(fragmentManager, fragment);
            return true;
        }

        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.search_menu) {
            Toast.makeText(context, "search", Toast.LENGTH_SHORT).show();
            return true;
        }else if(item.getItemId() == R.id.filter_menu) {
            if(drawerLayout.isDrawerOpen(drawerRightMenuListView)) {
                drawerLayout.closeDrawer(drawerRightMenuListView);
            }else {
                drawerLayout.openDrawer(drawerRightMenuListView);
            }

            return true;
        }else {
            return actionBarDrawerToggle.onOptionsItemSelected(item);
        }
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        actionBarDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
