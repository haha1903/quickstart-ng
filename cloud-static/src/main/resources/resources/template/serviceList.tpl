    <div class="con_nTitle">服务列表</div>
    <div class="con_nCon clearfix">
                <div class="con_columns"><!-- each colum -->
                    <dl class="colInPadding">
                        <dt class="col_t">投资管理服务</dt>
                        {{if type=="trading"}}
                            <a class="col_l"><img src="{{:~s('img/src/business/cloud/icons-app/')}}{{if image}}{{:image}}{{else}}noneImg.gif{{/if}}" alt="">{{:name}}</a>
                        {{/if}}
                    </dl>
                </div>
                <div class="con_columns"><!-- each colum -->
                    <dl class="colInPadding">
                        <dt class="col_t">办公服务</dt>
                        {{if type=="office"}}
                            <a class="col_l"><img src="{{:~s('img/src/business/cloud/icons-app/')}}{{if image}}{{:image}}{{else}}noneImg.gif{{/if}}" alt="">{{:name}}</a>
                        {{/if}}
                    </dl>
                </div>
                <div class="con_columns"><!-- each colum -->
                    <dl class="colInPadding">
                        <dt class="col_t">其它服务</dt>
                        {{if type=="other"}}
                            <a class="col_l"><img src="{{:~s('img/src/business/cloud/icons-app/')}}{{if image}}{{:image}}{{else}}noneImg.gif{{/if}}" alt="">{{:name}}</a>
                        {{/if}}
                    </dl>
                </div>
    </div>
