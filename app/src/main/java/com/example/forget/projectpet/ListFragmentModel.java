package com.example.forget.projectpet;

import java.util.ArrayList;

class ListFragmentModel {
    private ArrayList<ListItem> listItems = new ArrayList<>();

    void loadListData(){
        listItems.clear();

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
    }

    ArrayList<ListItem> getData(){
        return listItems;
    }
}
