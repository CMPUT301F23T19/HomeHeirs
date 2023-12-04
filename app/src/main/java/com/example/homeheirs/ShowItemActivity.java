package com.example.homeheirs;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * ShowItemActivity displays the details of an item and allows the user to edit and save the changes.
 * It interacts with Firestore to update item details.
 */
public class ShowItemActivity extends AppCompatActivity implements ChoosePhotoOptionFragment.CameraFragmentlistener, ShowEnlargedImageFragment.EnlargedFragmentlistener {

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

    // use seperate array for delete, ie hold the id_date for the delete photograph
    private ArrayList<String> image_deletePaths;

    StorageReference storageRef;
    StorageReference storageRef2;
    StorageReference storageRef3;

    // serves the purpose of the different users
    private String userID;

    // create Chipgroup for our tags
    private ChipGroup tag_group;

    String api = "https://api.barcodelookup.com/v3/products?barcode=" + "9780140157376" + "&formatted=y&key=" + "4w9q79i04ahvo4tspqzi22e2j4jle2";
    String api_base = "https://api.barcodelookup.com/v3/products?barcode=";
    String api_key = "4w9q79i04ahvo4tspqzi22e2j4jle2";
    String barcode_raw_value;
    String description;
    String serial_num;
    EditText nameTextView;
    EditText monthTextView;
    EditText yearTextView;
    EditText descriptionTextView;
    EditText makeTextView;
    EditText modelTextView;
    EditText serialNumTextView;
    EditText valueTextView;
    EditText commentTextView;

    /**
     * Initializes the activity, sets up the UI, and retrieves the item details.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_item_details);

        nameTextView = findViewById(R.id.show_item_name);
        monthTextView = findViewById(R.id.show_purchase_month);
        yearTextView = findViewById(R.id.show_purchase_year);
        descriptionTextView = findViewById(R.id.show_description);
        makeTextView = findViewById(R.id.show_make);
        modelTextView = findViewById(R.id.show_model);
        serialNumTextView = findViewById(R.id.show_serial_no);
        valueTextView = findViewById(R.id.show_value);
        commentTextView = findViewById(R.id.show_comments);

        // Initialize Firestore instance and Collection Reference
        db = FirebaseFirestore.getInstance();

        image_deletePaths=new ArrayList<>();

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
           // add the image filenames to the deletepath
            image_deletePaths.add(item.getImage_uriList().get(i));
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
            nameTextView.setText(item.getName());
            monthTextView.setText(String.valueOf(item.getPurchase_month()));
            EditText dayTextView = findViewById(R.id.show_purchase_day);
            dayTextView.setText(String.valueOf(item.getPurchase_day()));

            EditText yearTextView = findViewById(R.id.show_purchase_year);
            yearTextView.setText(String.valueOf(item.getPurchase_year()));
            descriptionTextView.setText(item.getDescription());makeTextView.setText(item.getMake());
            modelTextView.setText(item.getModel());
            serialNumTextView.setText(String.valueOf(item.getSerial_number()));
            valueTextView.setText(String.valueOf(item.getEstimated_value()));
            commentTextView.setText(item.getComment());

            tag_group= findViewById(R.id.show_tags);
            // implement the creations of the chips
            for (int i=0;i<item.getTag_list().size();i++){

                Chip chip =  new Chip(this);
                ChipDrawable drawable = ChipDrawable.createFromAttributes(this,null,0, com.google.android.material.R.style.Widget_MaterialComponents_Chip_Entry);

                chip.setChipDrawable(drawable);
                // set true if we want clickable ie useful for filtering OR editing the name of the tag
                chip.setCheckable(false);
                chip.setClickable(false);
                // gets the tag name
                chip.setText(item.getTag_list().get(i).getTag_name());

                // look into this, this is a autocorrect by android studio
                int finalI = i;
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tag_group.removeView(chip);
                        // remove the tag from the taglist
                        item.remove_tag(item.getTag_list().get(finalI));
                    }
                });
                tag_group.addView(chip);

            }
            // work on implementing gridview
            image_grid_view = findViewById(R.id.photograph_grid);




             imageAdapter = new ImageAdapter(this,getSupportFragmentManager(), imagePaths,image_deletePaths);
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
                    EditText dayTextView = findViewById(R.id.show_purchase_day);
                    EditText yearTextView = findViewById(R.id.show_purchase_year);
                    EditText descriptionTextView = findViewById(R.id.show_description);
                    EditText makeTextView = findViewById(R.id.show_make);
                    EditText modelTextView = findViewById(R.id.show_model);
                    EditText serialNumTextView = findViewById(R.id.show_serial_no);
                    EditText valueTextView = findViewById(R.id.show_value);
                    EditText commentTextView = findViewById(R.id.show_comments);

                    String name = nameTextView.getText().toString();
                    String month = monthTextView.getText().toString();
                    String day = dayTextView.getText().toString();
                    String year = yearTextView.getText().toString();
                    String description = descriptionTextView.getText().toString();
                    String make = makeTextView.getText().toString();
                    String model = modelTextView.getText().toString();
                    String serialNumber = serialNumTextView.getText().toString();
                    String value = valueTextView.getText().toString();
                    String detail = commentTextView.getText().toString();

                    Item newItem = new Item(name, Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(year), description, make, model, Integer.parseInt(serialNumber), Double.parseDouble(value), detail);
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
                                    "purchase_day", Integer.parseInt(day),
                                    "purchase_year", Integer.parseInt(year),
                                    "description", description,
                                    "make", make,
                                    "model", model,
                                    "serial_number", Integer.parseInt(serialNumber),
                                    "estimated_value", Double.parseDouble(value),
                                    "detail", detail,
                                    "image_uriList", item.getImage_uriList(),
                                    "tag_list",item.getTag_list())
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

        Button scanButton = findViewById(R.id.scan_button);
        scanButton.setOnClickListener(v -> {
            scanCode();
        });

    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result ->
    {
        if (result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ShowItemActivity.this);
            builder.setTitle("Result");
            api = api_base + result.getContents() + "&formatted=y&key=" + api_key;
            getData();
            builder.setMessage(description);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray singleProduct = jsonObject.getJSONArray("products");
                            JSONObject product = singleProduct.getJSONObject(0);
                            description = product.getString("description");
                            descriptionTextView.setText(description);
                            serial_num = product.getString("mpn");
                            serialNumTextView.setText(serial_num);
                        } catch (JSONException e) {
                            Log.e("api", "onResponse: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse:" + error.getLocalizedMessage());
            }
        });

        queue.add(stringRequest);
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
        Log.i("uristuff",String.valueOf(uri));
        imageAdapter.notifyDataSetChanged();

        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String filename = format.format(now);
        item.add_uri(filename);
        // double check if this works
        image_deletePaths.add(filename);
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

    @Override
    public void onDeleteImage(Uri uri_toDelete, String image_deletePath) {

        storageRef3 = FirebaseStorage.getInstance().getReference().child("images").child(userID).child(item.getDate_identifier()).child(image_deletePath);
        storageRef3.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ShowItemActivity.this, "deleteitem good",Toast.LENGTH_SHORT).show();
                item.deleteImageUri(image_deletePath);
                image_deletePaths.remove(image_deletePath);
                imagePaths.remove(uri_toDelete);
                imageAdapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShowItemActivity.this, "delete item bad",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
