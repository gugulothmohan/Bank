package alladi;

import java.io.IOException;
import java.util.*;
import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/approve")
public class AdminApprovalServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, String>> pendingUsers = new ArrayList<>();
        String query = "SELECT id, firstname, email FROM reg WHERE status = 'pending' AND role = 'user'";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pinky", "root", "Mohan123");
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    Map<String, String> user = new HashMap<>();
                    user.put("id", rs.getString("id"));
                    user.put("firstname", rs.getString("firstname"));
                    user.put("email", rs.getString("email"));
                    pendingUsers.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("pendingUsers", pendingUsers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_dashboard.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");
        String action = request.getParameter("action");

        if ("approve".equals(action)) {
            updateUserStatus(userId, "approved");
        } else if ("reject".equals(action)) {
            updateUserStatus(userId, "rejected");
        }

        response.sendRedirect(request.getContextPath() + "/approve");
    }

    private void updateUserStatus(String userId, String status) {
        String query = "UPDATE reg SET status = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pinky", "root", "Mohan123");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setString(2, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
