package com.pearlstudio.abdinasir.tips.Google;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

/**
 * Created by Abdinasir on 06/07/2015.
 */
public class GoogleServicesHelper implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    public GoogleServicesHelper(Activity activity, GoogleServicesListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.activity = activity;
        this.listener = listener;
        this.apiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API,Plus.PlusOptions.builder()
                        .setServerClientId("801173159264-u6lpitc2nrovjcn8iekkd71258hvgtv3.apps.googleusercontent.com")
                        .build())
                .build();
    }

    public interface GoogleServicesListener {
        public void onConnected();

        public void onDisconnected();
    }

    public static final int REQUEST_CODE_RESOLUTION = -100;
    public static final int REQUEST_CODE_AVAILABILITY = -101;

    private GoogleServicesListener listener;
    private Activity activity;
    private GoogleApiClient apiClient;




    public void connect() {
        if (isGooglePlayServicesAvailable()) {
            apiClient.connect();
        }else {
            listener.onDisconnected();
        }
    }

    public void disconnect() {
        if (isGooglePlayServicesAvailable()) {
            apiClient.disconnect();
        }else {
            listener.onDisconnected();
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        int availability = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        switch (availability) {
            case ConnectionResult.SUCCESS:
                return true;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
            case ConnectionResult.SERVICE_DISABLED:
            case ConnectionResult.SERVICE_INVALID:
                GooglePlayServicesUtil.getErrorDialog(availability, activity, REQUEST_CODE_AVAILABILITY).show();
                return false;
            default:
                return false;

        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        listener.onConnected();

    }

    @Override
    public void onConnectionSuspended(int i) {
        listener.onDisconnected();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()){
            try {
                connectionResult.startResolutionForResult(activity,REQUEST_CODE_RESOLUTION);
            } catch (IntentSender.SendIntentException e) {
                connect();
            }
        }else {
            listener.onDisconnected();
        }

    }

    public void handleActivityResult(int requestCode, int resulttCode, Intent data){
        if (requestCode == REQUEST_CODE_RESOLUTION || requestCode == REQUEST_CODE_AVAILABILITY){
            if(resulttCode == Activity.RESULT_OK){
                connect();
            }else {
                listener.onDisconnected();
            }
        }
    }
}
