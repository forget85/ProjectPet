package com.example.forget.projectpet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LikeFragment extends BaseFragment {
    public View initView(LayoutInflater inflater, ViewGroup container){
        return inflater.inflate(R.layout.fragment_like, container, false);
    }

    public String getFragmentTitle(){
        return "Like";
    }
}
