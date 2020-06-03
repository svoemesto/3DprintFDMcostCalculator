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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.svoemestodev.fdmcostcalculator.adapters.ListFilamentAdapter;
import com.svoemestodev.fdmcostcalculator.adapters.ListPartAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    Button pa_bt_add_new_part;
    AdView pa_ad_banner;
    TextView pa_tv_name_label;
    TextView pa_tv_name_value;
    TextView pa_tv_weight_label;
    TextView pa_tv_time_label;
    TextView pa_tv_time_value;
    TextView pa_tv_weight_value;
    TextView pa_tv_filament_cost_label;
    TextView pa_tv_filament_cost_value;
    TextView pa_tv_electric_cost_label;
    TextView pa_tv_electric_cost_value;
    TextView pa_tv_depreciation_cost_label;
    TextView pa_tv_depreciation_cost_value;
    TextView pa_tv_net_cost_label;
    TextView pa_tv_net_cost_value;
    TextView pa_tv_total_cost_label;
    TextView pa_tv_total_cost_value;
    TextView pa_tv_profit_cost_label;
    TextView pa_tv_profit_cost_value;
    ListView pa_lv_parts;

    public static Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

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

        String productName = product.getName() == null ? "N/A" : product.getName();

        pa_tv_name_value.setText(productName);
        pa_tv_time_value.setText(String.valueOf(product.getTimeString()));
        pa_tv_weight_value.setText(Utils.convertFloatToStringFormatter2digit(product.getWeight()));
        pa_tv_filament_cost_value.setText(Utils.convertFloatToStringFormatter2digit(product.getFilamentCost()));
        pa_tv_electric_cost_value.setText(Utils.convertFloatToStringFormatter2digit(product.getElectricityCost()));
        pa_tv_depreciation_cost_value.setText(Utils.convertFloatToStringFormatter2digit(product.getDepreciationCost()));
        pa_tv_net_cost_value.setText(Utils.convertFloatToStringFormatter2digit(product.getNetCost()));
        pa_tv_total_cost_value.setText(Utils.convertFloatToStringFormatter2digit(product.getTotalCost()));
        pa_tv_profit_cost_value.setText(Utils.convertFloatToStringFormatter2digit(product.getProfitCost()));
        pa_lv_parts.setAdapter(new ListProductsPartsAdapter(this));

    }

    private void initializeViews() {

        pa_ad_banner = findViewById(R.id.pa_ad_banner);
        pa_bt_add_new_part = findViewById(R.id.pa_bt_add_new_part);
        pa_tv_name_label = findViewById(R.id.pa_tv_name_label);
        pa_tv_name_value = findViewById(R.id.pa_tv_name_value);
        pa_tv_time_label = findViewById(R.id.pa_tv_time_label);
        pa_tv_time_value = findViewById(R.id.pa_tv_time_value);
        pa_tv_weight_label = findViewById(R.id.pa_tv_weight_label);
        pa_tv_weight_value = findViewById(R.id.pa_tv_weight_value);
        pa_tv_filament_cost_label = findViewById(R.id.pa_tv_filament_cost_label);
        pa_tv_filament_cost_value = findViewById(R.id.pa_tv_filament_cost_value);
        pa_tv_electric_cost_label = findViewById(R.id.pa_tv_electric_cost_label);
        pa_tv_electric_cost_value = findViewById(R.id.pa_tv_electric_cost_value);
        pa_tv_depreciation_cost_label = findViewById(R.id.pa_tv_depreciation_cost_label);
        pa_tv_depreciation_cost_value = findViewById(R.id.pa_tv_depreciation_cost_value);
        pa_tv_net_cost_label = findViewById(R.id.pa_tv_net_cost_label);
        pa_tv_net_cost_value = findViewById(R.id.pa_tv_net_cost_value);
        pa_tv_total_cost_label = findViewById(R.id.pa_tv_total_cost_label);
        pa_tv_total_cost_value = findViewById(R.id.pa_tv_total_cost_value);
        pa_tv_profit_cost_label = findViewById(R.id.pa_tv_profit_cost_label);
        pa_tv_profit_cost_value = findViewById(R.id.pa_tv_profit_cost_value);
        pa_lv_parts = findViewById(R.id.pa_lv_parts);

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

        product.save();
        super.onBackPressed();

    }

    public void addNewPart(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.select_part);

        final ListPartAdapter arrayAdapter = new ListPartAdapter(this);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Part item = arrayAdapter.getItem(which);
                List<Part> list = product.getParts();
                list.add(item);
                product.setParts(list);
                loadDataToViews();
            }
        });
        builder.show();

    }

    public void setName(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
        builder.setTitle(R.string.name);
        String defaultValue = product.getName();
        final EditText input = new EditText(ProductActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                product.setName(newValue);
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

    private class ListProductsPartsAdapter extends ArrayAdapter<Part> {

        public ListProductsPartsAdapter(@NonNull Context context) {
            super(context, R.layout.list_products_parts, product.getParts());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Part part = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_products_parts, null);
            }

            ImageButton lpp_bt_delete = convertView.findViewById(R.id.lpp_bt_delete);
            TextView lpp_tv_name_value = convertView.findViewById(R.id.lpp_tv_name_value);

            String partName = part.getName() == null ? "N/A" : part.getName();

            lpp_tv_name_value.setText(partName);

            lpp_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Part> list = new ArrayList<>();
                    for (Part item: product.getParts()) {
                        if (!item.getUuid().toString().equals(part.getUuid().toString())) {
                            list.add(item);
                        }
                    }
                    product.setParts(list);
                    loadDataToViews();;
                }
            });

            return convertView;

        }
    }

}