<%@page import="beans.BeanProjectJsp"%>
<%@page import="com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream"%>
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
			if (document.getElementById("login").value == '') {
				alert('Login required!');
				return false;
			} else if (document.getElementById("password").value == '') {
				alert('Password required!');
				return false;
			} else if (document.getElementById("name").value == '') {
				alert('Name required!');
				return false;
			} else if (document.getElementById("phone").value == '') {
				alert('Phone required!');
				return false;
			}
			return true;
		}
		
		function searchCep(){
			var cep = $("#zipCode").val();	
						
			//Consulta o webservice viacep.com.br/
            $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                if (!("erro" in dados)) {
                	 $("#street").val(dados.logradouro);
                	 $("#neighbor").val(dados.bairro);   
                	 $("#city").val(dados.localidade); 
                	 $("#state").val(dados.uf);   
                	 $("#ibge").val(dados.ibge);   
                } 
                else {
                    //CEP pesquisado não foi encontrado.
                     $("#zipCode").val('');
                     $("#street").val('');
                	 $("#neighbor").val('');   
                	 $("#city").val(''); 
                	 $("#state").val('');   
                	 $("#ibge").val('');  
                    alert("Zip Code not found.");
                }
            });
		}
	</script>
	
	
	<center>
		<h1>User Registration</h1>
		<h3 style="color: orange;" >${msg}</h3>
	</center>
	<form action="saveUser" method="post" id="formUser" enctype="multipart/form-data">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<!-- Linha -->
						<td>Id:</td>
						<!-- Celula -->
						<td><input type="text" readonly="readonly" id="id" name="id" value="${user.id}" class="field-long" style="width: 178px"></td>
						
						<td>Zip Code:</td>
						<td><input type="text" id="zipCode" name="zipCode" onblur="searchCep();" value="${user.zipCode}" placeholder="Enter a valid zip code" maxlength="9"></td>						
					</tr>
					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login" value="${user.login}" maxlength="10"></td>
						
						<td>Street:</td>
						<td><input type="text" id="street" name="street" value="${user.street}"></td>						
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type="password" id="password" name="password" value="${user.password}" maxlength="10"></td>
						
						<td>Neighborhood:</td>
						<td><input type="text" id="neighbor" name="neighbor" value="${user.neighbor}"></td>
					</tr>
					<tr>
						<td>Name:</td>
						<td><input type="text" id="name" name="name" value="${user.name}" placeholder="Enter the user name"></td>
						
						<td>City:</td>
						<td><input type="text" id="city" name="city" value="${user.city}" maxlength="50"></td>
					</tr>
					<tr>
						<td>State:</td>
						<td><input type="text" id="state" name="state" value="${user.state}"></td>	
						<td>Active:</td>
						<td><input type="checkbox" id="active" name="active" 
						<% 
							if(request.getAttribute("user") != null){
							
								BeanProjectJsp user = (BeanProjectJsp) request.getAttribute("user");
								if(user.isActive()){
									out.print(" ");
									out.print("checked=\"checked\"");
									out.print(" ");
								}
							}
						%>
						></td>					
					</tr>					
					<tr>
						<td>IBGE:</td>
						<td><input type="text" id="ibge" name="ibge" value="${user.ibge}"></td>
						<td>Sex:</td>
						<td>
						<input type="radio" name="sex"  
						<%
							if(request.getAttribute("user") != null){
								BeanProjectJsp user = (BeanProjectJsp) request.getAttribute("user");
								if(user.getSex() != null && user.getSex().equals("male")){
									out.print(" ");
									out.print("checked=\"checked\"");
									out.print(" ");
								}
							}
						%>
						value="male">Male</input>
						<input type="radio" name="sex" 
						<%
							if(request.getAttribute("user") != null){
								BeanProjectJsp user = (BeanProjectJsp) request.getAttribute("user");
								if(user.getSex() != null && user.getSex().equals("female")){
									out.print(" ");
									out.print("checked=\"checked\"");
									out.print(" ");
								}
							}
						%>
						value="female">Female</input>
						
						</td>
					</tr>
					
					<tr>
						<td>Photo:</td>
						<td> <input type="file" name="photo" > 
						<input type="text" style="display: none;" name="photoTemp" readonly="readonly" value="${user.photoBase64}"/> 
						<input type="text" style="display: none;" name="contentTypeTemp" readonly="readonly" value="${user.contentType}"/>
						</td>
						
						<td>Profile</td>
						<td>
						<select id="profile" name="profile" style="width: 180px;">
							<option value="uninformed">...</option>
							<option value="administrador"
							<%
								if(request.getAttribute("user") != null){
									BeanProjectJsp user = (BeanProjectJsp) request.getAttribute("user");
									if(user.getProfile() != null && user.getProfile().equalsIgnoreCase("administrador")){
										out.print(" ");
										out.print("selected=\"selected\"");
										out.print(" ");
									}
								}
							%>
							>Administrator</option>
							
							<option value="secretary"
							<%
								if(request.getAttribute("user") != null){
									BeanProjectJsp user = (BeanProjectJsp) request.getAttribute("user");
									if(user.getProfile() != null && user.getProfile().equalsIgnoreCase("secretary")){
										out.print(" ");
										out.print("selected=\"selected\"");
										out.print(" ");
									}
								}
							%>
							>Secretary</option>
							
							<option value="manager"
							<%
								if(request.getAttribute("user") != null){
									BeanProjectJsp user = (BeanProjectJsp) request.getAttribute("user");
									if(user.getProfile() != null && user.getProfile().equalsIgnoreCase("manager")){
										out.print(" ");
										out.print("selected=\"selected\"");
										out.print(" ");
									}
								}
							%>
							>Manager</option>
							
							<option value="employee"
							<%
								if(request.getAttribute("user") != null){
									BeanProjectJsp user = (BeanProjectJsp) request.getAttribute("user");
									if(user.getProfile() != null && user.getProfile().equalsIgnoreCase("employee")){
										out.print(" ");
										out.print("selected=\"selected\"");
										out.print(" ");
									}
								}
							%>
							>Employee</option>
							
						</select>
						</td>
					</tr>
					
					<tr>
						<td>CV:</td>
						<td> <input type="file" name="cv" value="cv">
						<input type="text" style="display: none;" name="photoTempPDF" readonly="readonly" value="${user.cvBase64}"/> 
						<input type="text" style="display: none;" name="contentTypeTempPDF" readonly="readonly" value="${user.contentTypeCV}"/>
						</td>
					
					</tr>
					<tr>
						<td></td>
						<td>
						   <input type="submit" value="Save" onclick="return validFields()? true : false;" style="width: 178px"> 
						</td>
						<td></td>
						<td>
						   <input type="submit" value="Cancel" onclick="document.getElementById('formUser').action = 'saveUser?acao=reset'" style="width: 178px">
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	<form method="post" action="search"  >
	<ul class="form-style-1">
			<li>
				<table>
					<tr>
					<td>Description</td>	
					<td><input type="text" id="searchDescription" name="searchDescription"></td>
					<td><input type="submit" value="Search"></td>
					</tr>			
				</table>
			</li>
		</ul>
	</form>
	<div class="container">
		<table class="responsive-table">
			<caption>User List</caption>
			<tr>
				<th>Id</th>
				<th>Photo</th>
				<th>CV</th>
				<th>Name</th>
				<th>Phone</th>
				<th>Delete</th>
				<th>Edit</th>
			</tr>
			<c:forEach items="${users}" var="user">
				<tr>
					<td style="width: 150px"><c:out value="${user.id}"></c:out></td>
					
					<c:if test="${user.photoBase64Miniature.isEmpty() == false}">
					<td><a href="saveUser?acao=download&tipo=image&user=${user.id}"><img src='<c:out value="${user.photoBase64Miniature}"/>' alt="Image User" title="Image User" width="30px" height="30px"/></a></td>
					</c:if>
					<c:if test="${user.photoBase64Miniature == null || user.photoBase64Miniature.isEmpty() == true}">
					  <td><img alt="Imagem User" src="resources/img/userDefault.png" width="32px" height="32px" onclick="alert('There is no Photo')"></td>
					</c:if>
					
					<c:if test="${user.cvBase64.isEmpty() == false}">
						<td><a href="saveUser?acao=download&tipo=cv&user=${user.id}"><img alt="CV" src="resources/img/pdf.png" width="32px" height="32px"></a></td>
					</c:if>
					<c:if test="${user.cvBase64 == null || user.cvBase64.isEmpty() == true}">
						<td><img alt="CV" src="resources/img/pdf.png" width="32px" height="32px" onclick="alert('There is no CV')"></td>
					</c:if>
					
					<td><c:out value="${user.name}"></c:out></td>
					<td><a href="savePhones?acao=addPhone&user=${user.id}"><img src="resources/img/phone.png" alt="Phones" title="Phones" width="30px" height="30px"></a></td>
					<td><a href="saveUser?acao=delete&user=${user.id}" onclick="return confirm('Confirm deletion?')"><img src="resources/img/delete.png" alt="Delete" title="Delete" width="20px" height="20px"></a></td>
					<td><a href="saveUser?acao=edit&user=${user.id}"><img src="resources/img/edit.png" alt="Edit" title="Edit" width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>