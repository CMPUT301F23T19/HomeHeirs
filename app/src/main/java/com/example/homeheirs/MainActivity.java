package com.example.homeheirs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
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
    private boolean isDateFilterActive = false;
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
        /**
         * Sets an item selected listener for a BottomNavigationView, handling various actions based on the selected item.
         *
         * @param onItemSelectedListener The listener to be invoked when a navigation item is selected.
         */
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

        // Filter button "ALL" functionality
        Button allFilterButton = findViewById(R.id.allFilterButton);
        /**
         * Sets an onClick listener for the "allFilterButton" view, triggering the reset of the RecyclerView when clicked.
         *
         * @param newOnClickListener The listener to be invoked when the "allFilterButton" view is clicked.
         */
        allFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRecyclerView();
            }
        });

        // Filter button "DATE" functionality
        Button dateFilterButton = findViewById(R.id.dateFilterButton);
        /**
         * Sets an onClick listener for the "dateFilterButton" view, toggling the state of the date filter
         * and showing a date filter dialog when the button is clicked.
         *
         * @param newOnClickListener The listener to be invoked when the "dateFilterButton" view is clicked.
         */
        dateFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDateFilterActive = !isDescFilterActive;

                // Show date dialog when button is clicked
                if (isDateFilterActive) {
                    showDateFilterDialog();
                }
            }
        });

        // Filter button "DESCR" functionality
        Button descFilterButton = findViewById(R.id.descFilterButton);
        /**
         * Sets an onClick listener for the "descFilterButton" view, toggling the state of the description filter
         * and showing/hiding the search bar based on the button's state. Additionally, resets the list of items
         * to the original state when the description filter is deactivated.
         *
         * @param newOnClickListener The listener to be invoked when the "descFilterButton" view is clicked.
         */
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
        /**
         * Sets an onClick listener for the "makeFilterButton" view, toggling the state of the make filter
         * and showing/hiding the search bar based on the button's state. Additionally, resets the list of items
         * to the original state when the make filter is deactivated.
         *
         * @param newOnClickListener The listener to be invoked when the "makeFilterButton" view is clicked.
         */
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

        /**
         * Sets an OnQueryTextListener for the "searchView," handling text submission and text change events.
         * Depending on the active filters, it filters the list of items by description or make when the text changes.
         *
         * @param onQueryTextListener The listener to be invoked when there are changes to the query text.
         */
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
        /**
         * Sets an onClick listener for the "tagsFilterButton" view, toggling the state of the tags filter
         * and showing a tags filter dialog when the button is clicked. Additionally, resets the list of items
         * to the original state when the tags filter is deactivated.
         *
         * @param newOnClickListener The listener to be invoked when the "tagsFilterButton" view is clicked.
         */
        tagsFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTagsFilterActive = !isTagsFilterActive;

                // Show tags filter dialog when button is clicked
                if (isTagsFilterActive) {
                    showTagsFilterDialog();
                }

                // Set list of items back to original
                else {
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
            //if item is long clicked, we set the make our custom bar visible to show the delete and add multiple tags
            original_bar.setVisibility(View.INVISIBLE);
            custom_bar.setVisibility(View.VISIBLE);
        } else{
            // When we are done with selecting, we revert to original settings
            original_bar.setVisibility(View.VISIBLE);
            custom_bar.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * Toggles the visibility of sorting options. If hidden, it shows the options; otherwise, it hides them.
     *
     * @param view The view that triggered the method.
     */
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

    /**
     * Hides the sorting options by setting their visibility to GONE.
     */
    private void hideSort() {
        sortView1.setVisibility(View.GONE);
        sortView2.setVisibility(View.GONE);
        asc.setVisibility(View.GONE);
        desc.setVisibility(View.GONE);

        sortButton.setText("SORT");
    }

    /**
     * Shows the sorting options by setting their visibility to VISIBLE.
     */
    private void showSort() {
        sortView1.setVisibility(View.VISIBLE);
        sortView2.setVisibility(View.VISIBLE);
        asc.setVisibility(View.VISIBLE);
        desc.setVisibility(View.VISIBLE);

        sortButton.setText("HIDE");
    }

    /**
     * Sorts the data list in ascending order based on the date and updates the RecyclerViewAdapter.
     *
     * @param view The view that triggered the method.
     */
    public void dateASCTapped(View view)
    {
        Collections.sort(dataList, Item.dateAscending);
        //recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
        recycleAdapter.notifyDataSetChanged();
        //recycleAdapter.setClickListener(this);
        //itemList.setAdapter(recycleAdapter);
        //recycleAdapter.notifyDataSetChanged();
    }

    /**
     * Sorts the data list in descending order based on the date and updates the RecyclerViewAdapter.
     *
     * @param view The view that triggered the method.
     */
    public void dateDESTapped(View view)
    {
        Collections.sort(dataList, Item.dateAscending);
        Collections.reverse(dataList);
//        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
//        recycleAdapter.setClickListener(this);
//        itemList.setAdapter(recycleAdapter);
        recycleAdapter.notifyDataSetChanged();
    }

    /**
     * Sorts the data list in ascending order based on the description and updates the RecyclerViewAdapter.
     *
     * @param view The view that triggered the method.
     */

    public void descriptionASCTapped(View view)
    {
        Collections.sort(dataList, Item.descriptionAscending);
//        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
//        recycleAdapter.setClickListener(this);
//        itemList.setAdapter(recycleAdapter);
        recycleAdapter.notifyDataSetChanged();
    }

    /**
     * Sorts the data list in descending order based on the description and updates the RecyclerViewAdapter.
     *
     * @param view The view that triggered the method.
     */
    public void descriptionDESTapped(View view)
    {
        Collections.sort(dataList, Item.descriptionAscending);
        Collections.reverse(dataList);
//        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
//        recycleAdapter.setClickListener(this);
//        itemList.setAdapter(recycleAdapter);
        recycleAdapter.notifyDataSetChanged();
    }

    /**
     * Sorts the data list in ascending order based on the make and updates the RecyclerViewAdapter.
     *
     * @param view The view that triggered the method.
     */
    public void makeASCTapped(View view)
    {
        Collections.sort(dataList, Item.makeAscending);
//        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
//        recycleAdapter.setClickListener(this);
//        itemList.setAdapter(recycleAdapter);
        recycleAdapter.notifyDataSetChanged();
    }

    /**
     * Sorts the data list in descending order based on the make and updates the RecyclerViewAdapter.
     *
     * @param view The view that triggered the method.
     */
    public void makeDESTapped(View view)
    {
        Collections.sort(dataList, Item.makeAscending);
        Collections.reverse(dataList);
//        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
//        recycleAdapter.setClickListener(this);
//        itemList.setAdapter(recycleAdapter);
        recycleAdapter.notifyDataSetChanged();
    }

    /**
     * Sorts the data list in ascending order based on the value and updates the RecyclerViewAdapter.
     *
     * @param view The view that triggered the method.
     */
    public void valueASCTapped(View view)
    {
        Collections.sort(dataList, Item.valueAscending);
//        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
//        recycleAdapter.setClickListener(this);
//        itemList.setAdapter(recycleAdapter);
        recycleAdapter.notifyDataSetChanged();
    }

    /**
     * Sorts the data list in descending order based on the value and updates the RecyclerViewAdapter.
     *
     * @param view The view that triggered the method.
     */
    public void valueDESTapped(View view)
    {
        Collections.sort(dataList, Item.valueAscending);
        Collections.reverse(dataList);
//        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
//        recycleAdapter.setClickListener(this);
//        itemList.setAdapter(recycleAdapter);
        recycleAdapter.notifyDataSetChanged();
    }

    /**
     * Sorts the data list in ascending order based on the tags and updates the RecyclerViewAdapter.
     *
     * @param view The view that triggered the method.
     */
    public void tagsASCTapped(View view)
    {
        Collections.sort(dataList, Item.tagAscending);
//        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
//        recycleAdapter.setClickListener(this);
//        itemList.setAdapter(recycleAdapter);
        recycleAdapter.notifyDataSetChanged();
    }

    /**
     * Sorts the data list in descending order based on the tags and updates the RecyclerViewAdapter.
     *
     * @param view The view that triggered the method.
     */
    public void tagsDESTapped(View view)
    {
        Collections.sort(dataList, Item.tagDescending);
        Collections.reverse(dataList);
//        recycleAdapter = new RecyclerViewAdapter(getApplicationContext(), itemList, dataList);
//        recycleAdapter.setClickListener(this);
//        itemList.setAdapter(recycleAdapter);
        recycleAdapter.notifyDataSetChanged();
    }

    /**
     * Resets the RecyclerView to display the original list of items.
     */
    private void resetRecyclerView() {
        dataList.clear();
        dataList.addAll(dataCopyList);
        recycleAdapter.notifyDataSetChanged();
        updateFullCost();
    }

    /**
     * Displays an AlertDialog with a date range filter. The user is able to select a
     * start and end date to filter the items based on their purchase dates. If the
     * user applies the filter, items within the date range will be displayed.
     */
    private void showDateFilterDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_date_range_filter, null);
        DatePicker startDatePicker = view.findViewById(R.id.startDatePicker);
        DatePicker endDatePicker = view.findViewById(R.id.endDatePicker);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setView(view)
                .setTitle("Select Date Range")
                .setNegativeButton("Cancel", (dialog, which) -> {
                    isDateFilterActive = false;
                });

        builder.setPositiveButton("Apply", (dialog, which) -> {
            // Get selected start and end dates
            int startYear = startDatePicker.getYear();
            int startMonth = startDatePicker.getMonth() + 1;
            int startDay = startDatePicker.getDayOfMonth();

            int endYear = endDatePicker.getYear();
            int endMonth = endDatePicker.getMonth() + 1;
            int endDay = endDatePicker.getDayOfMonth();

            // Handle date range selection and apply filter
            filterListByDate(startYear, startMonth, startDay, endYear, endMonth, endDay);
            isDateFilterActive = false;
        });

        builder.create().show();
    }

    /**
     * Filters the list of items by their purchase dates based on user's selected
     * date range.
     *
     * @param startYear - start year of date range
     * @param startMonth - start month of date range
     * @param startDay - start day of date range
     * @param endYear - end year of date range
     * @param endMonth - end month of date range
     * @param endDay - end day of date range
     */
    private void filterListByDate(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        // Create Calendar instance for start and end date
        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, startMonth - 1, startDay, 0, 0, 0);

        Calendar endDate = Calendar.getInstance();
        endDate.set(endYear, endMonth - 1, endDay, 23, 59, 59);

        // Create new list to store filtered items
        filteredList = new ArrayList<>();

        // Iterate through each item in original list
        for (Item item : dataCopyList) {
            // Create Calendar instance for item purchase date
            Calendar itemDate = Calendar.getInstance();
            itemDate.set(item.getPurchase_year(), item.getPurchase_month() - 1, item.getPurchase_day(), 0, 0, 0);

            // Check if items fall within start and end date
            if (itemDate.compareTo(startDate) >= 0 && itemDate.compareTo(endDate) <= 0) {
                filteredList.add(item);
            }
        }

        // Check if no items are filtered
        if (filteredList.isEmpty()) {
            Toast.makeText(this,"No data available.", Toast.LENGTH_SHORT).show();
        }

        // Otherwise, update RecyclerViewAdapter
        dataList.clear();
        dataList.addAll(filteredList);
        recycleAdapter.notifyDataSetChanged();
        updateFullCost();
    }

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
            Toast.makeText(this,"No data available.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this,"No data available.", Toast.LENGTH_SHORT).show();
        }

        // Otherwise, update RecyclerViewAdapter
        dataList.clear();
        dataList.addAll(filteredList);
        recycleAdapter.notifyDataSetChanged();
        updateFullCost();
    }

    /**
     * Displays an AlertDialog with a list of all unique tags for filtering. The user is
     * able to choose one or multiple tags, and when they apply the filter, the items
     * with the selected tags will be displayed.
     */
    private void showTagsFilterDialog() {
        // Get a list of all unique tags
        List<String> allTags = getAllUniqueTags();

        // Create a boolean array to track selected state
        boolean[] checkedTags = new boolean[allTags.size()];
        for (int i = 0; i < checkedTags.length; i++) {
            checkedTags[i] = false;
        }

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Select Tags")
                .setNegativeButton("Cancel", (dialog, which) -> {
                    isTagsFilterActive = false;
                });

        // Update state of selected tags when user checks/unchecks it
        builder.setMultiChoiceItems(allTags.toArray(new String[0]), checkedTags, (dialog, which, isChecked) -> {
            checkedTags[which] = isChecked;
        });
        builder.setPositiveButton("Apply", (dialog, which) -> {
            filterListByTags(allTags, checkedTags);
            isTagsFilterActive = false;
        });

        builder.create().show();
    }

    /**
     * Retrieves a list of all unique tags from original item list.
     *
     * @return - list of all unique tags
     */
    private List<String> getAllUniqueTags() {
        // Create a list to store all unique tags
        List<String> allTags = new ArrayList<>();

        // Iterate through each item in the original list
        for (Item item : dataCopyList) {
            // Iterate through each tag in the item's tag list
            for (Tag tag : item.getTag_list()) {
                // Add tag to list if not already in list
                if (!allTags.contains(tag.getTag_name())) {
                    allTags.add(tag.getTag_name());
                }
            }
        }

        return allTags;
    }

    /**
     * Filters the list of items by their tags based on user selected tags.
     * If no tags are selected, display original list.
     *
     * @param allTags - list of all unique tags
     * @param checkedTags - array indicating which tags are selected
     */
    private void filterListByTags(List<String> allTags, boolean[] checkedTags) {
        // Create new list to store filtered items
        filteredList = new ArrayList<>();

        // Check if any tags are selected
        boolean tagSelected = false;
        for (boolean isChecked : checkedTags) {
            if (isChecked) {
                tagSelected = true;
                break;
            }
        }

        // If no tags are selected, display original list
        if (!tagSelected) {
            dataList.clear();
            dataList.addAll(dataCopyList);
            recycleAdapter.notifyDataSetChanged();
            updateFullCost();
            return;
        }

        // Iterate through each item in original list
        for (Item item : dataCopyList) {
            // Iterate through each tag in item's tag list
            for (Tag tag : item.getTag_list()) {
                // Check if tag is selected and item not already in filtered list
                if (checkedTags[allTags.indexOf(tag.getTag_name())] && !filteredList.contains(item)) {
                    filteredList.add(item);
                }
            }
        }

        // Check if no items are filtered
        if (filteredList.isEmpty()) {
            Toast.makeText(this,"No data available.", Toast.LENGTH_SHORT).show();
        }

        // Otherwise, update RecyclerViewAdapter
        dataList.clear();
        dataList.addAll(filteredList);
        recycleAdapter.notifyDataSetChanged();
        updateFullCost();
    }
}