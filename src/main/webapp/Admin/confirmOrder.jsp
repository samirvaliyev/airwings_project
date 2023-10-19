<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <style>
        <%@include file="/Styles/adminForm.css" %>
        <%@include file="/Styles/catalogue.css" %>
    </style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <title><fmt:message key="confirm.order.title"/></title>
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
        <a class="active" href="../index.jsp"><fmt:message key="navbar.home"/></a>
        <a>
            <form id="catalog" class="navbar-form" method="get" action="${pageContext.request.contextPath}/controller">
                <input type="submit" class="btn col" value="<fmt:message key="button.airplanes.catalogue"/>">
                <input type="hidden" name="command" value="catalogue"/>
                <input type="hidden" name="page-path" value="/catalogue.jsp">
            </form>
        </a>
    </div>
    <div class="navElements col-sm-6">
        <div id="locale-changer" class="form-control">
            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="changeLocale">
                <input type="hidden" name="page-path" value="/Admin/confirmOrder.jsp">
                <input class="btn" style="background: lightgray; width: 50px" type="submit" name="locale"
                       value="az">
            </form>
            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="changeLocale">
                <input type="hidden" name="page-path" value="/Admin/confirmOrder.jsp">
                <input class="btn" style="background: lightgray; width: 50px;" type="submit" name="locale"
                       value="en">
            </form>
        </div>
    </div>
</div>
<hr style="background-color: aliceblue">
<h2 style="color: white" class="text-center"><fmt:message key="admin.description.label.confirm.order"/></h2>
<form class="container-fluid" style="display: flex;justify-content: space-around;flex-direction: column;" method="get"
      action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="confirmOrder"/>
    <c:if test="${sessionScope.message != null}">
        <h5 style="color: red; text-align: center"><fmt:message key="${sessionScope.message}"/></h5>
    </c:if>
    <table>
        <tr>
            <td>
                <h4><strong><fmt:message key="label.check"/></strong></h4>
            </td>
            <td>
                <h4><strong><fmt:message key="label.id"/></strong></h4>
            </td>
            <td>
                <h4><strong><fmt:message key="airplane.label.name"/></strong></h4>
            </td>
            <td>
                <h4><strong><fmt:message key="flight.label.name"/></strong></h4>
            </td>
            <td>
                <h4><strong><fmt:message key="airplane.label.departure"/></strong></h4>
            </td>
            <td>
                <h4><strong><fmt:message key="airplane.label.destination"/></strong></h4>
            </td>
            <td>
                <h4><strong><fmt:message key="airplane.label.duration"/></strong></h4>
            </td>
            <td>
                <h4><strong><fmt:message key="airplane.label.price"/></strong></h4>
            </td>
            <td>
                <h4><strong><fmt:message key="airplane.label.leaving.date"/></strong></h4>
            </td>
            <td>
                <h4><strong><fmt:message key="airplane.label.arriving.date"/></strong></h4>
            </td>
            <td>
                <h4><strong><fmt:message key="order.label.status"/></strong></h4>
            </td>
        </tr>
        <c:forEach var="order" items="${sessionScope.orders}">
            <tr>
                <td><input type="checkbox" name="id" value="${order.id}"></td>
                <td><h5><c:out value="${order.id}"/></h5></td>
                <td><h5><c:out value="${order.airplane.name}"/></h5></td>
                <td><h5><c:out value="${order.airplane.flight.name}"/></h5></td>
                <td><h5><c:out value="${order.airplane.route.departure}"/></h5></td>
                <td><h5><c:out value="${order.airplane.route.destination}"/></h5></td>
                <td><h5><c:out value="${order.airplane.route.transitTime}"/></h5></td>
                <td><h5><c:out value="${order.airplane.price}$"/></h5></td>
                <td><h5><c:out value="${order.airplane.startOfTheAirplane}"/></h5></td>
                <td><h5><c:out value="${order.airplane.endOfTheAirplane}"/></h5></td>
                <td><h5><c:out value="${order.status}"/></h5></td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" style="max-width: 120px;height: 50px; margin-top: 20px"
           value="<fmt:message key="button.confirm"/>">
</form>
<form class="container-fluid" method="get" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="confirmAllOrders"/>
    <input type="hidden" name="all" value="true">
    <input type="submit" style="max-width: 120px;height: 50px; margin-top: 20px" value="<fmt:message key="button.confirm.all"/>">
</form>
</body>
</html>