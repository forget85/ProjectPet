package com.example.forget.projectpet;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

class ListFragmentPresenter {
    private RecyclerView recyclerView = null;
    private ListViewAdapter listViewAdapter = null;
    private ListFragmentModel listFragmentModel = new ListFragmentModel();

    ListFragmentPresenter(Context _context, RecyclerView _recyclerView){
        recyclerView = _recyclerView;
        listViewAdapter = new ListViewAdapter(_context);

    }

    void updateList(){
        listViewAdapter.updateData(listFragmentModel.getData());
        listViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(listViewAdapter);
    }

    public void setJSONArray(JSONArray jsonArray){
        listFragmentModel.setJSONArray(jsonArray);
    }
}
