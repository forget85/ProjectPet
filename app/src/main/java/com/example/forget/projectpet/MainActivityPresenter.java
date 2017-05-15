package com.example.forget.projectpet;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivityPresenter implements TextWatcher{
    private LoadDataListener loadDataListener;
    private MenuItemCheckListener menuItemCheckListener;

    private int lastMenuItemID = R.id.navigation_home;
    private DrawerLayout drawerLayout;
    private MainActivityModel mainActivityModel = new MainActivityModel();
    private long backPressedTime = 0;

    private FragmentManager fragmentManager;
    private ActionBar actionBar;
    private Context context;
    private ReadDataTask readDataTask;

    private static final int FINISH_INTERVAL_TIME = 2000;
    private static final int MAX_FRAGMENT_MANAGER_BACK_STACK_SIZE = 10;

    private SearchEditText searchEditText;

    MainActivityPresenter(Context _context, View view, FragmentManager _fragmentManager, ActionBar _actionBar) {
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        ListView drawerLeftMenuListView = (ListView) view.findViewById(R.id.drawer_list_view_left);
        ListView drawerRightMenuListView = (ListView) view.findViewById(R.id.drawer_list_view_right);
        fragmentManager = _fragmentManager;
        actionBar = _actionBar;
        context = _context;

        drawerLeftMenuListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String menuString = (String) parent.getItemAtPosition(position);
            }
        });

        drawerRightMenuListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String menuString = (String) parent.getItemAtPosition(position);
            }
        });

        ArrayAdapter<String> leftAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mainActivityModel.getCategoryList());
        drawerLeftMenuListView.setAdapter(leftAdapter);

        ArrayAdapter<String> rightAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mainActivityModel.getFilterList());
        drawerRightMenuListView.setAdapter(rightAdapter);

        initActionBar();
    }

    private void initActionBar(){
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        View actionBarCustomView = LayoutInflater.from(context).inflate(R.layout.main_action_bar, null);
        SearchEditText clearEditText = (SearchEditText) actionBarCustomView.findViewById(R.id.action_bar_search);
        clearEditText.addTextChangedListener(this);

        ImageButton homeButton = (ImageButton) actionBarCustomView.findViewById(R.id.action_bar_home);
        homeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(drawerLayout.isDrawerOpen(Gravity.END)){
                    drawerLayout.closeDrawer(Gravity.END);
                }

                if(drawerLayout.isDrawerOpen(Gravity.START)){
                    drawerLayout.closeDrawer(Gravity.START);
                }else{
                    drawerLayout.openDrawer(Gravity.START);
                }
            }
        });

        ImageButton filterButton = (ImageButton) actionBarCustomView.findViewById(R.id.action_bar_filter);
        filterButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(drawerLayout.isDrawerOpen(Gravity.START)){
                    drawerLayout.closeDrawer(Gravity.START);
                }

                if(drawerLayout.isDrawerOpen(Gravity.END)){
                    drawerLayout.closeDrawer(Gravity.END);
                }else{
                    drawerLayout.openDrawer(Gravity.END);
                }
            }
        });

        actionBar.setCustomView(actionBarCustomView);
    }

    public void onDestroy(){
        context = null;
        drawerLayout = null;
        fragmentManager = null;
        actionBar = null;
        searchEditText= null;

        if(readDataTask != null)
            readDataTask.cancel(true);
    }

    void setLoadDataListener(LoadDataListener _loadDataListener){
        loadDataListener = _loadDataListener;
    }

    void setMenuItemCheckListener(MenuItemCheckListener _menuItemCheckListener){
        menuItemCheckListener = _menuItemCheckListener;
    }

    public void readData() {
        try {
            if(readDataTask == null)
                readDataTask = new ReadDataTask();

            readDataTask.execute("http://13.124.70.76/products");
        }catch (Exception exception){
            System.out.println(exception.toString());
        }
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (!currentFragment.getTag().equals("settingFragment")){
            BaseListFragment baseListFragment = (BaseListFragment) currentFragment;

            if (baseListFragment != null) {
                baseListFragment.setFilterText(s.toString());
            }
        }
    }

    public void afterTextChanged(Editable s) {}

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

            if(loadDataListener != null)
                loadDataListener.onFinishLoad(mainActivityModel.getListItems());
        }

        String getData(String urlString){
            try {
                URL url = new URL(urlString);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
                try {
                    JSONArray jsonArray = (JSONArray) JSONValue.parseWithException(inputStreamReader);
                    mainActivityModel.setJSONArray(jsonArray);
                } catch (ParseException parseException) {
                }
            }catch (IOException exception){
                return "fail";
            }
            return "Success";
        }
    }

    public void addFragment(String tag, boolean bChange, boolean bAddBackStack) {
        Fragment fragment = createFragment(tag);
        if(fragment == null)
            return;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(bChange){
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        }else {
            fragmentTransaction.add(R.id.fragment_container, fragment, tag);
        }

        int drawerLockMode = DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        if(tag.equals("listFragment")){
            drawerLockMode = DrawerLayout.LOCK_MODE_UNLOCKED;
        }

        drawerLayout.setDrawerLockMode(drawerLockMode);

        if(bAddBackStack) {
            if(MAX_FRAGMENT_MANAGER_BACK_STACK_SIZE < fragmentManager.getBackStackEntryCount()){
                fragmentManager.popBackStack();
            }

            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

        if(tag.equals("listFragment")) {
            ListFragment listFragment = (ListFragment)fragment;
            if(listFragment != null){
                setLoadDataListener(listFragment);
            }

            if (loadDataListener != null)
                loadDataListener.onFinishLoad(mainActivityModel.getListItems());
        }

        recalcActionBarLayout(tag);
    }

    private void recalcActionBarLayout(String tag){
        boolean bShowHome = false;
        boolean bShowLogo = true;
        boolean bShowSearchBar = false;
        boolean bShowFilter = false;
        if(tag.equals("listFragment")){
            bShowHome = true;
            bShowFilter = true;
            bShowSearchBar = true;
        }else if(tag.equals("likeFragment")){
            bShowHome = false;
            bShowFilter = false;
            bShowSearchBar = true;
        }else if(tag.equals("searchFragment")){
            bShowHome = false;
            bShowFilter = false;
            bShowSearchBar = true;
        }else if(tag.equals("settingFragment")){
            bShowHome = false;
            bShowFilter = false;
            bShowSearchBar = false;
        }

        View customView = actionBar.getCustomView();
        ImageButton homeButton = (ImageButton) customView.findViewById(R.id.action_bar_home);
        searchEditText = (SearchEditText) customView.findViewById(R.id.action_bar_search);
        ImageButton filterButton = (ImageButton) customView.findViewById(R.id.action_bar_filter);

        homeButton.setVisibility(bShowHome ? View.VISIBLE : View.GONE);
        filterButton.setVisibility(bShowFilter ? View.VISIBLE : View.GONE);
        searchEditText.setVisibility(bShowSearchBar ? View.VISIBLE : View.GONE);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(searchEditText.isShown() && searchEditText.hasFocus()) {
                if (!searchEditText.isIn((int) event.getRawX(), (int) event.getRawY())) {
                    searchEditText.clearFocus();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (lastMenuItemID == item.getItemId())
            return true;

        lastMenuItemID = item.getItemId();
        String tag = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                item.setChecked(true);
                tag = "listFragment";
                break;
            case R.id.navigation_like:
                item.setChecked(true);
                tag = "likeFragment";
                break;
            case R.id.navigation_search:
                item.setChecked(true);
                tag = "searchFragment";
                break;
            case R.id.navigation_setting:
                item.setChecked(true);
                tag = "settingFragment";
                break;
        }

        if (!tag.isEmpty()) {
            addFragment(tag, true, true);
            return true;
        }

        return false;
    }

    private Fragment createFragment(String tag){
        if(tag.equals("listFragment")){
            return new ListFragment();
        }else if(tag.equals("likeFragment")){
            return new LikeFragment();
        }else if(tag.equals("searchFragment")){
            return new SearchFragment();
        }else if(tag.equals("settingFragment")){
            return new SettingFragment();
        }

        return null;
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
                return true;
            }
        }

        return false;
    }

    private int getNavigationMenuID(String tag){
        if(tag.equals("listFragment")){
            return R.id.navigation_home;
        }else if(tag.equals("likeFragment")){
            return R.id.navigation_like;
        }else if(tag.equals("searchFragment")){
            return R.id.navigation_search;
        }else if(tag.equals("settingFragment")){
            return R.id.navigation_setting;
        }

        return -1;
    }

    public void postProcessAfterBackPressed(FragmentManager fragmentManager, ActionBar actionBar){
        if(fragmentManager.findFragmentByTag("itemViewFragment") == null){
            if(!actionBar.isShowing())
                actionBar.show();
        }

        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        menuItemCheckListener.onCheckItem(getNavigationMenuID(currentFragment.getTag()));
    }
}
