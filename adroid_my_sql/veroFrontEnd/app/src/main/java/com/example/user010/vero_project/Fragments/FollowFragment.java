package com.example.user010.vero_project.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.user010.vero_project.Adapters.InfoFollowListAdapter1;
import com.example.user010.vero_project.LogInActivity;
import com.example.user010.vero_project.Main;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.Adapters.InfoFollowListAdapter;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FollowFragment extends Fragment {

    private ListView followList;
    private InfoFollowListAdapter adapter;
    private InfoFollowListAdapter1 adapter1;
    private Context context = null;
    private ImageButton newfollowingBtn = null;
    private Button followingBtn = null;
    private Button followersBtn = null;

    private Activity ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_follow, container, false);

        context = rootView.getContext();
        MyInfoManager.getInstance().getALlFollowsFromServer(context);
        ctx = getActivity();
        if (ctx != null) {
            ctx.setTitle(ctx.getResources().getString(R.string.follows));
        }

        followList = (ListView) rootView.findViewById(R.id.followlist);


        newfollowingBtn = (ImageButton) rootView.findViewById(R.id.new_follow_btn);
        followingBtn = (Button) rootView.findViewById(R.id.followingButton);
        followersBtn = (Button) rootView.findViewById(R.id.followersButton);

        newfollowingBtn.setVisibility(View.VISIBLE);
        followingBtn.setVisibility( View.VISIBLE );
        followersBtn.setVisibility( View.VISIBLE );


        newfollowingBtn.setOnClickListener(newFollowingListener);
        followingBtn.setOnClickListener( FollowingListener );
        followersBtn.setOnClickListener( FollowerListener );



        return rootView;
    }






    private View.OnClickListener newFollowingListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            NewFollowFragment newFollowFragment = new NewFollowFragment();
            FragmentTransaction ftransaction = Main.fManager.beginTransaction();
            ftransaction.replace( R.id.rootview , newFollowFragment );
            ftransaction.commit();
        }
    };

    private View.OnClickListener FollowingListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            HashSet<User> list = MyInfoManager.getInstance().getAllUserFollowings( MyInfoManager.getLogedUser() );
            List<User> users=new ArrayList<User>();
            for(User user: list){
                users.add(user);
            }
            users.remove(MyInfoManager.getLogedUser());
            adapter1 = new InfoFollowListAdapter1(context, R.layout.fragment_follow, users);
            followList.setAdapter(adapter1);

        }
    };

    private View.OnClickListener FollowerListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            HashSet<User> list = MyInfoManager.getInstance().getAllUserFollowers( MyInfoManager.getLogedUser() );
            List<User> users=new ArrayList<User>();
            for(User user: list){
                users.add(user);
            }
            users.remove(MyInfoManager.getLogedUser());
            adapter1 = new InfoFollowListAdapter1(context, R.layout.fragment_follow, users);
            followList.setAdapter(adapter1 );


        }
    };



}
