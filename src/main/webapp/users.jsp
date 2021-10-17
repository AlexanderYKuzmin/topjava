<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<form action="users" method="post">
    <select name="user" id="user-selection" onchange="this.form.submit()">
        <option value="1" ${user == 1 ? 'selected' : ''}>User</option>
        <option value="2" ${user == 2 ? 'selected' : ''}>Admin</option>
    </select>
</form>
</body>
</html>