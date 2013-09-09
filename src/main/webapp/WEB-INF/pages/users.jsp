<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="common/head.jsp" %>
<body>
<div class="container span10 center">
    <%@include file="common/nav.jsp" %>
    <div class="text-center center">
        <%@include file="common/userLeft.jsp"%>
        <div class="span6">
            <table class="table table-striped table-hover">
                <tr>
                    <th>用户名</th>
                    <th>部门</th>
                </tr>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.name}</td>
                        <td>${user.dept}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${contextPath}/resources/js/login.js"></script>
</body>
</html>
