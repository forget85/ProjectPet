package com.example.forget.projectpet;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Context context;

    public ListFragment() {
    }

    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);
        final int columnCount = 2;
        final int dividerWidth = 20;
        final int dividerHeight = 20;
        recyclerView.addItemDecoration(new ListViewItemDecoration(columnCount, dividerWidth, dividerHeight));

        ArrayList<ListItem> listItems = new ArrayList<>();

        listItems.add(new ListItem("상품1", "옥션"));
        listItems.add(new ListItem("상품2", "지마켓"));
        listItems.add(new ListItem("상품3", "11번가"));
        listItems.add(new ListItem("상품4", "이베이"));
        listItems.add(new ListItem("상품5", "네이버"));
        listItems.add(new ListItem("상품6", "옥션"));
        listItems.add(new ListItem("상품7", "11번가"));
        listItems.add(new ListItem("상품8", "지마켓"));
        listItems.add(new ListItem("상품9", "옥션"));
        listItems.add(new ListItem("상품10", "이베이"));
        listItems.add(new ListItem("상품11", "11번가"));
        listItems.add(new ListItem("상품12", "지마켓"));
        listItems.add(new ListItem("상품13", "옥션"));
        listItems.add(new ListItem("상품14", "옥션"));
        listItems.add(new ListItem("상품15", "11번가"));
        listItems.add(new ListItem("상품16", "지마켓"));
        listItems.add(new ListItem("상품17", "네이버"));
        listItems.add(new ListItem("상품18", "지마켓"));
        listItems.add(new ListItem("상품19", "이베이"));
        listItems.add(new ListItem("상품20", "11번가"));

        recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        recyclerView.setAdapter(new ListViewAdapter(context, listItems));
        return view;
    }

    public void onAttach(Context _context) {
        super.onAttach(_context);
        context = _context;
    }

    public void onDetach() {
        super.onDetach();
    }
}
