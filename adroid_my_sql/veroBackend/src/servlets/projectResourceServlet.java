package servlets;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import databaseOperations.ConnPool;
import databaseOperations.PostReplyResProvider;
import databaseOperations.PostResProvider;
import databaseOperations.UserResProvider;
import objects.Follow;
import objects.Friend;
import objects.FriendRequest;
import objects.Post;
import objects.PostReply;
import objects.User;
import utils.FilesUtils;
public class projectResourceServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	// ========
	private static final int GET_ALL_POSTS_JSON_REQ = 0;
	private static final int INSERT_POST_REQ = 1;
	private static final int INSERT_POST_REPLY_REQ = 2;
//	private static final int GET_ALL_POST_REPLIES = 3;
	private static final int GET_ALL_USER_REQ = 4;
/*	private static final int GET_USER_FOLLOWERS_REQ = 5;
	private static final int GET_USER_FOLLOWING_REQ = 6;
	private static final int GET_USER_FREINDS_REQ = 7;
	private static final int GET_USER_FREINDS_REQUESTS_REQ = 8;
	private static final int GET_USER_FREINDS_REQUESTED_REQ = 9; */
	private static final int UPDATE_POST = 10;
	private static final int DELETE_POST= 11;
//	private static final int GET_ALL_USER_POSTS_REQ = 12;
	private static final int DELETE_POST_REPLY= 13;
	private static final int INSERT_FREINDS = 14;
	private static final int INSERT_FOLLOW= 15;
	private static final int INSERT_FREIND_REQUEST = 16;
	private static final int DELETE_FREIND= 17;
	private static final int DELETE_FREIND_REQUEST = 18;
	private static final int DELETE_FOLLOW= 19;
	
	private static final int GET_ALL_FOLLOWS = 20;
	private static final int GET_ALL_FREINDS = 21;
	private static final int GET_ALL_FRIEND_REQUESTS = 22;
	private static final int GET_ALL_REPLIES = 23;
	
	private static final int GET_POST_IMAGE_REQ = 24;
	private static final int GET_USER_IMAGE_REQ = 25;
	
	private static final String POST_ID = "postID";
	private static final String POST_USERNAME = "userName";
	private static final String POST_DATE = "date";
	private static final String POST_TYPE = "postType";
	private static final String POST_link = "link";
	private static final String POST_photo = "photo";
	private static final String POST_location = "location";
	private static final String POST_Description = "postDescription";
	private static final String POST_LIKES_NUM = "likesNum";
	private static final String POST_COMMENTS_NUM = "commentsNum";
	private static final String USER_EMAIL_REQ = "email";
	private static final String USER_EMAIL_NUM1_REQ = "email";
	private static final String USER_EMAIL_NUM2_REQ = "email";
	private static final String RELATIONtABLEID_REQ = "tableID";
	
	private static final String Follow_ID = "followsID";
	private static final String Friends_ID = "idfriends";
	private static final String REQUEST_ID = "requestID";
	private static final String POSTREPLY_ID = "replyID";
	private static final String POSTREPLY_USER = "userReply";
	private static final String POSTREPLY_POST_ID = "postID";
	private static final String POSTREPLY_IS_LIKE = "like";
	private static final String POSTREPLY_COMMENT = "comment";
	
	private static final String RESOURCE_FAIL_TAG = "{\"result_code\":0}";
	private static final String RESOURCE_SUCCESS_TAG = "{\"result_code\":1}";


	private static final String REQ = "req";

	public static final int DB_RETRY_TIMES = 5;


	public void init(ServletConfig config) throws ServletException {
		super.init();
		
		String tmp = config.getServletContext().getInitParameter("localAppDir");
		if (tmp != null) {
			FilesUtils.appDirName = config.getServletContext().getRealPath(tmp);
			System.out.println(FilesUtils.appDirName);

		}

	}

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String respPage = null;
		String userReq = req.getParameter(REQ);
		Connection conn = null;
		int retry = DB_RETRY_TIMES;
		if (userReq != null) {
			int reqNo = Integer.valueOf(userReq);
			System.out.println("ProjectResourceServlet:: req code ==>" + reqNo);
			while (retry > 0) {

				try {

					switch (reqNo) {
					case GET_POST_IMAGE_REQ: {
						String PostId = req.getParameter(POST_ID);
						respPage = RESOURCE_FAIL_TAG;

						conn = ConnPool.getInstance().getConnection();
						PostResProvider postsResProvider = new PostResProvider();

						byte[] imgBlob = postsResProvider.getImage(Integer.parseInt(PostId), conn);

						if (imgBlob != null && imgBlob.length > 0) {
							ServletOutputStream os = resp.getOutputStream();
							os.write(imgBlob);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
					
					case GET_USER_IMAGE_REQ: {
						String email = req.getParameter(USER_EMAIL_REQ);
						respPage = RESOURCE_FAIL_TAG;

						conn = ConnPool.getInstance().getConnection();
						UserResProvider usersResProvider = new UserResProvider();

						byte[] imgBlob = usersResProvider.getImage(email, conn);

						if (imgBlob != null && imgBlob.length > 0) {
							ServletOutputStream os = resp.getOutputStream();
							os.write(imgBlob);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
					
					case INSERT_FREINDS: {
						String id =req.getParameter(Friends_ID);
						String email1= req.getParameter(USER_EMAIL_NUM1_REQ);
						String email2= req.getParameter(USER_EMAIL_NUM2_REQ);
						respPage = RESOURCE_FAIL_TAG;
						resp.addHeader("Content-Type",
								"application/json; charset=UTF-8");
						conn = ConnPool.getInstance().getConnection();
						UserResProvider userRePrivider = new UserResProvider();
						if (userRePrivider.insertFreinds(Integer.parseInt(id), email1, email2, conn)) {
							respPage = RESOURCE_SUCCESS_TAG;
						}
						PrintWriter pw = resp.getWriter();
						pw.write(respPage);

						retry = 0;
						break;
					}
					case INSERT_FOLLOW: {
						String id = req.getParameter(Follow_ID);
						String email1= req.getParameter(USER_EMAIL_NUM1_REQ);
						String email2= req.getParameter(USER_EMAIL_NUM2_REQ);
						respPage = RESOURCE_FAIL_TAG;
						resp.addHeader("Content-Type",
								"application/json; charset=UTF-8");
						conn = ConnPool.getInstance().getConnection();
						UserResProvider userRePrivider = new UserResProvider();
						if (userRePrivider.insertFollows(Integer.parseInt(id),email1, email2, conn)) {
							respPage = RESOURCE_SUCCESS_TAG;
						}
						PrintWriter pw = resp.getWriter();
						pw.write(respPage);

						retry = 0;
						break;
					}
					case INSERT_FREIND_REQUEST: {
						String id= req.getParameter(REQUEST_ID);
						String email1= req.getParameter(USER_EMAIL_NUM1_REQ);
						String email2= req.getParameter(USER_EMAIL_NUM2_REQ);
						respPage = RESOURCE_FAIL_TAG;
						resp.addHeader("Content-Type",
								"application/json; charset=UTF-8");
						conn = ConnPool.getInstance().getConnection();
						UserResProvider userRePrivider = new UserResProvider();
						if (userRePrivider.insertFreindRequests(Integer.parseInt(id),email1, email2, conn)) {
							respPage = RESOURCE_SUCCESS_TAG;
						}
						PrintWriter pw = resp.getWriter();
						pw.write(respPage);

						retry = 0;
						break;
					}
					case DELETE_FREIND: {
						String feindsID= req.getParameter(RELATIONtABLEID_REQ);
						respPage = RESOURCE_FAIL_TAG;
						resp.addHeader("Content-Type",
								"application/json; charset=UTF-8");
						conn = ConnPool.getInstance().getConnection();
						UserResProvider userRePrivider = new UserResProvider();
						if (userRePrivider.deleteFreinds(Integer.parseInt(feindsID), conn)) {
							respPage = RESOURCE_SUCCESS_TAG;
						}
						PrintWriter pw = resp.getWriter();
						pw.write(respPage);

						retry = 0;
						break;
					}
					case DELETE_FOLLOW: {
						String followsID= req.getParameter(RELATIONtABLEID_REQ);
						respPage = RESOURCE_FAIL_TAG;
						resp.addHeader("Content-Type",
								"application/json; charset=UTF-8");
						conn = ConnPool.getInstance().getConnection();
						UserResProvider userRePrivider = new UserResProvider();
						if (userRePrivider.deletefollows(Integer.parseInt(followsID), conn)) {
							respPage = RESOURCE_SUCCESS_TAG;
						}
						PrintWriter pw = resp.getWriter();
						pw.write(respPage);

						retry = 0;
						break;
					}
					case DELETE_FREIND_REQUEST: {
						String feindRequestID= req.getParameter(RELATIONtABLEID_REQ);
						respPage = RESOURCE_FAIL_TAG;
						resp.addHeader("Content-Type",
								"application/json; charset=UTF-8");
						conn = ConnPool.getInstance().getConnection();
						UserResProvider userRePrivider = new UserResProvider();
						if (userRePrivider.deleteFreindRequests(Integer.parseInt(feindRequestID), conn)) {
							respPage = RESOURCE_SUCCESS_TAG;
						}
						PrintWriter pw = resp.getWriter();
						pw.write(respPage);

						retry = 0;
						break;
					}
					// == folder apis
					case GET_ALL_POSTS_JSON_REQ: {

						conn = ConnPool.getInstance().getConnection();
						PostResProvider postsResProvider = new PostResProvider();
						List<Post> postsList = postsResProvider.getAllPosts(conn);
						String resultJson = Post.toJson(postsList);

						if (resultJson != null && !resultJson.isEmpty()) {
							respPage = resultJson;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}

					case INSERT_POST_REQ: {
						String postId = req.getParameter(POST_ID);
						String userName = req.getParameter(POST_USERNAME);
						String date = req.getParameter(POST_DATE);
						String postType = req.getParameter(POST_TYPE);
						String link = req.getParameter(POST_link);
						ServletInputStream isServ = req.getInputStream();

						DataInputStream is = new DataInputStream(isServ);

						ByteArrayOutputStream bin = new ByteArrayOutputStream(2048);
						int data;
						while ((data = is.read()) != -1) {
							bin.write((byte) data);
						}

						byte[] imageBlob = bin.toByteArray();
						String postDescrition = req.getParameter(POST_Description);
						String likesNum = req.getParameter(POST_LIKES_NUM);
						String commentsNum = req.getParameter(POST_COMMENTS_NUM);
						respPage = RESOURCE_FAIL_TAG;
						resp.addHeader("Content-Type",
								"application/json; charset=UTF-8");
						conn = ConnPool.getInstance().getConnection();
						PostResProvider foldersResProvider = new PostResProvider();

				//		Post post = new Post(Integer.parseInt(postId),userName,link,imageBlob,postDescrition,likesNum,commentsNum);
				//		post.setLikesNum(Integer.parseInt(likesNum));
					//	post.setCommentsNum(Integer.parseInt(commentsNum));
					//	if (foldersResProvider.insertPost(post, conn)) {
					//		respPage = RESOURCE_SUCCESS_TAG;
				///		}
						PrintWriter pw = resp.getWriter();
						pw.write(respPage);

						retry = 0;
						break;
					}

					case INSERT_POST_REPLY_REQ: {
						String replyID = req.getParameter(POSTREPLY_ID);
						String userReply=req.getParameter(POSTREPLY_USER);
						String postID = req.getParameter(POSTREPLY_POST_ID);
						String isLike=req.getParameter(POSTREPLY_IS_LIKE);
						String comment = req.getParameter(POSTREPLY_COMMENT);
						
						respPage = RESOURCE_FAIL_TAG;
						resp.addHeader("Content-Type",
								"application/json; charset=UTF-8");
						conn = ConnPool.getInstance().getConnection();
						PostReplyResProvider postReplyResProvider = new PostReplyResProvider();

						PostReply postReply = new PostReply(Integer.parseInt(replyID),userReply
								,Integer.parseInt(postID),Boolean.parseBoolean(isLike),comment);
						if (postReplyResProvider.insertPostReply(postReply, conn)) {
							respPage = RESOURCE_SUCCESS_TAG;
						}
						PrintWriter pw = resp.getWriter();
						pw.write(respPage);

						retry = 0;
						break;
					}
					
		/*			case GET_ALL_POST_REPLIES: {
						String postID = req.getParameter(POST_ID);

						respPage = RESOURCE_FAIL_TAG;
						resp.addHeader("Content-Type",
								"application/json; charset=UTF-8");
						conn = ConnPool.getInstance().getConnection();
						PostReplyResProvider postReplyResProvider = new PostReplyResProvider();
						
						HashSet<PostReply> replies=postReplyResProvider.getAllReplies(conn,Integer.parseInt(postID));
						String resultJson = PostReply.toJson(replies);

						if (resultJson != null && !resultJson.isEmpty()) {
							respPage = resultJson;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
*/
					case GET_ALL_USER_REQ: {
						conn = ConnPool.getInstance().getConnection();
						UserResProvider userResProvider = new UserResProvider();
						HashSet<User> users = userResProvider.getAllUsers(conn);
						String resultJson = User.toJson(users);
						
						if (resultJson != null && !resultJson.isEmpty()) {
							respPage = resultJson;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
		/*			case GET_USER_FOLLOWERS_REQ: {

						String userName = req.getParameter(USER_EMAIL_REQ);
						conn = ConnPool.getInstance().getConnection();
						UserResProvider userResProvider = new UserResProvider();
						
						HashSet<User> usersList = userResProvider.getAllFollowers(conn, userName);
						String resultJson = User.toJson(usersList);

						if (resultJson != null && !resultJson.isEmpty()) {
							respPage = resultJson;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}

					case GET_USER_FOLLOWING_REQ: {

						String userName = req.getParameter(USER_EMAIL_REQ);
						conn = ConnPool.getInstance().getConnection();
						UserResProvider userResProvider = new UserResProvider();
						
						HashSet<User> usersList = userResProvider.getAllFollowing(conn, userName);
						String resultJson = User.toJson(usersList);

						if (resultJson != null && !resultJson.isEmpty()) {
							respPage = resultJson;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
					
					case GET_USER_FREINDS_REQ: {

						String userName = req.getParameter(USER_EMAIL_REQ);
						conn = ConnPool.getInstance().getConnection();
						UserResProvider userResProvider = new UserResProvider();
						
						HashSet<User> usersList = userResProvider.getAllfreinds(conn, userName);
						String resultJson = User.toJson(usersList);

						if (resultJson != null && !resultJson.isEmpty()) {
							respPage = resultJson;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
					
					case GET_USER_FREINDS_REQUESTS_REQ: {

						String userName = req.getParameter(USER_EMAIL_REQ);
						conn = ConnPool.getInstance().getConnection();
						UserResProvider userResProvider = new UserResProvider();
						
						HashSet<User> usersList = userResProvider.getAllfreindRequests(conn, userName);
						String resultJson = User.toJson(usersList);

						if (resultJson != null && !resultJson.isEmpty()) {
							respPage = resultJson;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}


					case GET_USER_FREINDS_REQUESTED_REQ: {

						String userName = req.getParameter(USER_EMAIL_REQ);
						conn = ConnPool.getInstance().getConnection();
						UserResProvider userResProvider = new UserResProvider();
						
						HashSet<User> usersList = userResProvider.getAllfreindRequested(conn, userName);
						String resultJson = User.toJson(usersList);

						if (resultJson != null && !resultJson.isEmpty()) {
							respPage = resultJson;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
					*/
					case UPDATE_POST: {
						String postID = req.getParameter(POST_ID);
						String link = req.getParameter(POST_link);
						String postDescription = req.getParameter(POST_Description);
						ServletInputStream isServ = req.getInputStream();
						DataInputStream is = new DataInputStream(isServ);
						ByteArrayOutputStream bin = new ByteArrayOutputStream(2048);
						int data;
						while ((data = is.read()) != -1) {
							bin.write((byte) data);
						}
						byte[] imageBlob = bin.toByteArray();
						conn = ConnPool.getInstance().getConnection();
						PostResProvider postResProvider=new PostResProvider();
						Boolean result=postResProvider.updatePost(conn,Integer.parseInt(postID), link, imageBlob, postDescription);
						
							respPage = result.toString();
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							if(result==true) {
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
					case DELETE_POST: {

						String postID = req.getParameter(POST_ID);
						conn = ConnPool.getInstance().getConnection();
						PostResProvider postResProvider=new PostResProvider();
						Boolean result=postResProvider.deletePost(conn,Integer.parseInt(postID));
							respPage = result.toString();
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							if(result==true) {
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
	/*				case GET_ALL_USER_POSTS_REQ: {
						String userName1=req.getParameter(POST_USERNAME);
						conn = ConnPool.getInstance().getConnection();
						PostResProvider postsResProvider = new PostResProvider();
						List<Post> postsList = postsResProvider.getAllUserPosts(conn, userName1);
						String resultJson = Post.toJson(postsList);

						if (resultJson != null && !resultJson.isEmpty()) {
							respPage = resultJson;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
					*/
					case DELETE_POST_REPLY: {

						String postID = req.getParameter(POSTREPLY_ID);
						conn = ConnPool.getInstance().getConnection();
						PostReplyResProvider postReplyResProvider=new PostReplyResProvider();
						Boolean result=postReplyResProvider.deletePostReply(conn,Integer.parseInt(postID));
							respPage = result.toString();
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							if(result==true) {
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
					case GET_ALL_FOLLOWS: {

						conn = ConnPool.getInstance().getConnection();
						UserResProvider usersResProvider = new UserResProvider();
						HashSet<Follow> followsList = usersResProvider.getFollows(conn);
						String resultJson = Follow.toJson(followsList);

						if (resultJson != null && !resultJson.isEmpty()) {
							respPage = resultJson;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
					case GET_ALL_FRIEND_REQUESTS: {

						conn = ConnPool.getInstance().getConnection();
						UserResProvider usersResProvider = new UserResProvider();
						HashSet<FriendRequest> friendRequestssList = usersResProvider.getFriendRequest(conn);
						String resultJson = FriendRequest.toJson(friendRequestssList);

						if (resultJson != null && !resultJson.isEmpty()) {
							respPage = resultJson;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
					case GET_ALL_FREINDS: {

						conn = ConnPool.getInstance().getConnection();
						UserResProvider usersResProvider = new UserResProvider();
						HashSet<Friend> friendsList = usersResProvider.getFriends(conn);
						String resultJson = Friend.toJson(friendsList);
						System.out.println(resultJson);
						if (resultJson != null && !resultJson.isEmpty()) {
							respPage = resultJson;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
					case GET_ALL_REPLIES: {

						conn = ConnPool.getInstance().getConnection();
						PostReplyResProvider postReplyResProvider = new PostReplyResProvider();
						HashSet<PostReply> friendsList = postReplyResProvider.getAllReplies(conn);
						String resultJson = PostReply.toJson(friendsList);

						if (resultJson != null && !resultJson.isEmpty()) {
							respPage = resultJson;
							resp.addHeader("Content-Type",
									"application/json; charset=UTF-8");
							PrintWriter pw = resp.getWriter();
							pw.write(respPage);
						} else {
							resp.sendError(404);
						}

						retry = 0;
						break;
					}
					default:
						retry = 0;
					}

				} catch (SQLException e) {
					e.printStackTrace();
					retry--;
				} catch (Throwable t) {
					t.printStackTrace();
					retry = 0;
				} finally {
					if (conn != null) {
						ConnPool.getInstance().returnConnection(conn);
					}
				}
			}
		}

	}
	
	

}
