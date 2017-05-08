package com.example.forget.projectpet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class BaseListFragmentPresenter {
    private RecyclerView recyclerView = null;
    private ListViewAdapter listViewAdapter = null;
    private BaseListFragmentModel baseListFragmentModel = new BaseListFragmentModel();

    BaseListFragmentPresenter(Context _context, RecyclerView _recyclerView){
        recyclerView = _recyclerView;
        listViewAdapter = new ListViewAdapter(_context);
    }

    void updateList(){
        listViewAdapter.updateData(baseListFragmentModel.getData());
        listViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(listViewAdapter);
    }

    public void setListItems(ArrayList<ListItem> listItem){
        baseListFragmentModel.setListItems(listItem);
    }
}
