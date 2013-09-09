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
            <div>用户名：${user.name}</div>
            <table class="table table-striped table-hover">
                <tr>
                    <th>服务</th>
                    <th>状态</th>
                </tr>
                <c:forEach var="service" items="${services}">
                    <tr>
                        <td>${service.name}</td>
                        <td><input class="service_status" name="${service.name}" user="${user.id}" type="checkbox" ${service.enabled ? 'checked' : ''}></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${contextPath}/resources/js/userinfo.js"></script>
</body>
</html>
