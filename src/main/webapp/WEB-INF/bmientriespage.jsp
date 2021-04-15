<%--
  Created by IntelliJ IDEA.
  User: kavvt
  Date: 14-04-2021
  Time: 09:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:genericpage>
    <jsp:attribute name="header">
        List of BMI entries
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${true}" scope="request"/>
    </jsp:attribute>

    <jsp:body>
        <h1>List of all BMI entries in the database</h1>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>EntryID</th>
                <th>Weight</th>
                <th>Height</th>
                <th>BMI</th>
                <th>Category</th>
                <th>Gender</th>
                <th>Sport</th>
                <th>User email</th>
                <th>Created at</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="bmiEntry" items="${requestScope.bmiEntryList}">
                <tr>
                    <td>${bmiEntry.entryId}</td>
                    <td>${bmiEntry.weight}</td>
                    <td>${bmiEntry.height}</td>
                    <td>${bmiEntry.bmi}</td>
                    <td>${bmiEntry.category}</td>
                    <td>${bmiEntry.gender}</td>
                    <td>${bmiEntry.sport.name}</td>
                    <td>${bmiEntry.user.email}</td>
                    <td>${bmiEntry.created}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:genericpage>
