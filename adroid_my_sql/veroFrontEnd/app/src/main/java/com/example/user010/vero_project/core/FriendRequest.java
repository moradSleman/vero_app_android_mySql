package com.example.user010.vero_project.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FriendRequest {

    public int friendshipID;
    public String userMail1;
    public String userMail2;
    public int friendshipStatus;

    public FriendRequest(){}

    public FriendRequest(int friendshipID, String userMail1, String userMail2, int friendshipStatus) {
        this.friendshipID = friendshipID;
        this.userMail1 = userMail1;
        this.userMail2 = userMail2;
        this.friendshipStatus = friendshipStatus;
    }

    public int getFriendshipID() {
        return friendshipID;
    }

    public void setFriendshipID(int friendshipID) {
        this.friendshipID = friendshipID;
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

    public void setUserMail2(String getUserMail2) {
        this.userMail2 = getUserMail2;
    }

    public int getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(int friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "friendshipID=" + friendshipID +
                ", userMail1='" + userMail1 + '\'' +
                ", getUserMail2='" + userMail2 + '\'' +
                ", friendshipStatus=" + friendshipStatus +
                '}';
    }

    public boolean fromJson(JSONObject fObj) {
        boolean res = false;
        try {
            setFriendshipID(fObj.getInt("id"));
            setUserMail1(fObj.getString("userMail1"));
            setUserMail2(fObj.getString("userMail2"));
            setFriendshipStatus(fObj.getInt("friendshipStatus"));
            res = true;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return res;

    }


    public static List<FriendRequest> parseJson(JSONObject json) {

        List<FriendRequest> friendRequests = null;
        try {

//			JSONTokener jsonTokener = new JSONTokener(content);
//
//			JSONObject json = (JSONObject) jsonTokener.nextValue();

            friendRequests = new ArrayList<FriendRequest>();

            JSONArray friendRequestsJsonArr = json.getJSONArray("friendsRequests");

            for (int i = 0; i < friendRequestsJsonArr.length(); i++) {
                try {
                    JSONObject fObj = friendRequestsJsonArr.getJSONObject(i);
                    FriendRequest friendRequest = new FriendRequest();
                    if(friendRequest.fromJson(fObj)){
                        friendRequests.add(friendRequest);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return friendRequests;
    }
}
