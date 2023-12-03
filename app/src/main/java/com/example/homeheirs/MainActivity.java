package com.example.homeheirs;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<Item> dataCopyList;
    private ArrayList<Item> filteredList = new ArrayList<>();
    private RecyclerView itemList;
    private RecyclerViewAdapter recycleAdapter;
    private RecyclerViewAdapter recycleAdapter_filter;
    private ArrayAdapter<Item> itemAdapter;
    private TextView total_estimated_value;
    private FirebaseFirestore db;
    private CollectionReference itemsRef;
    // will use to have a conditional button listener


    // Class through which database interactions should be handled
    private FirebaseOperations firebaseOperations;
    private FirebaseOperations firebaseOperations_forfilter;

    private LinearLayout custom_bar ;
    private LinearLayout original_bar;

    // Sort buttons
    private Button sortButton;
    private LinearLayout sortView1;
    private LinearLayout sortView2;
    boolean sortHidden = true;
    private TextView asc;
    private TextView desc;

    // Filter buttons
    private LinearLayout filterLayout;
    private boolean filterHidden = true;
    private boolean isDescFilterActive = false;
    private boolean isMakeFilterActive = false;
    private boolean isTagsFilterActive = false;

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

        custom_bar = findViewById(R.id.hidden_toolbar);
        original_bar=findViewById(R.id.custom_toolbar   );
        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("items");
        total_estimated_value = findViewById(R.id.total_value_amount);
        firebaseOperations = new FirebaseOperations(total_estimated_value,userId);
        firebaseOperations_forfilter = new FirebaseOperations(total_estimated_value,userId);
        dataList = firebaseOperations.get_dataList();
        dataCopyList = firebaseOperations_forfilter.get_dataList(); // copy of data for fitering

        // Initialize the array of items, and set up the RecyclerViewAdapter
        itemList = findViewById(R.id.item_list);
        itemList.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new RecyclerViewAdapter(this,itemList,dataList);

        // The following are for filtering needs
        recycleAdapter_filter = new RecyclerViewAdapter(this,itemList,dataCopyList);
        firebaseOperations_forfilter.setAdapter(recycleAdapter_filter);
        firebaseOperations_forfilter.listenForDataChanges();

        recycleAdapter.setClickListener(this);
        itemList.setAdapter(recycleAdapter);

        firebaseOperations.setAdapter(recycleAdapter);
        firebaseOperations.listenForDataChanges();
        dataList = firebaseOperations.get_dataList();
        recycleAdapter.notifyDataSetChanged();

        updateFullCost();

        // Implement the swipe feature that will launch dialog box
        // Create listener for the add tag button
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

        // Sort button functionality
        sortButton = (Button) findViewById(R.id.sort_item_button);
        sortView1 = (LinearLayout) findViewById(R.id.sortLayoutAsc);
        sortView2 = (LinearLayout) findViewById(R.id.sortLayoutDes);
        asc = findViewById(R.id.asc);
        desc = findViewById(R.id.desc);

        hideSort();

        sortButton.setOnClickListener( v -> {
            showSortTapped(v);
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        // Filter button functionality
        filterLayout = (LinearLayout) findViewById(R.id.filterLayout);
        filterLayout.setVisibility(View.GONE);
        SearchView searchView = (SearchView) findViewById(R.id.list_search_view);
        searchView.setVisibility(View.GONE);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // When the Profile button is clicked, show user's profile
                if (item.getItemId()== R.id.navigation_logout) {
                    startActivity(new Intent(getApplicationContext(), userProfile.class));
                    overridePendingTransition(0, 0);
                    return true;
                }

                // When the Filter button is clicked for the first time, show filtering options
                else if (item.getItemId() == R.id.navigation_filter && filterHidden == true) {
                    filterHidden = false;
                    filterLayout.setVisibility(View.VISIBLE);
                    return true;
                }

                // When the Filter button is clicked again, hide filtering options and Search bar
                else if (item.getItemId() == R.id.navigation_filter && filterHidden == false) {
                    filterHidden = true;
                    filterLayout.setVisibility(View.GONE);
                    return true;
                }

                // When the Home button is clicked, hide filtering/sorting options
                else if (item.getItemId() == R.id.navigation_home) {
                    if (filterHidden == false) {
                        filterHidden = true;
                        filterLayout.setVisibility(View.GONE);
                    }

                    if (sortHidden == false) {
                        sortHidden = true;
                        hideSort();
                    }

                    return true;
                }

                return false;
            }
        });

        // Filter button "DESCR." functionality
        Button descFilterButton = findViewById(R.id.descFilterButton);
        descFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDescFilterActive = !isDescFilterActive;

                // Show/hide Search bar based on button's state
                if (isDescFilterActive) {
                    searchView.setVisibility(View.VISIBLE);
                } else {
                    searchView.setVisibility(View.GONE);
                }

                // Set list of items back to original
                if (!isDescFilterActive) {
                    dataList = firebaseOperations.get_dataList();
                }
            }
        });

        // Filter button "MAKE" functionality
        Button makeFilterButton = findViewById(R.id.makeFilterButton);
        makeFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMakeFilterActive = !isMakeFilterActive;

                // Show/hide Search bar based on button's state
                if (isMakeFilterActive) {
                    searchView.setVisibility(View.VISIBLE);
                } else {
                    searchView.setVisibility(View.GONE);
                }

                // Set list of items back to original
                if (!isMakeFilterActive) {
                    dataList = firebaseOperations.get_dataList();
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (isDescFilterActive) {
                    filterListByDesc(newText);
                }

                if (isMakeFilterActive) {
                    filterListByMake(newText);
                }

                return true;
            }
        });

        // Filter button "TAGS" functionality
        Button tagsFilterButton = findViewById(R.id.tagsFilterButton);
        tagsFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTagsFilterActive = !isTagsFilterActive;

                if (!isTagsFilterActive) {
                    dataList = firebaseOperations.get_dataList();
                }
            }
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

    /*public void delSelectedItems(ArrayList<Item> dataList){


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
        recycleAdapter.setClickListener(this);
        itemList.setAdapter(recycleAdapter);
    }

    public void descriptionDESTapped(View view)
    {
        Collections.sort(dataList, Item.descriptionAscending);
        Collections.reverse(dataList);
        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
        recycleAdapter.setClickListener(this);
        itemList.setAdapter(recycleAdapter);
    }

    public void makeASCTapped(View view)
    {
        Collections.sort(dataList, Item.makeAscending);
        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
        recycleAdapter.setClickListener(this);
        itemList.setAdapter(recycleAdapter);
    }

    public void makeDESTapped(View view)
    {
        Collections.sort(dataList, Item.makeAscending);
        Collections.reverse(dataList);
        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
        recycleAdapter.setClickListener(this);
        itemList.setAdapter(recycleAdapter);
    }

    public void valueASCTapped(View view)
    {
        Collections.sort(dataList, Item.valueAscending);
        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
        recycleAdapter.setClickListener(this);
        itemList.setAdapter(recycleAdapter);
    }

    public void valueDESTapped(View view)
    {
        Collections.sort(dataList, Item.valueAscending);
        Collections.reverse(dataList);
        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
        recycleAdapter.setClickListener(this);
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

    /**
     * Filters the list of items by their description based on provided keyword
     *
     * @param keyword - user input to filter items by description
     */
    private void filterListByDesc(String keyword) {
        // Create new list to store filtered items
        filteredList = new ArrayList<>();

        // Iterate through each item in original list
        for (Item item : dataCopyList) {
            // Check if description contains keyword
            if (item.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(item);
            }
        }

        // Check if no items are filtered
        if (filteredList.isEmpty()) {
            Toast.makeText(this,"No data available", Toast.LENGTH_SHORT).show();
        }

        // Otherwise, update RecyclerViewAdapter
        dataList.clear();
        dataList.addAll(filteredList);
        recycleAdapter.notifyDataSetChanged();
        updateFullCost();
    }

    /**
     * Filters the list of items by their make based on provided keyword
     *
     * @param keyword - user input to filter by make
     */
    private void filterListByMake(String keyword) {
        // Create new list to store filtered items
        filteredList = new ArrayList<>();

        // Iterate through each item in original list
        for (Item item : dataCopyList) {
            // Check if description contains keyword
            if (item.getMake().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(item);
            }
        }

        // Check if no items are filtered
        if (filteredList.isEmpty()) {
            Toast.makeText(this,"No data available", Toast.LENGTH_SHORT).show();
        }

        // Otherwise, update RecyclerViewAdapter
        dataList.clear();
        dataList.addAll(filteredList);
        recycleAdapter.notifyDataSetChanged();
        updateFullCost();
    }
}