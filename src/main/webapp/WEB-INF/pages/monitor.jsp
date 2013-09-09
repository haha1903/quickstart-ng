<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="common/head.jsp" %>
<body>
<div class="container span10 center">
    <%@include file="common/nav.jsp" %>
    <div class="text-center">
        <h3 class="demo-title">IT基础架构</h3>
        <div class="demo-content-wide">
            <table class="table table-striped table-hover">
                <tr>
                    <th>服务名称</th>
                    <th>CPU</th>
                    <th>内存（MB）</th>
                    <th>磁盘（GB）</th>
                </tr>
                <c:forEach var="server" items="${servers}">
                    <tr>
                        <td>${server.name}</td>
                        <td>${server.vcpu}</td>
                        <td>${server.ram}</td>
                        <td>${server.disk}</td>
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
