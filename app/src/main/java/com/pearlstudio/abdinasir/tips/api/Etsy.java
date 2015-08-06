package com.pearlstudio.abdinasir.tips.api;

import com.pearlstudio.abdinasir.tips.model.ActiveListings;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Abdinasir on 05/07/2015.
 */
public class Etsy {
    private static final String API_KEY = "ihpsygcuj7fr3zpakgskxnhu";

    private static RequestInterceptor getInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addEncodedQueryParam("api_key", API_KEY);
            }
        };
    }


    private static Api getApi(){
        return new RestAdapter.Builder()
                .setEndpoint("https://openapi.etsy.com/v2/")
                .setRequestInterceptor(getInterceptor())
                .build()
                .create(Api.class);
    }

    public static void getActiveListings(Callback<ActiveListings> callback){
        getApi().activeListings("Images,Shops",callback);
    }
}
