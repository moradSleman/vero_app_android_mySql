package com.example.user010.vero_project.Adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user010.vero_project.Fragments.ReplyFragment;
import com.example.user010.vero_project.Main;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.core.Post;
import com.example.user010.vero_project.core.PostReply;

import java.util.List;

public class InfoReplyAdapter extends ArrayAdapter<PostReply> {

    private Context context;
    public static List<PostReply> postReplys;



    public InfoReplyAdapter(Context context, int resource, List<PostReply> posts) {
        super( context, resource, posts );
        this.context = context;
        postReplys = posts;
    }


    public int getCount() {
        return postReplys.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View rootView = mInflater.inflate(R.layout.fragment_myposts_reply, null);
        final PostReply currentReply = getPostRPosition( position );
        TextView title = (TextView) rootView.findViewById(R.id.postDesc6);
        title.setText(currentReply.getComment());

        return rootView;
    }


    public PostReply getPostRPosition(int position) {
        return postReplys.get(position);
    }

}
