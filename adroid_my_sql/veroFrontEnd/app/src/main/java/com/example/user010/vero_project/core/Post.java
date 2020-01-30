package com.example.user010.vero_project.core;



import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Post {


        private int postID;
        private String userName;
        private Date date;
        private String link;
        private Bitmap photo ;
        private String postDescription;
        private int likesNum;
        private int commentsNum;


    public Post() {

    }

        public Post(int postID, String userName,Date date , String link, Bitmap photo, String postDescription) {
            super();
            this.postID = postID;
            this.userName = userName;
            this.link = link;
            this.photo = photo;
            this.postDescription = postDescription;
            this.date= date;
            likesNum=0;
            commentsNum=0;
        }
        public Post(int postID) {
            super();
            this.postID=postID;
        }


        public int getPostID() {
            return postID;
        }
        public void setPostID(int postID) {
            this.postID = postID;
        }
        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
        public Date getDate() {
            return date;
        }
        public void setDate(Date date) {
            this.date = date;
        }

        public String getLink() {
            return link;
        }
        public void setLink(String link) {
            this.link = link;
        }
    public Bitmap getPhoto() {
        return photo;
    }
    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
        public String getPostDescription() {
            return postDescription;
        }
        public void setPostDescription(String postDescription) {
            this.postDescription = postDescription;
        }
        public int getLikesNum() {
            return likesNum;
        }
        public void setLikesNum(int likesNum) {
            this.likesNum = likesNum;
        }
        public int getCommentsNum() {
            return commentsNum;
        }
        public void setCommentsNum(int commentsNum) {
            this.commentsNum = commentsNum;
        }
        public void addLike() {
            this.likesNum++;
        }
        public void addComment() {
            this.commentsNum++;
        }




    @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Post other = (Post) obj;
            if (postID != other.postID)
                return false;
            return true;
        }


    public boolean fromJson(JSONObject fObj) {
        boolean res = false;
        try {
            setPostID(fObj.getInt("postID"));
            setUserName(fObj.getString("userName"));
            setLink(fObj.getString("link"));
            setPostDescription(fObj.getString("postDescription"));
            String dateStr = fObj.getString("date");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = sdf.parse(dateStr);
            setDate(birthDate);
            res = true;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return res;

    }


    public static List<Post> parseJson(JSONObject json) {

        List<Post> posts = null;
        try {

//			JSONTokener jsonTokener = new JSONTokener(content);
//
//			JSONObject json = (JSONObject) jsonTokener.nextValue();

            posts = new ArrayList<Post>();

            JSONArray postsJsonArr = json.getJSONArray("posts");

            for (int i = 0; i < postsJsonArr.length(); i++) {
                try {
                    JSONObject fObj = postsJsonArr.getJSONObject(i);
                    Post post = new Post();
                    if(post.fromJson(fObj)){
                        posts.add(post);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return posts;
    }


    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


}
