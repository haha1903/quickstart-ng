var User = Backbone.Model.extend({
    urlRoot: '/user'
});
var Users = Backbone.Collection.extend({
    model: User,
    url: '/user'
});
var UserDept = Backbone.Model.extend({
	urlRoot: '/user/dept'
});

var UserView = Backbone.View.extend({
    tagName: 'tr',
    template: template('user'),
    events: {
        'click .delThis': 'deleteUser',
        'click .editThis': 'editUser'        
    },
    initialize: function() {
        this.model.bind('change', this.render, this);
        this.model.bind('destroy', this.remove, this);
    },
    render: function () {
        $(this.el).html(this.template.render(this.model.toJSON()));
        return this;
    },
    deleteUser: function() {
        var self = this;
        var modal = app.showModal('confirmDialog', {
        	title: '删除用户',
        	message: '确定要删除"'+this.model.get('name')+'"吗？',
        	button: (BUTTON_OK | BUTTON_CANCEL)}).on('shown.bs.modal', function() {
        	 
            $(this).find('.btn-confirm').bind('click', function() {
                self.model.destroy({success: function() {
                    modal.modal('hide');
                }, error: function() {
                    alert('删除用户失败。');
                }, wait: true});
            })
        });
    },
    editUser: function() {
        var self = this;
        var modal = app.showModal('addUser',this.model.toJSON()).on('shown.bs.modal', function() {
        	
            $(this).find('.user-save').bind('click', function() {
                self.saveUser(modal,self.model);
            });
            $(this).find('#password').bind('keyup', app.checkPassword);
            $(this).find('form').validate({
        		rules : {
        		    surname : {required : true,
        		    	      },
        		    password : {required : true,
        		    	      minlength : 6,
        		    	      maxlength : 32},
        		    passwordConfirm : {equalTo: "#password"}
        		},
            	messages: {
            		passwordConfirm:{equalTo: '两次输入的密码不一致！'}
            	}
        	});
        });
    },
    saveUser: function(modal,user) {
        modal.find('input').each(function() {
            var input = $(this);
            user.set(input.attr('name'), input.val());
        });
        user.save(null, {success: function() {
            modal.modal('hide');
        }, error: function() {
            alert('save user failure');
        }, wait: true});
    },
    remove: function() {
        $(this.el).remove();
    }
});
var UsersView = Backbone.View.extend({
    el: '#content',
    template: template('userManager'),
    collection: new Users(),
    department: new UserDept(),
    events: {
        'click .user-refresh': 'refresh',
        'click #user-add': 'addUserModal',
        'click .permission-save': 'savePermission'
    },
    initialize: function () {
    	this.collection.bind('add', this.add, this);
        this.collection.bind('remove', this.refetchDept, this);
        this.collection.bind('change', this.refetchDept, this);
        this.department.on('change',this.updateDepartment,this);
    },
    render: function () {
    	this.$el.html(this.template.render());
    	
        this.collection.reset();
        this.collection.fetch();
        this.refetchDept();
        
        return this;
    },
    add: function (user) {
        var userView = new UserView({model: user});
        $('#users tbody').append(userView.render().el);
    },
    refresh: function () {
    	this.collection.fetch();
        this.refetchDept();
    },
    refetchDept: function() {
    	this.department.clear({silent:true});
    	this.department.fetch();
    },
    updateDepartment: function()  {
    	var userDept = convertMap(this.department.toJSON());
    	$('.sideListCont').html(template('userDepartment').render(userDept));
    	var total=0;
    	for (i=0;i<userDept.length;i++) total=total+userDept[i].value;
    	$('#total').html(total);
    },
    addUserModal: function() {
        var self = this;
        var modal = app.showModal('addUser',{}).on('shown.bs.modal', function() {
            $(this).find('#user-save').bind('click', function() {
                self.saveUser(modal);
            });
            $(this).find('#password').bind('keyup', app.checkPassword);
            $(this).find('form').validate({
        		rules : {
        		    name : {required : true,
        		    	    account: true,
        		    	       minlength : 2,
        		    	       maxlength : 16,
        		    	       remote : {url:"/user/checkUser" ,
   		    	    	        type:"post",
   		    	    	        contentType:"application/x-www-form-urlencoded"}
        		    	       },
        		    surname : {required : true,
        		    	      },
        		    password : {required : true,
        		    	      minlength : 5,
        		    	      maxlength : 32},
        		    passwordConfirm : {required : true,
        		    			equalTo: "#password"}
        		},
            	messages: {
            		passwordConfirm:{equalTo: '两次输入的密码不一致！'}
            	}
        	});
        });
    },
    savePermission: function(){
    	
    },
    
    saveUser: function(modal) {
        var user = new User();
        modal.find('input').each(function() {
            var input = $(this);
            if (input.attr('name')!='passwordConfirm') user.set(input.attr('name'), input.val());
        });
        user.set('tenantId', 1);
        this.collection.create(user, {success: function() {
            modal.modal('hide');
        }, error: function() {
            alert('save user failure');
        }, wait: true});
    }
});
