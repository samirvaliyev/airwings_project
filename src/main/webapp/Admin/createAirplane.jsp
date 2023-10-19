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

    <title><fmt:message key="create.airplane.title"/></title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <style>
        <%@include file="/Styles/adminForm.css" %>
    </style>
</head>
<body>
<div class="navbar row">
    <div class="navElements col-sm-6">
        <a>
            <form id="account" class="navbar-form" method="get"
                  action="${pageContext.request.contextPath}/controller">
                <input type="submit" class="btn col" value="<fmt:message key="button.profile"/>">
                <input type="hidden" name="command" value="profile"/>
            </form>
        </a>
        <a class="active" href="../index.jsp"><fmt:message key="navbar.home"/></a>
        <a>
            <form id="catalog" class="navbar-form" method="get"
                  action="${pageContext.request.contextPath}/controller">
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
                <input type="hidden" name="page-path" value="/Admin/createAirplane.jsp">
                <input class="btn" style="background: lightgray; width: 50px" type="submit" name="locale"
                       value="az">
            </form>
            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="changeLocale">
                <input type="hidden" name="page-path" value="/Admin/createAirplane.jsp">
                <input class="btn" style="background: lightgray; width: 50px;" type="submit" name="locale"
                       value="en">
            </form>
        </div>
    </div>
</div>

<form id="form" method="get" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="createAirplane"/>
    <hr style="background-color: aliceblue">
    <div class="row">
        <div class="container col-sm-6" id="container">
            <div class="card mx-auto col-sm-6" style="">
                <article class="card-body mx-auto">
                    <h3 class="card-title mt-3 text-center"><fmt:message key="form.header"/></h3>
                    <br>
                    <c:if test="${sessionScope.message != null}">
                        <h5 style="color: red; text-align: center"><fmt:message
                                key="${sessionScope.message}"/></h5>
                    </c:if>
                        <div class="form-group">
                            <label for="airplane_name"><fmt:message key="airplane.label.name"/></label>
                            <input
                                    type="text"
                                    name="airplane_name"
                                    id="airplane_name"
                                    placeholder="<fmt:message key="airplane.placeholder.name"/>"
                                    class="form-control col">
                        </div>
                        <div class="form-group">
                            <label for="flight_name"><fmt:message key="airplane.label.flight.name"/></label>
                            <input
                                    type="text"
                                    name="flight_name"
                                    id="flight_name"
                                    placeholder="<fmt:message key="airplane.placeholder.name"/>"
                                    class="form-control col">
                        </div>
                        <div class="form-group">
                            <label for="passenger_capacity"><fmt:message key="flight.label.capacity"/></label>
                            <input
                                    type="number"
                                    name="passenger_capacity"
                                    id="passenger_capacity"
                                    value="0"
                                    placeholder="<fmt:message key="flight.placeholder.capacity"/>"
                                    class="form-control col">
                        </div>

                        <div class="form-group">
                            <label for="departure"><fmt:message key="route.label.departure"/></label>
                            <input
                                    type="text"
                                    name="departure"
                                    id="departure"
                                    placeholder="<fmt:message key="route.placeholder.departure"/>"
                                    class="form-control col">
                        </div>
                        <div class="form-group">
                            <label for="destination"><fmt:message key="route.label.destination"/></label>
                            <input
                                    type="text"
                                    name="destination"
                                    id="destination"
                                    placeholder="<fmt:message key="route.placeholder.destination"/>"
                                    class="form-control col">
                        </div>
                            <% LocalDate date = LocalDate.now();%>
                        <div class="form-group">
                            <label for="start_date"><fmt:message key="airplane.label.leaving.date"/></label>
                            <input
                                    type="date"
                                    name="start_date"
                                    id="start_date"
                                    min="<%=date%>"
                                    value="00/00/0000"
                                    class="form-control col">
                        </div>
                        <div class="form-group">
                            <label for="end_date"><fmt:message key="airplane.label.arriving.date"/></label>
                            <input
                                    type="date"
                                    name="end_date"
                                    id="end_date"
                                    min="<%=date%>"
                                    value="00/00/0000"
                                    class="form-control col">
                        </div>
                        <div class="form-group">
                            <label for="price"><fmt:message key="airplane.label.price"/>$</label>
                            <input
                                    type="number"
                                    name="price"
                                    id="price"
                                    value="0"
                                    placeholder="<fmt:message key="airplane.placeholder.price"/>$"
                                    class="form-control col">
                        </div>
                        <div class="form-group">
                            <label for="transit_time"><fmt:message key="route.label.transitTime"/></label>
                            <input
                                    type="number"
                                    name="transit_time"
                                    id="transit_time"
                                    value="0"
                                    placeholder="<fmt:message key="route.placeholder.transitTime"/>"
                                    class="form-control col">
                        </div>
                        <div class=form-group" style="margin-top: 30px">
                            <button type="submit" id="login-submit" class="btn final-btn col"><fmt:message
                                    key="admin.button.create"/></button>
                        </div>
                        <p class="text-center">
                            <fmt:message key="form.label.back"/>
                            <a href="adminAccount.jsp" style="color: green"><fmt:message
                                    key="button.back"/></a>
                        </p>
                </article>
            </div>
        </div>
        <div class="col-sm-6 container" style="margin-top: 60px">
            <h4 class="text-center"><fmt:message key="create.staff.title"/></h4>
            <div class="card  mx-auto">
                <article class="card-body mx-auto" style="max-width: 400px">
                    <div class="form-group">
                        <h4 class="text-center"><fmt:message key="admin.entity.label.staff"/></h4>
                        <label for="first_name1"><fmt:message key="user.label.firstName"/></label>
                        <input
                                type="text"
                                name="first_name1"
                                id="first_name1"
                                placeholder="<fmt:message key="signUp.placeholder.firstName"/>"
                                required
                                class="form-control col">
                    </div>
                    <div class="form-group">
                        <label for="last_name1"><fmt:message key="user.label.lastName"/></label>
                        <input
                                type="text"
                                name="last_name1"
                                id="last_name1"
                                placeholder="<fmt:message key="signUp.placeholder.lastName"/>"
                                required
                                class="form-control col">
                    </div>
                </article>
            </div>
            <div class="card  mx-auto">
                <article class="card-body mx-auto" style="max-width: 400px;">
                    <div class="form-group">
                        <h4 class="text-center"><fmt:message key="admin.entity.label.staff"/></h4>
                        <label for="first_name2"><fmt:message key="user.label.firstName"/></label>
                        <input
                                type="text"
                                name="first_name2"
                                id="first_name2"
                                placeholder="<fmt:message key="signUp.placeholder.firstName"/>"
                                required
                                class="form-control col">
                    </div>
                    <div class="form-group">
                        <label for="last_name2"><fmt:message key="user.label.lastName"/></label>
                        <input
                                type="text"
                                name="last_name2"
                                id="last_name2"
                                placeholder="<fmt:message key="signUp.placeholder.lastName"/>"
                                required
                                class="form-control col">
                    </div>
                </article>
            </div>
            <div class="card  mx-auto">
                <article class="card-body mx-auto" style="max-width: 400px;">
                    <div class="form-group">
                        <h4 class="text-center"><fmt:message key="admin.entity.label.staff"/></h4>
                        <label for="first_name3"><fmt:message key="user.label.firstName"/></label>
                        <input
                                type="text"
                                name="first_name3"
                                id="first_name3"
                                placeholder="<fmt:message key="signUp.placeholder.firstName"/>"
                                required
                                class="form-control col">
                    </div>
                    <div class="form-group">
                        <label for="last_name3"><fmt:message key="user.label.lastName"/></label>
                        <input
                                type="text"
                                name="last_name3"
                                id="last_name3"
                                placeholder="<fmt:message key="signUp.placeholder.lastName"/>"
                                required
                                class="form-control col">
                    </div>
                </article>
            </div>
        </div>
    </div>
</form>
</body>
</html>
