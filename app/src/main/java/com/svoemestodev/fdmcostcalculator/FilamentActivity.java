package com.svoemestodev.fdmcostcalculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.svoemestodev.fdmcostcalculator.adapters.ListPlasticAdapter;

public class FilamentActivity extends AppCompatActivity {

    AdView fl_ad_banner;
    TextView fl_tv_name_label;
    TextView fl_tv_name_value;
    ImageButton fl_ib_get_plastic;
    TextView fl_tv_plastic_label;
    TextView fl_tv_plastic_value;
    TextView fl_tv_color_label;
    TextView fl_tv_color_value;
    TextView fl_tv_diameter_label;
    TextView fl_tv_diameter_value;
    TextView fl_tv_spool_weight_label;
    TextView fl_tv_spool_weight_value;
    TextView fl_tv_spool_cost_label;
    TextView fl_tv_spool_cost_value;
    TextView fl_tv_spool_length_label;
    TextView fl_tv_spool_length_value;
    TextView fl_tv_spool_weight_one_meter_label;
    TextView fl_tv_spool_weight_one_meter_value;
    TextView fl_tv_spool_cost_one_kg_label;
    TextView fl_tv_spool_cost_one_kg_value;
    TextView fl_tv_spool_cost_one_meter_label;
    TextView fl_tv_spool_cost_one_meter_value;

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

        fl_tv_name_value.setText(filamentName);
        fl_tv_plastic_value.setText(plasticName);
        fl_tv_color_value.setText(colorName);
        fl_tv_diameter_value.setText(Utils.convertFloatToStringFormatter2digit(filament.getDiameter()));
        fl_tv_spool_weight_value.setText(Utils.convertFloatToStringFormatter3digit(filament.getSpoolWeight()));
        fl_tv_spool_cost_value.setText(Utils.convertFloatToStringFormatter2digit(filament.getSpoolCost()));
        fl_tv_spool_length_value.setText(Utils.convertFloatToStringFormatter3digit(filament.getSpoolLength()));
        fl_tv_spool_weight_one_meter_value.setText(Utils.convertFloatToStringFormatter2digit(filament.getWeightOneMeter()));
        fl_tv_spool_cost_one_kg_value.setText(Utils.convertFloatToStringFormatter2digit(filament.getCostOneKilogram()));
        fl_tv_spool_cost_one_meter_value.setText(Utils.convertFloatToStringFormatter2digit(filament.getCostOneMeter()));

    }

    private void initializeViews() {

        fl_ad_banner = findViewById(R.id.fl_ad_banner);
        fl_tv_name_label = findViewById(R.id.fl_tv_name_label);
        fl_tv_name_value = findViewById(R.id.fl_tv_name_value);
        fl_ib_get_plastic = findViewById(R.id.fl_ib_get_plastic);
        fl_tv_plastic_label = findViewById(R.id.fl_tv_plastic_label);
        fl_tv_plastic_value = findViewById(R.id.fl_tv_plastic_value);
        fl_tv_color_label = findViewById(R.id.fl_tv_color_label);
        fl_tv_color_value = findViewById(R.id.fl_tv_color_value);
        fl_tv_diameter_label = findViewById(R.id.fl_tv_diameter_label);
        fl_tv_diameter_value = findViewById(R.id.fl_tv_diameter_value);
        fl_tv_spool_weight_label = findViewById(R.id.fl_tv_spool_weight_label);
        fl_tv_spool_weight_value = findViewById(R.id.fl_tv_spool_weight_value);
        fl_tv_spool_cost_label = findViewById(R.id.fl_tv_spool_cost_label);
        fl_tv_spool_cost_value = findViewById(R.id.fl_tv_spool_cost_value);
        fl_tv_spool_length_label = findViewById(R.id.fl_tv_spool_length_label);
        fl_tv_spool_length_value = findViewById(R.id.fl_tv_spool_length_value);
        fl_tv_spool_weight_one_meter_label = findViewById(R.id.fl_tv_spool_weight_one_meter_label);
        fl_tv_spool_weight_one_meter_value = findViewById(R.id.fl_tv_spool_weight_one_meter_value);
        fl_tv_spool_cost_one_kg_label = findViewById(R.id.fl_tv_spool_cost_one_kg_label);
        fl_tv_spool_cost_one_kg_value = findViewById(R.id.fl_tv_spool_cost_one_kg_value);
        fl_tv_spool_cost_one_meter_label = findViewById(R.id.fl_tv_spool_cost_one_meter_label);
        fl_tv_spool_cost_one_meter_value = findViewById(R.id.fl_tv_spool_cost_one_meter_value);

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

        filament.save();
        super.onBackPressed();

    }

    public void getPlastic(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.select_plastic);

        final ListPlasticAdapter arrayAdapter = new ListPlasticAdapter(this);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Plastic item = arrayAdapter.getItem(which);
                filament.setPlastic(item);
                loadDataToViews();
            }
        });
        builder.show();

    }

    public void setColor(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(FilamentActivity.this);
        builder.setTitle(R.string.color);
        String defaultValue = filament.getColor();
        final EditText input = new EditText(FilamentActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                filament.setColor(newValue);
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

    public void setName(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(FilamentActivity.this);
        builder.setTitle(R.string.name);
        String defaultValue = filament.getName();
        final EditText input = new EditText(FilamentActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                filament.setName(newValue);
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

    public void setSpoolCost(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(FilamentActivity.this);
        builder.setTitle(R.string.spool_cost);
        String defaultValue = String.valueOf(filament.getSpoolCost());
        final EditText input = new EditText(FilamentActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                filament.setSpoolCost(Float.parseFloat(newValue));
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

    public void setSpoolWeight(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(FilamentActivity.this);
        builder.setTitle(R.string.spool_weight);
        String defaultValue = String.valueOf(filament.getSpoolWeight());
        final EditText input = new EditText(FilamentActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                filament.setSpoolWeight(Float.parseFloat(newValue));
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

    public void setDiameter(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(FilamentActivity.this);
        builder.setTitle(R.string.diameter);
        String defaultValue = String.valueOf(filament.getDiameter());
        final EditText input = new EditText(FilamentActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                filament.setDiameter(Float.parseFloat(newValue));
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