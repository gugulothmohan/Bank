<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%
    List<Map<String, String>> pendingUsers = (List<Map<String, String>>) request.getAttribute("pendingUsers");
%>

<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
        }
        .container {
            max-width: 700px;
            margin: 30px auto;
            text-align: center;
        }
        .card {
            background-color: white;
            padding: 20px;
            margin-bottom: 15px;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            text-align: left;
        }
        .user-info {
            margin-bottom: 10px;
        }
        .user-info strong {
            display: inline-block;
            width: 100px;
        }
        .actions {
            text-align: right;
        }
        .actions input[type="submit"] {
            padding: 6px 12px;
            margin-left: 8px;
            border: none;
            border-radius: 6px;
            background-color: #007bff;
            color: white;
            cursor: pointer;
        }
        .actions input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #007bff;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Pending User Approvals</h2>

    <%
        for (Map<String, String> user : pendingUsers) {
    %>
    <div class="card">
        <div class="user-info"><strong>Name:</strong> <%= user.get("firstname") %></div>
        <div class="user-info"><strong>Email:</strong> <%= user.get("email") %></div>
        <form class="actions" action="approve" method="post">
            <input type="hidden" name="id" value="<%= user.get("id") %>" />
            <input type="submit" name="action" value="approve" />
            <input type="submit" name="action" value="reject" />
        </form>
    </div>
    <%
        }
    %>

    <a  href="Homepage.html">Back to Login</a>
</div>
</body>
</html>
