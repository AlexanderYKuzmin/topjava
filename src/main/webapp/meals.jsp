<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 09.10.2021
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Meals</h2>
    <h3><a href="edit-meals.jsp">Add meal</a></h3>
    <table border="1">
        <thead>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${mealsTo}" var="meal">
                <tr style="color: ${meal.excess ? 'red' : 'green'}">
                    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                    <td><fmt:formatDate value="${parsedDateTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td><c:out value="${meal.description}"/></td>
                    <td><c:out value="${meal.calories}"/></td>
                    <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Update</a></td>
                    <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>

    </table>
</body>
</html>
