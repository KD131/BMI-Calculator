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
                            <th>ID</th>
                            <th>Name</th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="sport" items="${applicationScope.sportList}">
                            <tr>
                                <td>${sport.id}</td>
                                <td>${sport.name}</td>
                                <td>
                                    <button type="submit" class="btn btn-warning" name="edit" value="${sport.id}">
                                        Edit
                                    </button>
                                </td>
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
                <c:if test="${requestScope.error != null}">
                    <p style="color: red">${requestScope.error}</p>
                </c:if>
                <form action="${pageContext.request.contextPath}/fc/managesports" method="post">
                    <button type="submit" class="btn btn-success" name="add">Add new sport</button>
                </form>
                <!-- TODO: Make Command and page for creating new sport. You can maybe reuse the edit page. -->
            </div>
        </div>
    </jsp:body>
</t:genericpage>
