package alladi;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String adminUsername = request.getParameter("email");
		String adminPassword = request.getParameter("password");

		String query = "SELECT * FROM reg WHERE email = ? AND password = ?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pinky", "root", "Mohan123");
			     PreparedStatement stmt = conn.prepareStatement(query)) {

				stmt.setString(1, adminUsername);
				stmt.setString(2, adminPassword);

				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						String role = rs.getString("role");
						String status = rs.getString("status");

						HttpSession session = request.getSession();
						session.setAttribute("admin", adminUsername);
						session.setAttribute("role", role);
						if ("admin".equalsIgnoreCase(role) && "pending".equalsIgnoreCase(status)) {
							String updateQuery = "UPDATE reg SET status = 'approved' WHERE email = ?";
							try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
								updateStmt.setString(1, adminUsername);
								updateStmt.executeUpdate();
							}
						}
						if ("admin".equalsIgnoreCase(role)) {
							response.sendRedirect(request.getContextPath() + "/approve");
						} else if ("user".equalsIgnoreCase(role)&&adminUsername.equals(adminUsername)&&adminPassword.equals(adminPassword)&&"approved".equals(status)) 
				                {
				                    response.sendRedirect(request.getContextPath() + "/demoServlet");

						}
					} else {
						request.setAttribute("errorMessage", "Invalid username or password.");
						RequestDispatcher dispatcher = request.getRequestDispatcher("Admin_login.jsp");
						dispatcher.forward(request, response);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
