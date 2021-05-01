<%--
  Created by IntelliJ IDEA.
  User: kavvt
  Date: 14-04-2021
  Time: 23:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:genericpage>
    <jsp:attribute name="header">
        Your BMI history
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${true}" scope="request"/>
    </jsp:attribute>

    <jsp:body>
        <h1>Your BMI entries in the database</h1>
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
                    <td>${bmiEntry.bmiString}</td>
                    <td>${bmiEntry.category}</td>
                    <td>${bmiEntry.gender}</td>
                    <td>${bmiEntry.sport.name}</td>
                    <td>${bmiEntry.user.email}</td>
                    <td>${bmiEntry.created}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <p>Click <a href="${pageContext.request.contextPath}">here</a> to make some new BMI calculations.</p>
    </jsp:body>
</t:genericpage>
