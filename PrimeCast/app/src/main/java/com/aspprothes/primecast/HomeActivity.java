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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.aspprothes.primecast.netconnectioncheck.Common;
import com.aspprothes.primecast.tvplayer.ExoPlayerActivity;
import com.bumptech.glide.Glide;
import com.mursaat.extendedtextview.AnimatedGradientTextView;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    public static boolean internetConnect;
    private ArrayList<HashMap<String,String>> myArrayList = new ArrayList<>();
    private HashMap<String,String> myHashMap;
    private InternetCheck internetCheck = new InternetCheck();
    private RelativeLayout relativeLayout;
    private GridView gridView;
    private LinearLayout no_internet_linerLayout;
    private ImageCarousel carousel;
    private String url = "https://prothesbarai.github.io/JSON_Array_Parsing/PrimeCastTv.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.color5));
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.color5));
        setContentView(R.layout.home_activity);

        gridView = findViewById(R.id.gridView);
        no_internet_linerLayout = findViewById(R.id.no_internet_linerLayout);
        relativeLayout = findViewById(R.id.relativeLayout);
        carousel = findViewById(R.id.carousel);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i=0; i<response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String tvlogo = jsonObject.getString("tvlogo");
                        String iptvlink = jsonObject.getString("iptvlink");
                        String tvname = jsonObject.getString("tvname");

                        myHashMap = new HashMap<>();
                        myHashMap.put("tvlogourl",tvlogo);
                        myHashMap.put("tvlinkurl",iptvlink);
                        myHashMap.put("tvtitle",tvname);
                        myArrayList.add(myHashMap);
                    }

                    carousel();
                    CustomGridBaseAdapter customGridBaseAdapter = new CustomGridBaseAdapter();
                    gridView.setAdapter(customGridBaseAdapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "Something Error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jsonArrayRequest);





    } /////////////// ======================================================== End Here onCreate Method ======================================== ////////////////////



    // Custom BaseAdapter Start Here ============================================
    public class CustomGridBaseAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return myArrayList.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.grid_item_layout_design,parent,false);
            }
            LinearLayout itemGridLinear = convertView.findViewById(R.id.itemGridLinear);
            ImageView gridImgView = convertView.findViewById(R.id.gridImgView);
            AnimatedGradientTextView gridAnimatedTxt = convertView.findViewById(R.id.gridAnimatedTxt);

            HashMap<String,String> hashMap = new HashMap<>();
            hashMap = myArrayList.get(position);
            String getImgUrl = hashMap.get("tvlogourl");
            String getTvUrl = hashMap.get("tvlinkurl");
            String getTvTitle = hashMap.get("tvtitle");

            Glide.with(HomeActivity.this)
                    .load(getImgUrl)
                    .circleCrop()
                    .into(gridImgView);
            gridAnimatedTxt.setText(""+getTvTitle);


            itemGridLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExoPlayerActivity.getTvName = getTvTitle;
                    ExoPlayerActivity.get_tv_url = getTvUrl;
                    startActivity(new Intent(HomeActivity.this, ExoPlayerActivity.class));
                }
            });


            return convertView;
        }
    }// Custom BaseAdapter End Here ============================================





    // Carousel Items Start Here ==========================
    public void carousel(){
        carousel.addData(new CarouselItem(getString(R.string.carousel_no_1),"Sports"));
        carousel.addData(new CarouselItem(getString(R.string.carousel_no_2),"Broadcast"));
        carousel.addData(new CarouselItem(getString(R.string.carousel_no_3),"News"));
        carousel.addData(new CarouselItem(getString(R.string.carousel_no_4),"Animals"));
        carousel.addData(new CarouselItem(getString(R.string.carousel_no_5),"Entertainment"));
    }// Carousel Items End Here ==========================


    // Internet Connection Checker Start Here ===================
    public class InternetCheck extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!Common.isNetworkConnected(HomeActivity.this)){
                relativeLayout.setVisibility(View.GONE);
                no_internet_linerLayout.setVisibility(View.VISIBLE);
                internetConnect = false;
            }else{
                relativeLayout.setVisibility(View.VISIBLE);
                no_internet_linerLayout.setVisibility(View.GONE);
                internetConnect = true;
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