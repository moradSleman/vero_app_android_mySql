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

import com.example.user010.vero_project.LogInActivity;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.Follow;
import com.example.user010.vero_project.core.Friend;
import com.example.user010.vero_project.core.FriendRequest;
import com.example.user010.vero_project.core.User;

import java.util.List;

public class InfoUserListAdapter extends ArrayAdapter<User> {

    private Context context;
    public static List<User> users;

    public InfoUserListAdapter(Context context, int resource, List<User> users) {
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
        View rootView = mInflater.inflate(R.layout.friend_list_addbtn, null);
        final User currentUser = getUserPosition( position );
        TextView title = (TextView) rootView.findViewById(R.id.userName1);
        title.setText(currentUser.getFirstName());


        ImageView addIcon = (ImageView) rootView.findViewById(R.id.addIcon);
        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = "Add Friend";
                final String msg = "Are you sure?";
                AlertDialog.Builder  builder=  new AlertDialog.Builder(context);
                builder.setTitle(title);
                builder.setMessage(msg);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        FriendRequest newFriendReq = new FriendRequest( MyInfoManager.getRandomNumberInRange() ,MyInfoManager.getLogedUser().getEmail()  , currentUser.getEmail() , 0 );
                        MyInfoManager.getInstance().createRequest( newFriendReq );
                        InfoUserListAdapter.this.remove(currentUser);
                        InfoUserListAdapter.this.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog .show();
            }
        });


        return rootView;
    }


    public User getUserPosition(int position) {
        return users.get(position);
    }
}

