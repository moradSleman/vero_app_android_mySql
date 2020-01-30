package objects;

import java.util.HashSet;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Follow {

    public int id;
    public String userMail1;
    public String userMail2;

    public Follow(){}
    public Follow(int id, String userMail1, String userMail2) {
        this.id = id;
        this.userMail1 = userMail1;
        this.userMail2 = userMail2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setUserMail2(String userMail2) {
        this.userMail2 = userMail2;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Follow other = (Follow) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", userMail1='" + userMail1 + '\'' +
                ", userMail2='" + userMail2 + '\'' +
                '}';
    }
    
    public static String toJson(HashSet<Follow> list) {

    	JSONObject jsonObj = new JSONObject();

    	if (list == null) {
    		return null;
    	}

    	if (list.size() == 0) {
    		return null;
    	}

    	JSONArray jsonArray = new JSONArray();

    	for (Follow f : list) {

    		if (f != null) {

    			JSONObject pObj = new JSONObject();
    			pObj.put("id", f.getId());
    			pObj.put("userMail1", f.getUserMail1());
    			pObj.put("userMail2", f.getUserMail2());
    			jsonArray.add(pObj);
    		}

    	}

    	jsonObj.put("follows", jsonArray);

    	return jsonObj.toString();
    }
}
