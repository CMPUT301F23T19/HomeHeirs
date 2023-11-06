package com.example.homeheirs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, AddItemFragment.OnFragmentInteractionListener {

    private ArrayList<Item> dataList;
    private RecyclerView itemList;
    private RecyclerViewAdapter recycleAdapter;
    private ArrayAdapter<Item> itemAdapter;
    private TextView total_estimated_value;
    private FirebaseFirestore db;
    private CollectionReference itemsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);



        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("items");


        // initialize the array and set up the Array Adapter with recycle view
        dataList = new ArrayList<>();
        itemList = findViewById(R.id.item_list);
        itemList.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new RecyclerViewAdapter(this,dataList);
        recycleAdapter.setClickListener( this);
        itemList.setAdapter(recycleAdapter);


        total_estimated_value = findViewById(R.id.total_value_amount);

        // Implement the swipe feature that will launch dialog box
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(itemList);

        FloatingActionButton addButton = findViewById(R.id.add_item_button);
        addButton.setOnClickListener( v -> {
            new AddItemFragment().show(getSupportFragmentManager(), "ADD_EXPENSE");
        });
    }






    @Override
    public void onItemClick(View view, int position) {
        Item item = dataList.get(position);
        AddItemFragment addItemFragment = new AddItemFragment(item);
        addItemFragment.setTitle("Edit item");
        addItemFragment.show(getSupportFragmentManager(), "EDIT_ITEM");
    }


    public void onOKPressed(Item item) {
        dataList.add(item);
        recycleAdapter.notifyDataSetChanged();
        updateFullCost();
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
        updateFullCost();
    }


    public void onDelete(Item item) {
        dataList.remove(item);
        recycleAdapter.notifyDataSetChanged();
        updateFullCost();
    }

    //  Upon clicking a city in the list, see what city it is and edit
//    private AdapterView.OnItemClickListener itemClickedHandler = (parent, view, position, id) -> {
//        Item item = dataList.get(position);
//        AddItemFragment addItemFragment = new AddItemFragment(item);
//        addItemFragment.setTitle("Edit item");
//        addItemFragment.show(getSupportFragmentManager(), "EDIT_ITEM");
//    };

    private void updateFullCost(){
        double total_estimated_value = 0.00;

        for (int i = 0; i < dataList.size(); i++) {
            total_estimated_value += dataList.get(i).getEstimated_value();
        }

        this.total_estimated_value.setText(String.format(Locale.getDefault(), " %.2f", total_estimated_value));
    }

}