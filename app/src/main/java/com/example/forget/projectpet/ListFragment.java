package com.example.forget.projectpet;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ListFragment extends BaseFragment {
    private ListFragmentPresenter listFragmentPresenter = null;

    public View initView(LayoutInflater inflater, ViewGroup container){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_view);

        initRecyclerView(recyclerView);

        listFragmentPresenter = new ListFragmentPresenter(context, recyclerView);

        listFragmentPresenter.setListItems(((MainActivity)context).getListItems());
        listFragmentPresenter.updateList();
        return view;
    }

    public void initRecyclerView(RecyclerView recyclerView){
        recyclerView.setHasFixedSize(true);

        final int columnCount = 2;
        final int dividerWidth = 20;
        final int dividerHeight = 20;
        recyclerView.addItemDecoration(new ListViewItemDecoration(columnCount, dividerWidth, dividerHeight));
        recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
    }
}
