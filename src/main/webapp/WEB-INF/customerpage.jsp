<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
         Demo Page for Customer Roles
    </jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>
        <h1>Hello ${sessionScope.email} </h1>
        <p>Are you ready to calculate your BMI? Go to the <a href="${pageContext.request.contextPath}">front page</a> to get started!</p>
        <p>Click <a href="${pageContext.request.contextPath}/fc/userbmientries">here</a> to see your BMI history.</p>
    </jsp:body>

</t:genericpage>

