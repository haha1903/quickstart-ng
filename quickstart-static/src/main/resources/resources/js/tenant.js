var Tenant = Backbone.Model.extend({
    urlRoot: '/tenant'
});
var Tenants = Backbone.Collection.extend({
    model: Tenant,
    url: '/tenant'
})
var TenantView = Backbone.View.extend({
    tagName: 'tr',
    template: template('tenant'),
    events: {
        'click .tenant-del': 'deleteTenant',
        'click .tenant-disable': 'disableTenant',
        'click .tenant-enable': 'enableTenant',
        'click .tenant-edit': 'editTenant'
    },
    initialize: function() {
        this.model.bind('change', this.render, this);
        this.model.bind('destroy', this.remove, this);
    },
    render: function () {
        $(this.el).html(this.template.render(this.model.toJSON()));
        return this;
    },
    deleteTenant: function() {
        var self = this;
        var modal = admin.showModal('confirmDialog', {
        	title: '删除租户',
        	message: '确定要删除租户 '+this.model.get('company')+' 吗？所有相关资源都将被释放！',
        	button: (BUTTON_OK | BUTTON_CANCEL) 
        }).on('shown.bs.modal', function() {
            $(this).find('.btn-confirm').bind('click', function() {
                self.model.destroy({success: function() {
                    modal.modal('hide');
                }, error: function() {
                    alert('delTenantFail');
                }, wait: true});
            })
        });
    },
    disableTenant: function() {
        var self = this;
        var modal = admin.showModal('confirmDialog', {
        	title: '锁定租户',
        	message: '确定要锁定租户"'+this.model.get('company')+'"吗？该租户所有相关服务都将暂时停止！',
        	button: (BUTTON_OK | BUTTON_CANCEL)
        }).on('shown.bs.modal', function() {
            $(this).find('.btn-confirm').bind('click', function() {
                self.model.save({status:'DISABLED'},{
                	success: function() {
                		modal.modal('hide');
                	// $('.tenant-refresh').trigger('click');
                	}, error: function() {
                     alert('更新租户状态失败.');
                }, wait: true});
            });
        });
    },
    enableTenant: function() {
        var self = this;
        var modal = admin.showModal('confirmDialog', {
        	title: '启用租户',
        	message: '确定要启用租户"'+this.model.get('company')+'"吗？请确保<br/><ul><li>虚拟机</li><li>AD</li><li>邮箱</li></ul>已经配置完成。',
        	button: (BUTTON_OK | BUTTON_CANCEL)
        }).on('shown.bs.modal', function() {
            $(this).find('.btn-confirm').bind('click', function() {
                self.model.save({status:'ENABLED'},{
                	success: function() {
                	modal.modal('hide');
                	//self.render();
                	}, error: function() {
                    alert('更新租户状态失败.');
                	}, wait: true});
            });
        });
    },
    editTenant: function() {
        var self = this;
        var modal = admin.showModal('editTenant', this.model.toJSON()).on('shown.bs.modal', function() {
            $(this).find('.tenant-save').bind('click', function() {
            	self.saveTenant(modal,self.model);
            });
        });
    },
    saveTenant: function(modal,tenant) {
        modal.find('input').each(function() {
            var input = $(this);
            tenant.set(input.attr('name'), input.val());
        });
        tenant.save(null, {success: function() {
            modal.modal('hide');
        }, error: function() {
            alert('save tenant failure');
        }, wait: true});
    },
    remove: function() {
        $(this.el).remove();
    }
});
var TenantManagerView = Backbone.View.extend({
    el: '#content',
    template: template('tenantManager'),
    collection: new Tenants(),
    events: {
        'click .tenant-refresh': 'refresh',
        'click .tenant-add': 'addTenantModal'
    },
    initialize: function () {
        this.collection.bind('add', this.add, this);
        this.collection.bind('fetch', this.addAll, this);
    },
    render: function () {
        this.$el.html(this.template.render());
        this.collection.reset();
        this.collection.fetch();
        return this;
    },
    add: function (tenant) {
        var tenantView = new TenantView({model: tenant});
        $('.tenant tbody').append(tenantView.render().el);
    },
    addAll: function () {
        this.collection.each(this.add);
    },
    refresh: function () {
    	this.collection.fetch();
    },
    addTenantModal: function() {
        var self = this;
        var modal = admin.showModal('editTenant',{}).on('shown.bs.modal', function() {
            $(this).find('.tenant-save').bind('click', function() {
                self.saveTenant(modal);
            });
            self.setValidation(modal);
        });
    },   
	setValidation: function(modal){
    	modal.find("form").validate({
    		rules : {
    		    company : {required : true,
    		    	       minlength : 2,
    		    	       maxlength : 255
    		    	       },
    		    domain : {required : true,
    		    	      domain : true,
    		    	      minlength : 3,
    		    	      maxlength : 255,
    		    	      remote : "/tenant/checkDomain/"
    		    	      },
    		    adUser : {required : true,
    		    	      minlength : 2,
    		    	      maxlength : 32},
    		    initPassword : {required : true,
        		    	      minlength : 8,
        		    	      maxlength : 32},
    		    email : {required : true,
    		    	     email : true},
    		    phone : {phone : true}
    		}
    		    					
    	});
    },   
    saveTenant: function(modal) {
        var tenant = new Tenant();
        modal.find('input').each(function() {
            var input = $(this);
            tenant.set(input.attr('name'), input.val());
        });
        tenant.set('status','PENDING');
        this.collection.create(tenant, {success: function() {
            modal.modal('hide');
        }, error: function() {
            alert('save user failure');
        }, wait: true});
    }
});
