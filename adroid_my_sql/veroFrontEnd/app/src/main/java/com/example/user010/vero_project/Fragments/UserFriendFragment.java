package com.example.user010.vero_project.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.user010.vero_project.Adapters.InfoFriendListAdapter;
import com.example.user010.vero_project.Adapters.InfoUserListAdapter;
import com.example.user010.vero_project.LogInActivity;
import com.example.user010.vero_project.Main;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.User;

import java.util.List;

public class UserFriendFragment extends Fragment {
    private ListView friendsList;
    private InfoFriendListAdapter adapter;
    private Context context = null;
    private ImageButton newfriendBtn = null;
    private Activity ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        context = rootView.getContext();

        ctx = getActivity();
        if (ctx != null) {
            ctx.setTitle(ctx.getResources().getString(R.string.Frineds));
        }

        friendsList = (ListView) rootView.findViewById(R.id.friendsListTT);


        newfriendBtn = (ImageButton) rootView.findViewById(R.id.newFriendBtn);
        newfriendBtn.setVisibility(View.VISIBLE);

        newfriendBtn.setOnClickListener(newFriendListener);

        List<User> list = MyInfoManager.getInstance().getAllUserFriends(MyInfoManager.getLogedUser() );
        adapter = new InfoFriendListAdapter(context, R.layout.friends_list, list);
        friendsList.setAdapter(adapter);
        friendsList.setVisibility( View.VISIBLE );

        return rootView;
    }






    private View.OnClickListener newFriendListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {

            UserListFragment userListFragment = new UserListFragment();
            FragmentTransaction ftransaction = Main.fManager.beginTransaction();
            ftransaction.replace( R.id.rootview , userListFragment );
            ftransaction.commit();

        }
    };

}
