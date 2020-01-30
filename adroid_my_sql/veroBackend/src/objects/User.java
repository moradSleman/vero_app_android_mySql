package objects;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
	
	public class User {
	private String email;
	private String fullName;
	private String password;
	private Date birthdate;
	private int phone;
	private String bio;
	private int numOfFollowers;
	private int numOfFollowing;
	private byte[] photo;
	public User(String email,String fullName, String password, Date birthdate, int phone, String bio,byte[] photo) {
	super();
	this.email=email;
	this.fullName = fullName;
	this.password = password;
	this.birthdate = birthdate;
	this.phone = phone;
	this.bio = bio;
	this.photo=photo;
	this.numOfFollowers = 0;
	this.numOfFollowing = 0;
}

public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

public byte[] getPhoto() {
	return photo;
}

public void setPhoto(byte[] photo) {
	this.photo = photo;
}

public String getFullName() {
	return fullName;
}
public void setFullName(String fullName) {
	this.fullName = fullName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public Date getBirthdate() {
	return birthdate;
}
public void setBirthdate(Date birthdate) {
	this.birthdate = birthdate;
}
public int getPhone() {
	return phone;
}
public void setPhone(int phone) {
	this.phone = phone;
}
public String getBio() {
	return bio;
}
public void setBio(String bio) {
	this.bio = bio;
}
public int getNumOfFollowers() {
	return numOfFollowers;
}
public void setNumOfFollowers(int numOfFollowers) {
	this.numOfFollowers = numOfFollowers;
}
public int getNumOfFollowing() {
	return numOfFollowing;
}
public void setNumOfFollowing(int numOfFollowing) {
	this.numOfFollowing = numOfFollowing;
}


@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((email == null) ? 0 : email.hashCode());
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
	User other = (User) obj;
	if (email == null) {
		if (other.email != null)
			return false;
	} else if (!email.equals(other.email))
		return false;
	return true;
}

public static String toJson(HashSet<User> users) {

	JSONObject jsonObj = new JSONObject();

	if(users==null || users.size()==0) {
		return null;
	}
	JSONArray jsonArray = new JSONArray();
			for(User user : users) {
			JSONObject userObj =new JSONObject();
			userObj.put("email", user.getEmail());
			userObj.put("fullName", user.getFullName());
			userObj.put("password", user.getPassword());
			userObj.put("birthdate", user.getBirthdate().toString());
			userObj.put("phone", user.getPhone());
			userObj.put("bio", user.getBio());
			userObj.put("numOfFollowers", user.getNumOfFollowers());
			userObj.put("numOfFollowing", user.getNumOfFollowing());
			jsonArray.add(userObj);
			}
	jsonObj.put("users", jsonArray);

	return jsonObj.toString();
}
}
