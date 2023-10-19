<%@ page import="java.time.LocalDate" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title><fmt:message key="edit.airplane.title"/></title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <style>
        <%@include file="/Styles/adminForm.css" %>
        <%@include file="/Styles/catalogue.css" %>
    </style>
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
                <input type="hidden" name="page-path" value="/Admin/displayAirplanes.jsp">
                <input class="btn" style="background: lightgray; width: 50px" type="submit" name="locale"
                       value="az">
            </form>
            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="changeLocale">
                <input type="hidden" name="page-path" value="/Admin/displayAirplanes.jsp">
                <input class="btn" style="background: lightgray; width: 50px;" type="submit" name="locale" value="en">
            </form>
        </div>
    </div>
</div>
<div class="container-fluid" id="container" style="display: flex;align-items: center; flex-direction: column;">
    <h1 style="margin-top: 50px; color: white"><fmt:message key="edit.airplane.title"/></h1>
    <hr style="background-color: aliceblue">
    <table class="pagination container-fluid" style="display: flex">
        <tr>
            <c:forEach begin="1" end="${sessionScope.numOfPages}" var="i">
                <c:choose>
                    <c:when test="${sessionScope.currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td class="pageItem">
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="catalogue"/>
                                <input type="hidden" name="page-path" value="/Admin/displayAirplanes.jsp">
                                <input type="hidden" name="page" value="${i}">
                                <button type="submit">${i}</button>
                            </form>
                        </td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>
    <table class="container-fluid">
        <tr>
            <td><h5><strong><fmt:message key="airplane.label.name"/></strong></h5></td>
            <td><h5><strong><fmt:message key="airplane.label.flight.name"/></strong></h5></td>
            <td><h5><strong><fmt:message key="flight.label.capacity"/></strong></h5></td>
            <td><h5><strong><fmt:message key="airplane.label.departure"/></strong></h5></td>
            <td><h5><strong><fmt:message key="airplane.label.destination"/></strong></h5></td>
            <td><h5><strong><fmt:message key="airplane.label.leaving.date"/></strong></h5></td>
            <td><h5><strong><fmt:message key="airplane.label.arriving.date"/></strong></h5></td>
            <td><h5><strong><fmt:message key="airplane.label.price"/></strong></h5></td>
            <td><h5><strong><fmt:message key="airplane.label.duration"/></strong></h5></td>
            <td><h5><strong><fmt:message key="admin.button.edit"/></strong></h5></td>
        </tr>
        <c:forEach var="airplane" items="${sessionScope.airplanes}">
            <form class="card-body text-center" method="get"
                  action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="displayFormWithAirplaneInfo"/>
                <input type="hidden" name="id" value="${airplane.id}">
                <tr>
                    <td><c:out value="${airplane.name}"/></td>
                    <td><c:out value="${airplane.flight.name}"/></td>
                    <td><c:out value="${airplane.flight.passengerCapacity}"/></td>
                    <td><c:out value="${airplane.route.departure}"/></td>
                    <td><c:out value="${airplane.route.destination}"/></td>
                    <td><c:out value="${airplane.startOfTheAirplane}"/></td>
                    <td><c:out value="${airplane.endOfTheAirplane}"/></td>
                    <td><c:out value="${airplane.price}"/></td>
                    <td><c:out value="${airplane.route.transitTime}"/></td>
                    <td>
                        <form>
                            <button class="edit-button" type="submit"><fmt:message
                                    key="admin.button.edit"/></button>
                        </form>
                    </td>
                </tr>
            </form>
        </c:forEach>
    </table>
</div>
</body>
</html>
