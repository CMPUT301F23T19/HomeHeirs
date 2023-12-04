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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Class for Implementing the logic behind the Add Tag Dialog
 * Extends Dialog fragment
 * @author Arsalan Ahmed

 */
public class AddTagFragment extends DialogFragment {


    private EditText TagName;
    private AddItemFragment.OnFragmentInteractionListener listener; //DOUBLE CHECK??

    /**
     * Method that sets listener for when Dialog needs to be created
     * @param : context - Context from main activity
     * Source Used : Lab Assignment and materials
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddItemFragment.OnFragmentInteractionListener) {
            listener = (AddItemFragment.OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + "OnFragmentInteractionListener tag is not implemented");
        }
    }

    /**
     * Method that describes interface that should be implemented in main
     * Currently not in use
     */
    public interface OnFragmentInteractionListener {
        // implement this method in main
        void onTagOKPressed(Item item);
    }

    /**
     * Method that implements main logic of showing the dialog
     * Takes User Input and validates- Only closes dialog if cancel is pressed
     * Or correct input is given ie Non empty field
     *When Input correct, creates new Tag object and passes it to OnTagOkPressed method in Main
     * Source Used : Lab Assignment and materials
     * @param savedInstanceState-Bundle
     * @return - Dialog- actual Dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_tag_fragment_layout, null);

        TagName = view.findViewById(R.id.tag_input);

        // At Any point, the dialog will just show the empty textbox to add a tag
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Add Tag")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK",null)
                //.setNeutralButton("Cancel", null)
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view){
                        // Save depending on if it is edit


                        // Otherwise, add new item to Item List

                        String new_tag_name = TagName.getText().toString();
                        // validate to make sure non empty string given
                        boolean check = validate(new_tag_name);
                        if (check){
                            // The idea is that we simply just return a Tag object which is appended to each selected items list of tags
                            listener.onTagOKPressed(new Tag(new_tag_name));
                            // Only dismiss dialog if error check passes
                            dialog.dismiss();
                        }

                    }
                    });
                }
        });
        dialog.show();
        return dialog;}

    /**
     * Method that implements validation check to make sure field not empty
     * If field is empty, user cannot exit dialog and warning message shows
     * @param tagName - String containinng name of tag
     * @return - boolean - returns true if information passes check
     */
    private boolean validate(String tagName){
        // error check function
        if (tagName.isEmpty()){
            TagName.requestFocus();
            TagName.setError("Field Can'nt be Empty");
            return false;
        }
        return true;
    }



}
