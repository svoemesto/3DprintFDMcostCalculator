package com.svoemestodev.fdmcostcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class FilamentActivity extends AppCompatActivity {

    AdView fl_ad_banner;
    TextView fl_tv_name_label;
    EditText fl_et_name_value;
    ImageButton fl_ib_get_plastic;
    TextView fl_tv_plastic_label;
    TextView fl_tv_plastic_value;
    TextView fl_tv_color_label;
    EditText fl_et_color_value;
    TextView fl_tv_diameter_label;
    EditText fl_et_diameter_value;
    TextView fl_tv_spool_weight_label;
    EditText fl_et_spool_weight_value;
    TextView fl_tv_spool_cost_label;
    EditText fl_et_spool_cost_value;
    TextView fl_tv_spool_length_label;
    TextView fl_et_spool_length_value;
    TextView fl_tv_spool_weight_one_meter_label;
    TextView fl_et_spool_weight_one_meter_value;
    TextView fl_tv_spool_cost_one_kg_label;
    TextView fl_et_spool_cost_one_kg_value;
    TextView fl_tv_spool_cost_one_meter_label;
    TextView fl_et_spool_cost_one_meter_value;

    public static Filament filament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filament);

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
        fl_ad_banner.loadAd(adRequest);

    }

    private void loadDataToViews() {

        String filamentName = filament.getName() == null ? "N/A" : filament.getName();
        String plasticName = filament.getPlastic() == null ? "N/A" : filament.getPlastic().getName();
        String colorName = filament.getColor() == null ? "N/A" : filament.getColor();

        fl_et_name_value.setText(filamentName);
        fl_tv_plastic_value.setText(plasticName);
        fl_et_color_value.setText(colorName);
        fl_et_diameter_value.setText(String.valueOf(filament.getDiameter()));
        fl_et_spool_weight_value.setText(String.valueOf(filament.getSpoolWeight()));
        fl_et_spool_cost_value.setText(String.valueOf(filament.getSpoolCost()));
        fl_et_spool_length_value.setText(String.valueOf(filament.getSpoolLength()));
        fl_et_spool_weight_one_meter_value.setText(String.valueOf(filament.getWeightOneMeter()));
        fl_et_spool_cost_one_kg_value.setText(String.valueOf(filament.getCostOneKilogram()));
        fl_et_spool_cost_one_meter_value.setText(String.valueOf(filament.getCostOneMeter()));

    }

    private void initializeViews() {

        fl_ad_banner = findViewById(R.id.fl_ad_banner);
        fl_tv_name_label = findViewById(R.id.fl_tv_name_label);
        fl_et_name_value = findViewById(R.id.fl_et_name_value);
        fl_ib_get_plastic = findViewById(R.id.fl_ib_get_plastic);
        fl_tv_plastic_label = findViewById(R.id.fl_tv_plastic_label);
        fl_tv_plastic_value = findViewById(R.id.fl_tv_plastic_value);
        fl_tv_color_label = findViewById(R.id.fl_tv_color_label);
        fl_et_color_value = findViewById(R.id.fl_et_color_value);
        fl_tv_diameter_label = findViewById(R.id.fl_tv_diameter_label);
        fl_et_diameter_value = findViewById(R.id.fl_et_diameter_value);
        fl_tv_spool_weight_label = findViewById(R.id.fl_tv_spool_weight_label);
        fl_et_spool_weight_value = findViewById(R.id.fl_et_spool_weight_value);
        fl_tv_spool_cost_label = findViewById(R.id.fl_tv_spool_cost_label);
        fl_et_spool_cost_value = findViewById(R.id.fl_et_spool_cost_value);
        fl_tv_spool_length_label = findViewById(R.id.fl_tv_spool_length_label);
        fl_et_spool_length_value = findViewById(R.id.fl_et_spool_length_value);
        fl_tv_spool_weight_one_meter_label = findViewById(R.id.fl_tv_spool_weight_one_meter_label);
        fl_et_spool_weight_one_meter_value = findViewById(R.id.fl_et_spool_weight_one_meter_value);
        fl_tv_spool_cost_one_kg_label = findViewById(R.id.fl_tv_spool_cost_one_kg_label);
        fl_et_spool_cost_one_kg_value = findViewById(R.id.fl_et_spool_cost_one_kg_value);
        fl_tv_spool_cost_one_meter_label = findViewById(R.id.fl_tv_spool_cost_one_meter_label);
        fl_et_spool_cost_one_meter_value = findViewById(R.id.fl_et_spool_cost_one_meter_value);

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

        setValues();
        filament.save();
        super.onBackPressed();

    }

    private void setValues() {

        filament.setName(fl_et_name_value.getText().toString());
        filament.setColor(fl_et_color_value.getText().toString());
        filament.setDiameter(Float.parseFloat(fl_et_diameter_value.getText().toString()));
        filament.setSpoolWeight(Float.parseFloat(fl_et_spool_weight_value.getText().toString()));
        filament.setSpoolCost(Float.parseFloat(fl_et_spool_cost_value.getText().toString()));

        loadDataToViews();

    }

    public void getPlastic(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Select plastic");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        for (Plastic plastic: Plastic.loadList()) {
            arrayAdapter.add(plastic.getName());
        }

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                filament.setPlastic(Plastic.getItemByName(strName));
                loadDataToViews();
            }
        });
        builder.show();

    }

}