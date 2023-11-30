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

public class ImageAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<Uri> image_paths;
    private LayoutInflater layoutInflater;


    // One idea is to pass the item here, and then access then load the images of the item into the array
    private FragmentManager fragmentManager;

    public ImageAdapter(Context context, FragmentManager fragmentManager, ArrayList<Uri> image_paths) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.image_paths = image_paths;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        // maybe change this but we need an additional for out add images button
        return image_paths.size()+1;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return null; // Plus button, no associated image path
        } else {
            return image_paths.get(position - 1);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView image_view;

        if (convertView==null){


            convertView = layoutInflater.inflate(R.layout.grid_layout,parent,false);
        }
        image_view = convertView.findViewById(R.id.grid_imageView);

        if (position==0){
            image_view.setImageResource(R.drawable.plus_sign);

            image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ChoosePhotoOptionFragment().show(fragmentManager, "ADD_photo");
                }
            });


        }else {

            Glide.with(context)
                    .load(image_paths.get(position - 1))
                    .into(image_view);

            image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "This has succeeded",Toast.LENGTH_SHORT).show();
                }
            });
        }


        // for testing purposes will remove






        return convertView;
    }
}
