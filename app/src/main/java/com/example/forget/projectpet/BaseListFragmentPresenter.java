package com.example.forget.projectpet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

public class BaseListFragmentPresenter {
    private ListViewAdapter listViewAdapter;
    private RecyclerView recyclerView;
    private BaseListFragmentModel baseListFragmentModel = new BaseListFragmentModel();

    void Attach(Context _context, RecyclerView _recyclerView){
        listViewAdapter = new ListViewAdapter(_context);
        recyclerView = _recyclerView;
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

    public void onDestroy(){
        if(recyclerView.getAdapter() != null){
            recyclerView.setAdapter(null);
        }

        recyclerView = null;
        baseListFragmentModel = null;
    }
}
