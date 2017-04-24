package com.example.forget.projectpet;

public class ListItem {
    boolean bSale;
    boolean bFreeShipping;
    String  productName;
    String  shoppingMallName;
        /*
        이미지 정보 추가
        가격 정보 추가
        */


    ListItem(String _productName, String _shoppingMallName){
        productName = _productName;
        shoppingMallName = _shoppingMallName;
    }

    public String getShoppingMallName() {
        return shoppingMallName;
    }

    public String getProductName() {
        return productName;
    }

    boolean getSale(){
        return bSale;
    }

    boolean getFreeShipping(){
        return bFreeShipping;
    }
}
