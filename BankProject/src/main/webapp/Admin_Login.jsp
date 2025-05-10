<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>admin Login</title>
    
    <script type="text/javascript">
      function display(){
    	  var email= document.hello.email.value;
    	  var password=document.hello.password.value;
    	  
    	  if (email==""){
    		  window.alert("Enter userName")
    		  return false;
    	  }
    	  else if (password==""){
    		  window.alert("Enter Password : ")
    		  
    		  return false;
    	  }
    	  return true;
      }
    
    </script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 300px;
            padding: 20px;
            background-color: white;
            margin: 100px auto;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
        }

        label {
            font-size: 14px;
        }

        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            border: none;
            color: white;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .error-message {
            color: red;
            text-align: center;
            margin-bottom: 10px;
        }
        .KLM{
        font-size: 40px
        padding=10px;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Admin Login</h2>

        <h3 class="error-message">
            <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>
        </h3>

        <form name="hello" action="adminLogin" method="POST">
            <label >Username:</label>
            <input type="email"  name="email" required><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>

            <input type="submit" value="Login" onclick="display()"> <br> <br>
            <a href="Leo.html">Forget Password </a> <br> <br>
             
        </form>
    </div>
 
</body>
</html>
