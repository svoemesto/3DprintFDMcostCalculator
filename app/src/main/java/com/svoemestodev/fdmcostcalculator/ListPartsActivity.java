package com.svoemestodev.fdmcostcalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;


public class ListPartsActivity extends AppCompatActivity {

    AdView lpa_ad_banner;
    Button lpa_bt_add_new_part;
    ListView lpa_lv_parts;
    private static final int REQUEST_CODE_PART_ACTIVITY = 100;

    private static List<Part> parts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_parts);

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
        lpa_ad_banner.loadAd(adRequest);


    }


    private void initializeViews() {
        lpa_ad_banner = findViewById(R.id.lpa_ad_banner);
        lpa_bt_add_new_part = findViewById(R.id.lpa_bt_add_new_part);
        lpa_lv_parts = findViewById(R.id.lpa_lv_parts);
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

        if (requestCode == REQUEST_CODE_PART_ACTIVITY) {
            displayRecords();
        }

    }

    private void displayRecords() {
        parts = Part.loadList();
        lpa_lv_parts.setAdapter(new ListPartsAdapter(this));
    }

    public void addNewPart(View view) {

        Part part = new Part();
        part.setName("Part #" + (parts.size()+1));
        PartActivity.part = part;
        Intent intent = new Intent(this, PartActivity.class);
        startActivityForResult(intent, REQUEST_CODE_PART_ACTIVITY);

    }

    private class ListPartsAdapter extends ArrayAdapter<Part> {

        public ListPartsAdapter(@NonNull Context context) {
            super(context, R.layout.list_parts, parts);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Part part = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_parts, null);
            }

            ImageButton lp_bt_delete = convertView.findViewById(R.id.lp_bt_delete);
            ImageButton lp_bt_edit = convertView.findViewById(R.id.lp_bt_edit);
            TextView lp_tv_name_value = convertView.findViewById(R.id.lp_tv_name_value);
            TextView lp_tv_filament_value = convertView.findViewById(R.id.lp_tv_filament_value);
            TextView lp_tv_settings_value = convertView.findViewById(R.id.lp_tv_settings_value);
            TextView lp_tv_time_value = convertView.findViewById(R.id.lp_tv_time_value);
            TextView lp_tv_length_value = convertView.findViewById(R.id.lp_tv_length_value);
            TextView lp_tv_weight_value = convertView.findViewById(R.id.lp_tv_weight_value);
            TextView lp_tv_electric_cost_value = convertView.findViewById(R.id.lp_tv_electric_cost_value);
            TextView lp_tv_depreciation_cost_value = convertView.findViewById(R.id.lp_tv_depreciation_cost_value);
            TextView lp_tv_filament_cost_value = convertView.findViewById(R.id.lp_tv_filament_cost_value);
            TextView lp_tv_net_cost_value = convertView.findViewById(R.id.lp_tv_net_cost_value);
            TextView lp_tv_total_cost_value = convertView.findViewById(R.id.lp_tv_total_cost_value);
            TextView lp_tv_profit_cost_value = convertView.findViewById(R.id.lp_tv_profit_cost_value);

            String partName = part.getName() == null ? "N/A" : part.getName();
            String filamentName = part.getFilament() == null ? "N/A" : part.getFilament().getName();
            String settingName = part.getProgramSettings() == null ? "N/A" : part.getProgramSettings().getName();

            lp_tv_name_value.setText(partName);
            lp_tv_filament_value.setText(filamentName);
            lp_tv_settings_value.setText(settingName);
            lp_tv_time_value.setText(part.getTimeString());
            lp_tv_length_value.setText(String.valueOf(part.getFilamentLength()));
            lp_tv_weight_value.setText(String.valueOf(part.getWeight()));
            lp_tv_electric_cost_value.setText(String.valueOf(part.getElectricityCost()));
            lp_tv_depreciation_cost_value.setText(String.valueOf(part.getDepreciationCost()));
            lp_tv_filament_cost_value.setText(String.valueOf(part.getFilamentCost()));
            lp_tv_net_cost_value.setText(String.valueOf(part.getNetCost()));
            lp_tv_total_cost_value.setText(String.valueOf(part.getTotalCost()));
            lp_tv_profit_cost_value.setText(String.valueOf(part.getProfitCost()));

            lp_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    part.delete();
                    displayRecords();
                }
            });

            lp_bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PartActivity.part = part;
                    Intent intent = new Intent(ListPartsActivity.this, PartActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_PART_ACTIVITY);
                }
            });

            return convertView;

        }
    }

}