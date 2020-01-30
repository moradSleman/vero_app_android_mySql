package objects;

import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Time;
import java.util.ArrayList;

public class Post {
private int postID;
private String email;
private Date date;
private String postType;
private String link;
private byte[] photo ;
private String postDescription;
private int likesNum;
private int commentsNum;
public Post(int postID, String email,Date date, String link, byte[] photo, String postDescription,int likesNum,int commentsNum) {
	super();
	this.postID = postID;
	this.email = email;
	this.link = link;
	this.photo = photo;
	this.postDescription = postDescription;
	this.date=date; 
	this.likesNum=likesNum;
	this.commentsNum=commentsNum;
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
	return email;
}
public void setUserName(String email) {
	this.email = email;
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
public byte[] getPhoto() {
	return photo;
}
public void setPhoto(byte[] photo) {
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
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + postID;
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
	Post other = (Post) obj;
	if (postID != other.postID)
		return false;
	return true;
}
public static String toJson(List<Post> list) {

	JSONObject jsonObj = new JSONObject();

	if (list == null) {
		return null;
	}

	if (list.size() == 0) {
		return null;
	}

	JSONArray jsonArray = new JSONArray();

	for (Post p : list) {

		if (p != null) {

			JSONObject pObj = new JSONObject();
			pObj.put("postID", p.getPostID());
			pObj.put("userName", p.getUserName());
			pObj.put("date", p.getDate().toString());
			pObj.put("link", p.getLink());
			pObj.put("postDescription", p.getPostDescription());

			jsonArray.add(pObj);
		}

	}

	jsonObj.put("posts", jsonArray);

	return jsonObj.toString();
}
}
