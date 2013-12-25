package com.op.cookit.util;

import android.util.Log;

import com.google.gson.Gson;

import com.op.cookit.SettingsConst;
import com.op.cookit.model.Product;
import com.op.cookit.model.ShopList;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by picaro on 24.12.13.
 */
public class ShopListRest {

    public ShopList getShopList(Integer id){
        ShopList shopList = null;
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder
                .append(SettingsConst.BASE_REST_URL)
                .append("shoplist/")
                .append(id);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        try {
            String result = (String)restTemplate.getForObject(urlBuilder.toString(), String.class);
            shopList =  new Gson().fromJson(result, ShopList.class);
            Log.d(">>", "" + result + " shopList:" + shopList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shopList;
    }

    public void deleteProduct(Integer shopList, Integer productId){
		StringBuilder urlBuilder = new StringBuilder();
        urlBuilder
                .append(SettingsConst.BASE_REST_URL)
                .append("shoplist/")
                .append(id);
				RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        try {
            restTemplate.delete(urlBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crossProduct(Integer shopList, Integer productId){

    }

    public void addProduct(Integer shopList, Product product){

    }

}
