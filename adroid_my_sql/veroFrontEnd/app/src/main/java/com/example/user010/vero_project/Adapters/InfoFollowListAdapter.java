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
import com.example.user010.vero_project.core.User;

import java.util.List;
import java.util.Random;

public class InfoFollowListAdapter extends ArrayAdapter<User> {

    private Context context;
    public static List<User> follows;

    public InfoFollowListAdapter(Context context, int resource, List<User> follows) {
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
        View rootView = mInflater.inflate(R.layout.friend_list_followbtn, null);
        final User currentUser = getUserPosition( position );
        TextView title = (TextView) rootView.findViewById(R.id.userName);
        title.setText(currentUser.getFirstName());


        ImageView followIcon = (ImageView) rootView.findViewById(R.id.followIcon);
        followIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = "Follow Friend";
                final String msg = "Are you sure?";
                AlertDialog.Builder  builder=  new AlertDialog.Builder(context);
                builder.setTitle(title);
                builder.setMessage(msg);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Follow newfollow = new Follow( MyInfoManager.getRandomNumberInRange() , MyInfoManager.getLogedUser().getEmail(), currentUser.getEmail() );
                        MyInfoManager.getInstance().createFollow( newfollow );
                        MyInfoManager.getInstance().deleteFollow(newfollow);
                        InfoFollowListAdapter.this.remove(currentUser);
                        InfoFollowListAdapter.this.notifyDataSetChanged();
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
        return follows.get(position);
    }
}
