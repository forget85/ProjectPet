package com.example.forget.projectpet;

import java.util.ArrayList;

class MainActivityModel {
    private ArrayList<String> menuList = new ArrayList<>();

    MainActivityModel(){
        initMenuList();
    }

    void initMenuList() {
        menuList.clear();

        menuList.add("사료");
        menuList.add("간식");
        menuList.add("영양제");
        menuList.add("목욕");
        menuList.add("미용");
        menuList.add("위생/배변");
        menuList.add("식기/물병");
        menuList.add("장난감");
        menuList.add("잡");
        menuList.add("의류");
        menuList.add("악세사리");
        menuList.add("목줄");
    }

    ArrayList<String> getMenuList(){
        return menuList;
    }
}
