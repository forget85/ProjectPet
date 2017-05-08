package com.example.forget.projectpet;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.BottomNavigationView;
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

import junit.framework.Assert;

import org.json.simple.JSONArray;

import java.util.ArrayList;

public class MainActivityPresenter {
    private int lastMenuItemID = R.id.navigation_home;
    private DrawerLayout drawerLayout = null;
    private ActionBarDrawerToggle actionBarDrawerToggle = null;
    private MainActivityModel mainActivityModel = new MainActivityModel();
    private ListView drawerLeftMenuListView = null;
    private ListView drawerRightMenuListView = null;
    private View lastDrawerView = null;
    private BottomNavigationView navigationView = null;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    private Context context;

    MainActivityPresenter(Context _context, View view) {
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        drawerLeftMenuListView = (ListView) view.findViewById(R.id.drawer_list_view_left);
        drawerRightMenuListView = (ListView) view.findViewById(R.id.drawer_list_view_right);
        navigationView = (BottomNavigationView) view.findViewById(R.id.navigation);

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

    public void addFragment(FragmentManager fragmentManager, Fragment fragment, String tag, boolean bAddBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment, tag);
        if(bAddBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void changeFragment(FragmentManager fragmentManager, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
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
        String tag = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new ListFragment();
                item.setChecked(true);
                tag = "listFragment";
                break;
            case R.id.navigation_like:
                fragment = new LikeFragment();
                item.setChecked(true);
                tag = "likeFragment";
                break;
            case R.id.navigation_search:
                fragment = new SearchFragment();
                item.setChecked(true);
                tag = "searchFragment";
                break;
            case R.id.navigation_setting:
                fragment = new SettingFragment();
                item.setChecked(true);
                tag = "settingFragment";
                break;
        }

        if (fragment != null) {
            changeFragment(fragmentManager, fragment, tag);
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

    public void setJSONArray(JSONArray jsonArray){
        mainActivityModel.setJSONArray(jsonArray);
    }

    ArrayList<ListItem> getListItems(){
        return mainActivityModel.getListItems();
    }

    public void openItemWebView(FragmentManager fragmentManager, String itemUrl){
        ItemViewFragment itemViewFragment = new ItemViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("itemUrlString", itemUrl);
        itemViewFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_container, itemViewFragment, "itemViewFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public boolean onBackPressed(FragmentManager fragmentManager){
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if(fragmentManager.getBackStackEntryCount() == 0) {
            if (!(0 <= intervalTime && intervalTime <= FINISH_INTERVAL_TIME)) {
                backPressedTime = tempTime;
                Toast.makeText(context, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        return false;
    }

    public void postProcessAfterBackPressed(FragmentManager fragmentManager, ActionBar actionBar){
        if(fragmentManager.findFragmentByTag("itemViewFragment") == null){
            if(!actionBar.isShowing())
                actionBar.show();
        }

        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);

        int menuID = -1;
        if(currentFragment.getTag() == "listFragment"){
            menuID = R.id.navigation_home;
        }else if(currentFragment.getTag() == "likeFragment"){
            menuID = R.id.navigation_like;
        }else if(currentFragment.getTag() == "searchFragment"){
            menuID = R.id.navigation_search;
        }else if(currentFragment.getTag() == "settingFragment") {
            menuID = R.id.navigation_setting;
        }

        if(menuID != -1){
            MenuItem menuItem = navigationView.getMenu().findItem(menuID);
            if(menuItem != null){
                menuItem.setChecked(true);
            }
        }
    }
}
