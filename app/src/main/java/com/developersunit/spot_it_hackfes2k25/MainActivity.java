package com.developersunit.spot_it_hackfes2k25;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);
    }
    public void ShowSearch(android.view.View button){
        Intent intent =new Intent(this,SearchactivityActivity.class);
       startActivity(intent);
    }

}