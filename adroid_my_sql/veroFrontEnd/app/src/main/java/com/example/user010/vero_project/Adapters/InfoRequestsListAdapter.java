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

public class InfoRequestsListAdapter extends ArrayAdapter<User> {

    private Context context;
    public static List<User> requests;

    public InfoRequestsListAdapter(Context context, int resource, List<User> requests) {
        super( context, resource, requests );
        this.context = context;
        this.requests = requests;
    }


    public int getCount() {
        return requests.size();
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View rootView = mInflater.inflate(R.layout.request_list_acceptbtn, null);
        final User currentUser = getUserPosition( position );
        TextView title = (TextView) rootView.findViewById(R.id.userName2);
        title.setText(currentUser.getFirstName());


        ImageView acceptIcon = (ImageView) rootView.findViewById(R.id.acceptIcon);
        ImageView declineIcon = (ImageView) rootView.findViewById(R.id.declineIcon);

        acceptIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = "Accept Friendship";
                final String msg = "Are you sure?";
                AlertDialog.Builder  builder=  new AlertDialog.Builder(context);
                builder.setTitle(title);
                builder.setMessage(msg);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Friend newFriend = new Friend( MyInfoManager.getRandomNumberInRange() , MyInfoManager.getLogedUser().getEmail() , currentUser.getEmail() );
                        MyInfoManager.getInstance().createFriend( newFriend );
                        List<FriendRequest> requests = MyInfoManager.getInstance().getAllFriendshipsRequest();
                        FriendRequest reqToRemove = null;
                        for(int i=0 ; i < requests.size() ; i++){
                            if(requests.get( i ).getUserMail1().equals(currentUser.getEmail() ) &&
                                    requests.get( i ).getUserMail2().equals( MyInfoManager.getLogedUser().getEmail()  )){
                                reqToRemove = requests.get( i );
                            }
                        }
                        MyInfoManager.getInstance().deleteFriendRequest( reqToRemove );
                        InfoRequestsListAdapter.this.remove(currentUser);
                        InfoRequestsListAdapter.this.notifyDataSetChanged();
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


        declineIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = "Decline Friendship";
                final String msg = "Are you sure?";
                AlertDialog.Builder  builder=  new AlertDialog.Builder(context);
                builder.setTitle(title);
                builder.setMessage(msg);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        List<FriendRequest> requests = MyInfoManager.getInstance().getAllFriendshipsRequest();
                        FriendRequest reqToRemove = null;
                        for(int i=0 ; i < requests.size() ; i++){
                            if(requests.get( i ).getUserMail1().equals(currentUser.getEmail() ) &&
                                    requests.get( i ).getUserMail2().equals( MyInfoManager.getLogedUser().getEmail()  )){
                                reqToRemove = requests.get( i );
                            }
                        }
                        MyInfoManager.getInstance().deleteFriendRequest( reqToRemove );

                        InfoRequestsListAdapter.this.remove(currentUser);
                        InfoRequestsListAdapter.this.notifyDataSetChanged();
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
        return requests.get(position);
    }
}
