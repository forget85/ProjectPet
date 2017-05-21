package com.example.forget.projectpet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

class MainActivityModel {
    private ArrayList<String> categoryList = new ArrayList<>();
    private ArrayList<String> filterList = new ArrayList<>();
    private HashMap<String, ListItem> listItems = new HashMap<>();

    MainActivityModel(){
        initCategoryList();
        initFilterList();
;    }

    void initCategoryList() {
        categoryList.clear();

        categoryList.add("전체");
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

    HashMap<String, ListItem> getListItems(){
        return listItems;
    }

    public void setJSONArray(JSONArray jsonArray){
        listItems.clear();
        parseData(jsonArray);
    }

    void parseData(JSONArray jsonArray){
        for (int iItem = 0; iItem < jsonArray.size(); iItem++) {
            JSONObject itemObject = (JSONObject) jsonArray.get(iItem);
            String ID = itemObject.get("id").toString();
            String productUrl = itemObject.get("product_url").toString();
            String productName = itemObject.get("product_name").toString();
            String imageUrl = itemObject.get("image_url").toString();
            String cost = itemObject.get("cost").toString();
            String productInfo = itemObject.get("product_info").toString();
            ListItem listItem = new ListItem(productUrl, productName, imageUrl, cost, productInfo);
            listItems.put(ID, listItem);
        }
    }
}
