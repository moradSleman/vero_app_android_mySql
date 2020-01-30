package com.example.user010.vero_project.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.user010.vero_project.Adapters.InfoFollowListAdapter1;
import com.example.user010.vero_project.Adapters.InfoPostAdapter;
import com.example.user010.vero_project.Adapters.InfoReplyAdapter;
import com.example.user010.vero_project.Main;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.Post;
import com.example.user010.vero_project.core.PostReply;
import com.example.user010.vero_project.core.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.example.user010.vero_project.Main.fManager;

public class ReplyFragment extends Fragment {

    private Context context = null;
    private Activity ctx;
    private ImageButton addReply;
    private InfoPostAdapter postAdapter;
    private InfoReplyAdapter adapter;
    private ListView replyList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_reply_list, container, false);

        context = rootView.getContext();

        ctx = getActivity();
        if (ctx != null) {
            ctx.setTitle(ctx.getResources().getString(R.string.reply));
        }

        addReply = (ImageButton) rootView.findViewById(R.id.addReply);
        addReply.setOnClickListener(addReplyListener);
        List<PostReply> comments = MyInfoManager.getInstance().getAllUsersPostReply( postAdapter.getCurrentPost() );
        replyList = (ListView) rootView.findViewById( R.id.replyList1 );
        adapter = new InfoReplyAdapter(context, R.layout.fragment_reply_list, comments);
        replyList.setAdapter( adapter );
        replyList.setVisibility( View.VISIBLE );

        return rootView;
    }


    private View.OnClickListener addReplyListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {

            AddReplyFragment replyFragment = new AddReplyFragment();
            FragmentTransaction ftransaction = fManager.beginTransaction();
            ftransaction.replace(R.id.rootview, replyFragment);
            ftransaction.commit();

        }
    };





}
