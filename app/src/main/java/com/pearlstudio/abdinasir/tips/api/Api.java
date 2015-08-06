package com.pearlstudio.abdinasir.tips.api;

import com.pearlstudio.abdinasir.tips.model.ActiveListings;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Abdinasir on 05/07/2015.
 */
public interface Api {
    @GET("/listings/active")
    void activeListings(@Query("includes") String includes,
                        Callback<ActiveListings> callback);
}
