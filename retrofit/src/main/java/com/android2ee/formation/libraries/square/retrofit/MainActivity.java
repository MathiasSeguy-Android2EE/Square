package com.android2ee.formation.libraries.square.retrofit;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.libraries.square.retrofit.com.WebServerIntf;
import com.android2ee.formation.libraries.square.retrofit.service.BusinessService;


public class MainActivity extends ActionBarActivity {

    TextView txvGetData, txvGetUsers;
    ImageView imvPhoto;
    WebServerIntf webService, webServiceComplex;
    BusinessService service=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvGetData = (TextView) findViewById(R.id.txvGetData);
        txvGetUsers = (TextView) findViewById(R.id.txvGetUser);
        imvPhoto = (ImageView) findViewById(R.id.imvPhoto);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //initialize your service here
        if(service==null){
            service=new BusinessService();
        }
        service.initialize(this);
        service.loadData();
        service.loadDataWithQuery();
        service.getAPicture();
        service.getPostPutDeleteSample();
        service.loadDataUrlEncoded();
        service.loadWithErrorHandlingCall();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //don't forget to release your service to avoir memory leak
        service.release();
        service=null;
    }
    /***********************************************************
     *  updating graphical element
     **********************************************************/
    /**
     * Update the textView txvGetData
     * @param message
     */
    public void updateTxvGetData(String message){
        txvGetData.append(message);
    }
    /**
     * Update the textView txvGetData
     * @param message
     */
    public void updateTxvGetUsers(String message){
        txvGetUsers.append(message);
    }/**
     * Update the textView txvGetData
     * @param drawable
     */
    public void updateImvPhoto(Drawable drawable){
        imvPhoto.setImageDrawable(drawable);
    }

    /***********************************************************
     * Managing menu
     **********************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
