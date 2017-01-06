<%--
  Created by IntelliJ IDEA.
  User: Валерий
  Date: 05.01.2017
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal list</title>

    <style>
        .normal{
            color: green;
        }
        .exceed{
            color: red;
        }
    </style>

</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>

<table border="2" cellpadding="8" cellspacing="1">
    <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="mealExceed" items="${mealWithExceedList}">
        <jsp:useBean id="mealExceed" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr class="${mealExceed.exceed ? 'exceed' : 'normal'}">
            <td>${mealExceed.dateTime.toLocalDate()} ${mealExceed.dateTime.toLocalTime()}</td>
            <td>${mealExceed.description}</td>
            <td>${mealExceed.calories}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
