package com.aspprothes.primecast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.aspprothes.primecast.netconnectioncheck.Common;

public class HomeActivity extends AppCompatActivity {
    private InternetCheck internetCheck = new InternetCheck();
    private GridView gridView;
    private LinearLayout no_internet_linerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.color5));
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.color5));
        setContentView(R.layout.home_activity);

        gridView = findViewById(R.id.gridView);
        no_internet_linerLayout = findViewById(R.id.no_internet_linerLayout);


    }





    // Internet Connection Checker Start Here ===================
    public class InternetCheck extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!Common.isNetworkConnected(HomeActivity.this)){
                gridView.setVisibility(View.GONE);
                no_internet_linerLayout.setVisibility(View.VISIBLE);
            }else{
                gridView.setVisibility(View.VISIBLE);
                no_internet_linerLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetCheck,intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(internetCheck);
        super.onStop();
    }
    // Internet Connection Checker End Here ===================


    // Alert Dialog Start Here ======================
    @Override
    public void onBackPressed() {
        if (isTaskRoot()){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("Do you want to exit this app ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.color5));
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.color5));

        }else{
            super.onBackPressed();
        }
    }// Alert Dialog End Here ======================
}