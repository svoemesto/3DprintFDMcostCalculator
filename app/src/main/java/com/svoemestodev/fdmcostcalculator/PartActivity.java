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

public class PartActivity extends AppCompatActivity {

    AdView pa_ad_banner;
    TextView pa_tv_name_label;
    EditText pa_et_name_value;
    ImageButton pa_ib_get_filament;
    TextView pa_tv_filament_label;
    TextView pa_tv_filament_value;
    ImageButton pa_ib_get_settings;
    TextView pa_tv_settings_label;
    TextView pa_tv_settings_value;
    TextView pa_tv_hours_label;
    EditText pa_et_hours_value;
    TextView pa_tv_minutes_label;
    EditText pa_et_minutes_value;
    TextView pa_tv_length_label;
    EditText pa_et_length_value;
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

        pa_et_name_value.setText(partName);
        pa_tv_filament_value.setText(filamentName);
        pa_tv_settings_value.setText(settingName);
        pa_et_hours_value.setText(String.valueOf(part.getPrintHours()));
        pa_et_minutes_value.setText(String.valueOf(part.getPrintMinutes()));
        pa_et_length_value.setText(String.valueOf(part.getFilamentLength()));
        pa_tv_weight_value.setText(String.valueOf(part.getWeight()));
        pa_tv_electric_cost_value.setText(String.valueOf(part.getElectricityCost()));
        pa_tv_depreciation_cost_value.setText(String.valueOf(part.getDepreciationCost()));
        pa_tv_filament_cost_value.setText(String.valueOf(part.getFilamentCost()));
        pa_tv_net_cost_value.setText(String.valueOf(part.getNetCost()));
        pa_tv_total_cost_value.setText(String.valueOf(part.getTotalCost()));
        pa_tv_profit_cost_value.setText(String.valueOf(part.getProfitCost()));
        
    }

    private void initializeViews() {
        pa_ad_banner = findViewById(R.id.pa_ad_banner);
        pa_tv_name_label = findViewById(R.id.pa_tv_name_label);
        pa_et_name_value = findViewById(R.id.pa_et_name_value);
        pa_ib_get_filament = findViewById(R.id.pa_ib_get_filament);
        pa_tv_filament_label = findViewById(R.id.pa_tv_filament_label);
        pa_tv_filament_value = findViewById(R.id.pa_tv_filament_value);
        pa_ib_get_settings = findViewById(R.id.pa_ib_get_settings);
        pa_tv_settings_label = findViewById(R.id.pa_tv_settings_label);
        pa_tv_settings_value = findViewById(R.id.pa_tv_settings_value);
        pa_tv_hours_label = findViewById(R.id.pa_tv_hours_label);
        pa_et_hours_value = findViewById(R.id.pa_et_hours_value);
        pa_tv_minutes_label = findViewById(R.id.pa_tv_minutes_label);
        pa_et_minutes_value = findViewById(R.id.pa_et_minutes_value);
        pa_tv_length_label = findViewById(R.id.pa_tv_length_label);
        pa_et_length_value = findViewById(R.id.pa_et_length_value);
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

        setValues();
        part.save();
        super.onBackPressed();

    }

    private void setValues() {

        part.setName(pa_et_name_value.getText().toString());
        part.setPrintHours(Integer.parseInt(pa_et_hours_value.getText().toString()));
        part.setPrintMinutes(Integer.parseInt(pa_et_minutes_value.getText().toString()));
        part.setFilamentLength(Integer.parseInt(pa_et_length_value.getText().toString()));

        loadDataToViews();

    }

    public void getFilament(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Select filament");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        for (Filament filament: Filament.loadList()) {
            arrayAdapter.add(filament.getName());
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
                part.setFilament(Filament.getItemByName(strName));
                loadDataToViews();
            }
        });
        builder.show();

    }

    public void getSettings(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Select settings");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        for (ProgramSettings programSettings: ProgramSettings.loadList()) {
            arrayAdapter.add(programSettings.getName());
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
                part.setProgramSettings(ProgramSettings.getItemByName(strName));
                loadDataToViews();
            }
        });
        builder.show();

    }
}