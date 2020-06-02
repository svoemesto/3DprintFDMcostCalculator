package com.svoemestodev.fdmcostcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class SettingsActivity extends AppCompatActivity {

    AdView st_ad_banner;
    TextView st_tv_name_label;
    EditText st_et_name_value;
    TextView st_tv_electricity_cost_label;
    EditText st_et_electricity_cost_value;
    TextView st_tv_printer_cost_label;
    EditText st_et_printer_cost_value;
    TextView st_tv_printer_power_label;
    EditText st_et_printer_power_value;
    TextView st_tv_depreciation_years_label;
    EditText st_et_depreciation_years_value;
    TextView st_tv_final_cost_label;
    EditText st_et_final_cost_value;
    TextView st_tv_cost_hour_electricity_label;
    TextView st_tv_cost_hour_electricity_value;
    TextView st_tv_cost_hour_deprecation_label;
    TextView st_tv_cost_hour_deprecation_value;
    public static ProgramSettings setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


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
        st_ad_banner.loadAd(adRequest);

    }

    private void loadDataToViews() {

        String settingName = setting.getName() == null ? "N/A" : setting.getName();

        st_et_name_value.setText(settingName);
        st_et_electricity_cost_value.setText(String.valueOf(setting.getElectricityCost()));
        st_et_printer_cost_value.setText(String.valueOf(setting.getPrinterCost()));
        st_et_printer_power_value.setText(String.valueOf(setting.getPrinterPower()));
        st_et_depreciation_years_value.setText(String.valueOf(setting.getDepreciationYears()));
        st_et_final_cost_value.setText(String.valueOf(setting.getFinalCostToGram()));
        st_tv_cost_hour_electricity_value.setText(String.valueOf(setting.getCostOneHourElectricity()));
        st_tv_cost_hour_deprecation_value.setText(String.valueOf(setting.getCostOneHourDepreciation()));

    }

    private void initializeViews() {

        st_ad_banner = findViewById(R.id.st_ad_banner);
        st_tv_name_label = findViewById(R.id.st_tv_name_label);
        st_et_name_value = findViewById(R.id.st_et_name_value);
        st_tv_electricity_cost_label = findViewById(R.id.st_tv_electricity_cost_label);
        st_et_electricity_cost_value = findViewById(R.id.st_et_electricity_cost_value);
        st_tv_printer_cost_label = findViewById(R.id.st_tv_printer_cost_label);
        st_et_printer_cost_value = findViewById(R.id.st_et_printer_cost_value);
        st_tv_printer_power_label = findViewById(R.id.st_tv_printer_power_label);
        st_et_printer_power_value = findViewById(R.id.st_et_printer_power_value);
        st_tv_depreciation_years_label = findViewById(R.id.st_tv_depreciation_years_label);
        st_et_depreciation_years_value = findViewById(R.id.st_et_depreciation_years_value);
        st_tv_final_cost_label = findViewById(R.id.st_tv_final_cost_label);
        st_et_final_cost_value = findViewById(R.id.st_et_final_cost_value);
        st_tv_cost_hour_electricity_label = findViewById(R.id.st_tv_cost_hour_electricity_label);
        st_tv_cost_hour_electricity_value = findViewById(R.id.st_tv_cost_hour_electricity_value);
        st_tv_cost_hour_deprecation_label = findViewById(R.id.st_tv_cost_hour_deprecation_label);
        st_tv_cost_hour_deprecation_value = findViewById(R.id.st_tv_cost_hour_deprecation_value);
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
        setting.save();
        super.onBackPressed();

    }

    private void setValues() {

        setting.setName(st_et_name_value.getText().toString());
        setting.setElectricityCost(Float.parseFloat(st_et_electricity_cost_value.getText().toString()));
        setting.setPrinterCost(Float.parseFloat(st_et_printer_cost_value.getText().toString()));
        setting.setPrinterPower(Integer.parseInt(st_et_printer_power_value.getText().toString()));
        setting.setDepreciationYears(Integer.parseInt(st_et_depreciation_years_value.getText().toString()));
        setting.setFinalCostToGram(Float.parseFloat(st_et_final_cost_value.getText().toString()));

        loadDataToViews();

    }


}