<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.example.epamfinalproject.Entities.Enums.Status" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <style>
        <%@include file="/Styles/clientAccount.css" %>
    </style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <title><fmt:message key="profile.title"/></title>
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
                <input type="hidden" name="command" value="catalogue"/>
                <input type="hidden" name="page-path" value="/catalogue.jsp">
            </form>
        </a>
    </div>
    <div class="navElements col-sm-6">
        <div id="locale-changer" class="form-control">
            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="changeLocale">
                <input type="hidden" name="page-path" value="/clientAccount.jsp">
                <input class="btn" style="background: lightgray; width: 50px" type="submit" name="locale"
                       value="az">
            </form>
            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="changeLocale">
                <input type="hidden" name="page-path" value="/clientAccount.jsp">
                <input class="btn" style="background: lightgray; width: 50px;" type="submit" name="locale"
                       value="en">
            </form>
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="user">
        <div class="user_info row">
            <c:set var="user" scope="session" value="${sessionScope.user}"/>
            <div class="col-sm-3">
                <p><h5><strong><fmt:message key="user.label.login"/>:</strong></h5>
                <c:out value="${user.login}"/>
            </div>
            <div class="col-sm-3">
                <p><h5><strong><fmt:message key="user.label.firstName"/>:</strong></h5>
                <c:out value="${user.firstName}"/>
            </div>
            <div class="col-sm-3">
                <p><h5><strong><fmt:message key="user.label.lastName"/>:</strong></h5>
                <c:out value="${user.lastName}"/>
            </div>
            <div style="display: flex;align-items: flex-end;">
                <form id="logout" class="navbar-form" method="get"
                      action="${pageContext.request.contextPath}/controller">
                    <input type="submit" class="btn col logout-btn" value="<fmt:message key="button.logout"/>">
                    <input type="hidden" name="command" value="logout"/>
                </form>
            </div>
        </div>
    </div>
    <div class="row">
        <c:if test="${sessionScope.passport_img!= null}">
            <div class="col-sm-3" style="margin-left: 50px; margin-top: 40px">
                <img src="${sessionScope.passport_img}" width="300" height="350" alt="passport">
            </div>
        </c:if>
        <div class="card-group container-fluid col-sm-8">
            <div class="row">
                <c:forEach var="order" items="${sessionScope.orders}">
                    <div class="card">
                        <div class="card-header">
                            <h3><strong><c:out value="${order.airplane.name}"/></strong></h3>
                        </div>
                        <form class="card-body text-center" method="post"
                              action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="payForTheOrder"/>
                            <input type="hidden" name="id" value="${order.id}">
                            <div class="card-text">
                                <h4><strong><fmt:message key="airplane.label.flight.name"/></strong>
                                </h4><h5><c:out value="${order.airplane.flight.name}"/></h5>
                                <div class="row text-center">
                                    <h4><strong><fmt:message key="airplane.label.departure"/></strong></h4>
                                    <p> --> </p>
                                    <h4><strong><fmt:message key="airplane.label.destination"/></strong></h4>
                                </div>
                                <div class="row text-center">
                                    <h5><c:out value="${order.airplane.route.destination}"/></h5>
                                    <p> --> </p>
                                    <h5><c:out value="${order.airplane.route.departure}"/></h5>
                                </div>
                                <div class="row" style="text-align: center">
                                    <h5><strong><fmt:message key="airplane.label.leaving.date"/></strong></h5>
                                    <p> --> </p>
                                    <h5><strong><fmt:message key="airplane.label.arriving.date"/></strong></h5>
                                </div>
                                <div class="row text-center">
                                    <h6><c:out value="${order.airplane.startOfTheAirplane}"/></h6>
                                    <p> --> </p>
                                    <h6><c:out value="${order.airplane.endOfTheAirplane}"/></h6>
                                </div>
                                <div class="card-footer">
                                    <p><strong><fmt:message key="order.label.status"/></strong>:
                                            <c:out value="${order.status}"/>
                                        <c:if test="${order.airplane.confirmed == true && order.status != Status.PAID}">
                                        <button type="submit" style="width: 90%"><fmt:message key="button.pay"/></button>
                                        </c:if>
                                </div>
                            </div>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>