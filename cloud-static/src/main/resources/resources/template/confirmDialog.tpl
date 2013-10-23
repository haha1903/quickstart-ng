<div class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">{{:title}}</h4>
            </div>
            <div class="modal-body">
                {{:message}}
            </div>
            <div class="modal-footer">
                {{if (button & 16)}}
                <button type="button" class="btn btn-default btn-confirm">确定</button>
                {{/if}}
                {{if (button & 8)}}
                <button type="button" class="btn btn-default btn-yes">是</button>
                {{/if}}
                {{if (button & 4)}}
                <button type="button" class="btn btn-default btn-no">否</button>
                {{/if}}
                <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>