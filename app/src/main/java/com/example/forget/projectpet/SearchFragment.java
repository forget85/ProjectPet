package com.example.forget.projectpet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

public class SearchFragment extends ListFragment implements LoadDataListener {
    public void onFinishLoad(HashMap<String, ListItem> listItems) {
        setListitems(listItems);
    }

    public ListFragmentPresenter createListFragmentPresenter(){
        return new SearchFragmentPresenter();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
