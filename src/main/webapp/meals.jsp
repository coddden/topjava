<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="ru">
<head>
	<title>Meals</title>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
	<body>
		<h3><a href="index.html">Home</a></h3>
		<hr>
		<h2>Meals</h2>
		<h3><a href="mealForm.jsp">Add Meal</a></h3>
		<table>
			<tr>
	   			<td>Date</td>
	   			<td>Description</td>
	   			<td>Calories</td>
	   			<td></td>
	   			<td></td>
  			</tr>
			<c:forEach items="${mealsTo}" var="mealTo">
				<tr class="${mealTo.excess ? 'excess' : 'normal'}">
	    			<td><fmt:formatDate value="${mealTo.dateTimeAsDate}" pattern="yyyy-MM-dd HH:mm"/></td>
	    			<td>${mealTo.description}</td>
	    			<td>${mealTo.calories}</td>
	    			<td><a href="meals?action=update&id=${mealTo.id}">Update</a></td>
	    			<td><a href="meals?action=delete&id=${mealTo.id}">Delete</a></td>
	  			</tr>
			</c:forEach>
		</table>
	</body>
</html>