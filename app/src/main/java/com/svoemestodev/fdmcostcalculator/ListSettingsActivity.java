package com.svoemestodev.fdmcostcalculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class ListSettingsActivity extends AppCompatActivity {

    AdView lst_ad_banner;
    Button lst_bt_add_new_setting;
    ListView lst_lv_settings;
    
    private static final int REQUEST_CODE_SETTING_ACTIVITY = 100;

    private static List<ProgramSettings> settings = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_settings);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        initializeViews();

        displayRecords();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        lst_ad_banner.loadAd(adRequest);

    }


    private void initializeViews() {
        lst_ad_banner = findViewById(R.id.lst_ad_banner);
        lst_bt_add_new_setting = findViewById(R.id.lst_bt_add_new_setting);
        lst_lv_settings = findViewById(R.id.lst_lv_settings);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();  // индекс нажатой кнопки
        if (id == android.R.id.home) { //если в шапке нажата кнопка "Назад"
            onBackPressed();    // вызываем метод "Назад"
            return true;        // возвращаем Истину
        }
        return super.onOptionsItemSelected(item);   // возвращаем супер-метод
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SETTING_ACTIVITY) {
            displayRecords();
        }

    }

    private void displayRecords() {
        settings = ProgramSettings.loadList();
        lst_lv_settings.setAdapter(new ListSettingsAdapter(this));
    }

    public void addNewSetting(View view) {

        ProgramSettings setting = new ProgramSettings();
        setting.setName("Setting #" + (settings.size()+1));
        SettingsActivity.setting = setting;
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SETTING_ACTIVITY);

    }

    private class ListSettingsAdapter extends ArrayAdapter<ProgramSettings> {

        public ListSettingsAdapter(@NonNull Context context) {
            super(context, R.layout.list_settings, settings);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final ProgramSettings setting = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_settings, null);
            }

            ImageButton lst_bt_delete = convertView.findViewById(R.id.lst_bt_delete);
            ImageButton lst_bt_edit = convertView.findViewById(R.id.lst_bt_edit);
            TextView lst_tv_name_value = convertView.findViewById(R.id.lst_tv_name_value);
            TextView lst_tv_electricity_cost_value = convertView.findViewById(R.id.lst_tv_electricity_cost_value);
            TextView lst_tv_printer_cost_value = convertView.findViewById(R.id.lst_tv_printer_cost_value);
            TextView lst_tv_printer_power_value = convertView.findViewById(R.id.lst_tv_printer_power_value);
            TextView lst_tv_depreciation_years_value = convertView.findViewById(R.id.lst_tv_depreciation_years_value);
            TextView lst_tv_final_cost_value = convertView.findViewById(R.id.lst_tv_final_cost_value);
            TextView lst_tv_cost_hour_electricity_value = convertView.findViewById(R.id.lst_tv_cost_hour_electricity_value);
            TextView lst_tv_cost_hour_deprecation_value = convertView.findViewById(R.id.lst_tv_cost_hour_deprecation_value);

            String settingName = setting.getName() == null ? "N/A" : setting.getName();

            lst_tv_name_value.setText(settingName);
            lst_tv_electricity_cost_value.setText(String.valueOf(setting.getElectricityCost()));
            lst_tv_printer_cost_value.setText(String.valueOf(setting.getPrinterCost()));
            lst_tv_printer_power_value.setText(String.valueOf(setting.getPrinterPower()));
            lst_tv_depreciation_years_value.setText(String.valueOf(setting.getDepreciationYears()));
            lst_tv_final_cost_value.setText(String.valueOf(setting.getFinalCostToGram()));
            lst_tv_cost_hour_electricity_value.setText(String.valueOf(setting.getCostOneHourElectricity()));
            lst_tv_cost_hour_deprecation_value.setText(String.valueOf(setting.getCostOneHourDepreciation()));

            lst_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setting.delete();
                    displayRecords();
                }
            });

            lst_bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SettingsActivity.setting = setting;
                    Intent intent = new Intent(ListSettingsActivity.this, SettingsActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SETTING_ACTIVITY);
                }
            });

            return convertView;

        }
    }

}