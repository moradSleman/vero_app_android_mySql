package com.example.user010.vero_project.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Friend {

    public int id;
    public String userMail1;
    public String userMail2;

    public Friend(){}
    public Friend(int id, String userMail1, String userMail2) {
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


    public static List<Friend> parseJson(JSONObject json) {

        List<Friend> friends = null;
        try {

//			JSONTokener jsonTokener = new JSONTokener(content);
//
//			JSONObject json = (JSONObject) jsonTokener.nextValue();

            friends = new ArrayList<Friend>();

            JSONArray friendsJsonArr = json.getJSONArray("friends");

            for (int i = 0; i < friendsJsonArr.length(); i++) {
                try {
                    JSONObject fObj = friendsJsonArr.getJSONObject(i);
                    Friend friend = new Friend();
                    if(friend.fromJson(fObj)){
                        friends.add(friend);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return friends;
    }
}
