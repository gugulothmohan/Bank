package alladi;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/pinky";
	private static final String userName = "root";
	private static final String password = "Mohan123";
	private Connection con = null;
	private Statement stmt = null;

	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect("index.html");
			return;
		}

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><style>");
		out.println(
				".card { border: 1px solid #ccc; border-radius: 10px; padding: 15px; margin: 15px; background-color: #f9f9f9; box-shadow: 2px 2px 8px #aaa; }");
		out.println(".card h3 { margin-top: 0; }");
		out.println(".logout { margin: 20px; }");
		out.println("</style></head><body>");

		out.println("<h1>Transaction Records</h1>");

		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from transactions");
			while (rs.next()) {
				out.println("<div class='card'>");
				out.println("<h3>Transaction ID: " + rs.getInt("transaction_id") + "</h3>");
				out.println("<p><strong>Receiver Account No:</strong> " + rs.getInt("receiver_id") + "</p>");
				out.println("<p><strong>Amount:</strong> ₹" + rs.getDouble("amount") + "</p>");
				out.println("<p><strong>Transaction Time:</strong> " + rs.getTimestamp("transaction_time") + "</p>");
				out.println("</div>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		out.println("</body></html>");
	}
}
