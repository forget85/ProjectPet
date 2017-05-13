package com.example.forget.projectpet;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

public class BaseListFragmentPresenter {
    private ListViewAdapter listViewAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BaseListFragmentModel baseListFragmentModel = new BaseListFragmentModel();

    void Attach(Context _context, RecyclerView _recyclerView, SwipeRefreshLayout _swipeRefreshLayout){
        listViewAdapter = new ListViewAdapter(_context);
        recyclerView = _recyclerView;
        swipeRefreshLayout = _swipeRefreshLayout;
        recyclerView.setAdapter(listViewAdapter);
        updateList();
    }

    void updateList(){
        if(listViewAdapter != null) {
            listViewAdapter.updateData(baseListFragmentModel.getData());
            listViewAdapter.notifyDataSetChanged();
        }
    }

    public void setListItems(ArrayList<ListItem> listItem){
        baseListFragmentModel.setListItems(listItem);
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

        swipeRefreshLayout = null;
        recyclerView = null;
        baseListFragmentModel = null;
    }
}
