package com.example.homeheirs;

import android.app.assist.AssistStructure;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Item> item_data;




    private ArrayList<Item> selected_items= new ArrayList<>();

    private Context context;

    private ItemClickListener ClickListener;

    private Boolean is_long_clicked = false;

    // Required for resetting selected items
    private RecyclerView recyclerView;



    // data is passed into the constructor and we initialize our list of items
    RecyclerViewAdapter(Context context,RecyclerView recyclerView1, ArrayList<Item> data) {
        this.context = context;
        this.item_data = data;
        this.recyclerView = recyclerView1;

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
        holder.purchase_date.setText(String.format(Locale.getDefault(), "%d" + "-" + "%d", item.getPurchase_year(), item.getPurchase_month()));
        holder.description.setText(item.getDescription());
        //holder.make.setText(item.getMake());
        //holder.model.setText(item.getModel());
        //holder.serial_number.setText(String.valueOf(item.getSerial_number()));
        holder.estimated_value.setText(String.format(Locale.getDefault(), "$%.2f", item.getEstimated_value()));
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
        TextView purchase_date ;
        TextView description ;
        TextView make ;
        TextView model ;
        TextView serial_number;
        TextView estimated_value;
      TextView comment ;

        ViewHolder(View itemView) {
            super(itemView);
            //will make a method for this later

            //Long click listener will detect long holds, and will allow for implementation of adding/deleting multiple items
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    is_long_clicked = true;
                    //implement showcustomtool in main activity
                    ((MainActivity) context).showcustomtool(is_long_clicked);
                    if(selected_items.contains(item_data.get(getAdapterPosition()))){
                        // change color back to original if it has already been selected
                        itemView.setBackgroundColor(Color.TRANSPARENT);
                        selected_items.remove(item_data.get(getAdapterPosition()));
                    }else{ // if item is selected , add it's position to selected_items list
                        itemView.setBackgroundResource((R.color.selected_color));
                        selected_items.add(item_data.get(getAdapterPosition()));
                    }
                    // if no items selected, we show the original toolbox
                    if (selected_items.size()==0){
                        is_long_clicked=false;
                        ((MainActivity) context).showcustomtool(is_long_clicked);

                    }
                    return true;
                }
            });


             name= itemView.findViewById(R.id.name);
             purchase_date = itemView.findViewById(R.id.purchase_date);
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

            if (ClickListener != null) {
                // implement what happens if we are in selection mode, will try to condense this class later on
                if(is_long_clicked){

                    if(selected_items.contains(item_data.get(getAdapterPosition()))){
                        itemView.setBackgroundColor(Color.TRANSPARENT);
                        selected_items.remove(item_data.get(getAdapterPosition()));
                    }else{
                        itemView.setBackgroundResource((R.color.selected_color));
                        selected_items.add(item_data.get(getAdapterPosition()));

                    }
                    if (selected_items.size()==0){
                        is_long_clicked=false;
                        ((MainActivity) context).showcustomtool(is_long_clicked);


                    }
                }else{
                    // calls on itemclick method in Main Activity if not in selection mode- ie to display item details
                    ClickListener.onItemClick(view, getAdapterPosition());
                }

            }

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

    public ArrayList<Item> getSelected_items(){
        // use this method to retrieve all current selected items
        // Use for deleting items and adding tags to items


        return selected_items;


        }



    public void resetSelected_items(){
        // use this to reset all selected items on the screen
        for (int i=0;i<selected_items.size();i++){
            //get the actual object then we find the position of that object withing the recycle view
            Item selectedItems = selected_items.get(i);
            int recycle_position = item_data.indexOf(selectedItems);
            // if the item is not found, it will return -1
            if (recycle_position != -1) {

                View itemView = recyclerView.getChildAt(recycle_position);
                if (itemView != null) {
                    itemView.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        }

        //clear and set the long clicked to false- and show original toolbar
        selected_items.clear();
        is_long_clicked=false;
        ((MainActivity) context).showcustomtool(is_long_clicked);

    }

}


