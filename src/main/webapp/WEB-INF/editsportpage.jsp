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
        Edit sport
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${true}" scope="request"/>
    </jsp:attribute>

    <jsp:body>
        <div class="row">
            <div class="col-sm-4 mx-auto">
                <h1>Edit ${requestScope.sport.name}</h1>
                <form action="${pageContext.request.contextPath}/fc/managesports" method="post">
                    <input type="hidden" name="id" value="${requestScope.sport.id}">
                    <div class="form-group mt-3">
                        <label for="name" class="form-label">
                            New name:</label>
                        <input type="text" class="form-control" name="name" id="name" placeholder="${requestScope.sport.name}" required>
                    </div>
                    <input type="submit" class="btn btn-primary col-sm-4 mt-2" name="update" value="Update">
                </form>
                <c:if test="${requestScope.error != null}">
                    <p style="color: red">${requestScope.error}</p>
                </c:if>
            </div>
        </div>
    </jsp:body>
</t:genericpage>
