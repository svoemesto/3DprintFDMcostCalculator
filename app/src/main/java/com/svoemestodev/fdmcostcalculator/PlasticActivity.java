package com.svoemestodev.fdmcostcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class PlasticActivity extends AppCompatActivity {

    AdView pl_ad_banner;
    TextView pl_tv_name_label;
    TextView pl_tv_name_value;
    TextView pl_tv_density_label;
    TextView pl_tv_density_value;

    public static Plastic plastic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plastic);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        initializeViews();
        loadDataToViews();


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        pl_ad_banner.loadAd(adRequest);

    }

    private void loadDataToViews() {

        String plasticName = plastic.getName() == null ? "N/A" : plastic.getName();

        pl_tv_name_value.setText(plasticName);
        pl_tv_density_value.setText(Utils.convertFloatToStringFormatter2digit(plastic.getDensity()));

    }

    private void initializeViews() {

        pl_ad_banner = findViewById(R.id.pl_ad_banner);
        pl_tv_name_label = findViewById(R.id.pl_tv_name_label);
        pl_tv_name_value = findViewById(R.id.pl_tv_name_value);
        pl_tv_density_label = findViewById(R.id.pl_tv_density_label);
        pl_tv_density_value = findViewById(R.id.pl_tv_density_value);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();  // индекс нажакой кнопки
        if (id == android.R.id.home) { //если в шапке нажата кнопка "Назад"
            onBackPressed();    // вызываем метод "Назад"
            return true;        // возвращаем Истину
        }
        return super.onOptionsItemSelected(item);   // возвращаем супер-метод
    }

    @Override
    public void onBackPressed() {

        plastic.save();
        super.onBackPressed();

    }

    public void setName(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(PlasticActivity.this);
        builder.setTitle(R.string.name);
        String defaultValue = plastic.getName();
        final EditText input = new EditText(PlasticActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                plastic.setName(newValue);
                loadDataToViews();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void setDensity(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(PlasticActivity.this);
        builder.setTitle(R.string.density);
        String defaultValue = String.valueOf(plastic.getDensity());
        final EditText input = new EditText(PlasticActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                plastic.setDensity(Float.parseFloat(newValue));
                loadDataToViews();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
}