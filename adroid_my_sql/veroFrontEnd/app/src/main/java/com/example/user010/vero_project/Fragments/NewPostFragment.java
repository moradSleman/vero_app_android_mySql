package com.example.user010.vero_project.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user010.vero_project.Adapters.InfoPostAdapter;
import com.example.user010.vero_project.CameraControlActivity;
import com.example.user010.vero_project.Main;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.Post;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class NewPostFragment extends Fragment {

    private Context context = null;
    private Activity ctx;

    public ImageView profilePic;
    private ImageButton takePic;
    private ImageButton browsePic;
    private static int RESULT_LOAD_IMAGE = 1;

    public EditText link;
    public EditText desc;
    private Button sendPost;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_new_post, container, false);

        context = rootView.getContext();

        ctx = getActivity();
        if (ctx != null) {
            ctx.setTitle(ctx.getResources().getString(R.string.new_post_btn_str));
        }

        profilePic = (ImageView) rootView.findViewById( R.id.profilePic );
        //profilePic.setVisibility( View.VISIBLE );

        takePic = (ImageButton) rootView.findViewById( R.id.takePic );
        browsePic = (ImageButton) rootView.findViewById( R.id.browsePic );

        takePic.setVisibility( View.VISIBLE );
        browsePic.setVisibility( View.VISIBLE );

        takePic.setOnClickListener( newPicListener );
        browsePic.setOnClickListener( newPicBListener );

        link = (EditText) rootView.findViewById( R.id.link );
        desc = (EditText) rootView.findViewById( R.id.postDesc );
        sendPost = (Button) rootView.findViewById( R.id.sendPost );
        sendPost.setOnClickListener( newPostListener );


        return rootView;
    }

    private View.OnClickListener newPicListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(getActivity(), CameraControlActivity.class);
            startActivity(intent);

        }
    };

    private View.OnClickListener newPicBListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, RESULT_LOAD_IMAGE);

        }
    };


  /*  public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            profilePic.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }*/


    private View.OnClickListener newPostListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Bitmap pic=null;
            Date currentDate = Calendar.getInstance().getTime();
            if(profilePic!=null) {
                 pic = ((BitmapDrawable) profilePic.getDrawable()).getBitmap();
            }

            Post newPost = new Post( MyInfoManager.getRandomNumberInRange(),MyInfoManager.getLogedUser().getEmail(),currentDate,
                                    link.getText().toString(),null, desc.getText().toString() );

            MyInfoManager.getInstance().createPost( newPost );



        }
    };

}
