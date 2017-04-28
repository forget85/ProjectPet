package com.example.forget.projectpet;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

class ListFragmentPresenter {
    private RecyclerView recyclerView = null;
    private ListViewAdapter listViewAdapter = null;
    private ListFragmentModel listFragmentModel = new ListFragmentModel();

    ListFragmentPresenter(Context _context, RecyclerView _recyclerView){
        recyclerView = _recyclerView;
        listViewAdapter = new ListViewAdapter(_context);

        loadModelData();
        updateRecyclerView(listFragmentModel.getData());
    }

    void updateRecyclerView(ArrayList<ListItem> _arrayList){
        listViewAdapter.updateData(_arrayList);
        listViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(listViewAdapter);
    }

    void loadModelData(){
        listFragmentModel.loadListData();
    }
}
