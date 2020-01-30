package com.example.user010.vero_project.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Follow {


    public int id;
    public String userMail1;
    public String userMail2;

    public Follow(){}
    public Follow(int id, String userMail1, String userMail2) {
        this.id = id;
        this.userMail1 = userMail1;
        this.userMail2 = userMail2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserMail1() {
        return userMail1;
    }

    public void setUserMail1(String userMail1) {
        this.userMail1 = userMail1;
    }

    public String getUserMail2() {
        return userMail2;
    }

    public void setUserMail2(String userMail2) {
        this.userMail2 = userMail2;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", userMail1='" + userMail1 + '\'' +
                ", userMail2='" + userMail2 + '\'' +
                '}';
    }

    public boolean fromJson(JSONObject fObj) {
        boolean res = false;
        try {
            setId(fObj.getInt("id"));
            setUserMail1(fObj.getString("userMail1"));
            setUserMail2(fObj.getString("userMail2"));
            res = true;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return res;

    }


    public static List<Follow> parseJson(JSONObject json) {

        List<Follow> follows = null;
        try {

//			JSONTokener jsonTokener = new JSONTokener(content);
//
//			JSONObject json = (JSONObject) jsonTokener.nextValue();

            follows = new ArrayList<Follow>();

            JSONArray followsJsonArr = json.getJSONArray("follows");

            for (int i = 0; i < followsJsonArr.length(); i++) {
                try {
                    JSONObject fObj = followsJsonArr.getJSONObject(i);
                    Follow follow = new Follow();
                    if(follow.fromJson(fObj)){
                        follows.add(follow);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return follows;
    }
}
