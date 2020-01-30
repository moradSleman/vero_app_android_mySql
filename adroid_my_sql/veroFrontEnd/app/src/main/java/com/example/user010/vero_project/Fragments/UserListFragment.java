package com.example.user010.vero_project.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


import com.example.user010.vero_project.Adapters.InfoUserListAdapter;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class UserListFragment extends Fragment  {

    private ListView usersList;
    private InfoUserListAdapter adapter;
    private Context context = null;
    private Activity ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_user_list, container, false);

        context = rootView.getContext();

        ctx = getActivity();
        if (ctx != null) {
            ctx.setTitle(ctx.getResources().getString(R.string.Users));
        }

        usersList = (ListView) rootView.findViewById(R.id.allusersList);
        HashSet<User> listHash = MyInfoManager.getInstance().getAllUsers();
        List<User> list = new ArrayList<User>();
        for(User user : listHash) {
            list.add(user);
        }
        System.out.println("erty");
        list.remove(MyInfoManager.getLogedUser());
        System.out.println("erty1");
        adapter = new InfoUserListAdapter(context, R.layout.friend_list_addbtn, list);
        usersList.setAdapter(adapter);
        System.out.println("erty2");

        return rootView;
    }



}
