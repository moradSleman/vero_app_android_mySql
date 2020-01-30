package com.example.user010.vero_project;




import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.user010.vero_project.Fragments.PostFragment;
import com.example.user010.vero_project.Fragments.UserFriendFragment;
import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.Fragments.PostListFragment;
import com.example.user010.vero_project.Fragments.ProfileFragment;
import com.example.user010.vero_project.network.NetworkConnector;


public class Main extends AppCompatActivity {

    public static FragmentManager fManager;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        MyInfoManager.getInstance().getALlFriendsFromServer(this);

        fManager = getFragmentManager();
        hideKeyboard();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;

    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_friends: {

                UserFriendFragment friendFragment = new UserFriendFragment();
                FragmentTransaction ftransaction = fManager.beginTransaction();
                ftransaction.replace(R.id.rootview, friendFragment);
                ftransaction.commit();
                return true;
            }
            case R.id.menuitem_posts: {

                PostFragment postsFragment = new PostFragment();
                FragmentTransaction ftransaction = fManager.beginTransaction();
                ftransaction.replace(R.id.rootview, postsFragment);
                ftransaction.commit();
                return true;
            }
            case R.id.menuitem_profile: {
                MyInfoManager.getInstance().getAllFriendsRequestsFromServer(this);
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentTransaction ftransaction = fManager.beginTransaction();
                ftransaction.replace(R.id.rootview, profileFragment);
                ftransaction.commit();
                return true;
            }
            case R.id.menuitem_logout: {
                MyInfoManager.getInstance().setLogedUser(null);
                Intent intent;
                intent = new Intent(this, LogInActivity.class);
                this.startActivity(intent);
            }

        }
        return false;
    }


    @Override
    protected void onResume() {
        MyInfoManager.getInstance().openDataBase(this);
        super.onResume();

    }

    @Override
    protected void onPause() {
        MyInfoManager.getInstance().closeDataBase();
        super.onPause();
    }


    private void hideKeyboard() {
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}