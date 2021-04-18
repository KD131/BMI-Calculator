<%--
  Created by IntelliJ IDEA.
  User: kavvt
  Date: 12-04-2021
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:genericpage>
    <jsp:attribute name="header">
        Result
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${true}" scope="request"/>
    </jsp:attribute>

    <jsp:body>
        <div class="row">
            <div class="col-sm-4 mx-auto">
                <h1>Result page, yun!</h1>
                <table class="table table-striped">
                    <tr>
                        <td>Weight</td>
                        <td>${requestScope.weight} kg</td>
                    </tr>
                    <tr>
                        <td>Height</td>
                        <td>${requestScope.height} m</td>
                    </tr>
                    <tr>
                        <td>BMI</td>
                        <td>${requestScope.bmi}</td>
                    </tr>
                    <tr>
                        <td>Category</td>
                        <td>${requestScope.category}</td>
                    </tr>
                    <tr>
                        <td>Gender</td>
                        <td>${requestScope.gender}</td>
                    </tr>
                    <tr>
                        <td>Primary sport (id)</td>
                        <td>${requestScope.sport.name}</td>
                    </tr>
                    <c:if test="${not empty requestScope.hobbies}">
                        <tr>
                            <td class="align-middle">Hobbies (id)</td>
                            <td>
                                <c:forEach var="hobby" items="${requestScope.hobbies}">
                                    ${hobby}<br/>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </div>
    </jsp:body>
</t:genericpage>
