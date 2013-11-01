<div class="modal fade" >
    <div class="modal-dialog model-confirm">
        <div class="modal-content">
            <div class="cnmTitl nobg"><!-- dia-head -->
                <ul class="bdTab">
                    <li class="bdtas">{{:title}}</li>
                </ul>
                <ul class="diactr">
                    <li class="clsx" data-dismiss="modal"></li>
                </ul>
            </div>
            <div class="diaDataCon cloudDia_2">
                <div class="cArelt">{{:message}}</div>                
            </div>
            <div class="cAncCtr">
                <button type="button" class="btnWite cAncBtn" data-dismiss="modal">取消</button>
                {{if (button & 16)}}
                <button type="button" class="btnBlue cAncBtn btn-confirm">确定</button>
                {{/if}}
                {{if (button & 8)}}
                <button type="button" class="btnBlue cAncBtn btn-yes">是</button>
                {{/if}}
                {{if (button & 4)}}
                <button type="button" class="btnBlue cAncBtn btn-no">否</button>
                {{/if}}
            </div>
        </div>
    </div>
</div>