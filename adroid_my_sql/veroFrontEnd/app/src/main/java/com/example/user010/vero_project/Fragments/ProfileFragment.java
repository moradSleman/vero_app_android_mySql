package com.example.user010.vero_project.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.user010.vero_project.Fragments.FollowFragment;
import com.example.user010.vero_project.Main;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.Post;

public class ProfileFragment extends Fragment  {

    private Context context = null;
    private ImageButton follow = null;
    private ImageButton requests = null;
    private ImageButton myPosts = null;


    private Activity ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        //fill Profile stats
        ImageView profilePic = (ImageView) rootView.findViewById( R.id.profilePic );
        EditText fullName = (EditText) rootView.findViewById( R.id.fullname );
        EditText phoneNum = (EditText) rootView.findViewById( R.id.phonenum );
        EditText birthdate = (EditText) rootView.findViewById( R.id.birthdateU );
        EditText bio = (EditText) rootView.findViewById( R.id.bio );

         profilePic.setImageBitmap( MyInfoManager.getLogedUser().getProfilePhoto() );
        fullName.setText( MyInfoManager.getLogedUser().getFirstName() );
        phoneNum.setText( ((Integer)MyInfoManager.getLogedUser().getPhoneNum()).toString() );
        birthdate.setText( MyInfoManager.getLogedUser().getDateOfBirth().toString() );
          bio.setText( MyInfoManager.getLogedUser().getBiography() );


        context = rootView.getContext();

        ctx = getActivity();
        if (ctx != null) {
            ctx.setTitle(ctx.getResources().getString(R.string.Profile));
        }

        follow = (ImageButton) rootView.findViewById(R.id.followButton);
        follow.setVisibility(View.VISIBLE);
        follow.setOnClickListener(followListener);

        requests = (ImageButton) rootView.findViewById(R.id.requestsButton);
        requests.setVisibility(View.VISIBLE);
        requests.setOnClickListener(requestListener);

        myPosts = (ImageButton) rootView.findViewById(R.id.myposts);
        myPosts.setVisibility(View.VISIBLE);
        myPosts.setOnClickListener(postsListener);
        return rootView;
    }


    private View.OnClickListener followListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {

            FollowFragment followFragment = new FollowFragment();
            FragmentTransaction ftransaction = Main.fManager.beginTransaction();
            ftransaction.replace( R.id.rootview , followFragment );
            ftransaction.commit();

        }
    };

    private View.OnClickListener requestListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            RequestsFragment requestsFragment = new RequestsFragment();
            FragmentTransaction ftransaction = Main.fManager.beginTransaction();
            ftransaction.replace( R.id.rootview , requestsFragment );
            ftransaction.commit();

        }
    };

    private View.OnClickListener postsListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
           /* MyInfoManager.getInstance().getAllPostsFromServer(context);
            MyInfoManager.getInstance().getAllPostRepliesFromServer(context);*/
            PostListFragment myPosts = new PostListFragment();
            FragmentTransaction ftransaction = Main.fManager.beginTransaction();
            ftransaction.replace( R.id.rootview , myPosts );
            ftransaction.commit();

        }
    };




}
