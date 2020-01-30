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
import com.example.user010.vero_project.core.FriendRequest;
import com.example.user010.vero_project.core.User;

import java.util.List;

public class InfoFriendListAdapter extends ArrayAdapter<User> {


    private Context context;
    public static List<User> users;

    public InfoFriendListAdapter(Context context, int resource, List<User> users) {
        super( context, resource, users );
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View rootView = mInflater.inflate(R.layout.friends_list, null);
        final User currentUser = getUserPosition( position );
        TextView title = (TextView) rootView.findViewById(R.id.userNameFL);
        title.setText(currentUser.getFirstName());

        return rootView;
    }


    public User getUserPosition(int position) {
        return users.get(position);
    }
}
