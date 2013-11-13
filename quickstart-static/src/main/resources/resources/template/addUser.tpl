<div class="modal fade" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="cnmTitl"><!-- dia-head -->
                <ul class="bdTab">
                    <li class="bdtas">{{if id}}编辑{{else}}创建新{{/if}}用户</li>
                </ul>
                <ul class="diactr">
                    <li class="clsx" data-dismiss="modal"></li>
                </ul>
            </div>
            <div class="diaDataCon cloudDia_1"><!-- dia-container -->
                <div class="alert alert-danger" style="display:none;">
                    <ul id="alert_ul"></ul>
                </div>
                <form action="">
                <table class="tableSty_2" width="100%" border="0" cellspacing="0">
                    <tr>
                        {{if id}}
                        <td class="odName">{{:name}}</td>
                        {{else}}
                        <td>
                                <label class="sr-only" for="name">用户名</label>
                                <input type="text" class="abInputSty" id="name" name="name" placeholder="用户名">
                        </td>
                        {{/if}}                        
                    </tr>
                    <tr>
                        <td>
                                <label class="sr-only" for="surname">姓</label>
                                <input type="text" id="surname" name="surname" class="abInputSty" placeholder="姓" value="{{:surname}}">
                        </td>
                    </tr>
                    <tr>
                        <td>
                                <label class="sr-only" for="givenName">名</label>
                                <input type="text" id="givenName" name="givenName" class="abInputSty" placeholder="名" value="{{:givenName}}">
                        </td>
                    </tr>
                    <tr>
                        <td><label class="sr-only" for="dept">部门</label><input type="text" id="dept" name="dept" class="abInputSty" placeholder="部门" value="{{:dept}}"></td>
                    </tr>
                    <tr>
                        <td><label class="sr-only" for="password">密码</label><input type="password" class="abInputSty" id="password" name="password" placeholder="密码"></td>
                    </tr>
                    <tr class="abFormTip">
                        <td>
                            <span class="pswstr"><i class="shypsw weak cur">弱</i></span
                            ><span class="pswstr"><i class="shypsw middle">中</i></span
                            ><span class="pswstr"><i class="shypsw vertnice">强</i></span>
                        </td>
                    </tr>
                    <tr>
                        <td><label class="sr-only" for="passwordConfirm">确认密码</label><input type="password" class="abInputSty" id="passwordConfirm" name="passwordConfirm" placeholder="确认密码"></td>
                    </tr>
                </table>
                </form>
            </div>
            
            
            <div class="cAncCtr"><!-- dia-control -->
                <div class="btnWite cAncBtn" data-dismiss="modal">取消</div>
                <div class="btnBlue cAncBtn"  id="user-save">确定</div>
            </div>
        </div>

    </div>
</div>