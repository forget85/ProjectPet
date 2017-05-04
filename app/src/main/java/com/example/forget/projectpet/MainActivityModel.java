package com.example.forget.projectpet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

class MainActivityModel {
    private ArrayList<String> categoryList = new ArrayList<>();
    private ArrayList<String> filterList = new ArrayList<>();

    MainActivityModel(){
        initCategoryList();
        initFilterList();
;    }

    void initCategoryList() {
        categoryList.clear();

        categoryList.add("사료");
        categoryList.add("간식");
        categoryList.add("영양제");
        categoryList.add("목욕");
        categoryList.add("미용");
        categoryList.add("위생/배변");
        categoryList.add("식기/물병");
        categoryList.add("장난감");
        categoryList.add("잡");
        categoryList.add("의류");
        categoryList.add("악세사리");
        categoryList.add("목줄");
    }

    void initFilterList() {
        filterList.clear();

        filterList.add("모두");
        filterList.add("강아지");
        filterList.add("고양이");
        filterList.add("햄스터");
    }

    ArrayList<String> getCategoryList(){
        return categoryList;
    }
    ArrayList<String> getFilterList() { return filterList; }
}
