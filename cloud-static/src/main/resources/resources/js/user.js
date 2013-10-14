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
        'click .user-del': 'deleteUser'
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
        var modal = app.showModal('delUser', {name: this.model.get('name')}).on('shown.bs.modal', function() {
            $(this).find('.user-del').bind('click', function() {
                self.model.destroy({success: function() {
                    modal.modal('hide');
                }, error: function() {
                    alert('delUserFail');
                }, wait: true});
            })
        });
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
        'click .user-add': 'addUserModal'
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
        $('.user tbody').append(userView.render().el);
    },
    addAll: function () {
        this.collection.each(this.add);
    },
    refresh: function () {
        this.collection.fetch();
    },
    addUserModal: function() {
        var self = this;
        var modal = app.showModal('addUser').on('shown.bs.modal', function() {
            $(this).find('.user-save').bind('click', function() {
                self.saveUser(modal);
            });
        });
    },
    saveUser: function(modal) {
        var user = new User();
        modal.find('input').each(function() {
            var input = $(this);
            user.set(input.attr('name'), input.val());
        });
        user.set('tenant', {id: 1, admin: 'admin'});
        this.collection.create(user, {success: function() {
            modal.modal('hide');
        }, error: function() {
            alert('save user failure');
        }, wait: true});
    }
});
