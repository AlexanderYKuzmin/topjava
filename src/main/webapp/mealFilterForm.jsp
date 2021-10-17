<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 16.10.2021
  Time: 12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Filter</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 100px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Filter meal</h2>
    <%--<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>--%>
    <form method="post" action="meals?action=postFilterData">
        <dl>
            <dt>Start date:</dt>
            <dd><input type="date" value="${startDate}" name="dateStart" required></dd>
            <dt style="margin-left: 30px">End date:</dt>
            <dd><input type="date" value="${endDate}" name="dateEnd" required></dd>
        </dl>
        <dl>
            <dt>Start time:</dt>
            <dd><input type="time" value="${startTime}"  name="timeStart" required></dd>
            <dt style="margin-left: 46px">End time:</dt>
            <dd><input type="time" value="${endTime}" name="timeEnd" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
