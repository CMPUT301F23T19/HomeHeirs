package com.example.homeheirs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Item> item_data;

    private Context context;
    private ItemClickListener ClickListener;

    // data is passed into the constructor and we initialize our list of items
    RecyclerViewAdapter(Context context, ArrayList<Item> data) {
        this.context = context;
        this.item_data = data;
    }

    // inflates the content.xml file as that represents each row
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.content, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //commented out code is due to the a change in the content displayed
        Item item = item_data.get(position);
        holder.name.setText(item.getName());
       // holder.purchase_month.setText(String.valueOf(item.getPurchase_month()));
        holder.purchase_year.setText(String.valueOf(item.getPurchase_year()));
        holder.description.setText(item.getDescription());
        //holder.make.setText(item.getMake());
        //holder.model.setText(item.getModel());
        //holder.serial_number.setText(String.valueOf(item.getSerial_number()));
        holder.estimated_value.setText(String.valueOf(item.getEstimated_value()));
        //holder.comment.setText(item.getComment());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return item_data.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView name ;
        TextView purchase_month ;
        TextView purchase_year ;
        TextView description ;
        TextView make ;
        TextView model ;
        TextView serial_number;
        TextView estimated_value;
        TextView comment ;

        ViewHolder(View itemView) {
            super(itemView);
             name = itemView.findViewById(R.id.name);
             //purchase_month = itemView.findViewById(R.id.purchase_month);
             purchase_year = itemView.findViewById(R.id.purchase_date);
             description = itemView.findViewById(R.id.description);
             //make = itemView.findViewById(R.id.make);
             //model = itemView.findViewById(R.id.model);
             //serial_number = itemView.findViewById(R.id.serial_number);
             estimated_value = itemView.findViewById(R.id.estimated_value);
             //comment = itemView.findViewById(R.id.comment);
            itemView.setOnClickListener(this);
        }




        @Override
        public void onClick(View view) {

            if (ClickListener != null) ClickListener.onItemClick(view, getAdapterPosition());
        }


    }

    // may need this method to retrieve data once clicked
    Item getItem(int id) {
        return item_data.get(id);
    }

    // allows clicks events to be caught- will be using this to launch the new activity
    void setClickListener(ItemClickListener itemClickListener) {
        this.ClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
