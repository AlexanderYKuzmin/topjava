<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
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
    <form method="post" action='<c:url value="/meals?action=add"/>'>
        <input type="submit" value="Add meal"
               style="border: none; background: none; font-size: 18px;
               font-weight: bold; text-decoration: underline; color: mediumblue"/>
    </form>
    <br>
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
                    <td width="240">${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td>
                        <form method="post" action='<c:url value="/meals?action=update"/>' style="display:inline;">
                            <input type="hidden" name="id" value="${meal.id}">
                            <input type="submit" value="Update">
                        </form>
                    <td>
                        <form method="post" action='<c:url value="/meals?action=delete"/>' style="display:inline;">
                            <input type="hidden" name="id" value="${meal.id}">
                            <input type="submit" value="Delete">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>

    </table>
</body>
</html>
