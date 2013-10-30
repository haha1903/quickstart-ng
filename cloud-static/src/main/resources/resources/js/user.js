var User = Backbone.Model.extend({
    urlRoot: '/user'
});
var Users = Backbone.Collection.extend({
    model: User,
    url: '/user'
})
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
    events: {
        'click .user-refresh': 'refresh',
        'click .user-add': 'addUserModal',
        'click .permission-save': 'savePermission'
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
    add: function (user) {
        var userView = new UserView({model: user});
        $('#user tbody').append(userView.render().el);
    },
    addAll: function () {
        this.collection.each(this.add);
    },
    refresh: function () {
        this.collection.fetch();
    },
    addUserModal: function() {
        var self = this;
        var modal = app.showModal('addUser',{}).on('shown.bs.modal', function() {
            $(this).find('.user-save').bind('click', function() {
                self.saveUser(modal);
            });
        });
    },
    savePermission: function(){
    	
    },
    
    saveUser: function(modal) {
        var user = new User();
        modal.find('input').each(function() {
            var input = $(this);
            user.set(input.attr('name'), input.val());
        });
        user.set('tenantId', 1);
        this.collection.create(user, {success: function() {
            modal.modal('hide');
        }, error: function() {
            alert('save user failure');
        }, wait: true});
    }
});
