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

public class ListPlasticActivity extends AppCompatActivity {

    AdView lpl_ad_banner;
    Button lpl_bt_add_new_plastic;
    ListView lpl_lv_plastics;

    private static final int REQUEST_CODE_PLASTIC_ACTIVITY = 100;

    private static List<Plastic> plastics = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plastic);

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
        lpl_ad_banner.loadAd(adRequest);


    }


    private void initializeViews() {
        lpl_ad_banner = findViewById(R.id.lpl_ad_banner);
        lpl_bt_add_new_plastic = findViewById(R.id.lpl_bt_add_new_plastic);
        lpl_lv_plastics = findViewById(R.id.lpl_lv_plastics);
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

        if (requestCode == REQUEST_CODE_PLASTIC_ACTIVITY) {
            displayRecords();
        }

    }

    private void displayRecords() {
        plastics = Plastic.loadList();
        lpl_lv_plastics.setAdapter(new ListPlasticsAdapter(this));
    }

    public void addNewPlastic(View view) {

        Plastic plastic = new Plastic();
        plastic.setName("Plastic #" + (plastics.size()+1));
        PlasticActivity.plastic = plastic;
        Intent intent = new Intent(this, PlasticActivity.class);
        startActivityForResult(intent, REQUEST_CODE_PLASTIC_ACTIVITY);

    }

    private class ListPlasticsAdapter extends ArrayAdapter<Plastic> {

        public ListPlasticsAdapter(@NonNull Context context) {
            super(context, R.layout.list_plastic, plastics);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Plastic plastic = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_plastic, null);
            }

            ImageButton lpl_bt_delete = convertView.findViewById(R.id.lpl_bt_delete);
            ImageButton lpl_bt_edit = convertView.findViewById(R.id.lpl_bt_edit);
            TextView lpl_tv_name_value = convertView.findViewById(R.id.lpl_tv_name_value);
            TextView lpl_tv_density_value = convertView.findViewById(R.id.lpl_tv_density_value);

            String plasticName = plastic.getName() == null ? "N/A" : plastic.getName();

            lpl_tv_name_value.setText(plasticName);
            lpl_tv_density_value.setText(String.valueOf(plastic.getDensity()));

            lpl_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plastic.delete();
                    displayRecords();
                }
            });

            lpl_bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlasticActivity.plastic = plastic;
                    Intent intent = new Intent(ListPlasticActivity.this, PlasticActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_PLASTIC_ACTIVITY);
                }
            });

            return convertView;

        }
    }

}