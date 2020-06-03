package com.svoemestodev.fdmcostcalculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.svoemestodev.fdmcostcalculator.Part;
import com.svoemestodev.fdmcostcalculator.R;

public class ListPartAdapter extends ArrayAdapter<Part> {

    public ListPartAdapter(@NonNull Context context) {
        super(context, R.layout.select_item, Part.loadList());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Part item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.select_item, null);
        }

        TextView select_item_name = convertView.findViewById(R.id.select_item_name);
        String itemName = item.getName() == null ? "N/A" : item.getName();

        select_item_name.setText(itemName);

        return convertView;

    }

}
