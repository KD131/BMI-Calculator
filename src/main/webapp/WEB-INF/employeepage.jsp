<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
         BMI Admin Page
    </jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
        <h1>Hello ${sessionScope.email} </h1>
        <p>You are now logged in as a EMPLOYEE of our wonderful site.</p>

        <p><a href="${pageContext.request.contextPath}/fc/bmientries">Show all BMI entries</a></p>
        <p><a href="${pageContext.request.contextPath}/fc/managesports">Manage sports list</a></p>
        <p>Click <a href="${pageContext.request.contextPath}/fc/userbmientries">here</a> to see your BMI history.</p>

    </jsp:body>
</t:genericpage>
