<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="common/head.jsp" %>
<body>
<%@include file="common/nav.jsp" %>
<div class="container">
    <div class="text-center">
        <div class="row">
            <input class="span4" name="name" type="text" placeholder="服务名称"/>
        </div>
        <div class="row">
            <button id="btn_addService" class="btn btn-info span2 center">添加服务</button>
        </div>
    </div>
</div>
<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${contextPath}/resources/js/addService.js"></script>
</body>
</html>
