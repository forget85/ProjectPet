package com.example.forget.projectpet;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {
    Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(inflater, container);
        MainActivity mainActivity = (MainActivity) context;
        if(mainActivity != null) {
            if(mainActivity.getSupportActionBar() != null) {
                mainActivity.getSupportActionBar().setTitle(getFragmentTitle());
            }
        }

        //savedInstanceState 처리

        if(view == null)
            view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    public View initView(LayoutInflater inflater, ViewGroup container){
        return null;
    }

    public String getFragmentTitle() {
        return "";
    }

    public void onAttach(Context _context) {
        super.onAttach(_context);
        context = _context;
    }
}
