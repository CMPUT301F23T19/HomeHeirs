package com.example.homeheirs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * ShowItemActivity displays the details of an item and allows the user to edit and save the changes.
 * It interacts with Firestore to update item details.
 */
public class ShowItemActivity extends AppCompatActivity {

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
        collectionRef = db.collection("initial");

        // Retrieve intent and the item
        Intent intent = getIntent();
        if (intent != null) {
            item = (Item) intent.getSerializableExtra("ITEM");
        }

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

                    /**
                     * Updates the item details in Firestore with the updated values
                     *
                     * @param item The Item object being updated
                     * @param newItem The Updated version of our Item
                     */
                    collectionRef.document(item.getName())
                            .set(newItem)
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

}
