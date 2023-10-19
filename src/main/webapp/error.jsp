<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>

<h1>Error</h1>
<c:out value="${requestScope.message}"/>
<a class="link" href="${pageContext.request.contextPath}/index.jsp">Back to main page</a>

</body>
</html>
