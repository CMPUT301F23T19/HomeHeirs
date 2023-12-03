package com.example.homeheirs;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.transition.TransitionInflater;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Class that Handles our Gridview and Displaying all the images in the Gridview
 * Source: Youtube
 * URL:
 * @author Arsalan Ahmed
 *
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<Uri> image_paths;

    private ArrayList<String> delete_paths;
    private LayoutInflater layoutInflater;



    // One idea is to pass the item here, and then access then load the images of the item into the array
    private FragmentManager fragmentManager;


    /**
     * Constructor for the Adapter
     * @param context- Context
     * @param fragmentManager
     * @param image_paths:ArrayList<Uri>- Contains the image paths from where we dowload the images off of the firestorage
     * ie the URL associated with the images
     * @param image_delete_paths:ArrayList<String>- Stores the Filename of the photographs so we can delete associated images
     * From the firestore
     */
    public ImageAdapter(Context context, FragmentManager fragmentManager, ArrayList<Uri> image_paths,ArrayList<String> image_delete_paths) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.image_paths = image_paths;
        this.layoutInflater = LayoutInflater.from(context);
        delete_paths = image_delete_paths;
    }

    /**
     * Method requried for the Adapter, since our first gridview is our button, we add an additonal 1
     * @return image_paths.size()+1- int describing the amount of images we currently have
     */
    @Override
    public int getCount() {

        // maybe change this but we need an additional for out add images button
        return image_paths.size()+1;
    }


    /**
     * Method Required for the adapter which retrievs image path
     * @param position:int
     *
     */
    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return null; // Plus button, no associated image path
        } else {
            return image_paths.get(position - 1);
        }
    }

    /**
     * Method Required for adapter
     */
    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * Method Required for adapter- this method loads the images into the gridview using the Glide library
     * The method plaes the very first position as the add button which gives the option of adding a photo
     * from gallary or camera when clicked, all other images are clickable which bring up an enlarged photo
     * fragment
     * @param position:int- describes position in gridveiew
     * @param convertView:View
     * @param parent :Viewgroup
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView image_view;

        if (convertView==null){


            convertView = layoutInflater.inflate(R.layout.grid_layout,parent,false);
        }
        image_view = convertView.findViewById(R.id.grid_imageView);

        // sets the image of very first thing in gridview
        //Onclick produces a photooption fragment
        if (position==0){
            image_view.setImageResource(R.drawable.plus_sign);

            image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ChoosePhotoOptionFragment().show(fragmentManager, "ADD_photo");
                }
            });


        }else {

            // Loads images into all other positions using Glide
            Glide.with(context)
                    .load(image_paths.get(position - 1))
                    .into(image_view);

            // Sets listeners for when other images are clicked- which shows enlarged image
            image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "This has succeeded",Toast.LENGTH_SHORT).show();

                    AppCompatActivity activity = (AppCompatActivity) context;
                    ShowEnlargedImageFragment enlargedImageFragment = ShowEnlargedImageFragment.newInstance(image_paths.get(position-1),delete_paths.get(position-1));
                    enlargedImageFragment.show(activity.getSupportFragmentManager(),"edit_expense_dialog");
                }
            });
        }


        // for testing purposes will remove






        return convertView;
    }
}
