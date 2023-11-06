package com.example.homeheirs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AddItemFragment.OnFragmentInteractionListener {

    private ArrayList<Item> dataList;
    private ListView itemList;
    private ArrayAdapter<Item> itemAdapter;
    private TextView total_estimated_value;
    private FirebaseFirestore db;
    private CollectionReference itemsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

//        String[] items = {
//        };
//
//        int[] purchaseMonth = {
//        };
//
//        int[] purchaseYear = {
//        };
//
//        String[] description = {
//        };
//
//        String[] make = {
//        };
//
//        String[] model = {
//        };
//
//        int[] serialNumber = {
//        };
//
//        double[] estimatedValue = {
//        };
//
//        String[] comment = {
//        };

        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("items");

        dataList = new ArrayList<Item>();

        // for (int i = 0; i < items.length; i++) {
        //     Item item = new Item(items[i], purchaseMonth[i], purchaseYear[i], description[i], make[i], model[i], serialNumber[i], estimatedValue[i], comment[i]);
        //     dataList.add(item);
        // }

        itemList = findViewById(R.id.item_list);
        total_estimated_value = findViewById(R.id.total_value_amount);
        updateFullCost();

        itemAdapter = new List_of_Items(this, dataList);
        itemList.setAdapter(itemAdapter);

      // Clicking on an item calls the itemClickedHandler function
        itemList.setOnItemClickListener(itemClickedHandler);

        final FloatingActionButton addButton = findViewById(R.id.add_item_button);
        addButton.setOnClickListener( v -> {
            new AddItemFragment().show(getSupportFragmentManager(), "ADD_ITEM");
        });
    }

    // Adds a new item to the list, then updates the total value
    @Override
    public void onOKPressed(Item item) {
        dataList.add(item);
        itemAdapter.notifyDataSetChanged();
        updateFullCost();
    }

    // Edits the selected item in the list, then updates the total value
    @Override
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

        itemAdapter.notifyDataSetChanged();
        updateFullCost();
    }

    // Deletes the selected item from the list, then updates the total value
    @Override
    public void onDelete(Item item) {
        dataList.remove(item);
        itemAdapter.notifyDataSetChanged();
        updateFullCost();
    }

    //  Upon clicking an item in the list, allow user to edit the item
    private AdapterView.OnItemClickListener itemClickedHandler = (parent, view, position, id) -> {
        Item item = dataList.get(position);
        AddItemFragment addItemFragment = new AddItemFragment(item);
        addItemFragment.setTitle("Edit Item");
        addItemFragment.show(getSupportFragmentManager(), "EDIT_ITEM");
    };

    // Updates the total value of user's item list
    private void updateFullCost(){
        double total_estimated_value = 0;

        // Iterates through the existing list to get the total value
        for (int i = 0; i < dataList.size(); i++) {
            total_estimated_value += dataList.get(i).getEstimated_value();
        }

        this.total_estimated_value.setText(String.format(Locale.getDefault(), "$%.2f", total_estimated_value));
    }

}