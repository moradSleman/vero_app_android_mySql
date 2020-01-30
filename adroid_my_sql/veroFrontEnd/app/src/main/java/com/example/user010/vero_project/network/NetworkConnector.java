package com.example.user010.vero_project.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.LruCache;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.user010.vero_project.core.Post;
import com.example.user010.vero_project.core.PostReply;
import com.example.user010.vero_project.core.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class NetworkConnector {

    private static NetworkConnector mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;


    private final String HOST_URL = "http://10.0.0.3:8080/veroBackend/";
    private  final String BASE_URL = HOST_URL + "projres";

    public static final String GET_ALL_POSTS_JSON_REQ = "0";
    public static final String INSERT_POST_REQ = "1";
    public static final String INSERT_POST_REPLY_REQ = "2";
  //  public static final String GET_ALL_POST_REPLIES = "3";
    public static final String GET_ALL_USER_REQ = "4";
  /*  public static final String GET_USER_FOLLOWERS_REQ = "5";
    public static final String GET_USER_FOLLOWING_REQ = "6";
    public static final String GET_USER_FREINDS_REQ = "7";
    public static final String GET_USER_FREINDS_REQUESTS_REQ = "8";
    public static final String GET_USER_FREINDS_REQUESTED_REQ = "9"; */
    public static final String UPDATE_POST = "10";
    public static final String DELETE_POST= "11";
  //  public static final String GET_ALL_USER_POSTS_REQ = "12";
    public static final String DELETE_POST_REPLY= "13";
    public static final String INSERT_FREINDS = "14";
    public static final String INSERT_FOLLOW= "15";
    public static final String INSERT_FREIND_REQUEST = "16";
    public static final String DELETE_FREIND= "17";
    public static final String DELETE_FREIND_REQUEST = "18";
    public static final String DELETE_FOLLOW= "19";

    public static final String GET_ALL_FOLLOWS = "20";
    public static final String GET_ALL_FREINDS = "21";
    public static final String GET_ALL_FRIEND_REQUESTS = "22";
    public static final String GET_ALL_REPLIES = "23";
    public static final String GET_Post_photo = "24";
    public static final String GET_User_photo = "25";

    public static final String RESOURCE_FAIL_TAG = "{\"result_code\":0}";
    public static final String RESOURCE_SUCCESS_TAG = "{\"result_code\":1}";

    private static final String POST_ID = "postID";
    private static final String POST_EMAIL = "email";
    private static final String POST_DATE = "date";
    private static final String POST_TYPE = "postType";
    private static final String POST_link = "link";
    private static final String POST_photo = "photo";
    private static final String POST_Description = "postDescription";
    private static final String POST_LIKES_NUM = "likesNum";
    private static final String POST_COMMENTS_NUM = "commentsNum";
    private static final String USER_EMAIL_REQ = "email";

    private static final String Follow_ID = "followsID";
    private static final String Friends_ID = "idfriends";
    private static final String REQUEST_ID = "requestID";
    private static final String POSTREPLY_ID = "replyID";
    private static final String POSTREPLY_USER = "userReply";
    private static final String POSTREPLY_POST_ID = "postID";
    private static final String POSTREPLY_IS_LIKE = "like";
    private static final String POSTREPLY_COMMENT = "comment";
    private static final String USER_EMAIL_NUM1_REQ = "email";
    private static final String USER_EMAIL_NUM2_REQ = "email";
    private static final String RELATIONtABLEID_REQ = "tableID";
    public static final String REQ = "req";


    private NetworkConnector() {

    }

    public static synchronized NetworkConnector getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkConnector();
        }
        return mInstance;
    }

    public void initialize(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    private void addToRequestQueue(String query, final NetworkResListener listener) {

        String reqUrl = BASE_URL + "?" + query;
        notifyPreUpdateListeners(listener);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, reqUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        notifyPostUpdateListeners(response, ResStatus.SUCCESS, listener);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        JSONObject err = null;
                        try {
                            err = new JSONObject(RESOURCE_FAIL_TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            notifyPostUpdateListeners(err, ResStatus.FAIL, listener);
                        }

                    }
                });
        getRequestQueue().add(jsObjRequest);
    }

    private void addImageRequestToQueue(String query, User user, Post post, final NetworkResListener listener){

        String reqUrl = BASE_URL + "?" + query;

        notifyPreUpdateListeners(listener);

        getImageLoader().get(reqUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bm = response.getBitmap();
                notifyPostBitmapUpdateListeners(bm, ResStatus.SUCCESS, listener);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                notifyPostBitmapUpdateListeners(null, ResStatus.FAIL, listener);
            }
        });
    }

    private ImageLoader getImageLoader() {
        return mImageLoader;
    }


    private void uploadPostAndImage(final Post post, final NetworkResListener listener) {

        String reqUrl = HOST_URL + "web_item_manage?";
        notifyPreUpdateListeners(listener);


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, reqUrl,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            notifyPostUpdateListeners(obj, ResStatus.SUCCESS, listener);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_SHORT).show();
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(RESOURCE_FAIL_TAG );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            notifyPostUpdateListeners(obj, ResStatus.FAIL, listener);
                        }

                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(POST_ID, ((Integer)(post.getPostID())).toString());
                params.put(POST_EMAIL, post.getUserName());
                params.put(POST_DATE, String.valueOf(post.getDate()));
                params.put(POST_link, post.getLink());
                params.put(POST_COMMENTS_NUM,((Integer)post.getCommentsNum()).toString());
                params.put(POST_LIKES_NUM,((Integer)post.getLikesNum()).toString());
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
               /* Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                byte[] pic = post.getBitmapAsByteArray(post.getPhoto());
                params.put("fileField", new DataPart(imagename + ".png", pic));

                return params;
              */
               return null;
            }

        };

        //adding the request to volley
        getRequestQueue().add(volleyMultipartRequest);
    }


    public void sendRequestToServer(String requestCode, Post data, NetworkResListener listener) {

        if (data == null) {
            return;
        }

        Uri.Builder builder = new Uri.Builder();

        switch (requestCode) {
            case GET_Post_photo: {
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(POST_EMAIL,data.getUserName());
                String query = builder.build().getEncodedQuery();

                addImageRequestToQueue(query,null,data, listener);
                break;
            }
            case INSERT_POST_REQ: {

                uploadPostAndImage(data, listener);

                break;
            }

            case DELETE_POST: {
                builder.appendQueryParameter(REQ, requestCode);
                builder.appendQueryParameter(POST_ID, ((Integer)data.getPostID()).toString());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }
      /*      case GET_ALL_USER_POSTS_REQ: {
                builder.appendQueryParameter(REQ, requestCode);
                builder.appendQueryParameter(POST_EMAIL, data.getUserMail());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }
            case GET_ALL_POST_REPLIES: {
                builder.appendQueryParameter(REQ, requestCode);
                builder.appendQueryParameter(POST_ID, String.valueOf(data.getPostID()));
                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;

            }
           case GET_ITEM_IMAGE_REQ: {
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(ITEM_ID , data.getId());


                String query = builder.build().getEncodedQuery();
                addImageRequestToQueue(query, listener);

                break;

        }
        */
        }
    }

    public void sendRequestToServer(String requestCode ,  NetworkResListener listener) {
        Uri.Builder builder = new Uri.Builder();
        switch (requestCode){
            case GET_ALL_USER_REQ: {
                builder.appendQueryParameter(REQ , requestCode);
                break;
            }
            case GET_ALL_FOLLOWS: {
                builder.appendQueryParameter(REQ , requestCode);
                break;
            }
            case GET_ALL_FREINDS: {
                builder.appendQueryParameter(REQ , requestCode);
                break;
            }
            case GET_ALL_FRIEND_REQUESTS: {
                builder.appendQueryParameter(REQ , requestCode);
                break;
            }
            case GET_ALL_POSTS_JSON_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                break;
            }
            case GET_ALL_REPLIES: {
                builder.appendQueryParameter(REQ , requestCode);
                break;
            }

        }

        String query = builder.build().getEncodedQuery();

        addToRequestQueue(query, listener);
    }
    public void sendRequestToServer(String requestCode, PostReply data, NetworkResListener listener) {


        if(data==null){
            return;
        }

        Uri.Builder builder = new Uri.Builder();

        switch (requestCode){
            case INSERT_POST_REPLY_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(POSTREPLY_ID , String.valueOf(data.getReplyID()));
                builder.appendQueryParameter(POSTREPLY_USER , data.getUserReply());
                builder.appendQueryParameter(POSTREPLY_POST_ID , String.valueOf(data.getPostID()));
                builder.appendQueryParameter(POSTREPLY_IS_LIKE , String.valueOf(data.isLike()));
                builder.appendQueryParameter(POSTREPLY_COMMENT , data.getComment());
                break;
            }
            case DELETE_POST_REPLY:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(POSTREPLY_ID , String.valueOf(data.getReplyID()));
                break;
            }

        }
        String query = builder.build().getEncodedQuery();

        addToRequestQueue(query, listener);

    }

    public void sendRequestToServer(String requestCode, User data, NetworkResListener listener) {


        if(data==null){
            return;
        }

        Uri.Builder builder = new Uri.Builder();

        switch (requestCode){
            case GET_User_photo: {
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(USER_EMAIL_REQ,data.getEmail());
                String query = builder.build().getEncodedQuery();

                addImageRequestToQueue(query,data,null, listener);
                break;
            }
   /*         case GET_USER_FOLLOWERS_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(USER_EMAIL_REQ , data.getEmail());
                String query = builder.build().getEncodedQuery();

                addToRequestQueue(query, listener);
                break;
            }
            case GET_USER_FOLLOWING_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(USER_EMAIL_REQ , data.getEmail());
                String query = builder.build().getEncodedQuery();

                addToRequestQueue(query, listener);
                break;
            }
            case GET_USER_FREINDS_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(USER_EMAIL_REQ , data.getEmail());
                String query = builder.build().getEncodedQuery();

                addToRequestQueue(query, listener);
                break;
            }
            case GET_USER_FREINDS_REQUESTS_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(USER_EMAIL_REQ , data.getEmail());
                String query = builder.build().getEncodedQuery();

                addToRequestQueue(query, listener);
                break;
            }
            case GET_USER_FREINDS_REQUESTED_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(USER_EMAIL_REQ , data.getEmail());
                String query = builder.build().getEncodedQuery();

                addToRequestQueue(query, listener);
                break;
            } */
        }


    }
    public void sendRequestToServer(String requestCode,int id,String email1,String email2, NetworkResListener listener) {

        if(email1==null || email2==null){
            return;
        }

        Uri.Builder builder = new Uri.Builder();

        switch (requestCode){
            case INSERT_FREINDS:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(Friends_ID,((Integer)id).toString());
                builder.appendQueryParameter(USER_EMAIL_NUM1_REQ , email1);
                builder.appendQueryParameter(USER_EMAIL_NUM2_REQ , email2);
                break;
            }
            case INSERT_FOLLOW:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(Follow_ID,((Integer)id).toString());
                builder.appendQueryParameter(USER_EMAIL_NUM1_REQ , email1);
                builder.appendQueryParameter(USER_EMAIL_NUM2_REQ , email2);
                break;
            }
            case INSERT_FREIND_REQUEST:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(REQUEST_ID,((Integer)id).toString());
                builder.appendQueryParameter(USER_EMAIL_NUM1_REQ , email1);
                builder.appendQueryParameter(USER_EMAIL_NUM2_REQ , email2);
                break;
            }

        }
        String query = builder.build().getEncodedQuery();

        addToRequestQueue(query, listener);

    }
    public void sendRequestToServer(String requestCode,Integer tableID, NetworkResListener listener) {


        if(tableID==null){
            return;
        }

        Uri.Builder builder = new Uri.Builder();

        switch (requestCode){
            case DELETE_FREIND:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(RELATIONtABLEID_REQ , tableID.toString());
                break;
            }
            case DELETE_FOLLOW:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(RELATIONtABLEID_REQ , tableID.toString());
                break;
            }
            case DELETE_FREIND_REQUEST:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(RELATIONtABLEID_REQ , tableID.toString());
                break;
            }

        }
        String query = builder.build().getEncodedQuery();

        addToRequestQueue(query, listener);

    }
    private void clean() {

    }


    private  void notifyPostBitmapUpdateListeners(final Bitmap res, final ResStatus status, final NetworkResListener listener) {

        Handler handler = new Handler(mCtx.getMainLooper());

        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                    listener.onPostUpdate(res, status);
                }
                catch(Throwable t){
                    t.printStackTrace();
                }
            }
        };
        handler.post(myRunnable);

    }

    private  void notifyPostUpdateListeners(final JSONObject res, final ResStatus status, final NetworkResListener listener) {

        Handler handler = new Handler(mCtx.getMainLooper());

        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                    listener.onPostUpdate(res, status);
                }
                catch(Throwable t){
                    t.printStackTrace();
                }
            }
        };
        handler.post(myRunnable);

    }

    private  void notifyPreUpdateListeners(final NetworkResListener listener) {


        Handler handler = new Handler(mCtx.getMainLooper());

        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                    listener.onPreUpdate();
                }
                catch(Throwable t){
                    t.printStackTrace();
                }
            }
        };
        handler.post(myRunnable);

    }
}
