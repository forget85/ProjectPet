package com.example.forget.projectpet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

class ListFragmentModel {
    private ArrayList<ListItem> listItems = new ArrayList<>();

    ArrayList<ListItem> getData(){
        return listItems;
    }

    public void setJSONArray(JSONArray jsonArray){
        parseData(jsonArray);
    }

    void parseData(JSONArray jsonArray){
        for (int iItem = 0; iItem < jsonArray.size(); iItem++) {
            JSONObject itemObject = (JSONObject) jsonArray.get(iItem);
            String productUrl = itemObject.get("product_url").toString();
            String productName = itemObject.get("product_name").toString();
            String imageUrl = itemObject.get("image_url").toString();
            String cost = itemObject.get("cost").toString();
            String productInfo = itemObject.get("product_info").toString();
            ListItem listItem = new ListItem(productUrl, productName, imageUrl, cost, productInfo);
            listItems.add(listItem);
        }
    }
}
