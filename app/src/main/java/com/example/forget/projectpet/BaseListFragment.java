package com.example.forget.projectpet;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class BaseListFragment extends BaseFragment {
    private BaseListFragmentPresenter baseListFragmentPresenter = null;

    public View initView(LayoutInflater inflater, ViewGroup container){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_view);
        initRecyclerView(recyclerView);
        baseListFragmentPresenter = new BaseListFragmentPresenter(context, recyclerView);

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

    public void setListitems(ArrayList<ListItem> _listItems){
        baseListFragmentPresenter.setListItems(_listItems);
        baseListFragmentPresenter.updateList();
    }
}
