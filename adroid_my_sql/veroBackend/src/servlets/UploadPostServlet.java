package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.management.Descriptor;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import databaseOperations.ConnPool;
import databaseOperations.PostResProvider;
import objects.Post;
public class UploadPostServlet extends HttpServlet {

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
	private static final long serialVersionUID = 1L;
	
	
	private static final String RESOURCE_FAIL_TAG = "{\"result_code\":0}";
	private static final String RESOURCE_SUCCESS_TAG = "{\"result_code\":1}";
	

	//public static final int DB_RETRY_TIMES=5;

	
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Commons file upload classes are specifically instantiated
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(500000);

		ServletFileUpload upload = new ServletFileUpload(factory);
		ServletOutputStream out = null;
		
		//int retry = DB_RETRY_TIMES;
		Connection conn = null;
		
		String PostId = null;
		String email = null;
		String date=null;
		String postType = null;
		String link = null;
		String postDescription = null;
		String commentsNum=null;
		String likesNum=null;
		String fileName = null;
		
		byte [] photo= null;
		String respPage = RESOURCE_FAIL_TAG;
		try {
			
			System.out.println("======= upload PostInfo with image Servlet =======");
			// Parse the incoming HTTP request
			// Commons takes over incoming request at this point
			// Get an iterator for all the data that was sent
			List<FileItem> items = upload.parseRequest(req);
			Iterator<FileItem> iter = items.iterator();

			// Set a response content type
			resp.setContentType("text/html");

			// Setup the output stream for the return XML data
			out = resp.getOutputStream();

			// Iterate through the incoming request data
			while (iter.hasNext()) {
				// Get the current item in the iteration
				FileItem item = (FileItem) iter.next();

				// If the current item is an HTML form field
				if (item.isFormField()) {
					// If the current item is file data
					
					// If the current item is file data
					String fieldname = item.getFieldName();
					String fieldvalue = item.getString();

					System.out.println(fieldname + "=" + fieldvalue);

				
					if (fieldname.equals(POST_ID)) {
						PostId =fieldvalue;
					} else if (fieldname.equals(POST_COMMENTS_NUM)) {
						commentsNum = fieldvalue;
					} else if (fieldname.equals(POST_DATE)) {
						date = fieldvalue;
					} else if (fieldname.equals(POST_Description)) {
						postDescription = fieldvalue;
					} else if (fieldname.equals(POST_LIKES_NUM)) {
						likesNum = fieldvalue;
					} else if (fieldname.equals(POST_link)) {
						link = fieldvalue;
					} else if (fieldname.equals(POST_USERNAME)) {
						email = fieldvalue;
					}




					
					
				} else {

					//fileName = item.getName();
				//	photo=item.get();

				}
			}
			
			//while (retry > 0) {

				try {

					conn = ConnPool.getInstance().getConnection();
					PostResProvider postResProvider = new PostResProvider();
					Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
					Post post = new Post(Integer.parseInt(PostId), email,date1, link, photo, postDescription
							,Integer.parseInt(likesNum),Integer.parseInt(commentsNum));
					if(postResProvider.insertPost(post, conn)) {
						respPage = RESOURCE_SUCCESS_TAG;
					}
					
					//retry = 0;

				} catch (Throwable t) {
					t.printStackTrace();
					//retry = 0;
				} finally {
					if (conn != null) {
						ConnPool.getInstance().returnConnection(conn);
					}
				}

		//	}
			
			out.println(respPage);
			out.close();
			
	
		} catch (FileUploadException fue) {
			fue.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

