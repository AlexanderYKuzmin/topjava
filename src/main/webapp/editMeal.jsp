<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:set var="title" value="${meal.id != null ? 'Edit meal' : 'Add meal'}"/>
    <title>${title}</title>
</head>
<body>
    <h2>${title}</h2>
    <form id="edit-meal" action="meals" method="post">
        <input type="hidden" name="id" value="${title.equals('Add meal') ? null : meal.id}">
        <table>
           <tbody>
                <tr style="background: #cccccc">
                    <td>Date Time:</td>
                    <td><input type="datetime-local" name="date" value="${meal.dateTime}">
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
    </form>
    <button type="submit" form="edit-meal">Save</button>
    <input type="button" name="cancel-btn" value="cancel" onclick="window.history.back()"/>

</body>
</html>
