package databaseOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.crypto.Data;


import objects.Post;
import objects.PostReply;
import objects.User;

public class PostResProvider {
	private static final String insertPost_sql="INSERT INTO post (postID,userName,date,link"
		+",photo,postDescription,likesNum,commentsNum) VALUES(?,?,?,?,?,?,?,?,?);";
	private static final String deletePost_sql="DELETE FROM Post Where postID=?;";
	private static final String selectAllPosts_sql="SELECT * FROM  post;";
//	private static final String selectUserPosts_sql="SELECT * FROM  post where userName=?;";
	private static final String updatePost_sql = "UPDATE Post SET link=?,photo=?,location=?,postDescription=?"
			+ " WHERE postID=?;";
	private static final String select_img_sql = "SELECT photo FROM  Post WHERE postID=?;";
	public boolean deletePost(Connection conn,int postID) {

		boolean result = false;
		PreparedStatement ps = null;

		try {
					ps = (PreparedStatement) conn.prepareStatement(deletePost_sql);
					ps.setInt(1, postID);
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
	public byte[] getImage(int postId, Connection conn)
			throws SQLException {

		byte[] result = null;

		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
		
			ps = conn.prepareStatement(select_img_sql);

			ps.setInt(1, postId);

			rs = ps.executeQuery();

			while (rs.next()) {

				java.sql.Blob imageBlob = rs.getBlob(6);
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
	
	public boolean updatePost(Connection conn,int postID ,String link,byte[] photo,String postDescription) {

		boolean result = false;
		PreparedStatement ps = null;

		try {
					ps = (PreparedStatement) conn.prepareStatement(updatePost_sql);
					ps.setInt(5, postID);
					ps.setString(1, link);
					if (photo != null) {
						InputStream is = new ByteArrayInputStream(photo);
						ps.setBlob(2, is);

					} else {

						ps.setNull(2, Types.BLOB);
					}
					ps.setString(4, postDescription);
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
	
	/*public List<Post> getAllUserPosts(Connection conn,String userName1) throws SQLException {

		List<Post> results = new ArrayList<Post>();

		ResultSet rs = null;
		PreparedStatement ps = null;
		try {

			ps = conn.prepareStatement(selectUserPosts_sql);
			ps.setString(1, userName1);
	
			rs = ps.executeQuery();

			while (rs.next()) {
				int postID=rs.getInt(1);
				String userName=rs.getString(2);
			    Date date=rs.getDate(3);
			    String postType=rs.getNString(4);
			    String link=rs.getString(5);
			    java.sql.Blob imageBlob = rs.getBlob(6);
				byte[] photo = null;
				if (imageBlob != null) {
					photo = imageBlob.getBytes(1, (int) imageBlob.length());
				}
			    String postDescription=rs.getString(8);
			    int likesNum=rs.getInt(9);
			    int commentsNum=rs.getInt(10);
			    Post post = new Post(postID,userName,postType,link,photo,postDescription);
				post.setDate(date);
				post.setLikesNum(likesNum);
				post.setCommentsNum(commentsNum);
				results.add(post);
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
		return results;	
	}
	*/
	public boolean insertPost(Post obj, Connection conn) {

		boolean result = false;
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement ps = null;
		PreparedStatement stt = null;

		try {

			int postID = obj.getPostID();
			String userName = obj.getUserName();
			Date date = obj.getDate();
			String link = obj.getLink();
			byte[] photo = obj.getPhoto();
			String postDescription = obj.getPostDescription();
			int likesNum = obj.getLikesNum();
			int commentsNum = obj.getCommentsNum();
			
					ps = (PreparedStatement) conn.prepareStatement(insertPost_sql);
					ps.setInt(1,postID);
					ps.setString(2, userName);
					ps.setDate(3, (java.sql.Date) date);
					ps.setString(5, link);
					if (photo != null) {
						InputStream is = new ByteArrayInputStream(photo);
						ps.setBlob(6, is);

					} else {

						ps.setNull(6, Types.BLOB);
					}
					ps.setString(8, postDescription);
					ps.setInt(9, likesNum);
					ps.setInt(10,commentsNum);
					
					ps.execute();
					result=true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			if (rs1 != null) {
				try {
					rs1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

			if (stt != null) {
				try {
					stt.close();
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
	public List<Post> getAllPosts(Connection conn) throws SQLException {

		List<Post> results = new ArrayList<Post>();

		ResultSet rs = null;
		PreparedStatement ps = null;
		try {

			ps = conn.prepareStatement(selectAllPosts_sql);

	
			rs = ps.executeQuery();

			while (rs.next()) {
				int postID=rs.getInt(1);
				String userName=rs.getString(2);
			    Date date=rs.getDate(3);
			    String link=rs.getString(4);
			    java.sql.Blob imageBlob = rs.getBlob(5);
				byte[] photo = null;
				if (imageBlob != null) {
					photo = imageBlob.getBytes(1, (int) imageBlob.length());
				}
			    String postDescription=rs.getString(6);
			    int likesNum=rs.getInt(7);
			    int commentsNum=rs.getInt(8);
		//	    Post post = new Post(postID,userName,date,link,photo,postDescription,likesNum,commentsNum);
			//	results.add(post);
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
		return results;	
	}
}
