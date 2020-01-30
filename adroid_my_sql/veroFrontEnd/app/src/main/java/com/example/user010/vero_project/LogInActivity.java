package com.example.user010.vero_project;

import android.app.Activity;
import android.os.Bundle;


import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user010.vero_project.controller.MyInfoManager;
import com.example.user010.vero_project.core.User;
import com.example.user010.vero_project.network.NetworkConnector;

import java.util.List;

public class LogInActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        MyInfoManager.getInstance().openDataBase(this);
        NetworkConnector.getInstance().initialize(this);
        MyInfoManager.getInstance().getALlUsersFromServer(this);

    }

    public void showMain(View view) {

        EditText mail = (EditText) findViewById(R.id.userName);
        String userMail = mail.getText().toString();
        EditText pass = (EditText) findViewById(R.id.userpass);
        String userPass = pass.getText().toString();


     /*   List<User> list = MyInfoManager.db.getAllUsers();
        for(int i=0 ; i<list.size() ; i++){
            //user validation
            if((list.get( i ).getEmail().equals( userMail )) && (list.get( i ).getPassword().equals( userPass ))){
                MyInfoManager.getInstance().setLogedUser( list.get( i ) );
                Intent intent;
                intent = new Intent(this , Main.class);
                this.startActivity(intent);
            }else{
                Toast.makeText( this,"Please enter your valid email and password .",Toast.LENGTH_LONG ).show();
            }
        }*/
        for (User user : MyInfoManager.db.getAllUsers()) {
            if (user.getEmail().equals(userMail)) {
                if (user.getPassword().equals(userPass)) {
                    Intent intent;
                    intent = new Intent(this, Main.class);
                    this.startActivity(intent);
                    MyInfoManager.getInstance().setLogedUser(user);
                    break;
                } else {
                    Toast.makeText(this, "Please enter your email and password .", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(this, "Please enter your email and password .", Toast.LENGTH_LONG).show();
            }
        }


    }
}
