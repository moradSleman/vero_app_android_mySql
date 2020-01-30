package com.example.user010.vero_project.core;


import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User {

    private String email;
    private String fullName;
    private String password;
    private String biography;
    private int phoneNum;
    private Date dateOfBirth;
    private Bitmap profilePhoto;
    private int followersNum;
    private int followingNum;





    public User(String email, String firstName, String password, String biography, int phoneNum, Date dateOfBirth, Bitmap profilePhoto , int followersNum , int followingNum) {
        this.email = email;
        this.fullName = firstName;
        this.password = password;
        this.biography = biography;
        this.phoneNum = phoneNum;
        this.dateOfBirth = dateOfBirth;
        this.profilePhoto = profilePhoto;
        this.followersNum = followersNum;
        this.followingNum = followingNum;
    }
    public User(){

    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return fullName;
    }


    public String getPassword() {
        return password;
    }

    public String getBiography() {
        return biography;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Bitmap getProfilePhoto() {
        return profilePhoto;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.fullName = firstName;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setProfilePhoto(Bitmap profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public int getFollowersNum() {
        return followersNum;
    }

    public void setFollowersNum(int followersNum) {
        this.followersNum = followersNum;
    }


    public int getFollowingNum() {
        return followingNum;
    }

    public void setFollowingNum(int followingNum) {
        this.followingNum = followingNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", biography='" + biography + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", profilePhoto=" + profilePhoto +
                '}';
    }


    public boolean fromJson(JSONObject fObj) {
        boolean res = false;
        try {
            setEmail(fObj.getString("email"));
            setFirstName(fObj.getString("fullName"));
            setPassword(fObj.getString("password"));

            String dateStr = fObj.getString("birthdate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = sdf.parse(dateStr);
            setDateOfBirth(birthDate);
            setPhoneNum(fObj.getInt("phone"));
            setBiography(fObj.getString("bio"));
            setFollowersNum(fObj.getInt("numOfFollowers"));
            setFollowingNum(fObj.getInt("numOfFollowing"));


            res = true;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return res;

    }


    public static List<User> parseJson(JSONObject json) {

        List<User> users = null;
        try {


//			JSONTokener jsonTokener = new JSONTokener(content);
//
//			JSONObject json = (JSONObject) jsonTokener.nextValue();

            users = new ArrayList<User>();
            JSONArray usersJsonArr = json.getJSONArray("users");

            for (int i = 0; i < usersJsonArr.length(); i++) {
                try {

                    JSONObject fObj = usersJsonArr.getJSONObject(i);

                    User user = new User();
                    if(user.fromJson(fObj)){
                        users.add(user);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return users;
    }
}
