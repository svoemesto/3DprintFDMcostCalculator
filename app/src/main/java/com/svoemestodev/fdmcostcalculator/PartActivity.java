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
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.svoemestodev.fdmcostcalculator.adapters.ListFilamentAdapter;
import com.svoemestodev.fdmcostcalculator.adapters.ListPlasticAdapter;
import com.svoemestodev.fdmcostcalculator.adapters.ListSettingAdapter;

public class PartActivity extends AppCompatActivity {

    AdView pa_ad_banner;
    TextView pa_tv_name_label;
    TextView pa_tv_name_value;
    ImageButton pa_ib_get_filament;
    TextView pa_tv_filament_label;
    TextView pa_tv_filament_value;
    ImageButton pa_ib_get_settings;
    TextView pa_tv_settings_label;
    TextView pa_tv_settings_value;
    TextView pa_tv_hours_label;
    TextView pa_tv_hours_value;
    TextView pa_tv_minutes_label;
    TextView pa_tv_minutes_value;
    TextView pa_tv_length_label;
    TextView pa_tv_length_value;
    TextView pa_tv_weight_label;
    TextView pa_tv_weight_value;
    TextView pa_tv_electric_cost_label;
    TextView pa_tv_electric_cost_value;
    TextView pa_tv_depreciation_cost_label;
    TextView pa_tv_depreciation_cost_value;
    TextView pa_tv_filament_cost_label;
    TextView pa_tv_filament_cost_value;
    TextView pa_tv_net_cost_label;
    TextView pa_tv_net_cost_value;
    TextView pa_tv_total_cost_label;
    TextView pa_tv_total_cost_value;
    TextView pa_tv_profit_cost_label;
    TextView pa_tv_profit_cost_value;

    public static Part part;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part);
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
        pa_ad_banner.loadAd(adRequest);

    }

    private void loadDataToViews() {

        String partName = part.getName() == null ? "N/A" : part.getName();
        String filamentName = part.getFilament() == null ? "N/A" : part.getFilament().getName();
        String settingName = part.getProgramSettings() == null ? "N/A" : part.getProgramSettings().getName();

        pa_tv_name_value.setText(partName);
        pa_tv_filament_value.setText(filamentName);
        pa_tv_settings_value.setText(settingName);
        pa_tv_hours_value.setText(Utils.convertIntToStringFormatter(part.getPrintHours()));
        pa_tv_minutes_value.setText(Utils.convertIntToStringFormatter(part.getPrintMinutes()));
        pa_tv_length_value.setText(Utils.convertIntToStringFormatter(part.getFilamentLength()));
        pa_tv_weight_value.setText(Utils.convertFloatToStringFormatter2digit(part.getWeight()));
        pa_tv_electric_cost_value.setText(Utils.convertFloatToStringFormatter2digit(part.getElectricityCost()));
        pa_tv_depreciation_cost_value.setText(Utils.convertFloatToStringFormatter2digit(part.getDepreciationCost()));
        pa_tv_filament_cost_value.setText(Utils.convertFloatToStringFormatter2digit(part.getFilamentCost()));
        pa_tv_net_cost_value.setText(Utils.convertFloatToStringFormatter2digit(part.getNetCost()));
        pa_tv_total_cost_value.setText(Utils.convertFloatToStringFormatter2digit(part.getTotalCost()));
        pa_tv_profit_cost_value.setText(Utils.convertFloatToStringFormatter2digit(part.getProfitCost()));
        
    }

    private void initializeViews() {
        pa_ad_banner = findViewById(R.id.pa_ad_banner);
        pa_tv_name_label = findViewById(R.id.pa_tv_name_label);
        pa_tv_name_value = findViewById(R.id.pa_tv_name_value);
        pa_ib_get_filament = findViewById(R.id.pa_ib_get_filament);
        pa_tv_filament_label = findViewById(R.id.pa_tv_filament_label);
        pa_tv_filament_value = findViewById(R.id.pa_tv_filament_value);
        pa_ib_get_settings = findViewById(R.id.pa_ib_get_settings);
        pa_tv_settings_label = findViewById(R.id.pa_tv_settings_label);
        pa_tv_settings_value = findViewById(R.id.pa_tv_settings_value);
        pa_tv_hours_label = findViewById(R.id.pa_tv_hours_label);
        pa_tv_hours_value = findViewById(R.id.pa_tv_hours_value);
        pa_tv_minutes_label = findViewById(R.id.pa_tv_minutes_label);
        pa_tv_minutes_value = findViewById(R.id.pa_tv_minutes_value);
        pa_tv_length_label = findViewById(R.id.pa_tv_length_label);
        pa_tv_length_value = findViewById(R.id.pa_tv_length_value);
        pa_tv_weight_label = findViewById(R.id.pa_tv_weight_label);
        pa_tv_weight_value = findViewById(R.id.pa_tv_weight_value);
        pa_tv_electric_cost_label = findViewById(R.id.pa_tv_electric_cost_label);
        pa_tv_electric_cost_value = findViewById(R.id.pa_tv_electric_cost_value);
        pa_tv_depreciation_cost_label = findViewById(R.id.pa_tv_depreciation_cost_label);
        pa_tv_depreciation_cost_value = findViewById(R.id.pa_tv_depreciation_cost_value);
        pa_tv_filament_cost_label = findViewById(R.id.pa_tv_filament_cost_label);
        pa_tv_filament_cost_value = findViewById(R.id.pa_tv_filament_cost_value);
        pa_tv_net_cost_label = findViewById(R.id.pa_tv_net_cost_label);
        pa_tv_net_cost_value = findViewById(R.id.pa_tv_net_cost_value);
        pa_tv_total_cost_label = findViewById(R.id.pa_tv_total_cost_label);
        pa_tv_total_cost_value = findViewById(R.id.pa_tv_total_cost_value);
        pa_tv_profit_cost_label = findViewById(R.id.pa_tv_profit_cost_label);
        pa_tv_profit_cost_value = findViewById(R.id.pa_tv_profit_cost_value);

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

        part.save();
        super.onBackPressed();

    }


    public void getFilament(View view) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.select_filament);

        final ListFilamentAdapter arrayAdapter = new ListFilamentAdapter(this);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Filament item = arrayAdapter.getItem(which);
                part.setFilament(item);
                loadDataToViews();
            }
        });
        builder.show();

    }

    public void getSettings(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.select_settings);

        final ListSettingAdapter arrayAdapter = new ListSettingAdapter(this);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProgramSettings item = arrayAdapter.getItem(which);
                part.setProgramSettings(item);
                loadDataToViews();
            }
        });
        builder.show();

    }

    public void setName(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(PartActivity.this);
        builder.setTitle(R.string.name);
        String defaultValue = part.getName();
        final EditText input = new EditText(PartActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                part.setName(newValue);
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

    public void setHours(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(PartActivity.this);
        builder.setTitle(R.string.time_printing_hours);
        String defaultValue = String.valueOf(part.getPrintHours());
        final EditText input = new EditText(PartActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                part.setPrintHours(Integer.parseInt(newValue));
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

    public void setMinutes(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(PartActivity.this);
        builder.setTitle(R.string.time_printing_minutes);
        String defaultValue = String.valueOf(part.getPrintMinutes());
        final EditText input = new EditText(PartActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                part.setPrintMinutes(Integer.parseInt(newValue));
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

    public void setLength(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(PartActivity.this);
        builder.setTitle(R.string.filament_length_mm);
        String defaultValue = String.valueOf(part.getFilamentLength());
        final EditText input = new EditText(PartActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                part.setFilamentLength(Integer.parseInt(newValue));
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