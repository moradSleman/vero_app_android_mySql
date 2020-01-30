package com.example.user010.vero_project.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.user010.vero_project.Adapters.InfoPostAdapter;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.Friend;
import com.example.user010.vero_project.core.Post;
import com.example.user010.vero_project.core.User;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends android.app.Fragment {

    private Context context = null;
    private Activity ctx;
    private InfoPostAdapter adapter;
    private ListView postsList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_post, container, false);

        context = rootView.getContext();

        ctx = getActivity();
        if (ctx != null) {
            ctx.setTitle(ctx.getResources().getString(R.string.Posts));
        }

        List<User> listFriends = MyInfoManager.getInstance().getAllUserFriends( MyInfoManager.getLogedUser()  );
        List<Post> friendsPosts = new ArrayList<Post>();

        for(int i=0 ; i< listFriends.size() ; i++){
            if( MyInfoManager.getInstance().getAllUsersPosts( listFriends.get( i ) )!=null)
            friendsPosts.addAll( MyInfoManager.getInstance().getAllUsersPosts( listFriends.get( i ) ) );
        }
        postsList = (ListView) rootView.findViewById( R.id.friendspostList );
        adapter = new InfoPostAdapter(context, R.layout.fragment_post, friendsPosts);
        postsList.setAdapter( adapter );
        postsList.setVisibility( View.VISIBLE );



        return rootView;
    }

}
