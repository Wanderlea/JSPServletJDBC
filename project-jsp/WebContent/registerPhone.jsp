<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration</title>
<link rel="stylesheet" href="resources/css/register.css">

 <!-- Adicionando JQuery -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>

</head>
<body>
	<a href="acessReleased.jsp"><img alt="Start" title="Start" src="resources/img/home.png" width="50px" height="50px"></a>  <!-- Redirect start -->
	<a href="index.jsp"><img alt="Exit" title="Exit" src="resources/img/exit.png" width="50px" height="50px"></a> <!-- Redirect start -->
	
	<script type="text/javascript">
		
		function validFields() {
			if (document.getElementById("number").value == '') {
				alert('Number required!');
				return false;
			}else if (document.getElementById("type").value == '') {
				alert('Type required!');
				return false;
			}
			
			return true;
		}
		
	</script>
	
	
	<center>
		<h1>Phone Registration</h1>
		<h3 style="color: orange;" ></h3>
	</center>
	<center><h2  style="color: orange;">${msg}</h2></center>
	<form action="savePhones" method="post" id="formUser" >
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>User:</td>
						<td><input type="text" readonly="readonly" id="user" name="user" class="field-long" value="${userChosen.id}"></td>						
						<td><input type="text" readonly="readonly" id="user" name="user" class="field-long" value="${userChosen.name}"></td>
					</tr>
					<tr>
						<td>Number:</td>	
						<td><input type="text" id="number" name="number"></td>		
						<td><select id="type" name="type" style="width: 173px">
							<option>Home</option>
							<option>Contact</option>	
							<option>Cellphone</option>
						</select></td>		
					</tr>
					
					<tr>
						<td></td>
						<td>
						   <input type="submit" value="Save" onclick="return validFields() ? true : false;" style="width: 178px"/>
						</td>
						<td>
						    <input type="submit" value="Return" onclick="document.getElementById('formUser').action = 'savePhones?acao=return'" style="width: 173px"/>
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Phones List</caption>
			<tr>
				<th>Id</th>
				<th>Number</th>
				<th>Type</th>
				<th>Delete</th>

			</tr>
			<c:forEach items="${phones}" var="phone">
				<tr>
					<td style="width: 150px"><c:out value="${phone.id}"></c:out></td>
					<td style="width: 150px"><c:out value="${phone.number}"></c:out></td>
					<td><c:out value="${phone.type}"></c:out></td>

					<td><a onclick="return confirm('Confirm deletion?')" href="savePhones?user=${phone.user_id}&acao=deletePhone&phoneId=${phone.id}"><img src="resources/img/delete.png" alt="Delete" title="Delete" width="20px" height="20px"></a></td>
					
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>