package alladi;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/newPasswords")
public class NewPassword extends HttpServlet 
{
		private static final long serialVersionUID = 1L;

		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException
		    {
	        PrintWriter out=response.getWriter();
			HttpSession session = request.getSession();
			String email = request.getParameter("email");
			String newPassword = request.getParameter("password");
			String conformpassword = request.getParameter("conPassword");
			
			
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pinky","root","Mohan123");
					PreparedStatement pst = con.prepareStatement("update reg set password = ?,confirmpassword=? where email = ?");
					pst.setString(1, newPassword);
					pst.setString(2, conformpassword);
					pst.setString(3, email);

					int rowCount = pst.executeUpdate();
					if (rowCount > 0)
					{
						RequestDispatcher rd=request.getRequestDispatcher("updatePassword.jsp");	
						rd.forward(request, response);
					} 
					else
					{
						
						RequestDispatcher rd=request.getRequestDispatcher("newPassword.html");	
						rd.forward(request, response);
						
					}
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}


