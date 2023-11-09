package com.example.homeheirs;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, AddItemFragment.OnFragmentInteractionListener {

    private ArrayList<Item> dataList;
    private RecyclerView itemList;
    private RecyclerViewAdapter recycleAdapter;
    private ArrayAdapter<Item> itemAdapter;
    private TextView total_estimated_value;
    private FirebaseFirestore db;
    private CollectionReference itemsRef;
    // will use to have a conditional button listener


    // Class through which database interactions should be handled
    private FirebaseOperations firebaseOperations;

    private LinearLayout custom_bar ;
    private LinearLayout original_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        custom_bar = findViewById(R.id.hidden_toolbar);
        original_bar=findViewById(R.id.custom_toolbar   );



        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("items");
        total_estimated_value = findViewById(R.id.total_value_amount);

        firebaseOperations = new FirebaseOperations(total_estimated_value);
        dataList= firebaseOperations.get_dataList();



        // initialize the array and set up the Array Adapter with recycle view
       // dataList = new ArrayList<>();
        itemList = findViewById(R.id.item_list);
        itemList.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new RecyclerViewAdapter(this,itemList,dataList);
        recycleAdapter.setClickListener( this);
        itemList.setAdapter(recycleAdapter);

        firebaseOperations.setAdapter(recycleAdapter);

        //firebaseOperations = new FirebaseOperations(recycleAdapter);







        firebaseOperations.listenForDataChanges();
        dataList = firebaseOperations.get_dataList();
        recycleAdapter.notifyDataSetChanged();



       //dataList = firebaseOperations.get_dataList();
        Log.i("what the fuck","boi"+ dataList);
       updateFullCost();
        //double estimated_value = firebaseOperations.updateFullCost();
        //this.total_estimated_value.setText(String.format(Locale.getDefault(), "$%.2f", estimated_value));
       // updateFullCost();








        // Implement the swipe feature that will launch dialog box
        //Create listener for the add tag button
        Button addTagButton = findViewById(R.id.mutliple_tag_button);
        addTagButton.setOnClickListener(v -> {
            new AddTagFragment().show(getSupportFragmentManager(), "ADD_TAG");

        });


        FloatingActionButton addButton = findViewById(R.id.add_item_button);
        addButton.setOnClickListener( v -> {
            new AddItemFragment().show(getSupportFragmentManager(), "ADD_EXPENSE");
        });

        Button delSelButton = findViewById(R.id.delete_button);
        delSelButton.setOnClickListener(v -> {

            delSelectedItems(dataList);

            //recycleAdapter.resetLongClickState();





        });

    }






    @Override
    public void onItemClick(View view, int position) {
        Item item = dataList.get(position);
        AddItemFragment addItemFragment = new AddItemFragment(item);
        addItemFragment.setTitle("Edit item");
        addItemFragment.show(getSupportFragmentManager(), "EDIT_ITEM");
    }

    // function to notify longclick - for longclick--delete functionality
    public boolean onItemLongClick(View view,int position){
        Item item = dataList.get(position);

        return true;
    }

    public void delSelectedItems(ArrayList<Item> dataList){


        ArrayList<Item> selectedItems = recycleAdapter.getSelected_items();
        for (int i=0;i<selectedItems.size();i++){

            dataList.remove(selectedItems.get(i));
            recycleAdapter.notifyDataSetChanged();
        }

        firebaseOperations.deleteData(selectedItems);

        // Unselect everything once we press ok
        recycleAdapter.resetSelected_items();

        recycleAdapter.resetLongClickState();
        recycleAdapter.notifyDataSetChanged();
        //recycleAdapter.notifyDataSetChanged();
    }








    public void onOKPressed(Item item) {
        //dataList.add(item);
        //recycleAdapter.notifyDataSetChanged();
        //updateFullCost();

        firebaseOperations.addData(item);
        //double estimated_value = firebaseOperations.updateFullCost();
        //this.total_estimated_value.setText(String.format(Locale.getDefault(), "$%.2f", estimated_value));
       // firebaseOperations.listenForDataChanges();
        //recycleAdapter.notifyDataSetChanged();
    }

    //  When u press ok upon editing the expense and province it sets the name and province to what is in the edit text

    public void onOkPressedEdit(Item item, String newName, String newPurchase_month, String newPurchase_year, String newDescription, String newMake, String newModel, String newSerial_number, String newEstimated_value, String newComment) {
        item.setName(newName);
        item.setPurchase_month(Integer.parseInt(newPurchase_month));
        item.setPurchase_year(Integer.parseInt(newPurchase_year));
        item.setDescription(newDescription);
        item.setMake(newMake);
        item.setModel(newModel);
        item.setSerial_number(Integer.parseInt(newSerial_number));
        item.setEstimated_value(Double.parseDouble(newEstimated_value));
        item.setComment(newComment);

        recycleAdapter.notifyDataSetChanged();
        //updateFullCost();
    }


//    public void onDelete(Item item) {
//        firebaseOperations.deleteData(item);
//       // double estimated_value = firebaseOperations.updateFullCost();
//        //this.total_estimated_value.setText(String.format(Locale.getDefault(), "$%.2f", estimated_value));
//        //updateFullCost();
//    }

    // We should have a list of the items to edit-make this change
    @Override
    public void onTagOKPressed( Tag tag) {
        // Method iterates through selected items and adds the tag
        // Method does not update dataList which should be checked!

        // Retreieve selected items and append the tag object to the item
        ArrayList<Item> selectedItems = recycleAdapter.getSelected_items();
        for (int i=0;i<selectedItems.size();i++){

            selectedItems.get(i).add_tag(tag);
            // Also want to update the database


        }
        firebaseOperations.addtag(selectedItems);
        // Unselect everything once we press ok
        recycleAdapter.resetSelected_items();


        // Also need to reset the selected items

    }



    private void updateFullCost(){
        double total_estimated_value = 0;


        for (int i = 0; i < dataList.size(); i++) {
            total_estimated_value += dataList.get(i).getEstimated_value();
        }

        this.total_estimated_value.setText(String.format(Locale.getDefault(), "$%.2f", total_estimated_value));
    }


    //method changes visibility of the toolbox to switch bewteen the orginial toolbox,
    //and one showing the add and delete fuctionality
    public void showcustomtool(boolean replace) {



        if(replace){

           //
            //if item is long clicked, we set the make our custom bar visible to show the delete and add multiple tags
            original_bar.setVisibility(View.INVISIBLE);

            custom_bar.setVisibility(View.VISIBLE);

        }
        else{
           // multiple_selection=false;
            // When we are done with selecting, we revert to original settings
            original_bar.setVisibility(View.VISIBLE);
            custom_bar.setVisibility(View.INVISIBLE);
        }
    }
}