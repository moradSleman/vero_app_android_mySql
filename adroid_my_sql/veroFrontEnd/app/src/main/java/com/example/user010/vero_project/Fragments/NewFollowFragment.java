package com.example.user010.vero_project.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.user010.vero_project.Adapters.InfoFollowListAdapter;
import com.example.user010.vero_project.LogInActivity;
import com.example.user010.vero_project.Main;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class NewFollowFragment extends Fragment {

    private ListView friendsList;
    private InfoFollowListAdapter adapter;
    private Context context = null;
    private Activity ctx;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_new_follow, container, false);

        context = rootView.getContext();

        ctx = getActivity();
        if (ctx != null) {
            ctx.setTitle(ctx.getResources().getString(R.string.Users));
        }

        friendsList = (ListView) rootView.findViewById(R.id.friendsList);
        HashSet<User> list = MyInfoManager.getInstance().getAllUsers();
        HashSet<User> followings=MyInfoManager.getInstance().getAllUserFollowings(MyInfoManager.getLogedUser());
        for(User user : followings){
            if(list.contains(user)){
                list.remove(user);
            }
        }
        List<User> list2=new ArrayList<User>();
        for(User user : list) {
            list2.add(user);
        }
        adapter = new InfoFollowListAdapter(context, R.layout.friend_list_followbtn, list2);
        friendsList.setAdapter( adapter );

        return rootView;
    }


}
