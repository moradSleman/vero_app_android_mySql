package objects;

import java.util.HashSet;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PostReply {
private int replyID;
private String userReply;
private int postID;
private boolean like;
private String comment;
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
	result = prime * result + replyID;
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
	if (replyID != other.replyID)
		return false;
	return true;
}

public static String toJson(HashSet<PostReply> list) {

	JSONObject jsonObj = new JSONObject();

	if (list == null) {
		return null;
	}

	if (list.size() == 0) {
		return null;
	}

	JSONArray jsonArray = new JSONArray();

	for (PostReply reply : list) {

		if (reply != null) {
			JSONObject replyObj =new JSONObject();
			replyObj.put("replyID", reply.getReplyID());
			replyObj.put("userReply", reply.getUserReply());
			replyObj.put("postID", reply.getPostID());
			replyObj.put("like", reply.isLike());
			replyObj.put("comment", reply.getComment());
			jsonArray.add(replyObj);
		}

	}

	jsonObj.put("PostRepleis", jsonArray);

	return jsonObj.toString();
}



}
