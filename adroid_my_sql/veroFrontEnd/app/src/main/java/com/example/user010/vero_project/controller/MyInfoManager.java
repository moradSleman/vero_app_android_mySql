package com.example.user010.vero_project.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.user010.vero_project.core.Follow;
import com.example.user010.vero_project.core.Friend;
import com.example.user010.vero_project.core.FriendRequest;
import com.example.user010.vero_project.core.Post;
import com.example.user010.vero_project.core.PostReply;
import com.example.user010.vero_project.core.User;
import com.example.user010.vero_project.network.NetworkConnector;
import com.example.user010.vero_project.network.NetworkResListener;
import com.example.user010.vero_project.network.ResStatus;

import org.json.JSONObject;

public class MyInfoManager implements NetworkResListener {

    private ProgressDialog progressDialog=null;

    private static MyInfoManager instance = null;
    private Context context = null;
    public static MyInfoDatabase db = null;
    public static User logedUser;


    public static MyInfoManager getInstance() {
        if (instance == null) {
            instance = new MyInfoManager();
        }
        return instance;
    }


    public static void releaseInstance() {
        if (instance != null) {
            instance.clean();
            instance = null;
        }
    }

    private void clean() {

    }


    public Context getContext() {
        return context;

    }

    public void openDataBase(Context context) {
        this.context = context;
        if (context != null) {
            db = new MyInfoDatabase(context);
            db.open();
        }
    }
    public void closeDataBase() {
        if(db!=null){
            db.close();
        }
    }




    /******************************************************************************************************************************/
    /***************************************************** User Data *************************************************************/
    /****************************************************************************************************************************/

    public void createUser(User user) {
        if (db != null) {
            db.createUser( user );
        }
    }
    public static User getLogedUser() {
        return logedUser;
    }

    public void setLogedUser(User logedUser) {
        this.logedUser = logedUser;
    }


    public HashSet<User> getAllUsers() {
        HashSet<User> result = new HashSet<User>();
        if (db != null) {
            result = db.getAllUsers();
        }
        return result;
    }

    public void deleteUser(User deleteUser){
        if (db != null) {
            db.deleteUser( deleteUser );
        }

    }


    /******************************************************************************************************************************/
    /***************************************************** Post Data *************************************************************/
    /****************************************************************************************************************************/

    public void createPost(Post post) {
        boolean res = false;
        if (db != null) {
            db.createPost(post);
                res = true;
                NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_POST_REQ, post, instance);
        }
    }


    public void readPost(int id) {
        if (db != null) {
            db.readPost( id );
        }
    }

    public List<Post> getAllPosts() {
        List<Post> result = new ArrayList<Post>();
        if (db != null) {
            result = db.getAllPosts();        }
        return result;
    }

    public void deletePost(Post deletePost){
        if (db != null) {
            db.deletePost( deletePost );
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.DELETE_POST, deletePost, instance);
        }

    }

    public List<Post> getAllUsersPosts(User user) {
        List<Post> result = new ArrayList<Post>();

        if (db != null) {
            result = db.getAllUsersPosts( user );
        }
        return result;
    }

    /******************************************************************************************************************************/
    /***************************************************** PostReply Data *************************************************************/
    /****************************************************************************************************************************/

    public void createReply(PostReply reply) {
        if (db != null) {
            db.createPostReply( reply );
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_POST_REPLY_REQ, reply, instance);

        }
    }


    public void readReply(int id) {
        if (db != null) {
            db.readPostReply( id );
        }
    }

    public List<PostReply> getAllReplys() {
        List<PostReply> result = new ArrayList<PostReply>();
        if (db != null) {
            result = db.getAllReplys();
        }
        return result;
    }

    public void deleteReply(PostReply deleteReply){
        if (db != null) {
            db.deleteReply( deleteReply );
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.DELETE_POST_REPLY, deleteReply, instance);

        }
    }

    public List<PostReply> getAllUsersPostReply(Post post){
        List<PostReply> result = new ArrayList<PostReply>();

        if (db != null) {
            result = db.getAllUPostReplys( post );
        }
        return result;
    }

    /******************************************************************************************************************************/
    /***************************************************** Friend Data *************************************************************/
    /****************************************************************************************************************************/

    public void createFriend(Friend friend) {
        if (db != null) {
            db.createFriend( friend );
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_FREINDS,friend.getId(), friend.userMail1,
                    friend.getUserMail2(), instance);
        }
    }


    public void readFriend(int id) {
        if (db != null) {
            db.readFriend( id );
        }
    }

    public List<Friend> getAllFriendships() {
        List<Friend> result = new ArrayList<Friend>();
        if (db != null) {
            result = db.getAllFriends();
        }
        return result;
    }

    public void deleteFriend(Friend deleteFriend){
        if (db != null) {
            db.deleteFriend( deleteFriend );
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.DELETE_FREIND, deleteFriend.getId()
                    , instance);
        }
    }

    public List<User> getAllUserFriends(User user){
        List<User> result = new ArrayList<User>();

        if (db != null) {
            result = db.getAllUsersFriends( user );
        }
        return result;
    }

    /******************************************************************************************************************************/
    /***************************************************** Request Data *************************************************************/
    /****************************************************************************************************************************/

    public void createRequest(FriendRequest friendRequest) {
        if (db != null) {
            db.createRequest( friendRequest );
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_FREIND_REQUEST,friendRequest.getFriendshipID(),
                    friendRequest.userMail1,friendRequest.getUserMail2(), instance);
        }
    }


    public void readRequest(int id) {
        if (db != null) {
            db.readRequest( id );
        }
    }

    public List<FriendRequest> getAllFriendshipsRequest() {
        List<FriendRequest> result = new ArrayList<FriendRequest>();
        if (db != null) {
            result = db.getAllRequests();
        }
        return result;
    }

    public void deleteFriendRequest(FriendRequest deleteFriendReq){
        if (db != null) {
            db.deleteRequest( deleteFriendReq );
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.DELETE_FREIND_REQUEST, deleteFriendReq.getFriendshipID(), instance);
        }
    }

    public List<User> getAllUserFriendRequests(User user){
        List<User> result = new ArrayList<User>();

        if (db != null) {
            result = db.getAllUsersRequests( user );
        }
        return result;
    }

    public List<User> getAllUserRecievedRequests(User user) {
        List<User> result = new ArrayList<User>();

        if (db != null) {
            result = db.getAllUserRecievedRequests( user );
        }
        return result;
    }

    /******************************************************************************************************************************/
    /***************************************************** Follow Data *************************************************************/
    /****************************************************************************************************************************/

    public void createFollow(Follow follow) {
        if (db != null) {
            db.createFollow( follow );
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_FOLLOW,follow.getId(), follow.userMail1,
                    follow.getUserMail2(), instance);
        }
    }


    public void readFollow(int id) {
        if (db != null) {
            db.readFollow( id );
        }
    }

    public List<Follow> getAllFollow() {
        List<Follow> result = new ArrayList<Follow>();
        if (db != null) {
            result = db.getAllFollows();
        }
        return result;
    }

    public void deleteFollow(Follow deleteFollow){
        if (db != null) {
            db.deleteFollow( deleteFollow );
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.DELETE_FOLLOW,deleteFollow.getId()
                    , instance);
        }
    }

    public HashSet<User> getAllUserFollowings(User user){
        HashSet<User> result = new HashSet<>();

        if (db != null) {
            result = db.getAllUsersFollowings( user );
        }
        return result;
    }

    public HashSet<User> getAllUserFollowers(User user){
        HashSet<User> result = new HashSet<>();

        if (db != null) {
            result = db.getAllUsersFollowers( user );
        }
        return result;
    }

    /*************************************************************************************************************************************/
    /**************************************************************************getDataFromServer***************************************************/
    /************************************************************************************************************************************/
    public void getALlUsersFromServer(final Context context){
        NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.GET_ALL_USER_REQ, new NetworkResListener() {
            @Override
            public void onPreUpdate() {
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Updating resources");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }

            @Override
            public void onPostUpdate(byte[] res, ResStatus status) {

            }

            @Override
            public void onPostUpdate(JSONObject res, ResStatus status) {
                if(status == ResStatus.SUCCESS){
                    Toast.makeText(context, "download ok...", Toast.LENGTH_LONG).show();
                    MyInfoManager.getInstance().updateUserResources(res);
                }
                else{
                    Toast.makeText(context,"download failed...", Toast.LENGTH_LONG).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onPostUpdate(Bitmap res, ResStatus status) {

            }
        });



    }

    public void getALlFollowsFromServer(final Context context){
        NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.GET_ALL_FOLLOWS, new NetworkResListener() {
            @Override
            public void onPreUpdate() {
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Updating resources");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public void onPostUpdate(byte[] res, ResStatus status) {

            }

            @Override
            public void onPostUpdate(JSONObject res, ResStatus status) {
                if(status == ResStatus.SUCCESS){
                    Toast.makeText(context, "download ok...", Toast.LENGTH_LONG).show();
                    MyInfoManager.getInstance().updateFollowResources(res);
                }
                else{
                    Toast.makeText(context,"download failed...", Toast.LENGTH_LONG).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onPostUpdate(Bitmap res, ResStatus status) {

            }
        });

    }
    public void getALlFriendsFromServer(final Context context){
        NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.GET_ALL_FREINDS, new NetworkResListener() {
            @Override
            public void onPreUpdate() {
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Updating resources");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public void onPostUpdate(byte[] res, ResStatus status) {

            }

            @Override
            public void onPostUpdate(JSONObject res, ResStatus status) {
                if(status == ResStatus.SUCCESS){
                    Toast.makeText(context, "download ok...", Toast.LENGTH_LONG).show();
                    MyInfoManager.getInstance().updateFriendsResources(res);
                }
                else{
                    Toast.makeText(context,"download failed...", Toast.LENGTH_LONG).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onPostUpdate(Bitmap res, ResStatus status) {

            }
        });

    }
    public void getAllFriendsRequestsFromServer(final Context context){
        NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.GET_ALL_FRIEND_REQUESTS, new NetworkResListener() {
            @Override
            public void onPreUpdate() {
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Updating resources");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public void onPostUpdate(byte[] res, ResStatus status) {

            }

            @Override
            public void onPostUpdate(JSONObject res, ResStatus status) {
                if(status == ResStatus.SUCCESS){
                    Toast.makeText(context, "download ok...", Toast.LENGTH_LONG).show();
                    MyInfoManager.getInstance().updateFriendsResources(res);
                }
                else{
                    Toast.makeText(context,"download failed...", Toast.LENGTH_LONG).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onPostUpdate(Bitmap res, ResStatus status) {

            }
        });

    }
    public void getAllPostsFromServer(final Context context){
        NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.GET_ALL_POSTS_JSON_REQ, new NetworkResListener() {
            @Override
            public void onPreUpdate() {
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Updating resources");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public void onPostUpdate(byte[] res, ResStatus status) {

            }

            @Override
            public void onPostUpdate(JSONObject res, ResStatus status) {
                if(status == ResStatus.SUCCESS){
                    Toast.makeText(context, "download ok...", Toast.LENGTH_LONG).show();
                    MyInfoManager.getInstance().updatePostResources(res);
                }
                else{
                    Toast.makeText(context,"download failed...", Toast.LENGTH_LONG).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onPostUpdate(Bitmap res, ResStatus status) {

            }
        });

    }
    public void getAllPostRepliesFromServer(final Context context){
        NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.GET_ALL_REPLIES, new NetworkResListener() {
            @Override
            public void onPreUpdate() {
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Updating resources");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public void onPostUpdate(byte[] res, ResStatus status) {

            }

            @Override
            public void onPostUpdate(JSONObject res, ResStatus status) {
                if(status == ResStatus.SUCCESS){
                    Toast.makeText(context, "download ok...", Toast.LENGTH_LONG).show();
                    MyInfoManager.getInstance().updatePostReplyResources(res);
                }
                else{
                    Toast.makeText(context,"download failed...", Toast.LENGTH_LONG).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onPostUpdate(Bitmap res, ResStatus status) {

            }
        });

    }

    /*************************************************************************************************************************************/
    /**************************************************************************UpdateResources***************************************************/
    /************************************************************************************************************************************/

    @Override
    public void onPreUpdate() {
        Toast.makeText(context,"Sync stated...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostUpdate(byte[] res, ResStatus status) {
        /**
         Toast.makeText(context,"Sync finished...status " + status.toString(),Toast.LENGTH_SHORT).show();
         */
    }

    @Override
    public void onPostUpdate(JSONObject res, ResStatus status) {
        if(res!=null){
            Toast.makeText(context,"Sync finished...status " + res.toString(),Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Sync finished...status " + status.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPostUpdate(Bitmap res, ResStatus status) {
    }

    public void updateUserResources(JSONObject content) {
        if(content==null){
            return;
        }
        try {

            //String content = new String(res, "UTF-8");

            List<User> list = User.parseJson(content);
            if(list!=null && list.size()>0){
              /*  for(User user:list){
                    syncUpdateUser(user);
                }*/
                //Gett all images of users
                for(final User user : list) {
                    NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.GET_User_photo, user,new NetworkResListener() {
                        @Override
                        public void onPreUpdate() {
                            Toast.makeText(context,"start download img... id=" + user.getEmail(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPostUpdate(byte[] res, ResStatus status) {

                        }

                        @Override
                        public void onPostUpdate(JSONObject res, ResStatus status) {

                        }

                        @Override
                        public void onPostUpdate(Bitmap res, ResStatus status) {
                            Toast.makeText(context,"Sync download img finished...status " + status.toString(),Toast.LENGTH_SHORT).show();
                            if(status == ResStatus.SUCCESS){
                                if(res!=null) {
                                    user.setProfilePhoto(res);
                                    syncUpdateUser(user);
                                 //   db.updateUser(user);
                                }
                            }
                        }
                    });
                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
        }

    }


    public void updateFollowResources(JSONObject content) {
        if(content==null){
            return;
        }
        try {
            //String content = new String(res, "UTF-8");
            List<Follow> list = Follow.parseJson(content);
            if(list!=null && list.size()>0){
                for(Follow follow:list){
                    syncUpdateFollow(follow);
                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
        }

    }
    public void updateFriendsResources(JSONObject content) {
        if(content==null){
            return;
        }
        try {
            //String content = new String(res, "UTF-8");
            List<Friend> list = Friend.parseJson(content);
            if(list!=null && list.size()>0){
                for(Friend friend:list){
                    syncUpdateFriend(friend);
                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
        }

    }
    public void updateFriendRequestResources(JSONObject content) {
        if(content==null){
            return;
        }
        try {
            //String content = new String(res, "UTF-8");
            List<FriendRequest> list = FriendRequest.parseJson(content);
            if(list!=null && list.size()>0){
                for(FriendRequest friendRequest:list){
                    syncUpdateFriendRequest(friendRequest);
                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
        }

    }
    public void updatePostResources(JSONObject content) {
        if(content==null){
            return;
        }
        try {
            //String content = new String(res, "UTF-8");
            List<Post> list = Post.parseJson(content);
            if(list!=null && list.size()>0){
              /*  for(Post post:list){
                    syncUpdatePost(post);
                }*/
                for(final Post post : list) {
                    //Gett all images of posts
                    NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.GET_Post_photo, post,new NetworkResListener() {
                        @Override
                        public void onPreUpdate() {
                            Toast.makeText(context,"start download img... id=" + post.getPostID(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPostUpdate(byte[] res, ResStatus status) {

                        }

                        @Override
                        public void onPostUpdate(JSONObject res, ResStatus status) {

                        }

                        @Override
                        public void onPostUpdate(Bitmap res, ResStatus status) {
                            Toast.makeText(context,"Sync download img finished...status " + status.toString(),Toast.LENGTH_SHORT).show();
                            if(status == ResStatus.SUCCESS){
                                if(res!=null) {
                                    post.setPhoto(res);
                                    syncUpdatePost(post);
                               //     db.updatePost(post);
                                }
                            }
                        }
                    });
                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
        }

    }

    public void updatePostReplyResources(JSONObject content) {
        if(content==null){
            return;
        }
        try {
            //String content = new String(res, "UTF-8");
            List<PostReply> list = PostReply.parseJson(content);
            if(list!=null && list.size()>0){
                for(PostReply postReply:list){
                    syncUpdatePostReply(postReply);
                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
        }

    }
    private boolean syncUpdateUser(User user) {
        boolean res = false;
        if (db != null) {

            db.createUser(user);
            res = true;
        }
        return res;
    }
    private boolean syncUpdateFollow(Follow  follow) {
        boolean res = false;
        if (db != null) {
            db.createFollow(follow);
            res = true;
        }
        return res;
    }
    private boolean syncUpdateFriend(Friend friend) {
        boolean res = false;
        if (db != null) {
            db.createFriend(friend);
            res = true;
        }
        return res;
    }
    private boolean syncUpdateFriendRequest(FriendRequest friendRequest) {
        boolean res = false;
        if (db != null) {
            db.createRequest(friendRequest);
            res = true;
        }
        return res;
    }
    private boolean syncUpdatePost(Post post) {
        boolean res = false;
        if (db != null) {
            db.createPost(post);
            res = true;
        }
        return res;
    }
    private boolean syncUpdatePostReply(PostReply postReply) {
        boolean res = false;
        if (db != null) {
            db.createPostReply(postReply);
            res = true;
        }
        return res;
    }
    public static int getRandomNumberInRange() {
        int min = 0,max=999999999;
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
