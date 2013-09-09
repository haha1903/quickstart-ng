<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="common/head.jsp" %>
<body>
<%@include file="common/nav.jsp" %>
<div class="container">
    <div class="text-center">
        <div class="row">
            <input class="span4" name="admin" type="text" placeholder="用户"/>
        </div>
        <div class="row">
            <input class="span4" name="password" type="password" placeholder="密码"/>
        </div>
        <div class="row">
            <input class="span4" name="name" type="text" placeholder="公司"/>
        </div>
        <div class="row">
            <button id="btn_reg" class="btn btn-info span2 center">注册</button>
        </div>
    </div>
</div>
<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${contextPath}/resources/js/reg.js"></script>
</body>
</html>
