package alladi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/Benficery")
public class AddBeneficiaryServlet extends HttpServlet 
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    int accountId = Integer.parseInt(request.getParameter("num"));
	    int beneficiaryId = Integer.parseInt(request.getParameter("nums"));
	    String name = request.getParameter("nm");

	    response.setContentType("text/html");

	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pinky", "root", "Mohan123")) {
	        String sql = "INSERT INTO beneficiaries (accountno, beneficiary_account_no, name) VALUES (?, ?, ?)";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, accountId);
	        stmt.setInt(2, beneficiaryId);
	        stmt.setString(3, name);

	        int rows = stmt.executeUpdate();

	        response.getWriter().println("<html><head><style>");
	        response.getWriter().println("a.back-link { text-decoration: none; color: white; background-color: #4CAF50; padding: 10px 15px; border-radius: 5px; }");
	        response.getWriter().println("</style></head><body>");

	        if (rows > 0) {
	            response.getWriter().println("<h3>✅ Beneficiary added successfully!</h3>");
	        } else {
	            response.getWriter().println("<h3>❌ Error adding beneficiary.</h3>");
	        }

	        response.getWriter().println("<a href='demoServlet' class='back-link'>⬅ Back to Page</a>");

	        response.getWriter().println("</body></html>");

	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	}
}