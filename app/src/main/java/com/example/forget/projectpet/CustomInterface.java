package com.example.forget.projectpet;

import java.util.ArrayList;

interface LoadDataListener {
    void onFinishLoad(ArrayList<ListItem> listItems);
}

interface MenuItemCheckListener{
    void onCheckItem(int menuID);
}