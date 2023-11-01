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
        TextView purchase_month = view.findViewById(R.id.purchase_month);
        TextView purchase_year = view.findViewById(R.id.purchase_year);
        TextView description = view.findViewById(R.id.description);
        TextView make = view.findViewById(R.id.make);
        TextView model = view.findViewById(R.id.model);
        TextView serial_number = view.findViewById(R.id.serial_number);
        TextView estimated_value = view.findViewById(R.id.estimated_value);
        TextView comment = view.findViewById(R.id.comment);

        name.setText(item.getName());
        purchase_month.setText(String.valueOf(item.getPurchase_month()));
        purchase_year.setText(String.valueOf(item.getPurchase_year()));
        description.setText(item.getDescription());
        make.setText(item.getMake());
        model.setText(item.getModel());
        serial_number.setText(String.valueOf(item.getSerial_number()));
        estimated_value.setText(String.format(Locale.getDefault(), "%.2f", item.getEstimated_value()));
        comment.setText(item.getComment());

        return view;
    }
}