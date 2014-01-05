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
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * Created by picaro on 24.12.13.
 */
public class ShopListRest extends BaseRest{

    public ShopList getShopList(Integer id){
        Log.i(AppBase.TAG, "getShopList");
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

    public void crossProduct(Integer shopList, Product product){
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder
                .append(AppBase.BASE_REST_URL)
                .append("product/");
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = getHttpHeaders();

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        try {
            product.setCrossed(!product.getCrossed());
            String prodJSON =  new Gson().toJson(product, Product.class);

            HttpEntity<String> entity = new HttpEntity<String>(prodJSON,headers);
            restTemplate.put(urlBuilder.toString(), entity);
            //restTemplate.postForObject(urlBuilder.toString(), prodJSON, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProduct(Integer shopList, Product product){
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder
                .append(AppBase.BASE_REST_URL)
                .append("product/");
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = getHttpHeaders();

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

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        Charset utf8 = Charset.forName("UTF-8");
        MediaType mediaType = new MediaType("application", "json", utf8);
        headers.setContentType(mediaType);
        return headers;
    }

}
