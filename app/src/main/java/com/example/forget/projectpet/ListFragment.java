package com.example.forget.projectpet;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

public class ListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private ListFragmentPresenter listFragmentPresenter = createListFragmentPresenter();
    static final int columnCount = 2;
    static final int dividerWidth = 20;
    static final int dividerHeight = 20;

    public ListFragmentPresenter createListFragmentPresenter(){
        return new ListFragmentPresenter();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new ListViewItemDecoration(columnCount, dividerWidth, dividerHeight));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        listFragmentPresenter.Attach(getActivity(), recyclerView, swipeRefreshLayout);
        return view;
    }

    public void setFilterText(String string){
        listFragmentPresenter.setFilterText(string);
    }

    public void setListitems(HashMap<String, ListItem> _listItems){
        listFragmentPresenter.setListItems(_listItems);
        listFragmentPresenter.updateList();
    }

    public void onDestroy() {
        super.onDestroy();
        listFragmentPresenter.onDestroy();
    }

    public void onRefresh() {
        listFragmentPresenter.onRefreshList(getActivity());
    }
}
