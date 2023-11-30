package com.example.homeheirs;

import static android.app.Activity.RESULT_OK;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.Manifest;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import java.io.ByteArrayOutputStream;

public class ChoosePhotoOptionFragment extends DialogFragment {

    private CameraFragmentlistener listener_camera;

    private TextView library_choice;

    private Handler handler = new Handler();
    private TextView takePhoto_choice;
    private ImageView imageView;

    private Context context;

    private Boolean camera_selected;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CameraFragmentlistener) {
            listener_camera = (CameraFragmentlistener) context;
        } else {
            throw new RuntimeException(context.toString() + "Cameralistener is not implemented");
        }
    }






    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.choose_photo_option, null);

        library_choice = view.findViewById(R.id.fragment_libray_option);
        takePhoto_choice = view.findViewById(R.id.fragment_camera_option);
        imageView=view.findViewById(R.id.imageView_fragment);






        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                System.out.println("asdfasfasdf");

                Toast.makeText(getActivity(), "no",Toast.LENGTH_SHORT).show();

                Log.i("hi","dfddfddfdfddfdffdfdfd");



                if (o.getResultCode()==RESULT_OK && o.getData()!=null && camera_selected==false ) {

                    Toast.makeText(getContext(), "no", Toast.LENGTH_SHORT).show();

                    // gets a uri which we use to set the photo.
                    Uri selectedImage = o.getData().getData();

                    listener_camera.onImageAdd(selectedImage);

                    // we have to do this because otherwise we dismiss to fast and the onImageAdd method does not register
                    getDialog().dismiss();

                   // imageView.setImageURI(selectedImage);


                }

             else if (camera_selected==true && o.getResultCode()==RESULT_OK && o.getData()!=null) {

                Toast.makeText(getContext(), "hi",Toast.LENGTH_SHORT).show();

                Bitmap picture = (Bitmap) o.getData().getExtras().get("data");
                    Uri imageUri = getImageUri(getContext(), picture);

                    listener_camera.onImageAdd(imageUri);
                    getDialog().dismiss();

                    //imageView.setImageURI(imageUri);


            // fill out what happens next
        }





            }
        });

       // Create the dialog
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Choose")
                .setNegativeButton("Cancel", null)
                //.setPositiveButton("OK",null)
                //.setNeutralButton("Cancel", null)
                .create();



        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {



                imageView.setImageResource(R.drawable.ic_home);

                library_choice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("dasf");
                        camera_selected=false;
                        // Open the gallery to choose photos
                        openGallery();
                       // dialog.dismiss();

                    }
                });

        takePhoto_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the camera to take a photo
                camera_selected = true;
                openCamera();
               // dialog.dismiss();
            }
        });
            }

            });


        dialog.show();
        return dialog;}

    public void openGallery() {
        // Launch the gallery intent
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(galleryIntent, 111);
        activityResultLauncher.launch(galleryIntent);
    }
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//
//
//        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(context, "no",Toast.LENGTH_SHORT).show();
//
//        Log.i("hi","dfddfddfdfddfdffdfdfd");
//
//        if (resultCode==RESULT_OK && data!=null && requestCode==111){
//
//            Toast.makeText(getContext(), "no",Toast.LENGTH_SHORT).show();
//
//            // gets a uri which we use to set the photo.
//            Uri selectedImage = data.getData();
//
//        } else if (requestCode==1 && resultCode==RESULT_OK) {
//
//            Toast.makeText(getContext(), "hi",Toast.LENGTH_SHORT).show();
//
//            Bitmap picture = (Bitmap) data.getExtras().get("data");
//            // fill out what happens next
//        }
//
//    }



    private void openCamera() {

        if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA)==PermissionChecker.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "hi", Toast.LENGTH_SHORT).show();
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


            activityResultLauncher.launch(cameraIntent);
        }else {
            requestPermissions(new String[]{Manifest.permission.CAMERA},100);
        }





        //startActivityForResult(cameraIntent, 1);
        // Launch the camera intent
//        if (checkSelfPermission(getContext(),Manifest.permission_group.CAMERA)== PermissionChecker.PERMISSION_GRANTED){
//
//            Toast.makeText(getContext(), "hi",Toast.LENGTH_SHORT).show();
//            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//            startActivityForResult(cameraIntent, 1);
//        }
//        else{
//            requestPermissions(new String[]{Manifest.permission.CAMERA},100);
//        }


    }

    public interface CameraFragmentlistener{
        void onImageAdd(Uri uri);
    }
}

