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
        itemTag = view.findViewById(R.id.tags_textview);

        // If item is being edited, fill in Dialog Box entries with existing information
        if (isEdit) {
            itemName.setText(item.getName());
            purchaseMonth.setText(String.valueOf(item.getPurchase_month()));
            purchaseYear.setText(String.valueOf(item.getPurchase_year()));
            itemDescription.setText(item.getDescription());
            itemMake.setText(item.getMake());
            itemModel.setText(item.getModel());
            itemSerialNumber.setText(String.valueOf(item.getSerial_number()));
            estimatedValue.setText(String.format(Locale.getDefault(), "%.2f", item.getEstimated_value()));
            itemComment.setText(item.getComment());

            // code adapted from https://stackoverflow.com/questions/44863723/how-to-show-list-of-string-in-textview-on-android- temporary test
            StringBuilder builder = new StringBuilder();
            for (Tag star: item.getTag_list()) {
                builder.append(star.getTag_name());
                builder.append(", ");
            }
            itemTag.setText(builder.toString());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add Item")
                .setNeutralButton("Cancel", null)
                .setNegativeButton("Delete", null)



                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String item_str = itemName.getText().toString();
                        String month_str = purchaseMonth.getText().toString();
                        String year_str = purchaseYear.getText().toString();
                        String make_str = itemMake.getText().toString();
                        String model_str = itemModel.getText().toString();
                        String est_val_str = estimatedValue.getText().toString();

                        boolean check = (validate(itemName, item_str) && validate(purchaseMonth, month_str) &&
                                validate(purchaseYear, year_str) && validate(itemMake, make_str) &&
                                validate(itemModel, model_str) && validate(estimatedValue, est_val_str));




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


                                listener.onOKPressed(new Item(name, month, year, description, make, model, serialNumber, value, detail));
                        dialog.dismiss();

                    }



                }).create();
    }


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