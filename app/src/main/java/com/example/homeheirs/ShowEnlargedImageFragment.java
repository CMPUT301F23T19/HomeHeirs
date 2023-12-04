package com.example.homeheirs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

/**
 * Class for Showning an Enlarged object dialogfragment
 * The Fragment gives the option of deleting the image which this class handles
 * @author Arsalan Ahmed
 */
public class ShowEnlargedImageFragment extends DialogFragment {
    private Button deletebutton;
    private Button back_button;
    private ImageView enlargedImageView;
    private EnlargedFragmentlistener listener_enlarged;

    /**
     * Method Bundles arguments so that they can be accesed in this fragment
     * @param imageUrl:Uri- Contains the image resource path which will be displayed
     * @param s:String - Contains the delete path ie the photograph name so it can be deleted
     */
    static ShowEnlargedImageFragment newInstance(Uri imageUrl, String s) {
        //ShowEnlargedImageFragment fragment = new ShowEnlargedImageFragment();
        Bundle args = new Bundle();
        args.putParcelable("image_url", imageUrl);
        args.putString("deletepath",s);
        ShowEnlargedImageFragment fragment=new ShowEnlargedImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Method for fragment
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ShowEnlargedImageFragment.EnlargedFragmentlistener) {
            listener_enlarged = (ShowEnlargedImageFragment.EnlargedFragmentlistener) context;
        } else {
            throw new RuntimeException(context.toString() + "Enlargedlistener is not implemented");
        }
    }

    /**
     * Describes a interface to be implemented into the showItemsactivity to handle the delete
     */
    public interface EnlargedFragmentlistener{
        void onDeleteImage(Uri uri_toDelete, String image_deletePath);
    }

    /**
     * Method Creating the Alertdialog that shows image and cancel and delete button
     * @return Dialog- the Dialog we need to show
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.show_enlarged_image_fragment, null);

        back_button = view.findViewById(R.id.enlarged_imageCancelButton);
        deletebutton = view.findViewById(R.id.enlarged_imageDeleteButton);
        enlargedImageView = view.findViewById(R.id.enlarged_imageView);

        // Method gets the arguments from the bundle
        Bundle args = getArguments();

            Uri image_uri = (Uri) args.get("image_url");

            String image_deletePath = (String) args.get("deletepath");

            final AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setView(view)
                    //.setTitle("Edit Expense")
                    //.setNegativeButton("Cancel", null)
                    //have the listener as null so that dialog can only cose if it passes the condition
                    //of having proper input
                    //.setPositiveButton("OK",null)
                    .create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                // load the image use glide
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Glide.with(getContext())
                            .load(image_uri)

                            .into(enlargedImageView);

                    back_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });

                    // The delete button sends the image and the delete path to a method implemented in showitems
                    deletebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener_enlarged.onDeleteImage(image_uri,image_deletePath);
                            dialog.dismiss();
                        }
                    });
                }
            });
        dialog.show();
        return dialog;
    }
}
