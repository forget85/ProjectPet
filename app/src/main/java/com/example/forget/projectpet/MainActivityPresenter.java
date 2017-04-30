package com.example.forget.projectpet;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivityPresenter {
    private MenuItem lastMenuItem = null;
    private DrawerLayout drawerLayout = null;
    private ActionBarDrawerToggle actionBarDrawerToggle = null;
    private MainActivityModel mainActivityModel = new MainActivityModel();
    private ListView drawerMenuListView = null;
    private Context context;

    MainActivityPresenter(Context _context, DrawerLayout _drawerLayout, ListView _drawerMenuListView) {
        drawerLayout = _drawerLayout;
        drawerMenuListView = _drawerMenuListView;
        context = _context;

        actionBarDrawerToggle = new ActionBarDrawerToggle((MainActivity)context, drawerLayout, 0, 0) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mainActivityModel.getMenuList());
        drawerMenuListView.setAdapter(adapter);

        drawerMenuListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String menuString = (String) drawerMenuListView.getItemAtPosition(position);
                Toast.makeText(context, menuString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addFragment(FragmentManager fragmentManager, BaseFragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void changeFragment(FragmentManager fragmentManager, BaseFragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public boolean onNavigationItemSelected(FragmentManager fragmentManager, @NonNull MenuItem item) {
        if (lastMenuItem == item)
            return false;

        lastMenuItem = item;
        BaseFragment fragment = null;
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
        return actionBarDrawerToggle.onOptionsItemSelected(item);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        actionBarDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
