package com.example.homeheirs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * ShowItemActivity displays the details of an item and allows the user to edit and save the changes.
 * It interacts with Firestore to update item details.
 */
public class ShowItemActivity extends AppCompatActivity implements ChoosePhotoOptionFragment.CameraFragmentlistener {

    /**
     * The item whose details are displayed and can be edited.
     */
    private Item item;



    /**
     * The instance of FirebaseFirestore for interacting with Firestore.
     */
    private FirebaseFirestore db;

    /**
     * The reference to the Firestore collection where the item details are stored.
     */
    private CollectionReference collectionRef;

    private GridView image_grid_view;
    private ImageAdapter imageAdapter;
    private ArrayList<Uri> imagePaths ;

    StorageReference storageRef;
    StorageReference storageRef2;

    // serves the purpose of the different users
    private String userID;

    /**
     * Initializes the activity, sets up the UI, and retrieves the item details.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_item_details);

        // Initialize Firestore instance and Collection Reference
        db = FirebaseFirestore.getInstance();




        // Retrieve intent and the item
        Intent intent = getIntent();
        if (intent != null) {
            item = (Item) intent.getSerializableExtra("ITEM");
            userID = (String) intent.getSerializableExtra("USERID");

        }
        collectionRef = db.collection("initial").document(userID).collection("items");
        imagePaths=new ArrayList<>();
        //imagePaths = item.getImage_uriList();
        //storageRef2 = FirebaseStorage.getInstance().getReference("images/" + userID + "/" + item.getDate_identifier());

        for (int i=0; i<item.getImage_uriList().size();i++) {
            // should work?
            storageRef2 = FirebaseStorage.getInstance().getReference().child("images").child(userID).child(item.getDate_identifier()).child(item.getImage_uriList().get(i));
            storageRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri downloadUri) {

                    imagePaths.add(downloadUri);
                    Log.i("cat", String.valueOf(downloadUri));
                    imageAdapter.notifyDataSetChanged();
                    //Toast.makeText(ShowItemActivity.this, "This has succeeded",Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(ShowItemActivity.this, "bad",Toast.LENGTH_SHORT).show();
                }
            });

        }

        // load each of the uris



//        for (String imageUri : item.getImage_uriList()) {
//            storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri downloadUri) {
//
//                    imagePaths.add(downloadUri);
//                    Toast.makeText(ShowItemActivity.this, "good",Toast.LENGTH_SHORT).show();
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    Toast.makeText(ShowItemActivity.this, "bad",Toast.LENGTH_SHORT).show();
//                }
//            });
//        }

//        storageReference1.listAll().addOnSuccessListener(listResult -> {
//            for (StorageReference item : listResult.getItems()) {
//                // Get the download URL for each item and add it to the imagePaths list
//                item.getDownloadUrl().addOnSuccessListener(uri -> {
//                    imagePaths.add(uri);
//                   // imageAdapter.notifyDataSetChanged();
//                }).addOnFailureListener(exception -> {
//                    // Handle failures
//                    Toast.makeText(ShowItemActivity.this, "Failed to retrieve download URL", Toast.LENGTH_SHORT).show();
//                });
//            }
//        }).addOnFailureListener(exception -> {
//            // Handle failures
//            Toast.makeText(ShowItemActivity.this, "Failed to list items", Toast.LENGTH_SHORT).show();
//        });

        /**
         * Sets values for each EditText field based on the item details
         */
        if (item != null && item.getName() != null) {
            // Set Values for each EditText
            EditText nameTextView = findViewById(R.id.show_item_name);
            nameTextView.setText(item.getName());

            EditText monthTextView = findViewById(R.id.show_purchase_month);
            monthTextView.setText(String.valueOf(item.getPurchase_month()));

            EditText yearTextView = findViewById(R.id.show_purchase_year);
            yearTextView.setText(String.valueOf(item.getPurchase_year()));

            EditText descriptionTextView = findViewById(R.id.show_description);
            descriptionTextView.setText(item.getDescription());

            EditText makeTextView = findViewById(R.id.show_make);
            makeTextView.setText(item.getMake());

            EditText modelTextView = findViewById(R.id.show_model);
            modelTextView.setText(item.getModel());

            EditText serialNumTextView = findViewById(R.id.show_serial_no);
            serialNumTextView.setText(String.valueOf(item.getSerial_number()));

            EditText valueTextView = findViewById(R.id.show_value);
            valueTextView.setText(String.valueOf(item.getEstimated_value()));

            EditText commentTextView = findViewById(R.id.show_comments);
            commentTextView.setText(item.getComment());

            TextView tagsTextView = findViewById(R.id.show_tags);
            tagsTextView.setText(formatTags(item.getTag_list()));

            // work on implementing gridview
            image_grid_view = findViewById(R.id.photograph_grid);




             imageAdapter = new ImageAdapter(this,getSupportFragmentManager(), imagePaths);
            image_grid_view.setAdapter(imageAdapter);
            Toast.makeText(ShowItemActivity.this, String.valueOf(imagePaths.size()),Toast.LENGTH_SHORT).show();
            imageAdapter.notifyDataSetChanged();
        }

        Button saveButton = findViewById(R.id.save_button);
        /**
         * Handles the click event of the "Save" button. Updates the item details in Firestore.
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item != null) {
                    // Get the edited values from the EditText fields
                    EditText nameTextView = findViewById(R.id.show_item_name);
                    EditText monthTextView = findViewById(R.id.show_purchase_month);
                    EditText yearTextView = findViewById(R.id.show_purchase_year);
                    EditText descriptionTextView = findViewById(R.id.show_description);
                    EditText makeTextView = findViewById(R.id.show_make);
                    EditText modelTextView = findViewById(R.id.show_model);
                    EditText serialNumTextView = findViewById(R.id.show_serial_no);
                    EditText valueTextView = findViewById(R.id.show_value);
                    EditText commentTextView = findViewById(R.id.show_comments);

                    String name = nameTextView.getText().toString();
                    String month = monthTextView.getText().toString();
                    String year = yearTextView.getText().toString();
                    String description = descriptionTextView.getText().toString();
                    String make = makeTextView.getText().toString();
                    String model = modelTextView.getText().toString();
                    String serialNumber = serialNumTextView.getText().toString();
                    String value = valueTextView.getText().toString();
                    String detail = commentTextView.getText().toString();

                    Item newItem = new Item(name, Integer.parseInt(month), Integer.parseInt(year), description, make, model, Integer.parseInt(serialNumber), Double.parseDouble(value), detail);
                    newItem.setDate_identifier(item.getDate_identifier());
                    /**
                     * Updates the item details in Firestore with the updated values
                     *
                     * @param item The Item object being updated
                     * @param newItem The Updated version of our Item
                     */

                    //  look into .update for this.
                    collectionRef.document(item.getDate_identifier())
                            //double check this otherwise .set(newItem)
                            .update("name", name,
                                    "purchase_month", Integer.parseInt(month),
                                    "purchase_year", Integer.parseInt(year),
                                    "description", description,
                                    "make", make,
                                    "model", model,
                                    "serial_number", Integer.parseInt(serialNumber),
                                    "estimated_value", Double.parseDouble(value),
                                    "detail", detail,
                                    "image_uriList", item.getImage_uriList())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.i("Firestore", "db add succeeded");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("Firestore", "db write failed");
                                }
                            });
                }
            }
        });

        Button backButton = findViewById(R.id.back_button);
        /**
         * Handles the click event of the "Back" button.
         * Navigates back to the previous screen or finishes the current activity.
         */
        backButton.setOnClickListener(view -> onBackPressed());
    }

    private String formatTags(List<Tag> tagList) {

        StringBuilder tagStringBuilder = new StringBuilder();
        for (Tag tag : tagList) {
            tagStringBuilder.append(tag.getTag_name()).append(", ");
        }

        if (tagStringBuilder.length() > 2) {
            tagStringBuilder.setLength(tagStringBuilder.length() - 2);
        }

        return tagStringBuilder.toString();
    }

    @Override
    public void onImageAdd(Uri uri) {
        imagePaths.add(uri);
        imageAdapter.notifyDataSetChanged();

        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String filename = format.format(now);
        item.add_uri(filename);
        storageRef= FirebaseStorage.getInstance().getReference("images/"+userID+"/"+item.getDate_identifier()+"/"+filename);
        storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(ShowItemActivity.this, "hi",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShowItemActivity.this, "Failed",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
