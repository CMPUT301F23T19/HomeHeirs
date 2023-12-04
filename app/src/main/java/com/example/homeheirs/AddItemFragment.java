package com.example.homeheirs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Locale;

/**
 * A DialogFragment for adding or editing items in the item list.
 * Provides a form for the user to input details of an item, including name, purchase date, description, make, model, serial number, estimated value, and additional comments.
 * @author Archi Patel, Rayyan Rashid
 */
public class AddItemFragment extends DialogFragment implements Scanner.OnScanActivityListener{
    private EditText itemName;
    private String name;
    private EditText purchaseMonth;
    private String month;
    private EditText purchaseDay;
    private String day;
    private EditText purchaseYear;
    private String year;
    private EditText itemDescription;
    private String description;
    private EditText itemMake;
    private String make;
    private EditText itemModel;
    private String model;
    private EditText itemSerialNumber;
    private String serialNumber;
    private EditText estimatedValue;
    private String value;
    private EditText itemComment;
    private String comment;
    // For test purposes
    private TextView itemTag;
    private OnFragmentInteractionListener listener;
    private String title = "Add Item";
    private Item item;
    private Boolean isEdit = false;
    private Scanner scanner;

    /**
     * Constructs an AddItemFragment for editing an existing item.
     * If item exists, set isEdit to true to allow user to edit item
     *
     * @param item The item to be edited.
     *        Source Used : Lab Assignment and materials
     */
    // If item exists, set isEdit to true to allow user to edit item
    public AddItemFragment(Item item) {
        this.item = item;
        this.isEdit = true;
    }

    /**
     * Default constructor for AddItemFragment.
     */
    public AddItemFragment() {
        super();
    }

    /**
     * Sets the title of the Dialog Box.
     *
     * @param title The title to be set for the Dialog Box.
     */
    // Sets the title of the Dialog Box
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Called when the fragment is attached to an activity.
     *
     * @param context The context to which the fragment is attached.
     * @throws RuntimeException If the context does not implement OnFragmentInteractionListener.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + "OnFragmentInteractionListener is not implemented");
        }
    }

    /**
     * Interface for communication between the fragment and the activity.
     */
    public interface OnFragmentInteractionListener {
        /**
         * Called when the "OK" button is pressed. Sends the entered item details to the listener.
         *
         * @param item The item object containing entered details.
         */
        void onOKPressed(Item item);

        /**
         * Called when the "OK" button is pressed during tag selection. Sends the selected tag to the listener.
         *
         * @param tag The selected tag.
         */
        void onTagOKPressed(Tag tag);
    }

    /**
     * Creates and returns the dialog for the fragment.
     *
     * @param savedInstanceState The saved instance state of the fragment.
     * @return The created Dialog.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_item_fragment_layout, null);

        itemName = view.findViewById(R.id.item_name_edit_text);
        purchaseMonth = view.findViewById(R.id.purchase_month_edit_text);
        purchaseDay = view.findViewById(R.id.purchase_day_edit_text);
        purchaseYear = view.findViewById(R.id.purchase_year_edit_text);
        itemDescription = view.findViewById(R.id.description_edit_text);
        itemMake = view.findViewById(R.id.make_edit_text);
        itemModel = view.findViewById(R.id.model_edit_text);
        itemSerialNumber = view.findViewById(R.id.serial_number_edit_text);
        estimatedValue = view.findViewById(R.id.estimated_value_edit_text);
        itemComment = view.findViewById(R.id.comment_edit_text);

        // If item is being edited, fill in Dialog Box entries with existing information
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Add Item")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK",null)
                .create();

        // Initialize Scanner instance, pass Activity context and Fragment instance
        scanner = new Scanner(getActivity(), this);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                /**
                 * Handles the click event of the "Scan" button.
                 * Uses the Scanner class to open the barcode scanner.
                 */
                Button scanButton = view.findViewById(R.id.scan_button);
                scanButton.setOnClickListener(v -> scanner.startScan(barLauncher));

                Button button2 = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view){
                        // Save depending on if it is edit
                        // Otherwise, add new item to Item List

                        name = itemName.getText().toString();
                        //Convert to int before passing
                        month = purchaseMonth.getText().toString();
                        //Convert to int before passing
                        day = purchaseDay.getText().toString();
                        //Convert to int before passing
                        year = purchaseYear.getText().toString();
                        description = itemDescription.getText().toString();
                        make = itemMake.getText().toString();
                        model = itemModel.getText().toString();
                        //Convert to int before passing
                        serialNumber = itemSerialNumber.getText().toString();
                        //Convert to Double before passing
                        value = estimatedValue.getText().toString();

                        comment = itemComment.getText().toString();
                        // validate to make sure non empty string given

                        if(validate(name,month,day,year,make,model,serialNumber,value)){
                            // The idea is that we simply just return a Tag object which is appended to each selected items list of tags
                            listener.onOKPressed(new Item(name, Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(year), description, make, model, Integer.parseInt(serialNumber), Double.parseDouble(value), comment));
                            // Only dismiss dialog if error check passes
                            dialog.dismiss();}
                    }
                });
            }
        });
        dialog.show();
        return dialog;}

    /**
     * Scans obtained code to get product information
     */
    private ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            String barcodeRawValue = result.getContents();
            scanner.scanCode(barcodeRawValue);
        }
    });

    /**
     * Sets the text fields according to the obtained product information
     * @param prod_description Obtained Product Description
     * @param prod_serial_num Obtained Manufacturer Product Number
     */
    @Override
    public void onScanResult(String prod_description, String prod_serial_num) {
        itemDescription.setText(prod_description);
        itemSerialNumber.setText(prod_serial_num);
    }

    /**
     * Validates the input the EditText fields for creating or editing an item
     *
     * @param name       The name of the item.
     * @param month      The purchase month of the item.
     * @param day        The purchase day of the item.
     * @param year       The purchase year of the item.
     * @param make       The make of the item.
     * @param model      The model of the item.
     * @param value      The estimated value of the item.
     * @return True if all input fields are valid; otherwise, false. Error messages are displayed for each invalid field.
     */
    private boolean validate(String name, String month, String day, String year, String make, String model, String serial_number, String value) {

        boolean check = true;
        if (name.isEmpty()) {
            itemName.requestFocus();
            itemName.setError("Please enter a name");
            check=false;
        }

        if (month.isEmpty() || Integer.parseInt(month) <= 0 || Integer.parseInt(month) > 12) {
            purchaseMonth.requestFocus();
            purchaseMonth.setError("Please enter a valid month");
            check=false;
        }

        if (day.isEmpty() || Integer.parseInt(day) <= 0 || Integer.parseInt(day) > 31) {
            purchaseMonth.requestFocus();
            purchaseMonth.setError("Please enter a valid month");
            check=false;
        }

        if (year.isEmpty() || Integer.parseInt(year) < 1990 || Integer.parseInt(year) > 2035) {
            purchaseYear.requestFocus();
            purchaseYear.setError("Please enter a valid year between 1990 and 2035");
            check=false;
        }

        if (make.isEmpty()) {
            itemMake.requestFocus();
            itemMake.setError("Please enter a make");
            check=false;
        }

        if (model.isEmpty()) {
            itemModel.requestFocus();
            itemModel.setError("Please enter a model");
            check=false;
        }

        if (serial_number.isEmpty()) {
            itemSerialNumber.requestFocus();
            itemSerialNumber.setError("Please enter a valid number");
            check=false;
        }

        if (value.isEmpty() || Double.parseDouble(value) < 0) {
            estimatedValue.requestFocus();
            estimatedValue.setError("Please enter a valid value");
            check=false;
        }

        return check;
    }
}
