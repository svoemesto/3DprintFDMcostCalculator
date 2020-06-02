package com.svoemestodev.fdmcostcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    AdView ma_ad_banner;
    ImageView ma_iv_logo;
    Button ma_bt_plastic;
    Button ma_bt_filament;
    Button ma_bt_settings;
    Button ma_bt_parts;
    Button ma_bt_products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        ma_ad_banner.loadAd(adRequest);
    }

    private void initializeViews() {
        ma_ad_banner = findViewById(R.id.ma_ad_banner);
        ma_iv_logo = findViewById(R.id.ma_iv_logo);
        ma_bt_plastic = findViewById(R.id.ma_bt_plastic);
        ma_bt_filament = findViewById(R.id.ma_bt_filament);
        ma_bt_settings = findViewById(R.id.ma_bt_settings);
        ma_bt_parts = findViewById(R.id.ma_bt_parts);
        ma_bt_products = findViewById(R.id.ma_bt_products);
    }

    public void openPlastic(View view) {
        Intent intent = new Intent(this, ListPlasticActivity.class);
        startActivityForResult(intent, 0);
    }

    public void openFilament(View view) {
        Intent intent = new Intent(this, ListFilamentActivity.class);
        startActivityForResult(intent, 0);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, ListSettingsActivity.class);
        startActivityForResult(intent, 0);
    }

    public void openParts(View view) {
        Intent intent = new Intent(this, ListPartsActivity.class);
        startActivityForResult(intent, 0);
    }

    public void openProducts(View view) {
        Intent intent = new Intent(this, ListProductsActivity.class);
        startActivityForResult(intent, 0);
    }
}