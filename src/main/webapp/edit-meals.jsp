<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 09.10.2021
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>Edit meal</h2>
    <form action="meals" method="post">
        <c:set var="meal" value="${meal}"/>
        <table>
           <tbody>
                <tr style="background: #cccccc">
                    <td>Date Time:</td>
                    <td><input type="datetime-local" name="date" value="${meal.dateTime}">
                        <input type="hidden" name="id" value="${meal.id}">
                    </td>
                </tr>
                <tr style="background: #cccccc">
                    <td>Description:</td>
                    <td><input size="45" name="description" value="${meal.description}"></td>
                </tr>
                <tr style="background: #cccccc">
                    <td>Calories:</td>
                    <td><input type="number" name="calories" value="${meal.calories}"></td>
                </tr>
           </tbody>
        </table>
        <button type="submit">Save</button>
        <button type="reset">Cancel</button>
    </form>





</body>
</html>
