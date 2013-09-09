<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar span10">
    <div class="navbar-inner">
        <div class="container">
            <button data-target="#nav-collapse-01" data-toggle="collapse" class="btn btn-navbar" type="button">
            </button>
            <a class="brand datayes-logo" target="_blank" href="http://www.datayes.com"></a>

            <div id="nav-collapse-01" class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active"><a href="${contextPath}/service">Portal</a></li>
                    <%--<li><a href="${contextPath}/signup">Sign Up</a></li>--%>
                    <li><a href="${contextPath}/monitor">监控</a></li>
                    <li><a href="${contextPath}/users">用户管理</a></li>
                    <%--<li><a href="${contextPath}/service">Login</a></li>--%>
                    <li><a href="${contextPath}/consumer?logout">Logout</a></li>
                </ul>
                <form action="" class="navbar-search form-search pull-right">
                    <div class="input-append">
                        <input type="text" placeholder="Search" class="search-query span2">
                        <button class="btn btn-large" type="submit">
                            <i class="fui-search"></i>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
