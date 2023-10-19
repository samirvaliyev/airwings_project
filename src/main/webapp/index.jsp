<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <style>
        <%@include file="/Styles/index.css" %>
    </style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <title><fmt:message key="home.title"/></title>
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
                <input type="hidden" name="page-path" value="/index.jsp">
                <input class="btn" type="submit" name="locale"
                       value="az">
            </form>
            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="changeLocale">
                <input type="hidden" name="page-path" value="/index.jsp">
                <input class="btn" type="submit" name="locale"
                       value="en">
            </form>
        </div>
    </div>
</div>
<div>
    <img src="banner.jpg" class="banner" alt="banner">
    <div class="centered">
        <h1><ctg:sloganTag/></h1>
        <c:if test="${sessionScope.message != null}">
            <h5 style="color: red; text-align: center"><fmt:message key="${sessionScope.message}"/></h5>
        </c:if>
        <div class="buttons">
            <c:if test="${sessionScope.user == null}">
                <a href="login.jsp" class="log-button"><fmt:message key="button.login"/></a>
                <a href="signUp.jsp" class="log-button" style="margin-top: 5px;"><fmt:message key="button.signUp"/></a>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>