package com.example.user010.vero_project.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.user010.vero_project.Adapters.InfoPostAdapter;
import com.example.user010.vero_project.Adapters.InfoReplyAdapter;
import com.example.user010.vero_project.R;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.Post;
import com.example.user010.vero_project.core.PostReply;

import java.util.List;

import static com.example.user010.vero_project.Main.fManager;

public class AddReplyFragment extends Fragment {


    private Context context = null;
    private Activity ctx;
    private Button addReply;
    private CheckBox like;
    private EditText comment;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_newreply, container, false);

        context = rootView.getContext();

        ctx = getActivity();
        if (ctx != null) {
            ctx.setTitle(ctx.getResources().getString(R.string.reply));
        }

        comment = (EditText) rootView.findViewById(R.id.replycomment);
        like = (CheckBox) rootView.findViewById(R.id.likeBox);
        addReply = (Button) rootView.findViewById(R.id.Replybutton);

        addReply.setOnClickListener(addReplyListener);


        return rootView;
    }


    private View.OnClickListener addReplyListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {

            boolean bool=false;
            if(like.isChecked()) {
                bool = true;
            }
            PostReply postReply = new PostReply(MyInfoManager.getRandomNumberInRange() , MyInfoManager.getLogedUser().getEmail(),
                                              InfoPostAdapter.getCurrentPost().getPostID(),bool,comment.getText().toString());
            System.out.println(comment.getText().toString());
            MyInfoManager.getInstance().createReply(postReply);


        }
    };

}
