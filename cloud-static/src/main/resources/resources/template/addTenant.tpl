<div class="container">
    <div id="top"/>
    <div id="tenentContainer" class="container">
    <br>
    <h1>租户注册</h1>
    <div class="alert alert-danger" style="display: none;">
        <ul id="alert_ul"></ul>
    </div>
    <form role="form" class="form-horizontal">
            <div class="form-group">
                <label class="col-md-4 control-label" for="company">公司名</label>
                <div class="col-md-6">
                    <input class="form-control" id="company" name="company" type="text" placeholder="请输入公司名"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label" for="domain">域名</label>
                <div class="col-md-6">
                    <input class="form-control" id="domain" name="domain" type="text" placeholder="请输入域名"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label" for="adUser">管理员用户名</label>
                <div class="col-md-6">
                    <input class="form-control" id="adUser" name="adUser" type="text" placeholder="请输入管理员用户名"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label" for="email">电子邮箱</label>
                <div class="col-md-6">
                    <input class="form-control" id="email" name="email" type="text" placeholder="请输入电子邮箱"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label" for="phone">联系电话</label>
                <div class="col-md-6">
                    <input class="form-control" id="phone" name="phone" type="text" placeholder="请输入联系电话"/>
                </div>
            </div>
    </form>
            <div class="row col-md-offset-6">
                <button id="btn_reg" class="btn btn-primary tenant-save">注册</button>
            </div>
    </div>
    <div class="footer">
        <p>&copy; Company 2013</p>
    </div>
</div>