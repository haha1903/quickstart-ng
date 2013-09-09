<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="common/head.jsp" %>
<body>
<div class="container span10 center">
    <%@include file="common/nav.jsp" %>
    <div class="text-center center">
        <%@include file="common/userLeft.jsp"%>
        <div class="span6">
            <div class="text-center">
                <div class="row">
                    <input class="span4" name="name" type="text" placeholder="用户"/>
                </div>
                <div class="row">
                    <input class="span4" name="password" type="password" placeholder="密码"/>
                </div>
                <div class="row">
                    <input class="span4" name="dept" type="text" placeholder="部门"/>
                </div>
                <div class="row">
                    <button id="btn_addUser" class="btn btn-info span2 center">注册</button>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${contextPath}/resources/js/addUser.js"></script>
</body>
</html>
