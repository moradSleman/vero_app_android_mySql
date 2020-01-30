package databaseOperations;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;


import objects.Follow;
import objects.Friend;
import objects.FriendRequest;
import objects.PostReply;
import objects.User;

public class UserResProvider {
	private static final String selectUser_sql="SELECT * FROM  User;";
/*	private static final String selectFollowing_sql="SELECT * FROM  follows WHERE userFollows=?;";
	private static final String selectFollowers_sql="SELECT * FROM  follows WHERE userFollowed=?;";
	private static final String selectFreinds_sql="SELECT * FROM  friends WHERE firstFriend=? || secondFriend=?;";
	private static final String selectFreindRequests_sql="SELECT * FROM  friendRequests WHERE userrequested=?;";
	private static final String selectFreindRequested_sql="SELECT * FROM  friendRequests WHERE userrequests=?;";*/
	private static final String insertFreinds_sql="INSERT INTO friends (idfriends,firstFriend,secondFriend) VALUES(?,?,?);";
	private static final String insertFollows_sql="INSERT INTO follows (followsID,userFollowed,userFollows) VALUES(?,?,?);";
	private static final String insertFreindRequest_sql="INSERT INTO friendrequests (requestID,userRequests,userrequested) VALUES(?,?,?);";
	private static final String deleteFreinds_sql="DELETE FROM friends Where idfriends=?;";
	private static final String deleteFollows_sql="DELETE FROM follows Where followsID=?;";
	private static final String deleteFreindRequest_sql="DELETE FROM friendrequests Where requestID=?;";
	private static final String selectFollows_sql="SELECT * FROM  follows;";
	private static final String selectFriends_sql="SELECT * FROM  friends;";
	private static final String selectFriendRequests_sql="SELECT * FROM  friendrequests;";
	private static final String select_img_sql = "SELECT photo FROM  User WHERE email=?;";
	
	public byte[] getImage(String userEmail, Connection conn)
			throws SQLException {

		byte[] result = null;

		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
		
			ps = conn.prepareStatement(select_img_sql);

			ps.setString(1, userEmail);

			rs = ps.executeQuery();

			while (rs.next()) {

				java.sql.Blob imageBlob = rs.getBlob(1);
				if (imageBlob != null) {
					result = imageBlob.getBytes(1, (int) imageBlob.length());
				}
			}

		} catch (SQLException e) {
			throw e;

		} catch (Throwable e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}
	public boolean insertFreinds(int id,String email1,String email2, Connection conn) {

		boolean result = false;
		PreparedStatement ps = null;

		try {
					ps = (PreparedStatement) conn.prepareStatement(insertFreinds_sql);
					ps.setInt(1, id);
					ps.setString(2,email1);
					ps.setString(3,email2);
					ps.execute();
					result=true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;

	}
	public boolean insertFollows(int id,String email1,String email2, Connection conn) {

		boolean result = false;
		PreparedStatement ps = null;

		try {
					ps = (PreparedStatement) conn.prepareStatement(insertFollows_sql);
					ps.setInt(1,id);
					ps.setString(2,email1);
					ps.setString(3,email2);
					ps.execute();
					result=true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;

	}
	public boolean insertFreindRequests(int id,String email1,String email2, Connection conn) {

		boolean result = false;
		PreparedStatement ps = null;

		try {
					ps = (PreparedStatement) conn.prepareStatement(insertFreindRequest_sql);
					ps.setInt(1, id);
					ps.setString(2,email1);
					ps.setString(3,email2);
					ps.execute();
					result=true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;

	}
	public boolean deleteFreinds(int feindsID, Connection conn) {

		boolean result = false;
		PreparedStatement ps = null;

		try {
					ps = (PreparedStatement) conn.prepareStatement(deleteFreinds_sql);
					ps.setInt(1,feindsID);
					ps.execute();
					result=true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;

	}
	
	public boolean deletefollows(int followsID, Connection conn) {

		boolean result = false;
		PreparedStatement ps = null;

		try {
					ps = (PreparedStatement) conn.prepareStatement(deleteFollows_sql);
					ps.setInt(1,followsID);
					ps.execute();
					result=true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;

	}
	public boolean deleteFreindRequests(int requestID, Connection conn) {

		boolean result = false;
		PreparedStatement ps = null;

		try {
					ps = (PreparedStatement) conn.prepareStatement(deleteFreindRequest_sql);
					ps.setInt(1,requestID);
					ps.execute();
					result=true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;

	}
	
	public HashSet<User> getAllUsers(Connection conn) {
		HashSet<User> users=new HashSet<User>();
		User user=null;
		 ResultSet rs1 = null;
			PreparedStatement ps1 = null;
			try {
		    ps1 = conn.prepareStatement(selectUser_sql);
		    if (ps1.execute()) {
				rs1 = ps1.getResultSet();
				while (rs1.next()) {
					String userEmail=rs1.getString(1);
					System.out.println(userEmail);
					String fullName=rs1.getString(2);	
					String password=rs1.getString(3);
					Date date=rs1.getDate(4);
					int phone=rs1.getInt(5);
					java.sql.Blob imageBlob = rs1.getBlob(6);
					byte[] photo = null;
					if (imageBlob != null) {
						photo = imageBlob.getBytes(1, (int) imageBlob.length());
					}
					String bio=rs1.getString(7);
					int followers=rs1.getInt(8);
					int following=rs1.getInt(9);
					user=new User(userEmail,fullName,password,date,phone,bio,photo);
					user.setNumOfFollowers(followers);
					user.setNumOfFollowing(following);
					users.add(user);
				}
			}
	}catch (SQLException e) {
		try {
			throw e;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	} catch (Throwable e) {
		e.printStackTrace();

	} finally {
		if (rs1 != null) {
			try {
				rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		if (ps1 != null) {
			try {
				ps1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
			return users;
}

/*	public HashSet<User> getAllfreinds(Connection conn,String userName) {
		HashSet<User> freinds=new HashSet<User>();
		 ResultSet rs1 = null;
			PreparedStatement ps1 = null;
			try {
		    ps1 = conn.prepareStatement(selectFreinds_sql);
		    ps1.setString(1, userName);
		    ps1.setString(2, userName);
		    if (ps1.execute()) {
				rs1 = ps1.getResultSet();
				if (rs1.next()) {
					String email=rs1.getString(1);
					String fullName=rs1.getString(2);
					String password=rs1.getString(3);
					Date date=rs1.getDate(4);
					int phone=rs1.getInt(5);
					java.sql.Blob imageBlob = rs1.getBlob(6);
					byte[] photo = null;
					if (imageBlob != null) {
						photo = imageBlob.getBytes(1, (int) imageBlob.length());
					}
					String bio=rs1.getString(7);
					int numOfFollowers=rs1.getInt(8);
					int numOfFollowing=rs1.getInt(9);	
					User user=new User(email,fullName,password,date,phone,bio,photo);
					user.setNumOfFollowers(numOfFollowers);
					user.setNumOfFollowing(numOfFollowing);
					freinds.add(user);
				}
	}
	} catch (SQLException e) {
		try {
			throw e;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	} catch (Throwable e) {
		e.printStackTrace();

	} finally {
		if (rs1 != null) {
			try {
				rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		if (ps1 != null) {
			try {
				ps1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	return freinds;
}


	public HashSet<User> getAllFollowers(Connection conn,String userName) {
		HashSet<User> followers=new HashSet<User>();
		 ResultSet rs1 = null;
			PreparedStatement ps1 = null;
			try {
		    ps1 = conn.prepareStatement(selectFollowers_sql);
		    ps1.setString(1, userName);
		    	
		    if (ps1.execute()) {
				rs1 = ps1.getResultSet();
				if (rs1.next()) {
					String fullName=rs1.getString(2);
					String password=rs1.getString(3);
					Date date=rs1.getDate(4);
					int phone=rs1.getInt(5);
					java.sql.Blob imageBlob = rs1.getBlob(6);
					byte[] photo = null;
					if (imageBlob != null) {
						photo = imageBlob.getBytes(1, (int) imageBlob.length());
					}
					String bio=rs1.getString(7);
					int numOfFollowers=rs1.getInt(8);
					int numOfFollowing=rs1.getInt(9);	
					User user=new User(userName,fullName,password,date,phone,bio,photo);
					user.setNumOfFollowers(numOfFollowers);
					user.setNumOfFollowing(numOfFollowing);
					followers.add(user);
				}
	}
	} catch (SQLException e) {
		try {
			throw e;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	} catch (Throwable e) {
		e.printStackTrace();

	} finally {
		if (rs1 != null) {
			try {
				rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		if (ps1 != null) {
			try {
				ps1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	return followers;
}

	public HashSet<User> getAllFollowing(Connection conn,String userName) {
		HashSet<User> followers=new HashSet<User>();
		 ResultSet rs1 = null;
			PreparedStatement ps1 = null;
			try {
		    ps1 = conn.prepareStatement(selectFollowing_sql);
		    ps1.setString(1, userName);
		    	
		    if (ps1.execute()) {
				rs1 = ps1.getResultSet();
				if (rs1.next()) {
					String fullName=rs1.getString(2);
					String password=rs1.getString(3);
					Date date=rs1.getDate(4);
					int phone=rs1.getInt(5);
					java.sql.Blob imageBlob = rs1.getBlob(6);
					byte[] photo = null;
					if (imageBlob != null) {
						photo = imageBlob.getBytes(1, (int) imageBlob.length());
					}
					String bio=rs1.getString(7);
					int numOfFollowers=rs1.getInt(8);
					int numOfFollowing=rs1.getInt(9);	
					User user=new User(userName,fullName,password,date,phone,bio,photo);
					user.setNumOfFollowers(numOfFollowers);
					user.setNumOfFollowing(numOfFollowing);
					followers.add(user);
				}
	}
	} catch (SQLException e) {
		try {
			throw e;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	} catch (Throwable e) {
		e.printStackTrace();

	} finally {
		if (rs1 != null) {
			try {
				rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		if (ps1 != null) {
			try {
				ps1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	return followers;
}

	public HashSet<User> getAllfreindRequested(Connection conn,String userName) {
		HashSet<User> freindRequests=new HashSet<User>();
		 ResultSet rs1 = null;
			PreparedStatement ps1 = null;
			try {
		    ps1 = conn.prepareStatement(selectFreindRequested_sql);
		    ps1.setString(1, userName);
		    if (ps1.execute()) {
				rs1 = ps1.getResultSet();
				if (rs1.next()) {
					String fullName=rs1.getString(2);
					String password=rs1.getString(3);
					Date date=rs1.getDate(4);
					int phone=rs1.getInt(5);
					java.sql.Blob imageBlob = rs1.getBlob(6);
					byte[] photo = null;
					if (imageBlob != null) {
						photo = imageBlob.getBytes(1, (int) imageBlob.length());
					}
					String bio=rs1.getString(7);
					int numOfFollowers=rs1.getInt(8);
					int numOfFollowing=rs1.getInt(9);	
					User user=new User(userName,fullName,password,date,phone,bio,photo);
					user.setNumOfFollowers(numOfFollowers);
					user.setNumOfFollowing(numOfFollowing);
					freindRequests.add(user);
				}
	}
	} catch (SQLException e) {
		try {
			throw e;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	} catch (Throwable e) {
		e.printStackTrace();

	} finally {
		if (rs1 != null) {
			try {
				rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		if (ps1 != null) {
			try {
				ps1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	return freindRequests;
}

	public HashSet<User> getAllfreindRequests(Connection conn,String userName) {
		HashSet<User> freindRequests=new HashSet<User>();
		 ResultSet rs1 = null;
			PreparedStatement ps1 = null;
			try {
		    ps1 = conn.prepareStatement(selectFreindRequests_sql);
		    ps1.setString(1, userName);
		    if (ps1.execute()) {
				rs1 = ps1.getResultSet();
				if (rs1.next()) {
					String fullName=rs1.getString(2);
					String password=rs1.getString(3);
					Date date=rs1.getDate(4);
					int phone=rs1.getInt(5);
					java.sql.Blob imageBlob = rs1.getBlob(6);
					byte[] photo = null;
					if (imageBlob != null) {
						photo = imageBlob.getBytes(1, (int) imageBlob.length());
					}
					String bio=rs1.getString(7);
					int numOfFollowers=rs1.getInt(8);
					int numOfFollowing=rs1.getInt(9);	
					User user=new User(userName,fullName,password,date,phone,bio,photo);
					user.setNumOfFollowers(numOfFollowers);
					user.setNumOfFollowing(numOfFollowing);
					freindRequests.add(user);
				}
	}
	} catch (SQLException e) {
		try {
			throw e;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	} catch (Throwable e) {
		e.printStackTrace();

	} finally {
		if (rs1 != null) {
			try {
				rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		if (ps1 != null) {
			try {
				ps1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	return freindRequests;
}
	
	*/
	public HashSet<Follow> getFollows( Connection conn) {
		HashSet<Follow> follows=new HashSet<Follow>();
		Follow follow=null;
		 ResultSet rs1 = null;
			PreparedStatement ps1 = null;
			try {
		    ps1 = conn.prepareStatement(selectFollows_sql);
		    if (ps1.execute()) {
				rs1 = ps1.getResultSet();
				while (rs1.next()) {
					int id=rs1.getInt(1);
					String userMail1=rs1.getString(2);
					String userMail2=rs1.getString(3);
					
					follow=new Follow(id,userMail1,userMail2);
					follows.add(follow);
				}
			}
	}catch (SQLException e) {
		try {
			throw e;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	} catch (Throwable e) {
		e.printStackTrace();

	} finally {
		if (rs1 != null) {
			try {
				rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		if (ps1 != null) {
			try {
				ps1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
			return follows;
	}
	public HashSet<Friend> getFriends( Connection conn) {
		HashSet<Friend> friends=new HashSet<Friend>();
		Friend friend=null;
		 ResultSet rs1 = null;
			PreparedStatement ps1 = null;
			try {
		    ps1 = conn.prepareStatement(selectFriends_sql);
		    if (ps1.execute()) {
				rs1 = ps1.getResultSet();
				while (rs1.next()) {
					int id=rs1.getInt(1);
					String userMail1=rs1.getString(2);
					String userMail2=rs1.getString(3);
					
					friend=new Friend(id,userMail1,userMail2);
					friends.add(friend);
				}
			}
	}catch (SQLException e) {
		try {
			throw e;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	} catch (Throwable e) {
		e.printStackTrace();

	} finally {
		if (rs1 != null) {
			try {
				rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		if (ps1 != null) {
			try {
				ps1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
			return friends;
	}
	public HashSet<FriendRequest> getFriendRequest( Connection conn) {
		HashSet<FriendRequest> friendRequests=new HashSet<FriendRequest>();
		FriendRequest friendRequest=null;
		 ResultSet rs1 = null;
			PreparedStatement ps1 = null;
			try {
		    ps1 = conn.prepareStatement(selectFriendRequests_sql);
		    if (ps1.execute()) {
				rs1 = ps1.getResultSet();
				while (rs1.next()) {
					int friendshipID=rs1.getInt(1);
					String userMail1=rs1.getString(2);
					String userMail2=rs1.getString(3);
					int friendshipStatus=rs1.getInt(4);
					
					friendRequest=new FriendRequest(friendshipID,userMail1,userMail2,friendshipStatus);
					friendRequests.add(friendRequest);
				}
			}
	}catch (SQLException e) {
		try {
			throw e;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	} catch (Throwable e) {
		e.printStackTrace();

	} finally {
		if (rs1 != null) {
			try {
				rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		if (ps1 != null) {
			try {
				ps1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
			return friendRequests;
	}
	
}
