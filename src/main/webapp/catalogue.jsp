<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <style>
        <%@include file="/Styles/catalogue.css" %>
    </style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <title><fmt:message key="button.airplanes.catalogue"/></title>
</head>
<body>
<div class="navbar row">
    <div class="navElements col-sm-6">
        <a>
            <form id="account" class="navbar-form" method="get" action="${pageContext.request.contextPath}/controller">
                <input type="submit" class="btn col" value="<fmt:message key="button.profile"/>">
                <input type="hidden" name="command" value="profile"/>
            </form>
        </a>
        <a class="active" href="index.jsp"><fmt:message key="navbar.home"/></a>
        <a>
            <form id="catalog" class="navbar-form" method="get" action="${pageContext.request.contextPath}/controller">
                <input type="submit" class="btn col" value="<fmt:message key="button.airplanes.catalogue"/>">
                <input type="hidden" name="page" value="${1}">
                <input type="hidden" name="command" value="catalogue"/>
                <input type="hidden" name="page-path" value="/catalogue.jsp">
            </form>
        </a>
    </div>
    <div class="navElements col-sm-6">
        <div id="locale-changer" class="form-control">
            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="changeLocale">
                <input type="hidden" name="page-path" value="/catalogue.jsp">
                <input class="btn" style="background: lightgray; width: 50px" type="submit" name="locale"
                       value="az">
            </form>
            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="changeLocale">
                <input type="hidden" name="page-path" value="/catalogue.jsp">
                <input class="btn" style="background: lightgray; width: 50px;" type="submit" name="locale"
                       value="en">
            </form>
        </div>
    </div>
</div>
<form id="form" method="get" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="catalogue"/>
    <input type="hidden" name="page-path" value="/catalogue.jsp">
    <c:if test="${sessionScope.message != null}">
        <h5 style="color: red; text-align: center"><fmt:message key="${sessionScope.message}"/></h5>
    </c:if>
    <div class="sortbar row" style="margin-bottom: 30px">
        <div class="inpt startInput">
            <label for="start_date"><fmt:message key="airplane.label.leaving.date"/></label>
            <input type="date" id="start_date" name="start_date">
        </div>
        <div class="inpt endInput">
            <label for="end_date"><fmt:message key="airplane.label.arriving.date"/></label>
            <input type="date" id="end_date" name="end_date">
        </div>
        <div class="inpt durInput">
            <label for="transit_time"><fmt:message key="airplane.label.duration"/></label>
            <input type="number" id="transit_time" name="transit_time"
                  style="padding: 10px" placeholder="<fmt:message key="airplane.label.duration"/>">
        </div>
        <input type="submit" class="submit-btn" value="<fmt:message key="button.search"/>">
    </div>
</form>
<div class="filter-values">
    <c:if test="${sessionScope.start_date != null}">
        <div class="element">
            <h5><strong><fmt:message key="airplane.label.leaving.date"/>:</strong></h5>
            <h5 style="color: black; text-align: center"><c:out value="${sessionScope.start_date}"/></h5>
        </div>
    </c:if>
    <c:if test="${sessionScope.end_date != null}">
        <div class="element">
            <h5><strong><fmt:message key="airplane.label.arriving.date"/>:</strong></h5>
            <h5 style="color: black; text-align: center"><c:out value="${sessionScope.end_date}"/></h5>
        </div>
    </c:if>
    <c:if test="${sessionScope.transit_time != null}">
        <div class="element">
            <h5><strong><fmt:message key="route.label.transitTime"/>:</strong></h5>
            <h5 style="color: black; text-align: center"><c:out value="${sessionScope.transit_time}"/></h5>
        </div>
    </c:if>
    <c:if test="${sessionScope.start_date != null || sessionScope.end_date != null || sessionScope.transit_time != null}">
        <form method="get" action="${pageContext.request.contextPath}/controller" class="reset-btn">
            <input type="hidden" name="command" value="resetFilter"/>
            <input type="hidden" name="page-path" value="/catalogue.jsp">
            <button type="submit" style="border-radius: 50px"><h5><fmt:message key="button.reset"/></h5></button>
        </form>
    </c:if>
</div>
<h1 class="text-center"><fmt:message key="button.airplanes.catalogue"/></h1>
<div>
    <a href="Admin/adminAccount.jsp">Admin Account</a>
</div>
<div class="container">

    <table class="pagination">
        <tr>
            <c:forEach begin="1" end="${sessionScope.numOfPages}" var="i">
                <c:choose>
                    <c:when test="${sessionScope.currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td class="pageItem">
                            <form method="get" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="catalogue"/>
                                <input type="hidden" name="page-path" value="/catalogue.jsp">
                                <input type="hidden" name="page" value="${i}">
                                <button type="submit">${i}</button>
                            </form>
                        </td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>
</div>
<div class="card-group container">
    <c:forEach var="airplane" items="${sessionScope.airplanes}">
        <div class="card">
            <div class="card-header">
                <h3><strong><c:out value="${airplane.name}"/></strong></h3>
            </div>
            <form class="card-body text-center" method="get"
                  action="${pageContext.request.contextPath}/controller">
                <c:if test="${sessionScope.role == 'client' || sessionScope.role == null}">
                    <input type="hidden" name="command" value="displayFormWithAirplaneInfo"/>
                </c:if>
                <c:if test="${sessionScope.role == 'administrator'}">
                    <input type="hidden" name="command" value="confirmAirplane"/>
                </c:if>
                <input type="hidden" name="id" value="${airplane.id}">
                <div class="card-text">
                    <h4><strong><fmt:message key="airplane.label.flight.name"/></strong>
                    </h4><h5><c:out value="${airplane.flight.name}"/></h5>
                    <div class="row text-center">
                        <h4><strong><fmt:message key="airplane.label.departure"/></strong></h4>
                        <p> --> </p>
                        <h4><strong><fmt:message key="airplane.label.destination"/></strong></h4>
                    </div>
                    <div class="row text-center">
                        <h5><c:out value="${airplane.route.destination}"/></h5>
                        <p> --> </p>
                        <h5><c:out value="${airplane.route.departure}"/></h5>
                    </div>
                    <div class="row" style="text-align: center">
                        <h5><strong><fmt:message key="airplane.label.leaving.date"/></strong></h5>
                        <p> --> </p>
                        <h5><strong><fmt:message key="airplane.label.arriving.date"/></strong></h5>
                    </div>
                    <div class="row text-center">
                        <h6><c:out value="${airplane.startOfTheAirplane}"/></h6>
                        <p> --> </p>
                        <h6><c:out value="${airplane.endOfTheAirplane}"/></h6>
                    </div>
                    <c:if test="${sessionScope.role == 'client' || sessionScope.role == null}">
                        <button class="create-button" type="submit"><fmt:message key="button.order"/></button>
                    </c:if>
                    <c:if test="${sessionScope.role == 'administrator'}">
                        <button class="create-button" type="submit"><fmt:message key="button.confirm"/></button>
                    </c:if>
                </div>
            </form>
        </div>
    </c:forEach>
</div>
<footer class="container-fluid">
</footer>
</body>
</html>