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

import java.util.ArrayList;
import java.util.Locale;

public class FirebaseOperations {


    private FirebaseFirestore db;
    private CollectionReference ItemsCollections;

    private RecyclerViewAdapter Adapter;

    private ArrayList<Item> dataList;
    private ArrayList<Item> dataList2;

    private TextView totalvalue;



    public FirebaseOperations(TextView total_estimated_value) {

        //Different user implementation comin soon
        db = FirebaseFirestore.getInstance();
        dataList = new ArrayList<>();
        totalvalue=total_estimated_value;

        ItemsCollections=db.collection("initial");
        //initialize a empty array
        //this.dataList = dataList;
    }


    //
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

    public void addData(Item item){
        //HashMap<String, Object> data = new HashMap<>();
        //data.put("Item", item);
        ItemsCollections
                .document(item.getName())
                .set(item)
               //.add(item)
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



//        ItemsCollections.document(item.getName())
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.i("Firestore", "db delete succeeded");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e("Firestore", "db delete fails");
//                    }
//                });
//        updateFullCost();
    }

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

    public void updateFullCost(){
        double total_estimated_value = 0;

        for (int i = 0; i < dataList.size(); i++) {
            total_estimated_value += dataList.get(i).getEstimated_value();
        }

        totalvalue.setText(String.format(Locale.getDefault(), "$%.2f", total_estimated_value));
    }

    public ArrayList<Item> get_dataList(){

        return dataList;



    }

    public void setAdapter(RecyclerViewAdapter Adapter){
        this.Adapter=Adapter;
    }



}
