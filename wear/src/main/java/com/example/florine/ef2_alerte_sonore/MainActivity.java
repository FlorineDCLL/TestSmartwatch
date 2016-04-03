package com.example.florine.ef2_alerte_sonore;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;


public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, MessageApi.MessageListener, DataApi.DataListener {


    private BoxInsetLayout mContainerView;
    private Button boutonOn, boutonOff;
    protected GoogleApiClient mApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //etAmbientEnabled();
        boutonOn = (Button) findViewById(R.id.start);
        boutonOff = (Button) findViewById(R.id.stop);
        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
    }

    @Override
    protected void onStart(){
        System.out.println("on start");
        super.onStart();
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mApiClient.connect();
        System.out.println("connected");
    }

    @Override
    protected void onStop(){
        if(null != mApiClient && mApiClient.isConnected()){
            Wearable.MessageApi.removeListener(mApiClient,this);
            mApiClient.disconnect();
        }
        super.onStop();
    }

    public void sonOn(View view){
        System.out.println("Méthode sonOn montre");
        sendMessage("on","smartphone");
        System.out.println("send message on");
    }

    public void sonOff(View view){
        System.out.println("Méthode sonOff montre");
        sendMessage("off","smartphone");
        System.out.println("send message off");
    }


    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener(mApiClient, this);
        Wearable.DataApi.addListener(mApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

    }

    protected void sendMessage(final String path, final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mApiClient).await();
                for(Node node : nodes.getNodes())
                    Wearable.MessageApi.sendMessage(mApiClient,node.getId(),path, message.getBytes()).await();
            }
        }).start();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {

    }
}
