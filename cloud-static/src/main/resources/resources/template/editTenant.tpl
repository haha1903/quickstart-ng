<div class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">{{if id}}修改{{else}}创建{{/if}}租户</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="company">公司名</label>
                        <div class="col-md-6">
                            <input class="form-control" id="company" name="company" type="text" placeholder="请输入公司名" value="{{:company}}"/>
                            
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="domain">域名</label>
                        <div class="col-md-6">
                            <input class="form-control" id="domain" name="domain" type="text" placeholder="请输入域名" value="{{:domain}}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="adUser">管理员用户名</label>
                        <div class="col-md-6">
                            <input class="form-control" id="adUser" name="adUser" type="text" placeholder="请输入管理员用户名" value="{{:adUser}}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="initPassword">密码</label>
                        <div class="col-md-6">
                            <input class="form-control" type="text" id="initPassword" name="initPassword" type="text" placeholder="请输入密码" value="{{:initPassword}}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="email">电子邮箱</label>
                        <div class="col-md-6">
                            <input class="form-control" id="email" name="email" type="text" placeholder="请输入电子邮箱" value="{{:email}}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="phone">联系电话</label>
                        <div class="col-md-6">
                            <input class="form-control" id="phone" name="phone" type="text" placeholder="请输入联系电话" value="{{:phone}}" />
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary tenant-save">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>