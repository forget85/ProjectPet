package com.example.forget.projectpet;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

public class SearchFragmentPresenter extends ListFragmentPresenter {
    void createListViewAdapter(Context _context) {
        super.createListViewAdapter(_context);
        listViewAdapter.setSearchListView(true);
    }

    void Attach(Context _context, RecyclerView _recyclerView, SwipeRefreshLayout _swipeRefreshLayout) {
        super.Attach(_context, _recyclerView, _swipeRefreshLayout);
        setFilterText(null);
    }
}
