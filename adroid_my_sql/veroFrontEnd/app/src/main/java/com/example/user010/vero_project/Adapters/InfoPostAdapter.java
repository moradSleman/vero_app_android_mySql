package com.example.user010.vero_project.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user010.vero_project.Fragments.PostFragment;
import com.example.user010.vero_project.Fragments.ReplyFragment;
import com.example.user010.vero_project.Main;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.Follow;
import com.example.user010.vero_project.core.FriendRequest;
import com.example.user010.vero_project.core.Post;
import com.example.user010.vero_project.core.User;

import java.util.List;

public class InfoPostAdapter  extends ArrayAdapter<Post> {
    private Context context;
    public static List<Post> posts;
    public static Post currentPost;


    public InfoPostAdapter(Context context, int resource, List<Post> posts) {
        super( context, resource, posts );
        this.context = context;
        this.posts = posts;
    }


    public int getCount() {
        return posts.size();
    }

    public static Post getCurrentPost() {
        return currentPost;
    }

    public static void setCurrentPost(Post currentPost) {
        InfoPostAdapter.currentPost = currentPost;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View rootView = mInflater.inflate(R.layout.post_list_replybtn, null);
        currentPost = getPostPosition(position);
        TextView title = (TextView) rootView.findViewById(R.id.postDesc1);
      //  TextView title1 = (TextView) rootView.findViewById(R.id.postDesc);
            title.setText(currentPost.getPostDescription());


        ImageView replyIcon = (ImageView) rootView.findViewById(R.id.postreply);
        replyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCurrentPost(currentPost);
                ReplyFragment replyFragment = new ReplyFragment();
                FragmentTransaction ftransaction = Main.fManager.beginTransaction();
                ftransaction.replace(R.id.rootview, replyFragment);
                ftransaction.commit();

                    }
                });

        return rootView;
    }


    public Post getPostPosition(int position) {
        return posts.get(position);
    }
}
