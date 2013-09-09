<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="common/head.jsp" %>
<body>
<div class="container span10 center">
    <%@include file="common/nav.jsp" %>
    <div class="text-center center">
        <%@include file="common/userLeft.jsp" %>
        <div class="span6">
            <table class="table table-striped table-hover">
                <tr>
                    <th>用户名</th>
                    <th>部门</th>
                    <th>服务</th>
                </tr>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>
                            <%--<a href="${contextPath}/userinfo?id=${user.id}">--%>
                                    ${user.name}
                            <%--</a>--%>
                        </td>
                        <td>${user.dept}</td>
                        <td>
                            <c:forEach var="service" items="${services}">
                                ${service.name}<input name="${service.name}" class="service" type="checkbox" checked disabled="disabled">
                            </c:forEach>
                        </td>
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
