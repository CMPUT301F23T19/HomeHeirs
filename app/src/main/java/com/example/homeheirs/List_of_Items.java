package com.example.homeheirs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Locale;

public class List_of_Items extends ArrayAdapter<Item> {
    private ArrayList<Item> items;
    private Context context;

    public List_of_Items(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content, parent, false);
        }

        Item item = items.get(position);

        TextView name = view.findViewById(R.id.name);
        TextView purchase_date = view.findViewById(R.id.purchase_date);
        TextView description = view.findViewById(R.id.description);
        TextView estimated_value = view.findViewById(R.id.estimated_value);

        name.setText(item.getName());
        purchase_date.setText(String.format(Locale.getDefault(), "%d" + "-" + "%d", item.getPurchase_year(), item.getPurchase_month()));
        description.setText(item.getDescription());
        estimated_value.setText(String.format(Locale.getDefault(), "$%.2f", item.getEstimated_value()));

        return view;
    }
}