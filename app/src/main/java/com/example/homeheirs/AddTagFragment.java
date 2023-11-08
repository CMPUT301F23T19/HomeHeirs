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

public class AddTagFragment extends DialogFragment {


    private EditText TagName;
   // private Tag tag;

    private AddItemFragment.OnFragmentInteractionListener listener; //DOUBLE CHECK??

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

    // Describe methods that should be implemented
    public interface OnFragmentInteractionListener {
        // implement this method in main

        void onTagOKPressed(Item item);


    }





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
