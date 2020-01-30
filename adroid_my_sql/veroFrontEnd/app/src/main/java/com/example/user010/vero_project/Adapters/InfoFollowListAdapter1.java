package com.example.user010.vero_project.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user010.vero_project.R;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.Follow;
import com.example.user010.vero_project.core.User;

import java.util.List;

public class InfoFollowListAdapter1 extends ArrayAdapter<User> {

    private Context context;
    public static List<User> follows;

    public InfoFollowListAdapter1(Context context, int resource, List<User> follows) {
        super( context, resource, follows );
        this.context = context;
        this.follows = follows;
    }


    public int getCount() {
        return follows.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View rootView = mInflater.inflate(R.layout.friend_list_follow, null);
        final User currentUser = getUserPosition( position );
        TextView title = (TextView) rootView.findViewById(R.id.userNameFF);
        title.setText(currentUser.getFirstName());

        return rootView;
    }


    public User getUserPosition(int position) {
        return follows.get(position);
    }
}
