package com.op.cookit.util;

import android.util.Log;

import com.google.gson.Gson;

import com.op.cookit.AppBase;
import com.op.cookit.model.Product;
import com.op.cookit.model.ShopList;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by picaro on 24.12.13.
 */
public class ShopListRest {

    public ShopList getShopList(Integer id){
        ShopList shopList = null;
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder
                .append(AppBase.BASE_REST_URL)
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
                .append(AppBase.BASE_REST_URL)
                .append("product/")
                .append(productId);
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
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder
                .append(AppBase.BASE_REST_URL)
                .append("product/");
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        try {
            String prodJSON =  new Gson().toJson(product, Product.class);

            HttpEntity<String> entity = new HttpEntity<String>(prodJSON,headers);
            restTemplate.put(urlBuilder.toString(), entity);

            //restTemplate.postForObject(urlBuilder.toString(), prodJSON, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
