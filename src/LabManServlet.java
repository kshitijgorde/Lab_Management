import java.io.*;
import javax.servlet.*; //for request dispatcher///
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

public class LabManServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LabManServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		//out.println("<link rel="stylesheet" type="text/css" href=styles.css>");
		out.println("  <HEAD><TITLE>Admin Records!</TITLE>");
		out.println("<style = 'text/css'>{background-color:#696969}</style>");
		out.println("</HEAD>");
		out.println("  <BODY>");
		//out.print("    This is ");
		//out.print(this.getClass());
		//out.println(", using the GET method");
		
		out.println("<div id='wrapper'>");
		Connection con = null;
		Statement stmt = null;
		try{
			
			//Register JDBC driver......
			Class.forName("com.mysql.jdbc.Driver");
			//open up a connection
			con = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
			System.out.println("\n\nConnection to Database established......Retrieve Record in Progress");
			
			out.print("<table width=50% border=1>");
			out.print("<caption>Complaint Database!</caption>");
			stmt = con.createStatement();
			String sqlquery;
			sqlquery = "select * from Complaints";
			ResultSet rs = stmt.executeQuery(sqlquery);
			//Extract Data from result set...
			//out.println("Name\t\t"+"Roll no\t\t"+"Complaint\n\n");
			out.print("<tr>");
			out.print("<th>");
			out.print("Complaint_No");
			out.print("</th>");
			out.print("<th>");
			out.print("Name");
			out.print("</th>");
			out.print("<th>");
			out.print("Roll_no");
			out.print("</th>");
			out.print("<th>");
			out.print("CompID");
			out.print("</th>");
			out.print("<th>");
			out.print("Complaint");
			out.print("</th>");
			
			out.print("</tr>");
			//out.print("<tr>");
			while(rs.next()){
				String name = rs.getString("Name");
				int rno = rs.getInt("RollNo");
				int comp_no = rs.getInt("Comp_no");
				String comp = rs.getString("Complaint");
				int compl_no = rs.getInt("ComplaintNo");
				//Display values...
				out.print("<tr>");
				out.print("<td>");
				out.print(compl_no);
				out.print("</td>");
				out.print("<td>");
				out.print(name);
				out.print("</td>");
				out.print("<td>");
				out.print(rno );
				out.print("</td>");
				out.print("<td>");
				out.print(comp_no);
				out.print("</td>");
				out.print("<td>");
				out.print(comp);
				out.print("</td>");
				out.print("</tr>");
			}
			
			rs.close();
			stmt.close();
			con.close();
			
		}catch(Exception e){System.out.print(e);}
		out.println("</div>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML>");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>Submission!</TITLE></HEAD>");
		out.println("  <BODY>");
		
		
		
		if(request.getQueryString().equals("stud")){
		String name = request.getParameter("name");
		int roll_no = Integer.parseInt(request.getParameter("Roll_no"));
		//int roll_no = Integer.pa
		int compNo = Integer.parseInt(request.getParameter("CompNo"));
		String complaint = request.getParameter("complaints");
		System.out.println("parameters are"+name+roll_no+complaint+compNo);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
		 Connection con =DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
		 	System.out.println("\n\nConnected to database");
		 PreparedStatement ps = (PreparedStatement)con.prepareStatement("insert into complaints values(?,?,?,?,?)");
		
		 ps.setString(1,name);
		 ps.setInt(2,roll_no);
		 
		 ps.setInt(3,compNo);
		 ps.setString(4,complaint);
		 ps.setString(5,"0");
		 int i = ps.executeUpdate();
		 if(i>0){
			 out.println("Your Complaint has been successfully Submitted!");
			 out.println("<a href='http://ironman:8080/Lab_Man/' ><input type='button' value='Go back' > </a>");
		 }
		 else{
			 out.println("There was some error!");
		 }
		}catch(Exception e){System.out.print("Error in Submitting complaint...");
		 out.println("<a href='http://ironman:8080/Lab_Man/' ><input type='button' value='Go back' > /a>");
		}
		out.println("  </BODY>");
		out.println("</HTML>");
		}
		
		
		else if(request.getQueryString().equals("auth")){
			String uname = request.getParameter("uname");
			String pass = request.getParameter("pass");
			
				if(uname.compareTo("ksgorde")==0 && pass.compareTo("1234")==0){
					//valid user...
					response.sendRedirect("http://ironman:8080/Lab_Man/admin.html");
				}
				
				else{
					out.println("Authentication Error!");
				}
		}
	
	
		else if(request.getQueryString().equals("resolve")){
			//resolve complaint from here.....
			int complaint_no = Integer.parseInt(request.getParameter("delcomp"));
			try{
			Class.forName("com.mysql.jdbc.Driver");
			
			 Connection con =DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
			 System.out.println("\n\nConnected to database for deletion");
			 PreparedStatement ps = (PreparedStatement)con.prepareStatement("delete from complaints where ComplaintNo=?");
			
			 ps.setInt(1, complaint_no);
			 int i = ps.executeUpdate();
			 if(i>0){
				 out.println("The Complaint has been successfully Deleted...!");
				 out.println("<a href='http://ironman:8080/Lab_Man/' ><input type='button' value='Go back' > </a>");
			 }
			
			}catch(Exception e){out.println(e+"\nError in executing query...!");
			out.println("<a href='http://ironman:8080/Lab_Man/' ><input type='button' value='Go back' > </a>");
			}
			
		}
	
	
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
