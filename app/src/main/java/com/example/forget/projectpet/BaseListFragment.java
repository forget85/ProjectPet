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

import java.util.ArrayList;

public class BaseListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private BaseListFragmentPresenter baseListFragmentPresenter = new BaseListFragmentPresenter();
    static final int columnCount = 2;
    static final int dividerWidth = 20;
    static final int dividerHeight = 20;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new ListViewItemDecoration(columnCount, dividerWidth, dividerHeight));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        baseListFragmentPresenter.Attach(getActivity(), recyclerView, swipeRefreshLayout);
        return view;
    }

    public void setListitems(ArrayList<ListItem> _listItems){
        baseListFragmentPresenter.setListItems(_listItems);
        baseListFragmentPresenter.updateList();
    }

    public void onDestroy() {
        super.onDestroy();
        baseListFragmentPresenter.onDestroy();
        baseListFragmentPresenter = null;
    }

    public void onRefresh() {
        baseListFragmentPresenter.onRefreshList(getActivity());
    }
}
