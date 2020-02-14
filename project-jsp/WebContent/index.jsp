<jsp:useBean id="calc" class="beans.BeanProjectJsp"
	type="beans.BeanProjectJsp" scope="page" />

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="resources/css/style.css">

</head>
<body>
	<div class="login-page">
		<center><h3>Didactic Project</h3></center>
		<center><h3>JSP + Servlet + JDBC</h3></center>
		<center><h3>Login: admin / Password: admin</h3></center>
		<div class="form">
			<form action="LoginServlet" method="post" class="login-form">
				Login: <input type="text" id="login" name="login"> <br />
				Password: <input type="password" id="password" name="password">	<br /> 
				<button type="submit" value="log into">log into</button>
			</form>
		</div>
	</div>
</body>
</html>