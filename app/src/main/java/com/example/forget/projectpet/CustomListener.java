package com.example.forget.projectpet;

import android.view.MenuItem;
import java.util.ArrayList;

interface LoadDataListener {
    void onFinishLoad(ArrayList<ListItem> listItems);
}

interface InvalidataOptionMenuListener{
    void onInvalidateOptionsMenu();
}

interface MenuItemCheckListener{
    void onCheckItem(int menuID);
}