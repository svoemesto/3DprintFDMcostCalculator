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
import java.util.Locale;

public class ListFilamentActivity extends AppCompatActivity {

    AdView lfl_ad_banner;
    Button lfl_bt_add_new_filament;
    ListView lfl_lv_filaments;

    private static final int REQUEST_CODE_FILAMENT_ACTIVITY = 100;

    private static List<Filament> filaments = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_filament);


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
        lfl_ad_banner.loadAd(adRequest);


    }


    private void initializeViews() {
        lfl_ad_banner = findViewById(R.id.lfl_ad_banner);
        lfl_bt_add_new_filament = findViewById(R.id.lfl_bt_add_new_filament);
        lfl_lv_filaments = findViewById(R.id.lfl_lv_filaments);
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

        if (requestCode == REQUEST_CODE_FILAMENT_ACTIVITY) {
            displayRecords();
        }

    }

    private void displayRecords() {
        filaments = Filament.loadList();
        lfl_lv_filaments.setAdapter(new ListFilamentsAdapter(this));
    }

    public void addNewFilament(View view) {

        Filament filament = new Filament();
        filament.setName("Filament #" + (filaments.size()+1));
        FilamentActivity.filament = filament;
        Intent intent = new Intent(this, FilamentActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FILAMENT_ACTIVITY);

    }

    private class ListFilamentsAdapter extends ArrayAdapter<Filament> {

        public ListFilamentsAdapter(@NonNull Context context) {
            super(context, R.layout.list_filament, filaments);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Filament filament = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_filament, null);
            }

            ImageButton lfl_bt_delete = convertView.findViewById(R.id.lfl_bt_delete);
            ImageButton lfl_bt_edit = convertView.findViewById(R.id.lfl_bt_edit);
            TextView lfl_tv_name_value = convertView.findViewById(R.id.lfl_tv_name_value);
            TextView lfl_tv_plastic_value = convertView.findViewById(R.id.lfl_tv_plastic_value);
            TextView lfl_tv_color_value = convertView.findViewById(R.id.lfl_tv_color_value);
            TextView lfl_tv_diameter_value = convertView.findViewById(R.id.lfl_tv_diameter_value);
            TextView lfl_tv_spool_weight_value = convertView.findViewById(R.id.lfl_tv_spool_weight_value);
            TextView lfl_tv_spool_cost_value = convertView.findViewById(R.id.lfl_tv_spool_cost_value);
            TextView lfl_et_spool_length_value = convertView.findViewById(R.id.lfl_et_spool_length_value);
            TextView lfl_et_spool_weight_one_meter_value = convertView.findViewById(R.id.lfl_et_spool_weight_one_meter_value);
            TextView lfl_et_spool_cost_one_kg_value = convertView.findViewById(R.id.lfl_et_spool_cost_one_kg_value);
            TextView lfl_et_spool_cost_one_meter_value = convertView.findViewById(R.id.lfl_et_spool_cost_one_meter_value);

            String filamentName = filament.getName() == null ? "N/A" : filament.getName();
            String plasticName = filament.getPlastic() == null ? "N/A" : filament.getPlastic().getName();
            String colorName = filament.getColor() == null ? "N/A" : filament.getColor();

            lfl_tv_name_value.setText(filamentName);
            lfl_tv_plastic_value.setText(plasticName);
            lfl_tv_color_value.setText(colorName);
            lfl_tv_diameter_value.setText(Utils.convertFloatToStringFormatter2digit(filament.getDiameter()));
            lfl_tv_spool_weight_value.setText(Utils.convertFloatToStringFormatter3digit(filament.getSpoolWeight()));
            lfl_tv_spool_cost_value.setText(Utils.convertFloatToStringFormatter2digit(filament.getSpoolCost()));
            lfl_et_spool_length_value.setText(Utils.convertFloatToStringFormatter3digit(filament.getSpoolLength()));
            lfl_et_spool_weight_one_meter_value.setText(Utils.convertFloatToStringFormatter2digit(filament.getWeightOneMeter()));
            lfl_et_spool_cost_one_kg_value.setText(Utils.convertFloatToStringFormatter2digit(filament.getCostOneKilogram()));
            lfl_et_spool_cost_one_meter_value.setText(Utils.convertFloatToStringFormatter2digit(filament.getCostOneMeter()));

            lfl_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filament.delete();
                    displayRecords();
                }
            });

            lfl_bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FilamentActivity.filament = filament;
                    Intent intent = new Intent(ListFilamentActivity.this, FilamentActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_FILAMENT_ACTIVITY);
                }
            });

            return convertView;

        }
    }

}