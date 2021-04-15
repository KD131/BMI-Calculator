<%--
  Created by IntelliJ IDEA.
  User: kavvt
  Date: 15-04-2021
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:genericpage>
    <jsp:attribute name="header">
        Manage sports
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${true}" scope="request"/>
    </jsp:attribute>

    <jsp:body>
        <div class="row">
            <div class="col-sm-4 mx-auto">
                <h1>Manage the sports list</h1>
                <form action="${pageContext.request.contextPath}/fc/managesports" method="post">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>SportID</th>
                            <th>Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="sport" items="${applicationScope.sportList}">
                            <tr>
                                <td>${sport.id}</td>
                                <td>${sport.name}</td>
                                <td>
                                    <button type="submit" class="btn btn-danger" name="delete" value="${sport.id}">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
    </jsp:body>
</t:genericpage>
