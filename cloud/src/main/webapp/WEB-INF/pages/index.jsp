<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="head.jsp" %>
<body>
<%@include file="nav.jsp" %>
<div class="demo-headline">Portal</div>
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
            <button id="btn_reg" class="btn btn-primary">注册</button>
        </div>
    </div>
</div>
<%@include file="foot.jsp" %>
<script type="text/javascript" src="${contextPath}/resources/js/reg.js"></script>
</body>
</html>
