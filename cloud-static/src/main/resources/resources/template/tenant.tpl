<td>{{:id}}</td>
<td>{{:company}}</td>
<td>{{:domain}}</td>
<td>{{:adUser}}</td>
<td>{{:initPassword}}</td>
<td>{{:email}}</td>
<td>{{:phone}}</td>
<td>
    {{if status=="PENDING"}}
               <font color = 'blue'>准备中</font>
    {{else status=="ENABLED"}}
               <font color = 'green'>可用</font>
    {{else status=="DELETED"}}
               <font color = 'grey'>删除</font>
    {{else}}
        <font color='red'>禁用</font>
    {{/if}}
</td>
<td>
    {{if status=="PENDING"}}
        <span data-toggle="tooltip" title="编辑" class="glyphicon glyphicon-wrench tenant-edit action-icon"/>
    {{/if}}
    {{if status=="PENDING" || status=="DISABLED"}}
        <span data-toggle="tooltip" title="启用" class="glyphicon glyphicon-ok tenant-enable action-icon"/>
    {{else status=="ENABLED"}}
        <span data-toggle="tooltip" title="停用" class="glyphicon glyphicon-lock tenant-disable action-icon"/>
    {{/if}}
    {{if status!="DELETED"}}
        <span data-toggle="tooltip" title="删除" class="glyphicon glyphicon-remove tenant-del action-icon"/>
    {{/if}}
</td>