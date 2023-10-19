<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <style>
        <%@include file="/Styles/adminAccount.css" %>
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
            <form method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="changeLocale">
                <input type="hidden" name="page-path" value="/Admin/adminAccount.jsp">
                <input class="btn" style="background: lightgray; width: 50px" type="submit" name="locale"
                       value="az">
            </form>
            <form method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="changeLocale">
                <input type="hidden" name="page-path" value="/Admin/adminAccount.jsp">
                <input class="btn" style="background: lightgray; width: 50px;" type="submit" name="locale"
                       value="en">
            </form>
        </div>
    </div>
</div>
<div class="container-fluid row">
    <div class="user col-sm-2">
        <div class="user_info row">
            <c:set var="user" scope="session" value="${sessionScope.user}"/>
            <div class="col-sm-5">
                <p><h5><strong><fmt:message key="user.label.login"/>:</strong></h5>
                <c:out value="${user.login}"/>
            </div>
            <div class="col-sm-8">
                <p><h5><strong><fmt:message key="user.label.firstName"/>:</strong></h5>
                <c:out value="${user.firstName}"/>
                <p><h5><strong><fmt:message key="user.label.lastName"/>:</strong></h5>
                <c:out value="${user.lastName}"/>
            </div>
        </div>
        <div>
            <form id="logout" class="navbar-form" method="get" action="${pageContext.request.contextPath}/controller">
                <input type="submit" class="btn col logout-btn" value="<fmt:message key="button.logout"/>">
                <input type="hidden" name="command" value="logout"/>
            </form>
        </div>
    </div>
    <div class="col-sm-7 commands container-fluid">
        <c:if test="${sessionScope.message != null}">
            <h5 style="color: red; text-align: center"><fmt:message key="${sessionScope.message}"/></h5>
        </c:if>
        <h2 class="text-center"><fmt:message key="admin.services"/></h2>
        <br>
        <br>
        <div class="text-center row">
            <a class="col-sm-3" href="createAirplane.jsp" style="color: black">
                <h5><strong><fmt:message key="admin.label.create"/></strong></h5>
                <p><fmt:message key="admin.description.label.create.airplane"/></p>
            </a>
            <form class=" col-sm-3" method="get" action="${pageContext.request.contextPath}/controller">
                <button type="submit" style="border: none; background-color: inherit">
                    <h5><strong><fmt:message key="admin.label.edit"/></strong></h5>
                    <p><fmt:message key="admin.description.label.edit.airplane"/></p>
                    <input type="hidden" name="page" value="${1}">
                    <input type="hidden" name="command" value="catalogue"/>
                    <input type="hidden" name="page-path" value="/Admin/displayAirplanes.jsp">
                </button>
            </form>
            <form class="col-sm-3" method="get" action="${pageContext.request.contextPath}/controller">
                <button type="submit" style="border: none; background-color: inherit">
                    <h5><strong><fmt:message key="admin.label.delete"/></strong></h5>
                    <p><fmt:message key="admin.description.label.delete.airplane"/></p>
                    <input type="hidden" name="page" value="${1}">
                    <input type="hidden" name="command" value="catalogue"/>
                    <input type="hidden" name="page-path" value="/Admin/deleteAirplane.jsp">
                </button>
            </form>
            <a class="col-sm-3" href="confirmOrder.jsp" style="color: black">
                <input type="hidden" name="command" value="confirmOrder"/>
                <h5><strong><fmt:message key="admin.entity.label.order"/></strong></h5>
                <p><fmt:message key="admin.description.label.confirm.order"/></p>
            </a>
        </div>
    </div>
</div>
</body>
</html>