package com.example.forget.projectpet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ItemViewFragment extends BaseFragment{
    public View initView(LayoutInflater inflater, ViewGroup container){
        View view = inflater.inflate(R.layout.fragment_item_view, container, false);
        WebView webView = (WebView) view.findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        String urlString = getArguments().getString("itemUrlString");
        if(!urlString.isEmpty()){
            webView.loadUrl(urlString);
        }
        return view;
    }
}
