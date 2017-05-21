package com.example.forget.projectpet;

import java.util.HashMap;

interface LoadDataListener {
    void onFinishLoad(HashMap<String, ListItem> listItems);
}

interface MenuItemCheckListener{
    void onCheckItem(int menuID);
}