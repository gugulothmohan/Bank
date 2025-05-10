package alladi;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/maharshi")
@MultipartConfig
public class Insertservlet extends HttpServlet {

    private static final String Driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/pinky";
    private static final String username = "root";
    private static final String password = "Mohan123";

    private Connection con;
    private PreparedStatement ps;

    @Override
    public void init() throws ServletException {
        try {
         
            Class.forName(Driver);
         
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new ServletException("Error initializing servlet: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String num = request.getParameter("id");
        int id = Integer.parseInt(num);
        String fname = request.getParameter("Fname");
        String lname = request.getParameter("Lname");
        String email = request.getParameter("mail");
        String pa = request.getParameter("password");
        String conpass = request.getParameter("conpassword");
        String mn = request.getParameter("mobileno");
        String add = request.getParameter("ad");
        String photo = request.getParameter("photo");
        Part file = request.getPart("photo"); 
        String fileName = getFileName(file);
        InputStream fileContent = file.getInputStream();
         String role = request.getParameter("role");

            try
            {
            ps = con.prepareStatement("INSERT INTO reg VALUES(?,?,?,?,?,?,?,?,?,?,status)");
            ps.setInt(1, id);
            ps.setString(2, fname);
            ps.setString(3, lname);
            ps.setString(4, email);
            ps.setString(5, pa);
            ps.setString(6, conpass);
            ps.setString(7, mn);
            ps.setString(8, add);
            ps.setBinaryStream(9, fileContent);
            ps.setString(10, role);
            int count = ps.executeUpdate();
            if (count > 0) {
                out.println("<h1>Registration successfully completed...</h1>");
                out.println("<a href='Admin_Login.jsp'> Back to Registration page </a>");
                out.println("<br><br>");
                out.println("<a href ='Homepage.html'>Login </a> ");
                
            } else {
                out.println("<h3>Error during registration.</h3>");
                out.println("<a href='reg.html'>Try Again</a>");
           
            }
            }
           
        catch(Exception e)
        {
        	System.out.println(e);
        }
    }
          private String getFileName(Part part) {
            for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
                if (contentDisposition.trim().startsWith("filename")) {
                    return contentDisposition.substring(contentDisposition.indexOf("=") + 2, contentDisposition.length() - 1);
                }
            }
            return null;
        }
          
    @Override
    public void destroy() {
        try {
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}