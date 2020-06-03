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

public class ListProductsActivity extends AppCompatActivity {

    AdView lpr_ad_banner;
    Button lpr_bt_add_new_product;
    ListView lpr_lv_products;

    private static final int REQUEST_CODE_PRODUCT_ACTIVITY = 100;

    private static List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);

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
        lpr_ad_banner.loadAd(adRequest);


    }


    private void initializeViews() {
        lpr_ad_banner = findViewById(R.id.lpr_ad_banner);
        lpr_bt_add_new_product = findViewById(R.id.lpr_bt_add_new_product);
        lpr_lv_products = findViewById(R.id.lpr_lv_products);
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

        if (requestCode == REQUEST_CODE_PRODUCT_ACTIVITY) {
            displayRecords();
        }

    }

    private void displayRecords() {
        products = Product.loadList();
        lpr_lv_products.setAdapter(new ListProductsAdapter(this));
    }

    public void addNewProduct(View view) {

        Product product = new Product();
        product.setName("Product #" + (products.size()+1));
        ProductActivity.product = product;
        Intent intent = new Intent(this, ProductActivity.class);
        startActivityForResult(intent, REQUEST_CODE_PRODUCT_ACTIVITY);

    }

    private class ListProductsAdapter extends ArrayAdapter<Product> {

        public ListProductsAdapter(@NonNull Context context) {
            super(context, R.layout.list_products, products);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Product product = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_products, null);
            }

            ImageButton lpr_bt_delete = convertView.findViewById(R.id.lpr_bt_delete);
            ImageButton lpr_bt_edit = convertView.findViewById(R.id.lpr_bt_edit);
            TextView lpr_tv_name_value = convertView.findViewById(R.id.lpr_tv_name_value);
            TextView lpr_tv_count_parts_value = convertView.findViewById(R.id.lpr_tv_count_parts_value);
            TextView lpr_tv_weight_value = convertView.findViewById(R.id.lpr_tv_weight_value);
            TextView lpr_tv_electric_cost_value = convertView.findViewById(R.id.lpr_tv_electric_cost_value);
            TextView lpr_tv_depreciation_cost_value = convertView.findViewById(R.id.lpr_tv_depreciation_cost_value);
            TextView lpr_tv_filament_cost_value = convertView.findViewById(R.id.lpr_tv_filament_cost_value);
            TextView lpr_tv_net_cost_value = convertView.findViewById(R.id.lpr_tv_net_cost_value);
            TextView lpr_tv_total_cost_value = convertView.findViewById(R.id.lpr_tv_total_cost_value);
            TextView lpr_tv_profit_cost_value = convertView.findViewById(R.id.lpr_tv_profit_cost_value);

            String productName = product.getName() == null ? "N/A" : product.getName();

            lpr_tv_name_value.setText(productName);
            lpr_tv_count_parts_value.setText(Utils.convertIntToStringFormatter(product.getPartsCount()));
            lpr_tv_weight_value.setText(Utils.convertFloatToStringFormatter2digit(product.getWeight()));
            lpr_tv_electric_cost_value.setText(Utils.convertFloatToStringFormatter2digit(product.getElectricityCost()));
            lpr_tv_depreciation_cost_value.setText(Utils.convertFloatToStringFormatter2digit(product.getDepreciationCost()));
            lpr_tv_filament_cost_value.setText(Utils.convertFloatToStringFormatter2digit(product.getFilamentCost()));
            lpr_tv_net_cost_value.setText(Utils.convertFloatToStringFormatter2digit(product.getNetCost()));
            lpr_tv_total_cost_value.setText(Utils.convertFloatToStringFormatter2digit(product.getTotalCost()));
            lpr_tv_profit_cost_value.setText(Utils.convertFloatToStringFormatter2digit(product.getProfitCost()));

            lpr_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product.delete();
                    displayRecords();
                }
            });

            lpr_bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductActivity.product = product;
                    Intent intent = new Intent(ListProductsActivity.this, ProductActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_PRODUCT_ACTIVITY);
                }
            });

            return convertView;

        }
    }

}