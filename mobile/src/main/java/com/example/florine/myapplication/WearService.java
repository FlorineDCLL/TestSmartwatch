package com.example.florine.myapplication;

import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.concurrent.TimeUnit;

/**
 * Created by florine on 3/13/16.
 */
public class WearService extends WearableListenerService {

    private final static String TAG = WearService.class.getCanonicalName();
    protected GoogleApiClient mApiClient;

    @Override
    public void onCreate(){
        super.onCreate();
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mApiClient.connect();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mApiClient.disconnect();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent){
        super.onMessageReceived(messageEvent);

        // ouvrir une connection vers la montre
        ConnectionResult connectionResult = mApiClient.blockingConnect(30, TimeUnit.SECONDS);

        if(!connectionResult.isSuccess()){
            Log.e(TAG, "Failed to connect to GoogleApiClient");
            return;
        }
        final String path = messageEvent.getPath();
        if(path.equals("bonjour")){
            int random = (int)(Math.random()*100);
            sendMessage("bonjour","affiche : "+random);
        }
    }

    //envoi d'un message à la montre
    protected void sendMessage(final String path, final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mApiClient).await();
                for(Node node : nodes.getNodes())
                    Wearable.MessageApi.sendMessage(mApiClient, node.getId(), path, message.getBytes()).await();
            }
        }).start();
    }
}