<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="common/head.jsp" %>
<body>
<%@include file="common/nav.jsp" %>
<div class="container">
    <div class="text-center">
        <div class="row">
            <input class="span4" name="name" type="text" placeholder="服务器名称"/>
        </div>
        <div class="row">
            <input class="span4" name="description" type="text" placeholder="服务器描述"/>
        </div>
        <div class="row">
            <input class="span4" name="serverId" type="text" placeholder="服务器ID"/>
        </div>
        <div class="row">
            <input class="span4" name="type" type="text" placeholder="服务器类型"/>
        </div>
        <div class="row">
            <button id="btn_addServer" class="btn btn-info span2 center">添加服务器</button>
        </div>
    </div>
</div>
<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${contextPath}/resources/js/addServer.js"></script>
</body>
</html>
