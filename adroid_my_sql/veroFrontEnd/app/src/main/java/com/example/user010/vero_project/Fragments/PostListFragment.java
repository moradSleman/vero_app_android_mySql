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
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.user010.vero_project.Adapters.InfoPostAdapter;
import com.example.user010.vero_project.LogInActivity;
import com.example.user010.vero_project.Main;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.Post;

import java.util.List;

public class PostListFragment extends Fragment {

    private ListView postsList;
    private InfoPostAdapter adapter;
    private Context context = null;
    private ImageButton newpostBtn = null;
    private Activity ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_post_list, container, false);

        context = rootView.getContext();

        ctx = getActivity();
        if (ctx != null) {
            ctx.setTitle(ctx.getResources().getString(R.string.myPosts));
        }

        postsList = (ListView) rootView.findViewById(R.id.postList);


        newpostBtn = (ImageButton) rootView.findViewById(R.id.new_post_btn);
        newpostBtn.setVisibility(View.VISIBLE);
        newpostBtn.setOnClickListener(newPostListener);

        List<Post> list = MyInfoManager.getInstance().getAllUsersPosts( MyInfoManager.getLogedUser()  );
        adapter = new InfoPostAdapter(context, R.layout.fragment_post_list, list);
        postsList.setAdapter( adapter );
        postsList.setVisibility( View.VISIBLE );

        return rootView;
    }

    private View.OnClickListener newPostListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            NewPostFragment newpostFragment = new NewPostFragment();
            FragmentTransaction ftransaction = Main.fManager.beginTransaction();
            ftransaction.replace( R.id.rootview , newpostFragment );
            ftransaction.commit();

        }
    };




}
