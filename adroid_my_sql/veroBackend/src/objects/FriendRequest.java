package objects;

import java.util.HashSet;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class FriendRequest {

    public int friendshipID;
    public String userMail1;
    public String userMail2;
    public int friendshipStatus;

    public FriendRequest(){}

    public FriendRequest(int friendshipID, String userMail1, String userMail2, int friendshipStatus) {
        this.friendshipID = friendshipID;
        this.userMail1 = userMail1;
        this.userMail2 = userMail2;
        this.friendshipStatus = friendshipStatus;
    }

    public int getFriendshipID() {
        return friendshipID;
    }

    public void setFriendshipID(int friendshipID) {
        this.friendshipID = friendshipID;
    }

    public String getUserMail1() {
        return userMail1;
    }

    public void setUserMail1(String userMail1) {
        this.userMail1 = userMail1;
    }

    public String getUserMail2() {
        return userMail2;
    }

    public void setUserMail2(String getUserMail2) {
        this.userMail2 = getUserMail2;
    }

    public int getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(int friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + friendshipID;
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
		FriendRequest other = (FriendRequest) obj;
		if (friendshipID != other.friendshipID)
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "FriendRequest{" +
                "friendshipID=" + friendshipID +
                ", userMail1='" + userMail1 + '\'' +
                ", getUserMail2='" + userMail2 + '\'' +
                ", friendshipStatus=" + friendshipStatus +
                '}';
    }
    
    public static String toJson(HashSet<FriendRequest> list) {

    	JSONObject jsonObj = new JSONObject();

    	if (list == null) {
    		return null;
    	}

    	if (list.size() == 0) {
    		return null;
    	}

    	JSONArray jsonArray = new JSONArray();

    	for (FriendRequest fr : list) {

    		if (fr != null) {

    			JSONObject pObj = new JSONObject();
    			pObj.put("id", fr.getFriendshipID());
    			pObj.put("userMail1", fr.getUserMail1());
    			pObj.put("userMail2", fr.getUserMail2());
    			pObj.put("friendshipStatus", fr.getFriendshipStatus());
    			jsonArray.add(pObj);
    		}

    	}

    	jsonObj.put("friendsRequests", jsonArray);

    	return jsonObj.toString();
    }
}
