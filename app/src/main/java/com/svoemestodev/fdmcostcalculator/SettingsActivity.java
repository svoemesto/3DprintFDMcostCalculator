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
    TextView st_tv_name_value;
    TextView st_tv_electricity_cost_label;
    TextView st_tv_electricity_cost_value;
    TextView st_tv_printer_cost_label;
    TextView st_tv_printer_cost_value;
    TextView st_tv_printer_power_label;
    TextView st_tv_printer_power_value;
    TextView st_tv_depreciation_years_label;
    TextView st_tv_depreciation_years_value;
    TextView st_tv_final_cost_label;
    TextView st_tv_final_cost_value;
    TextView st_tv_cost_hour_electricity_label;
    TextView st_tv_cost_hour_electricity_value;
    TextView st_tv_cost_hour_depreciation_label;
    TextView st_tv_cost_hour_depreciation_value;
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

        st_tv_name_value.setText(settingName);
        st_tv_electricity_cost_value.setText(Utils.convertFloatToStringFormatter2digit(setting.getElectricityCost()));
        st_tv_printer_cost_value.setText(Utils.convertFloatToStringFormatter2digit(setting.getPrinterCost()));
        st_tv_printer_power_value.setText(Utils.convertIntToStringFormatter(setting.getPrinterPower()));
        st_tv_depreciation_years_value.setText(Utils.convertIntToStringFormatter(setting.getDepreciationYears()));
        st_tv_final_cost_value.setText(Utils.convertFloatToStringFormatter2digit(setting.getFinalCostToGram()));
        st_tv_cost_hour_electricity_value.setText(Utils.convertFloatToStringFormatter2digit(setting.getCostOneHourElectricity()));
        st_tv_cost_hour_depreciation_value.setText(Utils.convertFloatToStringFormatter2digit(setting.getCostOneHourDepreciation()));

    }

    private void initializeViews() {

        st_ad_banner = findViewById(R.id.st_ad_banner);
        st_tv_name_label = findViewById(R.id.st_tv_name_label);
        st_tv_name_value = findViewById(R.id.st_tv_name_value);
        st_tv_electricity_cost_label = findViewById(R.id.st_tv_electricity_cost_label);
        st_tv_electricity_cost_value = findViewById(R.id.st_tv_electricity_cost_value);
        st_tv_printer_cost_label = findViewById(R.id.st_tv_printer_cost_label);
        st_tv_printer_cost_value = findViewById(R.id.st_tv_printer_cost_value);
        st_tv_printer_power_label = findViewById(R.id.st_tv_printer_power_label);
        st_tv_printer_power_value = findViewById(R.id.st_tv_printer_power_value);
        st_tv_depreciation_years_label = findViewById(R.id.st_tv_depreciation_years_label);
        st_tv_depreciation_years_value = findViewById(R.id.st_tv_depreciation_years_value);
        st_tv_final_cost_label = findViewById(R.id.st_tv_final_cost_label);
        st_tv_final_cost_value = findViewById(R.id.st_tv_final_cost_value);
        st_tv_cost_hour_electricity_label = findViewById(R.id.st_tv_cost_hour_electricity_label);
        st_tv_cost_hour_electricity_value = findViewById(R.id.st_tv_cost_hour_electricity_value);
        st_tv_cost_hour_depreciation_label = findViewById(R.id.st_tv_cost_hour_depreciation_label);
        st_tv_cost_hour_depreciation_value = findViewById(R.id.st_tv_cost_hour_depreciation_value);
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

        setting.save();
        super.onBackPressed();

    }


    public void setName(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(R.string.name);
        String defaultValue = setting.getName();
        final EditText input = new EditText(SettingsActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                setting.setName(newValue);
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
    
    public void setPrinterCost(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(R.string.printer_cost);
        String defaultValue = String.valueOf(setting.getPrinterCost());
        final EditText input = new EditText(SettingsActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                setting.setPrinterCost(Float.parseFloat(newValue));
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

    public void setPrinterPower(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(R.string.printer_power);
        String defaultValue = String.valueOf(setting.getPrinterPower());
        final EditText input = new EditText(SettingsActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                setting.setPrinterPower(Integer.parseInt(newValue));
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

    public void setDepreciationYears(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(R.string.depreciation_years);
        String defaultValue = String.valueOf(setting.getDepreciationYears());
        final EditText input = new EditText(SettingsActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                setting.setDepreciationYears(Integer.parseInt(newValue));
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

    public void setFinalCost(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(R.string.final_cost_to_1_gram);
        String defaultValue = String.valueOf(setting.getFinalCostToGram());
        final EditText input = new EditText(SettingsActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                setting.setFinalCostToGram(Float.parseFloat(newValue));
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
    
    public void setElectricityCost(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(R.string.electricity_cost);
        String defaultValue = String.valueOf(setting.getElectricityCost());
        final EditText input = new EditText(SettingsActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                setting.setElectricityCost(Float.parseFloat(newValue));
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