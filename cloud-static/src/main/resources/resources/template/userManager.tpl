<div class="con_nTitle"><div class="orangeBtn" id="addUser">添加用户</div>用户管理</div>
            <div class="con_nCon">
                <div class="con_nConPadding_1 clearfix">
                    <div class="con_nLeft_1">
                        <div class="siderList">
                            <div class="sideListTitle">全部用户({{:total}})</div>
                            <div class="sideListCont">
                                {{for department}}
                                    <a href="#" class="sideLiks">{{:name}} ({{:number}})</a>
                                {{/for}}
                            </div>
                        </div>
                    </div>
                    <div class="con_nRight_1">
                        <div class="tableWrap_1">
                            <table class="tableSty_1 editable" id="users" border="0" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>用户名</th>
                                        <th>姓名</th>
                                        <th>部门</th>
                                        <th>组合管理</th>
                                        <th>交易系统</th>
                                        <th>行情数据</th>
                                        <th>电子邮件</th>
                                        <th>社交办公</th>
                                        <th>流程管理</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>
            </div>
            