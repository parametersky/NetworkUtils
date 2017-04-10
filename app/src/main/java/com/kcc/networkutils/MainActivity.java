package com.kcc.networkutils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kcc.httputils.HttpGet;
import com.kcc.httputils.HttpRequest;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private final String TAG = "NetworkUtils";
    //    private HttpURLConnection ÷
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkTask task = new NetworkTask();
                task.execute();
            }
        });
//        task.
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        return (network !=null) && network.isConnected();
    }

    public NetworkInfo getActiveNetwork(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        if ( network != null)
            return network;
        else
            return null;
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.kcc.networkutils/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.kcc.networkutils/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public  class NetworkTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            Log.d(TAG, "doInBackground: work start");
            InputStream file = getResources().openRawResource(R.raw.mysitename);
            Log.i(TAG, "doInBackground: file == null? "+ (file == null));
//            HttpGet get1 = new HttpGet("https://10.0.2.2:5001/cookie");
            HttpGet get1 = new HttpGet("https://google.com");
//            get1.setCertfile(file);
            HttpRequest.HttpResponse response = get1.connect();
            Log.d(TAG, "doInBackground: response: "+response.toString());

//            file = getResources().openRawResource(R.raw.mysitename);
//            HttpGet get = new HttpGet("https://10.0.2.2:5001/login");
//            get.setCertfile(file);
//            HttpRequest.HttpResponse response1 = get.connect();
//            Log.d(TAG, "doInBackground: response: "+response1.toString());
//
//            file = getResources().openRawResource(R.raw.mysitename);
//            HashMap<String,String> hashMap = new HashMap<>();
//            hashMap.put("user_name","kyle");
//            hashMap.put("password","1234");
//            HttpPost post = new HttpPost("https://10.0.2.2:5001/register",hashMap);
//            HttpRequest.HttpResponse httpResponse = post.connect();
//            Log.d(TAG, "doInBackground: p response: "+httpResponse.toString());
            return null;
        }
    }
}
