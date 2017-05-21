package com.example.forget.projectpet;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import java.util.HashMap;

public class ListFragmentPresenter {
    protected ListViewAdapter listViewAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListFragmentModel listFragmentModel = new ListFragmentModel();

    void Attach(Context _context, RecyclerView _recyclerView, SwipeRefreshLayout _swipeRefreshLayout){
        recyclerView = _recyclerView;
        swipeRefreshLayout = _swipeRefreshLayout;
        createListViewAdapter(_context);
        recyclerView.setAdapter(listViewAdapter);
        updateList();
    }

    void createListViewAdapter(Context _context){
        listViewAdapter = new ListViewAdapter(_context);
    }

    void updateList(){
        if(listViewAdapter != null) {
            listViewAdapter.updateData(listFragmentModel.getData());
            listViewAdapter.notifyDataSetChanged();
        }
    }

    public void setFilterText(String string){
        listViewAdapter.getFilter().filter(string);
    }

    public void setListItems(HashMap<String, ListItem> listItem){
        listFragmentModel.setListItems(listItem);
    }

    public void onRefreshList(Activity activity){
        MainActivity mainActivity = (MainActivity)activity;
        if(mainActivity != null){
            mainActivity.reloadItems();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    public void onDestroy(){
        if(recyclerView.getAdapter() != null){
            recyclerView.setAdapter(null);
        }
    }
}
