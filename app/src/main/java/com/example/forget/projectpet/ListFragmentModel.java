package com.example.forget.projectpet;

import java.util.HashMap;

public class ListFragmentModel {
    private HashMap<String, ListItem> listItems;

    HashMap<String, ListItem> getData(){
        return listItems;
    }

    public void setListItems(HashMap<String, ListItem> _listItems){
        listItems = _listItems;
    }
}
