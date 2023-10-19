<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<html lang="${sessionScope.locale}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title><fmt:message key="order.title"/></title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <style>
        <%@include file="/Styles/order.css" %>
    </style>
</head>
<body>
<div class="container" id="container">
    <div class="container align-content-center">
        <br>
        <h2 class="text-center" style="margin-top: 50px; color: aliceblue"><fmt:message key="order.header"/></h2>
        <div id="locale-changer" class="form-control">
            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="changeLocale">
                <input type="hidden" name="page-path" value="/order.jsp">
                <input class="btn" style="background: lightgray; width: 50px" type="submit" name="locale" value="az">
            </form>
            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="changeLocale">
                <input type="hidden" name="page-path" value="/order.jsp">
                <input class="btn" style="background: lightgray; width: 50px;" type="submit" name="locale" value="en">
            </form>
        </div>
        <hr style="background-color: aliceblue">
        <form id="order-form" method="post" action="${pageContext.request.contextPath}/controller"
              enctype='multipart/form-data'>
        <input type="hidden" name="command" value="createOrder"/>

            <div class="row">
                <div class="card mx-auto col-sm-6" style="margin-right: 10px! important">
                    <article class="card-body mx-auto">
                        <h3 class="card-title mt-3 text-center"><fmt:message key="order.label.header"/></h3>
                        <br>
                        <div id="info">
                            <c:set var="airplane" scope="session" value="${sessionScope.airplane}"/>
                            <input type="hidden" name="id" value="<c:out value="${airplane.id}"/>">
                            <p><fmt:message key="airplane.label.name"/>:
                                <c:out value="${airplane.name}"/>
                            <p><fmt:message key="airplane.label.price"/>:
                                <c:out value="${airplane.price}$"/>
                            <p><fmt:message key="airplane.label.free.seats"/>:
                                <c:out value="${sessionScope.freeSeats}"/>
                            <p><fmt:message key="airplane.label.leaving.date"/>:
                                <c:out value="${airplane.startOfTheAirplane}"/>
                            <p><fmt:message key="airplane.label.arriving.date"/>:
                                <c:out value="${airplane.endOfTheAirplane}"/>
                            <p><fmt:message key="airplane.label.departure"/>:
                                <c:out value="${airplane.route.departure}"/>
                            <p><fmt:message key="airplane.label.destination"/>:
                                <c:out value="${airplane.route.destination}"/>
                        </div>
                    </article>
                </div>
                <form>
                    <div class="card mx-auto col-sm-6" style="margin-left: 10px! important">
                        <article class="card-body mx-auto">
                            <h3 class="card-title mt-3 text-center"><fmt:message key="form.header"/></h3>
                            <br>
                            <c:if test="${sessionScope.message != null}">
                                <h5 style="color: red; text-align: center"><fmt:message
                                        key="${sessionScope.message}"/></h5>
                            </c:if>
                            <div class="passport_form">
                                <h5><strong><fmt:message key="order.label.passport"/></strong></h5>
                                <input type="file" placeholder="<fmt:message key="button.submit"/>" name="passport"  accept="image/png"/>
                                <input type="submit" id="submit_btn" value="<fmt:message key="button.order"/>"/>
                            </div>
                        </article>
                    </div>
                </form>
            </div>
        </form>
    </div>
</div>
</body>
</html>