package alladi;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/demoServlet")
public class DemoServlet extends HttpServlet {
    
    PreparedStatement stmt, stmt1;
    ResultSet rs, rs1;
    String nm;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("admin");

        String query = "SELECT firstname FROM reg WHERE email = ?";
        String query1 = "SELECT balance FROM account WHERE firstname = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pinky", "root", "Mohan123");

            stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            rs = stmt.executeQuery();

            out.println("<html><head><title>Dashboard</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; padding: 20px; background-color: #f4f4f4; }");
            out.println("h1, h2 { color: #333; }");
            out.println("ul { list-style-type: none; padding-left: 0; }");
            out.println("li { margin: 10px 0; }");
            out.println("a { text-decoration: none; color: #0066cc; font-weight: bold; }");
            out.println("a:hover { color: #004499; }");
     
            out.println(".logout-btn {");
            out.println("  padding: 10px 20px;");
            out.println("  background-color: #e74c3c;");
            out.println("  color: white;");
            out.println("  border: none;");
            out.println("  border-radius: 5px;");
            out.println("  font-size: 16px;");
            out.println("  cursor: pointer;");
            out.println("  margin: 20px 0;");
            out.println("  transition: background-color 0.3s;");
            out.println("}");
            out.println(".logout-btn:hover { background-color: #c0392b; }");
            out.println("</style>");
            out.println("</head><body>");
            if (rs.next()) {
                nm = rs.getString("firstname");
                out.println("<h1>Welcome, " + nm + "!</h1>");
            } else {
                out.println("<h1>User not found.</h1>");
            }

            stmt1 = conn.prepareStatement(query1);
            stmt1.setString(1, nm);
            rs1 = stmt1.executeQuery();
            if (rs1.next()) {
                Double amt = rs1.getDouble("balance");
                out.println("<h2>Available Balance: ₹" + amt + "</h2>");
            } else {
                out.println("<h2>Balance not available.</h2>");
            }

            out.println("<hr style='margin-top: 30px;'>");
            out.println("<h3>Actions:</h3>");
            out.println("<ul>");
            out.println("<li><a href='add.html'>➕ Add a Beneficiary</a></li>");
            out.println("<li><a href='tranfer.html'>💸 Transfer</a></li>");
            out.println("<li><a href='transaction'>📜 Transactions</a></li>");
            out.println("</ul>");
            out.println("<a href='Homepage.html' class='logout-btn'>Logout</a>");
            out.println("</body></html>");
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
    }
}