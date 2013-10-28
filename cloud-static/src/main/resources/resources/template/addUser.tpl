<div class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">{{if id}}编辑{{else}}创建{{/if}}用户</h4>
            </div>
            <div class="modal-body">
                <form role="form">
                    <div class="form-group">
                        <label for="name">帐号</label>
                        {{if id}}
                       <p class="form-control-static" id="name" name="name">{{:name}}</p>
                        {{else}}
                         <input type="text" class="form-control" id="name" name="name" placeholder="请输入用户账号">
                        
                        {{/if}}
                    </div>
                    <div class="form-group">
                        <label for="password">密码</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" value="{{:password}}">
                    </div>
                    <div class="form-group">
                        <label for="surname">姓</label>
                        <input type="text" class="form-control" id="surname" name="surname" placeholder="请输入用户姓" value="{{:surname}}">
                    </div>
                    <div class="form-group">
                        <label for="givenName">名</label>
                        <input type="text" class="form-control" id="givenName" name="givenName" placeholder="请输入用户名" value="{{:givenName}}">
                    </div>
                    <div class="form-group">
                        <label for="dept">部门</label>
                        <input type="text" class="form-control" id="dept" name="dept" placeholder="请输入部门" value="{{:dept}}">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary user-save">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>