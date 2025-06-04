package alladi;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/pinky";
	private static final String userName = "root";
	private static final String password = "Mohan123";

	private Connection con = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			String query = "SELECT transaction_id, sender_id, receiver_id, amount, transaction_time FROM transactions";

			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			out.println("<!DOCTYPE html>");
			out.println("<html><head><title>Transaction Records</title>");
			out.println("<style>");
			out.println("body { font-family: Arial; background: #f0f2f5; padding: 20px; }");
			out.println(
					".card { background: #fff; padding: 15px; margin: 15px auto; width: 60%; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }");
			out.println(".card p { margin: 8px 0; font-size: 16px; }");
			out.println("h1 { text-align: center; }");
			out.println("</style>");
			out.println("</head><body>");

			out.println("<h1>Transaction Records</h1>");

			boolean found = false;
			while (rs.next()) {
				found = true;
				int id = rs.getInt("transaction_id");
				int senderId = rs.getInt("sender_id");
				int receiverId = rs.getInt("receiver_id");
				double amount = rs.getDouble("amount");
				Timestamp time = rs.getTimestamp("transaction_time");

				out.println("<div class='card'>");
				out.println("<p><strong>Transaction ID:</strong> " + id + "</p>");
				out.println("<p><strong>Sender ID:</strong> " + senderId + "</p>");
				out.println("<p><strong>Receiver ID:</strong> " + receiverId + "</p>");
				out.println("<p><strong>Amount:</strong> ₹" + amount + "</p>");
				out.println("<p><strong>Date:</strong> " + time + "</p>");
				out.println("</div>");
			}

			if (!found) {
				out.println("<p style='text-align:center;'>No transactions found.</p>");
			}

			out.println("</body></html>");

			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			out.println("<p style='color:red;'>Error fetching transaction data: " + e.getMessage() + "</p>");
		}
	}

	public void destroy() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
