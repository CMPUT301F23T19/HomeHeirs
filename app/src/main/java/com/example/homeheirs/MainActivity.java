package com.example.homeheirs;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


/**
 * MainActivity serves as the main entry point for the Home Heirs apps.
 * It displays a list of items in the item list using a RecyclerView and provides options to add, edit, delete, and view details of items.
 * haven't implemented the onclick filter
 */
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

    private Button sortButton;
    private LinearLayout sortView1;
    private LinearLayout sortView2;
    boolean sortHidden = true;
    private TextView asc;
    private TextView desc;

    // Create firebase auth variables
    FirebaseAuth auth;
    FirebaseUser user;

    private BottomNavigationView bottomNavigationView;

    private String userId;

    /**
     * Called when the activity is first created. Also setup the navigation menu and firestore
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user==null){
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        }
        else{
            userId=user.getUid();
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    if (item.getItemId()== R.id.navigation_logout) {
                        startActivity(new Intent(getApplicationContext(), userProfile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    } else if (item.getItemId()== R.id.navigation_home) {
                        return true;
                    }


                return false;
            }
        });





    custom_bar = findViewById(R.id.hidden_toolbar);
        original_bar=findViewById(R.id.custom_toolbar   );



        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("items");
        total_estimated_value = findViewById(R.id.total_value_amount);

        firebaseOperations = new FirebaseOperations(total_estimated_value,userId);
        dataList= firebaseOperations.get_dataList();

        // initialize the array and set up the Array Adapter with recycle view

        itemList = findViewById(R.id.item_list);
        itemList.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new RecyclerViewAdapter(this,itemList,dataList);
        recycleAdapter.setClickListener( this);
        itemList.setAdapter(recycleAdapter);

        firebaseOperations.setAdapter(recycleAdapter);









        firebaseOperations.listenForDataChanges();
        dataList = firebaseOperations.get_dataList();
        recycleAdapter.notifyDataSetChanged();





       updateFullCost();


       // search bar

        EditText editTextSearch = findViewById(R.id.search_edit_text);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // filter the dataList based on the search text

                recycleAdapter.filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });









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

            //recycleAdapter.deleteSelectedItems();

            //fixed bug for delte fuctionality
            firebaseOperations.deleteData(recycleAdapter.getSelected_items());

            recycleAdapter.resetSelected_items();
        });

        sortButton = (Button) findViewById(R.id.sort_item_button);
        sortView1 = (LinearLayout) findViewById(R.id.sortLayoutAsc);
        sortView2 = (LinearLayout) findViewById(R.id.sortLayoutDes);
        asc = findViewById(R.id.asc);
        desc = findViewById(R.id.desc);

        hideSort();

        sortButton.setOnClickListener( v -> {
            showSortTapped(v);
        });




    }

    /**
     * Handles the item click event in the RecyclerView.
     *
     * When an item is clicked, this method is invoked to open the details of the selected item
     * in the {@link ShowItemActivity}.
     *
     * @param view The View that was clicked within the RecyclerView.
     * @param position The position of the clicked item in the RecyclerView's data set.
     */
    @Override
    public void onItemClick(View view, int position) {
        Item item = dataList.get(position);
        Intent intent = new Intent(MainActivity.this, ShowItemActivity.class);
        intent.putExtra("ITEM", item);
        intent.putExtra("USERID", userId);
        startActivity(intent);
    }

    /**
     * Handles the long item click event in the RecyclerView.
     *
     * When an item is long-clicked, this method is invoked to perform actions such as
     * enabling multi-selection mode or showing additional options.
     *
     * @param view The View that was long-clicked within the RecyclerView.
     * @param position The position of the long-clicked item in the RecyclerView's data set.
     * @return True if the long click is consumed, false otherwise.
     */
    public boolean onItemLongClick(View view,int position){
        Item item = dataList.get(position);

        return true;
    }
/*
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
    }*/







    /**
     * Called when the "OK" button is pressed in the AddItemFragment.
     *
     * @param item The item to be added.
     */
    public void onOKPressed(Item item) {


        firebaseOperations.addData(item);

    }

    //  When u press ok upon editing the expense and province it sets the name and province to what is in the edit text



    // We should have a list of the items to edit-make this change
    /**
     * Called when the "OK" button is pressed in the AddTagFragment.
     *
     * @param tag The tag to be added to selected items.
     */
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


    /**
     * Updates the total estimated value displayed on the UI.
     */
    private void updateFullCost(){
        double total_estimated_value = 0;


        for (int i = 0; i < dataList.size(); i++) {
            total_estimated_value += dataList.get(i).getEstimated_value();
        }

        this.total_estimated_value.setText(String.format(Locale.getDefault(), "$%.2f", total_estimated_value));
    }



    /**
     * Changes the visibility of the toolbox to switch between the original toolbox and one showing
     * the delete and add multiple tags functionality.
     *
     * @param replace True to show the custom toolbox, false to revert to the original toolbox.
     */
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

    public void showSortTapped(View view)
    {
        if(sortHidden == true)
        {
            sortHidden = false;
            showSort();
        }
        else
        {
            sortHidden = true;
            hideSort();
        }
    }

    private void hideSort() {
        sortView1.setVisibility(View.GONE);
        sortView2.setVisibility(View.GONE);
        asc.setVisibility(View.GONE);
        desc.setVisibility(View.GONE);

        sortButton.setText("SORT");
    }

    private void showSort() {
        sortView1.setVisibility(View.VISIBLE);
        sortView2.setVisibility(View.VISIBLE);
        asc.setVisibility(View.VISIBLE);
        desc.setVisibility(View.VISIBLE);

        sortButton.setText("HIDE");
    }

//    public void dateASCTapped(View view)
//    {
//        Collections.sort(shapeList, Shape.idAscending);
//        checkForFilter();
//    }
//
//    public void dateDESTapped(View view)
//    {
//        Collections.sort(shapeList, Shape.idAscending);
//        Collections.reverse(shapeList);
//        checkForFilter();
//    }

    public void descriptionASCTapped(View view)
    {
        Collections.sort(dataList, Item.descriptionAscending);
        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
        itemList.setAdapter(recycleAdapter);
    }

    public void descriptionDESTapped(View view)
    {
        Collections.sort(dataList, Item.descriptionAscending);
        Collections.reverse(dataList);
        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
        itemList.setAdapter(recycleAdapter);
    }

    public void makeASCTapped(View view)
    {
        Collections.sort(dataList, Item.makeAscending);
        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
        itemList.setAdapter(recycleAdapter);
    }

    public void makeDESTapped(View view)
    {
        Collections.sort(dataList, Item.makeAscending);
        Collections.reverse(dataList);
        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
        itemList.setAdapter(recycleAdapter);
    }

    public void valueASCTapped(View view)
    {
        Collections.sort(dataList, Item.valueAscending);
        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
        itemList.setAdapter(recycleAdapter);
    }

    public void valueDESTapped(View view)
    {
        Collections.sort(dataList, Item.valueAscending);
        Collections.reverse(dataList);
        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
        itemList.setAdapter(recycleAdapter);
    }

//    public void tagsASCTapped(View view)
//    {
//        Collections.sort(dataList, Item.tagAscending);
//        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
//        itemList.setAdapter(recycleAdapter);
//    }
//
//    public void tagsDESTapped(View view)
//    {
//        Collections.sort(dataList, Item.tagAscending);
//        Collections.reverse(dataList);
//        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
//        itemList.setAdapter(recycleAdapter);
//    }
}