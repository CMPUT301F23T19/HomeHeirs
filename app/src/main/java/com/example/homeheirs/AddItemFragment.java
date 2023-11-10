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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Locale;

public class AddItemFragment extends DialogFragment {
    private EditText itemName;
    private EditText purchaseMonth;
    private EditText purchaseYear;
    private EditText itemDescription;
    private EditText itemMake;
    private EditText itemModel;
    private EditText itemSerialNumber;
    private EditText estimatedValue;
    private EditText itemComment;
    // For test purposes
    private TextView itemTag;
    private OnFragmentInteractionListener listener;
    private String title = "Add Item";
    private Item item;
    private Boolean isEdit = false;

    // If item exists, set isEdit to true to allow user to edit item
    public AddItemFragment(Item item) {
        this.item = item;
        this.isEdit = true;
    }

    public AddItemFragment() {
        super();
    }

    // Sets the title of the Dialog Box
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + "OnFragmentInteractionListener is not implemented");
        }
    }

    public interface OnFragmentInteractionListener {
        void onOKPressed(Item item);

        //void onOkPressedEdit(Item item, String name, String purchase_month, String purchase_year, String description, String make, String model, String serial_number, String estimated_value, String comment);

        //void onDelete(Item item);

        void onTagOKPressed(Tag tag);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_item_fragment_layout, null);

        itemName = view.findViewById(R.id.item_name_edit_text);
        purchaseMonth = view.findViewById(R.id.purchase_month_edit_text);
        purchaseYear = view.findViewById(R.id.purchase_year_edit_text);
        itemDescription = view.findViewById(R.id.description_edit_text);
        itemMake = view.findViewById(R.id.make_edit_text);
        itemModel = view.findViewById(R.id.model_edit_text);
        itemSerialNumber = view.findViewById(R.id.serial_number_edit_text);
        estimatedValue = view.findViewById(R.id.estimated_value_edit_text);
        itemComment = view.findViewById(R.id.comment_edit_text);
//        itemTag = view.findViewById(R.id.tags_textview);

        // If item is being edited, fill in Dialog Box entries with existing information
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Add Item")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK",null)
                //.setNeutralButton("Cancel", null)
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button2 = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);

                button2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view){
                        // Save depending on if it is edit


                        // Otherwise, add new item to Item List

                        String name = itemName.getText().toString();
                        int month = Integer.parseInt(purchaseMonth.getText().toString());
                        int year = Integer.parseInt(purchaseYear.getText().toString());
                        String description = itemDescription.getText().toString();
                        String make = itemMake.getText().toString();
                        String model = itemModel.getText().toString();
                        int serialNumber = Integer.parseInt(itemSerialNumber.getText().toString());
                        double value = Double.parseDouble(estimatedValue.getText().toString());
                        String detail = itemComment.getText().toString();
                        // validate to make sure non empty string given


                            // The idea is that we simply just return a Tag object which is appended to each selected items list of tags
                            listener.onOKPressed(new Item(name, month, year, description, make, model, serialNumber, value, detail));
                            // Only dismiss dialog if error check passes
                            dialog.dismiss();


                    }
                });
            }
        });
        dialog.show();
        return dialog;}


    private boolean validate(EditText editText, String text) {
        if (text.isEmpty()) {
            editText.requestFocus();
            editText.setError("Field Can't be Empty");
            return false;
        }
        return true;
    }







    // Validate that the EditText field is not empty
    /*
    private boolean validate(EditText editText) {

        String string = editText.getText().toString();
        if (string.isEmpty()) {

            editText.setError("Field cannot be empty");
            editText.requestFocus();

            return false;
        }
        return true;

    }*/


}