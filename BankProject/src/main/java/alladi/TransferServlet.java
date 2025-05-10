package alladi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/transfer")

public class TransferServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int senderId = Integer.parseInt(request.getParameter("sender_id"));
		int receiverId = Integer.parseInt(request.getParameter("receiver_id"));
		double amount = Double.parseDouble(request.getParameter("amount"));

		Connection conn = null;
		response.setContentType("text/html");

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pinky","root","Mohan123");
			conn.setAutoCommit(false);

			PreparedStatement check = conn.prepareStatement("SELECT balance FROM account WHERE accountno = ?");
			check.setInt(1, senderId);
			ResultSet rs = check.executeQuery();

			if (!rs.next() || rs.getDouble("balance") < amount) {
				response.getWriter().println("Insufficient funds or invalid account.");
				return;
			}

			PreparedStatement beneficiaryCheck = conn.prepareStatement(
				"SELECT * FROM beneficiaries WHERE accountno = ? AND beneficiary_account_no = ?");
			beneficiaryCheck.setInt(1, senderId);
			beneficiaryCheck.setInt(2, receiverId);
			ResultSet beneficiaryRs = beneficiaryCheck.executeQuery();

			if (!beneficiaryRs.next()) {
				response.getWriter().println("Transfer failed: The beneficiary is not registered.");
				return;
			}

			PreparedStatement deduct = conn.prepareStatement(
				"UPDATE account SET balance = balance - ? WHERE accountno = ?");
			deduct.setDouble(1, amount);
			deduct.setInt(2, senderId);
			deduct.executeUpdate();

			PreparedStatement credit = conn.prepareStatement(
				"UPDATE account SET balance = balance + ? WHERE accountno = ?");
			credit.setDouble(1, amount);
			credit.setInt(2, receiverId);
			credit.executeUpdate();

			PreparedStatement log = conn.prepareStatement(
				"INSERT INTO transactions (sender_id, receiver_id, amount) VALUES (?, ?, ?)");
			log.setInt(1, senderId);
			log.setInt(2, receiverId);
			log.setDouble(3, amount);
			log.executeUpdate();

			conn.commit();
			response.getWriter().println("<html><body>");
			response.getWriter().println("<h1>Transfer successful!</h1>");
			response.getWriter().println("<a href='demoServlet'>Go to Home Page (demoServlet)</a>");
			response.getWriter().println("</body></html>");

		} catch (Exception e) {
			try {
				if (conn != null) conn.rollback();
			} catch (Exception rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
			response.getWriter().println("Transfer failed due to a server error.");
		}
	}
}
