package com.example.user010.vero_project.controller;


import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import com.example.user010.vero_project.core.Follow;
import com.example.user010.vero_project.core.Friend;
import com.example.user010.vero_project.core.FriendRequest;
import com.example.user010.vero_project.core.Post;
import com.example.user010.vero_project.core.PostReply;
import com.example.user010.vero_project.core.User;

public class MyInfoDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyInfoDB11";

    // user table
    private static final String TABLE_USER_NAME = "user";
    private static final String USER_COLUMN_MAIL = "email";
    private static final String USER_COLUMN_FULL = "firstName";
    private static final String USER_COLUMN_PASS = "password";
    private static final String USER_COLUMN_BIO = "biography";
    private static final String USER_COLUMN_PHONE = "phoneNum";
    private static final String USER_COLUMN_BIRTH = "dateOfBirth";
    private static final String USER_COLUMN_PHOTO = "profilePhoto";
    private static final String USER_COLUMN_FOLLOWERS = "followersNum";
    private static final String USER_COLUMN_FOLLOWINGS = "followingsNum";

    private static final String[] TABLE_USER_COLUMNS = {USER_COLUMN_MAIL, USER_COLUMN_FULL ,USER_COLUMN_PASS
            ,USER_COLUMN_BIO, USER_COLUMN_PHONE ,USER_COLUMN_BIRTH , USER_COLUMN_PHOTO ,USER_COLUMN_FOLLOWERS , USER_COLUMN_FOLLOWINGS};



    // user posts
    private static final String TABLE_POST_NAME = "post";
    private static final String POST_COLUMN_ID = "postid";
    private static final String POST_COLUMN_MAIL = "userMail";
    private static final String POST_COLUMN_DATE = "date";
    private static final String POST_COLUMN_LINK = "link";
    private static final String POST_COLUMN_PHOTO = "photo";
    private static final String POST_COLUMN_DESC = "postDescription";
    private static final String POST_COLUMN_LIKES = "likesNum";
    private static final String POST_COLUMN_COMMENTS = "commentsNum";

    private static final String[] TABLE_POST_COLUMNS = {POST_COLUMN_ID, POST_COLUMN_MAIL ,POST_COLUMN_DATE
           , POST_COLUMN_LINK ,POST_COLUMN_PHOTO , POST_COLUMN_DESC ,POST_COLUMN_LIKES , POST_COLUMN_COMMENTS};



    // post replys
    private static final String TABLE_REPLY_NAME = "postreply";
    private static final String REPLY_COLUMN_ID = "replyID";
    private static final String REPLY_COLUMN_USER = "userReply";
    private static final String REPLY_COLUMN_POST = "postID";
    private static final String REPLY_COLUMN_LIKE = "likes";
    private static final String REPLY_COLUMN_COMMENT = "comment";

    private static final String[] TABLE_REPLY_COLUMNS = {REPLY_COLUMN_ID, REPLY_COLUMN_USER ,REPLY_COLUMN_POST
            ,REPLY_COLUMN_POST, REPLY_COLUMN_LIKE, REPLY_COLUMN_COMMENT};



    // friends table
    private static final String TABLE_FRIEND_NAME = "friends";
    private static final String FRIEND_COLUMN_ID = "id";
    private static final String FRIEND_COLUMN_USER1 = "userMail1";
    private static final String FRIEND_COLUMN_USER2 = "userMail2";

    private static final String[] TABLE_FRIEND_COLUMNS = {FRIEND_COLUMN_ID, FRIEND_COLUMN_USER1 ,FRIEND_COLUMN_USER2};


    // follows table
    private static final String TABLE_FOLLOW_NAME = "follows";
    private static final String FOLLOW_COLUMN_ID = "id";
    private static final String FOLLOW_COLUMN_USER1 = "userMail1";
    private static final String FOLLOW_COLUMN_USER2 = "userMail2";

    private static final String[] TABLE_FOLLOW_COLUMNS = {FOLLOW_COLUMN_ID, FOLLOW_COLUMN_USER1 ,FOLLOW_COLUMN_USER2};


    // requests table
    private static final String TABLE_REQUEST_NAME = "friendrequests";
    private static final String REQUEST_COLUMN_ID = "id";
    private static final String REQUEST_COLUMN_USER1 = "userMail1";
    private static final String REQUEST_COLUMN_USER2 = "userMail2";
    private static final String REQUEST_COLUMN_STATUS = "friendshipStatus";

    private static final String[] TABLE_REQUEST_COLUMNS = {REQUEST_COLUMN_ID, REQUEST_COLUMN_USER1 ,REQUEST_COLUMN_USER2 ,REQUEST_COLUMN_STATUS };

    public static SQLiteDatabase db = null;

    public MyInfoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            if (!isTableExist(TABLE_USER_NAME, db)) {
                // SQL statement to create user table
                String CREATE_USER_TABLE = "create table if not exists "+ TABLE_USER_NAME+" ( "
                        + USER_COLUMN_MAIL +" TEXT, "
                        + USER_COLUMN_FULL +" TEXT, "
                        + USER_COLUMN_PASS + " TEXT, "
                        + USER_COLUMN_BIO + " TEXT, "
                        + USER_COLUMN_PHONE + " INTEGER, "
                        + USER_COLUMN_BIRTH + " TEXT, "
                        + USER_COLUMN_PHOTO + " BLOB, "
                        + USER_COLUMN_FOLLOWERS + " INTEGER,"
                        + USER_COLUMN_FOLLOWINGS + " INTEGER)";

                db.execSQL(CREATE_USER_TABLE);
            }



            if (!isTableExist(TABLE_POST_NAME, db)) {
                // SQL statement to create user table
                String CREATE_POST_TABLE = "create table if not exists "+ TABLE_POST_NAME+" ( "
                        + POST_COLUMN_ID +" INTEGER, "
                        + POST_COLUMN_MAIL +" TEXT, "
                        + POST_COLUMN_DATE + " TEXT, "
                        + POST_COLUMN_LINK + " TEXT, "
                        + POST_COLUMN_PHOTO + " BLOB, "
                        + POST_COLUMN_DESC + " TEXT, "
                        + POST_COLUMN_LIKES + " INTEGER,"
                        + POST_COLUMN_COMMENTS + " INTEGER)";

                db.execSQL(CREATE_POST_TABLE);
            }

            if (!isTableExist(TABLE_REPLY_NAME, db)) {
                // SQL statement to create user table
                String CREATE_REPLY_TABLE = "create table if not exists "+ TABLE_REPLY_NAME+" ( "
                        + REPLY_COLUMN_ID +" INTEGER, "
                        + REPLY_COLUMN_USER +" TEXT, "
                        + REPLY_COLUMN_POST + " INTEGER, "
                        + REPLY_COLUMN_LIKE + " BOOLEAN, "
                        + REPLY_COLUMN_COMMENT + " TEXT)";

                db.execSQL(CREATE_REPLY_TABLE);
            }

            if (!isTableExist(TABLE_FRIEND_NAME, db)) {
                // SQL statement to create user table
                String CREATE_FRIEND_TABLE = "create table if not exists "+ TABLE_FRIEND_NAME+" ( "
                        + FRIEND_COLUMN_ID +" INTEGER, "
                        + FRIEND_COLUMN_USER1 +" TEXT, "
                        + FRIEND_COLUMN_USER2 + " TEXT)";

                db.execSQL(CREATE_FRIEND_TABLE);
            }

            if (!isTableExist(TABLE_FOLLOW_NAME, db)) {
                // SQL statement to create user table
                String CREATE_FOLLOW_TABLE = "create table if not exists "+ TABLE_FOLLOW_NAME+" ( "
                        + FOLLOW_COLUMN_ID +" INTEGER, "
                        + FOLLOW_COLUMN_USER1 +" TEXT, "
                        + FOLLOW_COLUMN_USER2 + " TEXT)";

                db.execSQL(CREATE_FOLLOW_TABLE);
            }

            if (!isTableExist(TABLE_REQUEST_NAME, db)) {
                // SQL statement to create user table
                String CREATE_REQUEST_TABLE = "create table if not exists "+ TABLE_REQUEST_NAME+" ( "
                        + REQUEST_COLUMN_ID +" INTEGER, "
                        + REQUEST_COLUMN_USER1 +" TEXT, "
                        + REQUEST_COLUMN_USER2 +" TEXT, "
                        + REQUEST_COLUMN_STATUS + " INTEGER)";

                db.execSQL(CREATE_REQUEST_TABLE);
            }




        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("DROP TABLE IF EXISTS user");
                System.out.println("nenr");
                db.execSQL("DROP TABLE IF EXISTS post");
                db.execSQL("DROP TABLE IF EXISTS postreply");
                db.execSQL("DROP TABLE IF EXISTS friends");
                db.execSQL("DROP TABLE IF EXISTS follows");
                db.execSQL("DROP TABLE IF EXISTS friendrequests");


            } catch (Throwable t) {
                t.printStackTrace();
            }
            //onCreate(db);

    }
    /****************************************************************************************************************************/
    /******************************************************************************************************************************/
    /***************************************************** User Data *************************************************************/
    /****************************************************************************************************************************/
    /****************************************************************************************************************************/

    public void createUser(User user) {

        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(USER_COLUMN_MAIL , user.getEmail());
            values.put(USER_COLUMN_FULL, user.getFirstName());
            values.put(USER_COLUMN_PASS, user.getPassword());
            values.put(USER_COLUMN_BIO, user.getBiography());
            values.put(USER_COLUMN_PHONE, user.getPhoneNum());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            values.put(USER_COLUMN_BIRTH, dateFormat.format(user.getDateOfBirth()));
            //images
            Bitmap image1 = user.getProfilePhoto();
            if (image1 != null) {
                byte[] data = getBitmapAsByteArray(image1);
                if (data != null && data.length > 0) {
                    values.put(USER_COLUMN_PHOTO, data);
                }
            }
            values.put(USER_COLUMN_FOLLOWERS, user.getFollowersNum());
            values.put(USER_COLUMN_FOLLOWINGS, user.getFollowingNum());



            db.insert(TABLE_USER_NAME, null, values);
        } catch (Throwable t) {

            t.printStackTrace();
        }


    }


    public User readUser(String mail) {
        User user = null;
        Cursor cursor = null;
        try {
            // get reference of the userDB database

            // get  query
            cursor = db
                    .query(TABLE_USER_NAME, // a. table
                            TABLE_USER_COLUMNS, USER_COLUMN_MAIL + " = ?",
                            new String[] { String.valueOf(mail) }, null, null,
                            null, null);

            // if results !=null, parse the first one
            if (cursor != null)
                cursor.moveToFirst();

            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date date = format.parse( cursor.getString(5 ) );
            int followers = Integer.parseInt( cursor.getString( 7 ) );
            int followings = Integer.parseInt( cursor.getString( 8 ) );

            //images
            byte[] img1Byte = cursor.getBlob(6);
            Bitmap img = null;
            if (img1Byte != null && img1Byte.length > 0) {
                Bitmap image1 = BitmapFactory.decodeByteArray(img1Byte, 0, img1Byte.length);
                if (image1 != null) {
                    img = image1;
                }
            }

            user = new User( cursor.getString( 0) , cursor.getString( 1 ),cursor.getString( 2 ),
                    cursor.getString( 3 ),cursor.getInt( 4 ),
                    date ,img ,followers , followings );


        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return user;
    }

    private User cursorToUser(Cursor cursor) {
        User result = new User();
        try {
            result.setEmail( cursor.getString( 0 ) );//setId(Integer.parseInt(cursor.getString(0)));
            result.setFirstName( cursor.getString( 1 ) );
            result.setPassword( cursor.getString( 2 ) );
            result.setBiography( cursor.getString( 3 ) );
            result.setPhoneNum( cursor.getInt( 4 ) );

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse( cursor.getString( 5) );
            result.setDateOfBirth( date );

            int followers = Integer.parseInt( cursor.getString( 7 ) );
            result.setFollowersNum( followers );
            int followings = Integer.parseInt( cursor.getString( 8 ) );
            result.setFollowingNum( followings );

            //images
            byte[] img1Byte = cursor.getBlob(6);
            if (img1Byte != null && img1Byte.length > 0) {
                Bitmap image1 = BitmapFactory.decodeByteArray( img1Byte, 0, img1Byte.length );
                if (image1 != null) {
                    result.setProfilePhoto( image1 );
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }


    public int updateUser(User user) {
        int i = 0;
        try {


            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(USER_COLUMN_MAIL, user.getEmail());

            // update
            i = db.update(TABLE_USER_NAME, values, USER_COLUMN_MAIL + " = ?",
                    new String[] { String.valueOf(user.getEmail()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return i;
    }

    public void deleteUser(User user) {

        try {

            // delete item
            db.delete(TABLE_USER_NAME, USER_COLUMN_MAIL + " = ?",
                    new String[] { String.valueOf(user.getEmail()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }


    public HashSet<User> getAllUsers() {
        HashSet<User> result = new HashSet<User>();
        Cursor cursor = null;
        try {


            cursor = db.query(TABLE_USER_NAME, TABLE_USER_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = cursorToUser(cursor);
                result.add(user);
                cursor.moveToNext();
            }

        } catch (Throwable t) {

            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }
        return result;
    }

    /****************************************************************************************************************************/
    /******************************************************************************************************************************/
    /***************************************************** Post Data *************************************************************/
    /****************************************************************************************************************************/
    /****************************************************************************************************************************/

    public void createPost(Post post) {

        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(POST_COLUMN_ID , post.getPostID());
            values.put(POST_COLUMN_MAIL, post.getUserName());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            values.put(POST_COLUMN_DATE, dateFormat.format(post.getDate()));
            values.put(POST_COLUMN_LINK, post.getLink());

            Bitmap image = post.getPhoto();
            if (image != null) {
                byte[] data = Post.getBitmapAsByteArray(image);
                if (data != null && data.length > 0) {
                    values.put(POST_COLUMN_PHOTO, data);
                }
            }

            values.put(POST_COLUMN_DESC, post.getPostDescription());
            values.put(POST_COLUMN_LIKES, post.getLikesNum());
            values.put(POST_COLUMN_COMMENTS, post.getCommentsNum());

            // insert item
            db.insert(TABLE_POST_NAME, null, values);


        } catch (Throwable t) {
            t.printStackTrace();
        }


    }


    public Post readPost(int id) {
        Post post = null;
        Cursor cursor = null;
        try {
            // get reference of the postDB database

            // get  query
            cursor = db
                    .query(TABLE_POST_NAME, // a. table
                            TABLE_POST_COLUMNS, POST_COLUMN_ID + " = ?",
                            new String[] { String.valueOf(id) }, null, null,
                            null, null);

            // if results !=null, parse the first one
            if (cursor != null)
                cursor.moveToFirst();

            String postID = cursor.getString( 0 );
            String mail = cursor.getString( 1 );
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date date = format.parse( cursor.getString(2 ) );
            String link = cursor.getString( 3 );
            byte[] img = cursor.getBlob(4);
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            String desc = cursor.getString( 5 );


            post = new Post( Integer.parseInt(postID), mail, date, link, bitmap, desc );


        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return post;
    }

    private Post cursorToPost(Cursor cursor) {
        Post result = new Post();
        try {
            result.setPostID( Integer.parseInt(cursor.getString( 0 )) );
            result.setUserName( cursor.getString( 1 ) );

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse( cursor.getString( 2) );
            result.setDate( date );
            result.setLink( cursor.getString( 3 ) );
            //byte[] img= cursor.getBlob( 4 );
             //   Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            Bitmap img=null;
                result.setPhoto(img);
            result.setPostDescription( cursor.getString( 5 ) );
            result.setLikesNum( cursor.getInt( 6) );
            result.setCommentsNum( cursor.getInt( 7 ) );


        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }


    public int updatePost(Post post) {
        int i = 0;
        try {


            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(POST_COLUMN_ID, post.getPostID());

            // update
            i = db.update(TABLE_POST_NAME, values, POST_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(post.getPostID()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return i;
    }

    public void deletePost(Post post) {

        try {

            // delete item
            db.delete(TABLE_POST_NAME, POST_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(post.getPostID()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }


    public List<Post> getAllPosts() {
        List<Post> result = new ArrayList<Post>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_POST_NAME, TABLE_POST_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Post post = cursorToPost(cursor);
                result.add(post);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }
        return result;
    }

    public List<Post> getAllUsersPosts(User user) {
        List<Post> result = new ArrayList<Post>();
        Cursor cursor = null;

        try {
            String userMail = user.getEmail();
            System.out.println("iyuti");
            cursor = db.query(TABLE_POST_NAME, TABLE_POST_COLUMNS, POST_COLUMN_MAIL +" = ?",
                    new String[] { userMail }, null, null,
                    null, null);

            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Post post = cursorToPost(cursor);
                    result.add(post);
                    cursor.moveToNext();
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null) {
                // make sure to close the cursor
                cursor.close();
            }
        }
        return result;

    }


    /****************************************************************************************************************************/
    /******************************************************************************************************************************/
    /***************************************************** Post Reply Data *************************************************************/
    /****************************************************************************************************************************/
    /****************************************************************************************************************************/

    public void createPostReply(PostReply reply) {

        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(REPLY_COLUMN_ID , reply.getReplyID());
            values.put(REPLY_COLUMN_USER,reply.getUserReply());
            values.put(REPLY_COLUMN_POST, reply.getPostID());
            values.put(REPLY_COLUMN_LIKE, reply.isLike());
            values.put(REPLY_COLUMN_COMMENT, reply.getComment());


            // insert item
            db.insert(TABLE_REPLY_NAME, null, values);


        } catch (Throwable t) {
            t.printStackTrace();
        }


    }


    public PostReply readPostReply(int id) {
        PostReply reply = null;
        Cursor cursor = null;
        try {
            // get reference of the postDB database

            // get  query
            cursor = db
                    .query(TABLE_REPLY_NAME, // a. table
                            TABLE_REPLY_COLUMNS, REPLY_COLUMN_ID + " = ?",
                            new String[] { String.valueOf(id) }, null, null,
                            null, null);

            // if results !=null, parse the first one
            if (cursor != null)
                cursor.moveToFirst();

            int replyID = cursor.getInt( 0 );
            String mail = cursor.getString( 1 );
            int postID = cursor.getInt( 2 );
            String like = cursor.getString( 3);
            Boolean islike = false;
            if(like == "true"){
                islike = true;
            }
            String comment = cursor.getString( 4 );

            reply = new PostReply( replyID, mail, postID, islike, comment );

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return reply;
    }

    private PostReply cursorToReply(Cursor cursor) {
        PostReply result = new PostReply();
        try {

            result.setReplyID( cursor.getInt(  0) );
            result.setUserReply( cursor.getString( 1 ) );
            result.setPostID( cursor.getInt( 2 ) );
            String like = cursor.getString( 3);
            Boolean islike = false;
            if(like == "true"){
                islike = true;
            }
            result.setLike( islike );
            result.setComment( cursor.getString( 4 ) );



        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }


    public int updateReply(PostReply reply) {
        int i = 0;
        try {


            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(REPLY_COLUMN_ID, reply.getReplyID());

            // update
            i = db.update(TABLE_REPLY_NAME, values, REPLY_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(reply.getReplyID()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return i;
    }

    public void deleteReply(PostReply reply) {

        try {

            // delete item
            db.delete(TABLE_REPLY_NAME, REPLY_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(reply.getReplyID()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }


    public List<PostReply> getAllReplys() {
        List<PostReply> result = new ArrayList<PostReply>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_REPLY_NAME, TABLE_REPLY_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                PostReply reply = cursorToReply(cursor);
                result.add(reply);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }
        return result;
    }

    public List<PostReply> getAllUPostReplys(Post post) {
        List<PostReply> result = new ArrayList<PostReply>();
        Cursor cursor = null;

        try {
            String postID = ((Integer)post.getPostID()).toString();
            cursor = db.query(TABLE_REPLY_NAME, TABLE_REPLY_COLUMNS, REPLY_COLUMN_POST +" = ?",
                    new String[] { postID }, null, null,
                    null, null);

            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    PostReply reply = cursorToReply(cursor);
                    result.add(reply);
                    cursor.moveToNext();
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null) {
                // make sure to close the cursor
                cursor.close();
            }
        }
        return result;

    }

    /****************************************************************************************************************************/
    /******************************************************************************************************************************/
    /***************************************************** Friends Data *************************************************************/
    /****************************************************************************************************************************/
    /****************************************************************************************************************************/

    public void createFriend(Friend friend) {

        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(FRIEND_COLUMN_ID , friend.id);
            values.put(FRIEND_COLUMN_USER1,friend.userMail1);
            values.put(FRIEND_COLUMN_USER2, friend.userMail2);

            // insert item
            db.insert(TABLE_FRIEND_NAME, null, values);


        } catch (Throwable t) {
            t.printStackTrace();
        }


    }


    public Friend readFriend(int id) {
        Friend friend = null;
        Cursor cursor = null;
        try {
            // get reference of the postDB database

            // get  query
            cursor = db
                    .query(TABLE_FRIEND_NAME, // a. table
                            TABLE_FRIEND_COLUMNS, FRIEND_COLUMN_ID + " = ?",
                            new String[] { String.valueOf(id) }, null, null,
                            null, null);

            // if results !=null, parse the first one
            if (cursor != null)
                cursor.moveToFirst();

            int fid = cursor.getInt( 0 );
            String mail1 = cursor.getString( 1 );
            String mail2 = cursor.getString( 2 );

            friend = new Friend( fid ,mail1,mail2 );
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return friend;
    }

    private Friend cursorToFriend(Cursor cursor) {
        Friend result = new Friend();
        try {

           result.setId( cursor.getInt( 0 ) );
           result.setUserMail1( cursor.getString( 1 ) );
           result.setUserMail2(cursor.getString( 2 ));



        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }


    public int updateFriend(Friend friend) {
        int i = 0;
        try {


            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(FRIEND_COLUMN_ID, friend.id);

            // update
            i = db.update(TABLE_FRIEND_NAME, values, FRIEND_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(friend.getId()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return i;
    }

    public void deleteFriend(Friend friend) {

        try {

            // delete item
            db.delete(TABLE_FRIEND_NAME, FRIEND_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(friend.getId()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }


    public List<Friend> getAllFriends() {
        List<Friend> result = new ArrayList<Friend>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_FRIEND_NAME, TABLE_FRIEND_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Friend friend = cursorToFriend(cursor);
                result.add(friend);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }
        return result;
    }

    public List<User> getAllUsersFriends(User user) {
        HashSet<User> result = new HashSet<>();
        Cursor cursor = null;

        try {
            String mail = user.getEmail();
            cursor = db.query(TABLE_FRIEND_NAME, TABLE_FRIEND_COLUMNS, FRIEND_COLUMN_USER1 +"=?",
                    new String[] { mail }, null, null,
                    null, null);

            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Friend friend = cursorToFriend(cursor);
                    String friendMail = friend.getUserMail2();
                    User uf = getUser( friendMail );
                    result.add(uf);
                    cursor.moveToNext();
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null) {
                // make sure to close the cursor
                cursor.close();
            }
        }
        try {
            String mail = user.getEmail();
            cursor = db.query(TABLE_FRIEND_NAME, TABLE_FRIEND_COLUMNS, FRIEND_COLUMN_USER2 +"=?",
                    new String[] { mail }, null, null,
                    null, null);

            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Friend friend = cursorToFriend(cursor);
                    String friendMail = friend.getUserMail1();
                    User uf = getUser( friendMail );
                    result.add(uf);
                    cursor.moveToNext();
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null) {
                // make sure to close the cursor
                cursor.close();
            }
        }
        result.remove(MyInfoManager.getLogedUser());
        List<User> users=new ArrayList<User>();
        for(User user1: result) {
            users.add(user1);
        }
        return users;

    }



    /****************************************************************************************************************************/
    /******************************************************************************************************************************/
    /***************************************************** Requests Data *************************************************************/
    /****************************************************************************************************************************/
    /****************************************************************************************************************************/

    public void createRequest(FriendRequest request) {

        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(REQUEST_COLUMN_ID , request.getFriendshipID());
            values.put(REQUEST_COLUMN_USER1,request.getUserMail1());
            values.put(REQUEST_COLUMN_USER2, request.getUserMail2());
            values.put(REQUEST_COLUMN_STATUS, request.getFriendshipStatus());


            // insert item
            db.insert(TABLE_REQUEST_NAME, null, values);
            System.out.println("lili");

        } catch (Throwable t) {
            t.printStackTrace();
        }


    }


    public FriendRequest readRequest(int id) {
        FriendRequest request = null;
        Cursor cursor = null;
        try {
            // get reference of the postDB database

            // get  query
            cursor = db
                    .query(TABLE_REQUEST_NAME, // a. table
                            TABLE_REQUEST_COLUMNS, REQUEST_COLUMN_ID + " = ?",
                            new String[] { String.valueOf(id) }, null, null,
                            null, null);

            // if results !=null, parse the first one
            if (cursor != null)
                cursor.moveToFirst();

            int fid = cursor.getInt( 0 );
            String mail1 = cursor.getString( 1 );
            String mail2 = cursor.getString( 2 );
            int statusF = cursor.getInt( 3 );

            request = new FriendRequest( fid, mail1 , mail2 , statusF );
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return request;
    }

    private FriendRequest cursorToRequest(Cursor cursor) {
        FriendRequest result = new FriendRequest();
        try {

            result.setFriendshipID(  cursor.getInt( 0 ) );
            result.setUserMail1( cursor.getString( 1 ) );
            result.setUserMail2(cursor.getString( 2 ));
            result.setFriendshipStatus(cursor.getInt( 3));




        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }


    public int updateRequest(FriendRequest request) {
        int i = 0;
        try {


            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(REQUEST_COLUMN_ID, request.getFriendshipID());

            // update
            i = db.update(TABLE_REQUEST_NAME, values, REQUEST_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(request.getFriendshipID()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return i;
    }

    public void deleteRequest(FriendRequest request) {

        try {

            // delete item
            db.delete(TABLE_REQUEST_NAME, REQUEST_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(request.getFriendshipID()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }


    public List<FriendRequest> getAllRequests() {
        List<FriendRequest> result = new ArrayList<FriendRequest>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_REQUEST_NAME, TABLE_REQUEST_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                FriendRequest request = cursorToRequest(cursor);
                result.add(request);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }
        return result;
    }

    public List<User> getAllUsersRequests(User user) {
        List<User> result = new ArrayList<User>();
        Cursor cursor = null;

        try {
            String mail = user.getEmail();
            cursor = db.query(TABLE_REQUEST_NAME, TABLE_REQUEST_COLUMNS, REQUEST_COLUMN_USER1 +" = ?",
                    new String[] { mail }, null, null,
                    null, null);

            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    FriendRequest friendRequest = cursorToRequest(cursor);
                    String mailU = friendRequest.getUserMail2();
                    User uf = getUser( mailU );
                    result.add(uf);
                    cursor.moveToNext();
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null) {
                // make sure to close the cursor
                cursor.close();
            }
        }
        return result;

    }

    public List<User> getAllUserRecievedRequests(User user) {
        List<User> result = new ArrayList<User>();
        Cursor cursor = null;

        try {
            String mail = user.getEmail();
            cursor = db.query(TABLE_REQUEST_NAME, TABLE_REQUEST_COLUMNS, REQUEST_COLUMN_USER2 +" = ?",
                    new String[] { mail }, null, null,
                    null, null);

            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    FriendRequest friendRequest = cursorToRequest(cursor);
                    String mailU = friendRequest.getUserMail1();
                    User uf = getUser( mailU );
                    result.add(uf);
                    cursor.moveToNext();
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null) {
                // make sure to close the cursor
                cursor.close();
            }
        }
        return result;

    }


    /****************************************************************************************************************************/
    /******************************************************************************************************************************/
    /***************************************************** Follows Data *************************************************************/
    /****************************************************************************************************************************/
    /****************************************************************************************************************************/

    public void createFollow(Follow follow) {

        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(FOLLOW_COLUMN_ID , follow.id);
            values.put(FOLLOW_COLUMN_USER1,follow.userMail1);
            values.put(FOLLOW_COLUMN_USER2, follow.userMail2);

            // insert follow
            db.insert(TABLE_FOLLOW_NAME, null, values);


        } catch (Throwable t) {
            t.printStackTrace();
        }


    }


    public Follow readFollow(int id) {
        Follow follow = null;
        Cursor cursor = null;
        try {
            // get reference of the postDB database

            // get  query
            cursor = db
                    .query(TABLE_FOLLOW_NAME, // a. table
                            TABLE_FOLLOW_COLUMNS, FOLLOW_COLUMN_ID + " = ?",
                            new String[] { String.valueOf(id) }, null, null,
                            null, null);

            // if results !=null, parse the first one
            if (cursor != null)
                cursor.moveToFirst();

            int fid = cursor.getInt( 0 );
            String mail1 = cursor.getString( 1 );
            String mail2 = cursor.getString( 2 );

            follow = new Follow( fid ,mail1,mail2 );
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return follow;
    }

    private Follow cursorToFollow(Cursor cursor) {
        Follow result = new Follow();
        try {

            result.setId( cursor.getInt( 0 ) );
            result.setUserMail1( cursor.getString( 1 ) );
            result.setUserMail2(cursor.getString( 2 ));



        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }


    public int updateFollow(Follow follow) {
        int i = 0;
        try {


            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(FOLLOW_COLUMN_ID, follow.id);

            // update
            i = db.update(TABLE_FOLLOW_NAME, values, FOLLOW_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(follow.getId()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return i;
    }

    public void deleteFollow(Follow follow) {

        try {

            // delete item
            db.delete(TABLE_FOLLOW_NAME, FOLLOW_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(follow.getId()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }


    public List<Follow> getAllFollows() {
        List<Follow> result = new ArrayList<Follow>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_FOLLOW_NAME, TABLE_FOLLOW_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Follow follow = cursorToFollow(cursor);
                result.add(follow);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }
        return result;
    }

    public HashSet<User> getAllUsersFollowings(User user) {
        HashSet<User> result = new HashSet<>();
        Cursor cursor = null;

        try {
            String mail = user.getEmail();
            cursor = db.query(TABLE_FOLLOW_NAME, TABLE_FOLLOW_COLUMNS, FOLLOW_COLUMN_USER1 +" = ?",
                    new String[] { mail }, null, null,
                    null, null);

            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Friend friend = cursorToFriend(cursor);
                    String mailU = friend.getUserMail2();
                    User uf = getUser( mailU );
                    result.add(uf);
                    cursor.moveToNext();
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null) {
                // make sure to close the cursor
                cursor.close();
            }
        }

        return result;

    }

    public HashSet<User> getAllUsersFollowers(User user) {
        HashSet<User> result = new HashSet<>();
        Cursor cursor = null;

        try {
            String mail = user.getEmail();
            cursor = db.query(TABLE_FOLLOW_NAME, TABLE_FOLLOW_COLUMNS, FOLLOW_COLUMN_USER2 +" = ?",
                    new String[] { mail }, null, null,
                    null, null);

            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Friend friend = cursorToFriend(cursor);
                    String mailU = friend.getUserMail1();
                    User uf = getUser( mailU );
                    result.add(uf);
                    cursor.moveToNext();
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null) {
                // make sure to close the cursor
                cursor.close();
            }
        }
        return result;

    }

    /****************************************************************************************************************************/
    /************************************************************** Helper Methods *****************************************************/
    /****************************************************************************************************************************/

    public User getUser(String mail){
        HashSet<User> resultU = new HashSet<>();
        ArrayList<User> users=new ArrayList<>();
        resultU = getAllUsers();
            for(User user : resultU){
                users.add(user);
            }
        for(int i=0 ; i < resultU.size() ; i++){
            if(users.get( i ).getEmail().equals( mail )){
                return  users.get( i );
            }
        }

        return null;

    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    private boolean isTableExist(String name, SQLiteDatabase db) {

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ name + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void close() {
        try {
            db.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
