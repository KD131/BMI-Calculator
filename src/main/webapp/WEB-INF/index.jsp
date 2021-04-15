<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>

    <jsp:attribute name="header">
         Home
    </jsp:attribute>

    <jsp:attribute name="footer">
        <c:set var="addHomeLink" value="${false}" scope="request"/>
    </jsp:attribute>

    <jsp:body>
        <div class="row">
            <div class="col-sm-4 mx-auto">
                <h2>BMI calculator</h2>

                <form action="${pageContext.request.contextPath}/fc/bmiresult" method="post">
                    <div class="form-group form-floating mb-3">
                        <input type="number" class="form-control" name="weight" id="weight" placeholder="Weight" required>
                        <label for="weight" class="form-label">
                            Weight (kg)
                        </label>
                    </div>
                    <div class="form-group form-floating mb-3">
                        <input type="number" class="form-control" name="height" id="height" placeholder="Height"
                               required>
                        <label for="height" class="form-label">
                            Height (cm)
                        </label>
                    </div>
                    <hr>
                    <div class="form-group mb-3">
                        <div class="form-check">
                            <input type="radio" class="form-check-input" id="male" name="gender" value="male" required>
                            <label for="male" class="form-check-label">
                                Male
                            </label>
                        </div>
                        <div class="form-check">
                            <input type="radio" class="form-check-input" id="female" name="gender" value="female" required>
                            <label for="female" class="form-check-label">
                                Female
                            </label>
                        </div>
                    </div>
                    <div class="form-group mb-3">
                        <label for="primary-sport" class="form-label">
                            Your primary sport is
                        </label> <br/>
                        <select id="primary-sport" class="form-select" name="primary-sport" required>
                            <c:forEach var="sport" items="${applicationScope.sportList}">
                                <option value="${sport.id}">${sport.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="hobby1" name="hobby" value="1">
                        <label for="hobby1" class="form-check-label">
                            I care about my diet.
                        </label>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="hobby2" name="hobby" value="2">
                        <label for="hobby2" class="form-check-label">
                            I have a summer house.
                        </label>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="hobby3" name="hobby" value="3">
                        <label for="hobby3" class="form-check-label">
                            I have a pet.
                        </label>
                    </div>

            <c:if test="${requestScope.error != null}">
                <p style="color: red">${requestScope.error}</p>
            </c:if>

            <input type="submit" class="btn btn-success mt-2" value="Calculate">
            </form>
        </div>
        <c:if test="${sessionScope.role == 'employee' }">
            <p style="font-size: larger">This is what you can do,
                since your are logged in as an employee</p>
            <p><a href="fc/employeepage">Employee Page</a>
        </c:if>

        <c:if test="${sessionScope.role == 'customer' }">
            <p style="font-size: larger">This is what you can do, since your
                are logged in as a customer</p>
            <p><a href="fc/customerpage">Customer Page</a>
        </c:if>
<%--        <p><a href="fc/bmiresult">To result page</a></p>--%>

        </div>

    </jsp:body>
</t:genericpage>