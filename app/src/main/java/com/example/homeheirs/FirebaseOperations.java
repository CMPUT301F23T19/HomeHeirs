package com.example.homeheirs;



import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

/**
 * This is a class that is reposible for all database interactions
 * Involves method for adding items, tags, as well as deleting items
 * @author : Arsalan
 */
public class FirebaseOperations {

    private FirebaseFirestore db;
    private CollectionReference ItemsCollections;

    private RecyclerViewAdapter Adapter;

    private ArrayList<Item> dataList;
    private ArrayList<Item> dataList2;

    private TextView totalvalue;

    /**
     * Class Constructor. When Called initializes the database and sets to interact with the initial collection- to be
     * changed depending on the User in the future
     * @param total_estimated_value : Required due to strange bug not correctly updating the total cost so its done here
     */
    public FirebaseOperations(TextView total_estimated_value) {

        //Different user implementation comin soon
        db = FirebaseFirestore.getInstance();
        dataList = new ArrayList<>();
        totalvalue=total_estimated_value;

        ItemsCollections=db.collection("initial");

    }


    /**
     * Method for checking when data is changed, and updating our realtime database following
     * Main logic taken from labs
     * Updates the total cost as well to make ensure realtime updates
     */
    public void listenForDataChanges() {

        ItemsCollections.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshots,
                                @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("erherweherhwehwerh", error.toString());
                    return;
                }

                if (querySnapshots != null) {
                    dataList.clear();

                    for (QueryDocumentSnapshot doc: querySnapshots) {


                        String name = doc.getId();

                        Item item = doc.toObject(Item.class);
//

                        Log.i("Firestore", String.format("Item(%s, %d, %d, %s, %s, %s, %s, %s, %s) fetched",
                                name, item.getPurchase_month(), item.getPurchase_year(), item.getDescription(),
                                item.getMake(), item.getModel(), item.getSerial_number(), item.getEstimated_value(),
                                item.getComment()));


                        dataList.add(item);


                    }
                    Adapter.notifyDataSetChanged();

                    updateFullCost();

                }

            }

        });


    }

    /**
     * Method for adding an Item to the firestore database
     * Updates for Future - Work on changing how it relies on Item name in order to store-
     * pick something unique
     * Updates full cost to reflect changes
     * Source Used : Firebase documentation
     * URL : https://firebase.google.com/docs/firestore/manage-data/add-data
     *
     * @param : item - and Item object that is added to the database
     */
    public void addData(Item item){

        ItemsCollections
                .document(item.getName())
                .set(item)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("Firestore", "db add succeeded");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "db write failed");
                    }
                })
        ;
        updateFullCost();

    }


    /**
     * Method for deleting Items from firebase db
     * Updates full cost to reflect changes
     * Source Used : Code from lab
     * @param : itemsToDelete - A list of Item objects that are to be deleted
     */
    public void deleteData(ArrayList<Item> itemsToDelete) {
        //may need to come up with something better as an identifier


        for (int i=0;i<itemsToDelete.size();i++){

            ItemsCollections.document(itemsToDelete.get(i).getName())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.i("Firestore", "db delete succeeded");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Firestore", "db delete fails");
                        }
                    });
            updateFullCost();


        }


    }


    /**
     * Method for updating tags to firebase db
     * iterates through and updates ie re adds (maybe) each sequential item to be updated
     * Updates full cost to reflect changes
     * Source Used : Firebase documentation
     * URL : https://firebase.google.com/docs/firestore/manage-data/add-data
     *
     * @param : itemsToUpdate - List of item objects to be updated ie tag updates
     */
    public void addtag(ArrayList<Item> itemsToUpdate){
        // Add tags to the database- Make sure

        for (int i=0;i<itemsToUpdate.size();i++){

            ItemsCollections.document(itemsToUpdate.get(i).getName())
                    .set(itemsToUpdate.get(i))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.i("Firestore", "db delete succeeded");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Firestore", "db delete fails");
                        }
                    });


        }
    }



    /**
     * Method that sets the Textview to reflect changes made ie adding/deleting items
     * Updates the total cost on screen by iterating through whole DataList
     */
    public void updateFullCost(){

        double total_estimated_value = 0;

        for (int i = 0; i < dataList.size(); i++) {
            total_estimated_value += dataList.get(i).getEstimated_value();
        }

        totalvalue.setText(String.format(Locale.getDefault(), "$%.2f", total_estimated_value));
    }


    /**
     * Getter method for Retrieving dataList, which contains all our objects
     * @return dataList - A list of item objects containing all data
     */
    public ArrayList<Item> get_dataList(){

        return dataList;



    }


    /**
     * Method required to enable updates to the Recyclerview Adapter for realtime change
     * @param Adapter - A recylceviewAdapter that is notified whenever data is manipulated
     */
    public void setAdapter(RecyclerViewAdapter Adapter){
        this.Adapter=Adapter;
    }



}
