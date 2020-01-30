package databaseOperations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import objects.Post;
import objects.PostReply;
import objects.User;

public class PostReplyResProvider {
//	private static final String selectAllpostReply_sql="SELECT * FROM  postReply WHERE postID=?;";
	private static final String insertPostReply_sql="INSERT INTO postReply (postID,userReply,like,comment)"
			+ " VALUES(?,?,?,?);";
	private static final String deletePostReply_sql="DELETE FROM postReply Where replyID=?;";
	private static final String selectAllReplies_sql="SELECT * FROM  postReply;";
	
	public HashSet<PostReply> getAllReplies(Connection conn){
		HashSet<PostReply> replies=new HashSet<PostReply>();
	    ResultSet rs1 = null;
		PreparedStatement ps1 = null;
		try {
	    ps1 = conn.prepareStatement(selectAllReplies_sql);
	    if (ps1.execute()) {
			rs1 = ps1.getResultSet();
			if (rs1.next()) {
				int replyID=rs1.getInt(1);
				String userReply=rs1.getString(2);
				int postID=rs1.getInt(3);
				boolean like=rs1.getBoolean(4);
				String comment=rs1.getString(5);
				PostReply postR=new PostReply(replyID,userReply,postID,like,comment);
				replies.add(postR);
			}
				
	    }
		}
		catch (SQLException e) {
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
		return replies;
	}
	
	public boolean deletePostReply(Connection conn,int replyID) {

		boolean result = false;
		PreparedStatement ps = null;

		try {
					ps = (PreparedStatement) conn.prepareStatement(deletePostReply_sql);
					ps.setInt(1, replyID);
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
	
	public boolean insertPostReply(PostReply obj, Connection conn) {

		boolean result = false;
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement ps = null;
		PreparedStatement stt = null;

		try {

			int postID = obj.getPostID();
			String userReply = obj.getUserReply();
			boolean like=obj.isLike();
			String comment = obj.getComment();
					ps = (PreparedStatement) conn.prepareStatement(insertPostReply_sql);
					ps.setInt(1,postID);
					ps.setString(2,userReply);
					ps.setBoolean(3,like);
					ps.setString(4, comment);
					
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
	
/*	public HashSet<PostReply> getAllReplies(Connection conn,int postID){
		HashSet<PostReply> replies=new HashSet<PostReply>();
		// get all post relpies for this post
	    ResultSet rs1 = null;
		PreparedStatement ps1 = null;
		try {
	    ps1 = conn.prepareStatement(selectAllpostReply_sql);
	    ps1.setInt(1,postID);
	    if (ps1.execute()) {
			rs1 = ps1.getResultSet();
			if (rs1.next()) {
				int ReplyID=rs1.getInt(1);
				String userReply=rs1.getString(3);
				boolean like=rs1.getBoolean(4);
				String comment=rs1.getString(5);
				PostReply postR=new PostReply(ReplyID,userReply,postID,like,comment);
				replies.add(postR);
			}
				
	    }
		}
		catch (SQLException e) {
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
		return replies;
	}
	*/
}