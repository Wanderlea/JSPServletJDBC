<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product Registration</title>

<script src="resources\javascrit\jquery.min.js" type="text/javascript"></script>
<script src="resources\javascrit\jquery.maskMoney.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="resources/css/register.css">
</head>
<body>
	<a href="acessReleased.jsp"><img alt="Start" title="Start" src="resources/img/home.png" width="50px" height="50px"></a>  <!-- Redirect start -->
	<a href="index.jsp"><img alt="Exit" title="Exit" src="resources/img/exit.png" width="50px" height="50px"></a> <!-- Redirect start -->
	<script type="text/javascript">
		function validFields(){
			if (document.getElementById("name").value == '') {
				alert('Name required!');
				return false;
			}else if (document.getElementById("quantity").value == '') {
				alert('Quantity required!');
				return false;
			}else if (document.getElementById("value").value == '') {
				alert('Value required!');
				return false;
			}
			return true;
		}
		

		$(function() {
			$('#value').maskMoney();
		});
		

		$(document).ready(function() {
			$("#quantity").keyup(function() {
				$("#quantity").val(this.value.match(/[0-9]*/));
			});
		});
	</script>

	<center>
		<h1>Product Registration</h1>
		<h3 style="color: orange;" >${msg}</h3>
	</center>
	<form action="saveProduct" method="post" id="formProduct">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Id:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" value="${product.id}" class="field-long" ></td>
					</tr>
					<tr>
						<td>Name:</td>
						<td><input type="text" id="name" name="name" data-thousands="." data-decimal="," data-precision="2" value="${product.name}" style="width: 200px" maxlength="100"></td>
					</tr>
					<tr>
						<td>Quantity:</td>
						<td><input type="text" id="quantity" name="quantity" value="${product.quantity}" class="field-long" maxlength="7" ></td>
					</tr>
					<tr>
						<td>Value:</td>
						<td><input type="text" id="value" name="value" value="${product.valueText}" class="field-long" maxlength="8"></td>
					</tr>
					
					<tr>
						<td>Category:</td>
						<td>
							<select id=categories name="category_id">
							<c:forEach items="${categories}" var="cat">
								<option value="${cat.id}" id="${cat.id}" 
									<c:if test="${cat.id == product.category_id}">
									<c:out value="selected=selected"/>
									</c:if>>
									${cat.name}
								</option>		
							</c:forEach>
							</select>
						</td>
					</tr>
					
					<tr>
						<td></td>
						<td>
						<input type="submit" value="Save" style="width: 95px" onclick="return validFields()? true : false;"> 
						<input type="submit" value="Cancel" style="width: 95px" onclick="document.getElementById('formProduct').action = 'saveProduct?acao=reset'">
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Product List </caption>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Quantity</th>
				<th>Value</th>
				<th>Delete</th>
				<th>Edit</th>

			</tr>
			<c:forEach items="${products}" var="product">
				<tr>
					<td style="width: 150px"><c:out value="${product.id}"></c:out></td>
					<td style="width: 150px"><c:out value="${product.name}"></c:out></td>
					<td><c:out value="${product.quantity}"></c:out></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${product.value}"/></td>

					<td><a href="saveProduct?acao=delete&product=${product.id}" onclick="return confirm('Confirm deletion?')"><img src="resources/img/delete.png" alt="Delete" title="Delete" width="20px" height="20px"></a></td>
					<td><a href="saveProduct?acao=edit&product=${product.id}"><img src="resources/img/edit.png" alt="Edit" title="Edit" width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>