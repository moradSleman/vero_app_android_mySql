package com.example.user010.vero_project.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostReply {

    private int replyID;
    private String userReply;
    private int postID;
    private boolean like;
    private String comment;


    public PostReply() {}
    public PostReply(int replyID,String userReply, int postID, boolean like, String comment) {
        super();
        this.replyID=replyID;
        this.userReply = userReply;
        this.postID = postID;
        this.like = like;
        this.comment = comment;
    }

    public int getReplyID() {
        return replyID;
    }

    public void setReplyID(int replyID) {
        this.replyID = replyID;
    }

    public String getUserReply() {
        return userReply;
    }
    public void setUserReply(String userReply) {
        this.userReply = userReply;
    }
    public int getPostID() {
        return postID;
    }
    public void setPostID(int postID) {
        this.postID = postID;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + postID;
        result = prime * result + replyID;
        result = prime * result + ((userReply == null) ? 0 : userReply.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PostReply other = (PostReply) obj;
        if (postID != other.postID)
            return false;
        if (replyID != other.replyID)
            return false;
        if (userReply == null) {
            if (other.userReply != null)
                return false;
        } else if (!userReply.equals(other.userReply))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PostReply{" +
                "replyID=" + replyID +
                ", userReply='" + userReply + '\'' +
                ", postID=" + postID +
                ", like=" + like +
                ", comment='" + comment + '\'' +
                '}';
    }



    public boolean fromJson(JSONObject fObj) {
        boolean res = false;
        try {
            setReplyID(fObj.getInt("replyID"));
            setUserReply(fObj.getString("userReply"));
            setPostID(fObj.getInt("postID"));
            setLike(fObj.getBoolean("like"));
            setComment(fObj.getString("comment"));
            res = true;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return res;

    }


    public static List<PostReply> parseJson(JSONObject json) {

        List<PostReply> postReplies = null;
        try {

//			JSONTokener jsonTokener = new JSONTokener(content);
//
//			JSONObject json = (JSONObject) jsonTokener.nextValue();

            postReplies = new ArrayList<PostReply>();

            JSONArray postRepliesJsonArr = json.getJSONArray("PostRepleis");

            for (int i = 0; i < postRepliesJsonArr.length(); i++) {
                try {
                    JSONObject fObj = postRepliesJsonArr.getJSONObject(i);
                    PostReply postReply = new PostReply();
                    if(postReply.fromJson(fObj)){
                        postReplies.add(postReply);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return postReplies;
    }
}
