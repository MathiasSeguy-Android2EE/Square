package com.android2ee.formation.lib.squarelibs.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android2ee.formation.lib.squarelibs.R;
import com.android2ee.formation.lib.squarelibs.tool.MoshiSample;

public class MoshiActivity extends ActionBarActivity {

    /**
     * Displaying the result in a textview
     */
    TextView txvJsonSimple,txvJsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moshi);
        getSupportActionBar().setSubtitle("Moshi");
        txvJsonObject= (TextView) findViewById(R.id.txvJsonObject);
        txvJsonSimple= (TextView) findViewById(R.id.txvJsonSimple);
        //then write the file
        MoshiSample moshiMethods=new MoshiSample();
        moshiMethods.writeJson(this);
        //then read it
        txvJsonSimple.setText(moshiMethods.readJson(this));
        //do the same with the object
        txvJsonObject.setText(moshiMethods.usingAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_moshi, menu);
        menu.findItem(R.id.show_moshi).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent startActivity=null;
        switch(item.getItemId()){
            case R.id.show_moshi:
                break;
            case R.id.show_okhttp:
                startActivity=new Intent(this,OkHttpActivity.class);
                break;
            case R.id.show_okio:
                startActivity = new Intent(this, OkioActivity.class);
                break;
        }
        if(startActivity!=null){
            startActivity(startActivity);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
