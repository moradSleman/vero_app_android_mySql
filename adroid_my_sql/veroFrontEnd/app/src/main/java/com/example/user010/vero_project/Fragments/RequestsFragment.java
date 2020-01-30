package com.example.user010.vero_project.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.user010.vero_project.Adapters.InfoRequestsListAdapter;
import com.example.user010.vero_project.Adapters.InfoRequestsListAdapter1;
import com.example.user010.vero_project.LogInActivity;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.User;

import java.util.List;

public class RequestsFragment extends Fragment {

    private ListView requestList;
    private InfoRequestsListAdapter adapter;
    private InfoRequestsListAdapter1 adapter1;
    private Context context = null;
    private Button sentBtn = null;
    private Button recievedBtn = null;

    private Activity ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_requests, container, false);

        context = rootView.getContext();
        MyInfoManager.getInstance().getAllFriendsRequestsFromServer(context);
        ctx = getActivity();
        if (ctx != null) {
            ctx.setTitle(ctx.getResources().getString(R.string.requests));
        }

        requestList = (ListView) rootView.findViewById(R.id.requestsList);
        requestList.setVisibility( View.VISIBLE );

        sentBtn = (Button) rootView.findViewById(R.id.sentRequest);
        recievedBtn = (Button) rootView.findViewById(R.id.recieveRequest);

        sentBtn.setVisibility( View.VISIBLE );
        recievedBtn.setVisibility( View.VISIBLE );

        sentBtn.setOnClickListener( sentListener );
        recievedBtn.setOnClickListener( recievedListener );



        return rootView;
    }




    private View.OnClickListener sentListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            List<User> list = MyInfoManager.getInstance().getAllUserFriendRequests( MyInfoManager.getLogedUser()  );
            adapter1 = new InfoRequestsListAdapter1(context, R.layout.request_list_declinebtn, list);
            requestList.setAdapter( (ListAdapter) adapter1 );


        }
    };

    private View.OnClickListener recievedListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            List<User> list = MyInfoManager.getInstance().getAllUserRecievedRequests(MyInfoManager.getLogedUser()  );
            adapter = new InfoRequestsListAdapter(context, R.layout.request_list_acceptbtn, list);
            requestList.setAdapter( (ListAdapter) adapter );

        }
    };

}
