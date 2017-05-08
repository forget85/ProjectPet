package com.example.forget.projectpet;

import java.util.ArrayList;

public class BaseListFragmentModel {
    private ArrayList<ListItem> listItems = new ArrayList<>();

    ArrayList<ListItem> getData(){
        return listItems;
    }

    public void setListItems(ArrayList<ListItem> _listItems){
        listItems = _listItems;
    }
}
