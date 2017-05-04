package com.example.forget.projectpet;

public class ListItem {
    boolean bSale;
    boolean bFreeShipping;
    String  productName;
    String  productUrl;
    String  imageUrl;
    String  cost;
    String  productInfo;
    String  shoppingMallName;


    ListItem(String _productUrl, String _productName, String _imageUrl, String _cost, String _productInfo){
        productUrl = _productUrl;
        productName = _productName;
        imageUrl = _imageUrl;
        cost = _cost;
        productInfo = _productInfo;
    }

    public String getShoppingMallName() {
        return shoppingMallName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public String getProductName() {
        return productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCost() {
        return cost;
    }

    public String getProductInfo() {
        return productInfo;
    }

    boolean getSale(){
        return bSale;
    }

    boolean getFreeShipping(){
        return bFreeShipping;
    }
}
