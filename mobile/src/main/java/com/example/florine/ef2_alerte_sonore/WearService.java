package com.example.florine.ef2_alerte_sonore;

import android.media.MediaPlayer;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.concurrent.TimeUnit;

/**
 * Created by florine on 4/3/16.
 */
public class WearService extends WearableListenerService {

    private final static String TAG = WearService.class.getCanonicalName();
    protected GoogleApiClient mApiClient;
    MediaPlayer mp;

    @Override
    public void onCreate(){
        super.onCreate();
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mApiClient.connect();
        try{
           mp = MediaPlayer.create(this, R.raw.sound);
        }catch(NullPointerException e){
            System.out.println("NPE");
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mApiClient.disconnect();
    }

    //Appelé à la reception d'un message de la montre
    @Override
    public void onMessageReceived(MessageEvent messageEvent){
        super.onMessageReceived(messageEvent);

        // On ouvre une connexion vers la montre
        ConnectionResult conncectionResult = mApiClient.blockingConnect(30, TimeUnit.SECONDS);

        if(!conncectionResult.isSuccess()) {
            Log.e(TAG, "Failed to connect to GoogleApiClient");
            return;
        }

        final String path = messageEvent.getPath();
        System.out.println("message recu cote smartphone");
        System.out.println("path : "+path);

        if(path.equals("on")){
            System.out.println("allumer le son");
            mp.start();
        }else{
            if(path.equals("off")){
                System.out.println("eteindre le son");
                mp.stop();
            }else{
                System.out.println("path autre");
            }
        }

    }

}
