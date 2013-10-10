var User = Backbone.Model.extend({});
var Users = Backbone.Collection.extend({
    model: User,
    url: '/user'
})
var UserView = Backbone.View.extend({
    tagName: 'tr',
    template: template('user'),
    initialize: function() {
        this.model.bind('change', this.render, this);
        this.model.bind('destroy', this.remove, this);
    },
    render: function () {
        $(this.el).html(this.template.render(this.model.toJSON()));
        return this;
    },
    remove: function() {
        alert('remove');
    }
});
var UsersView = Backbone.View.extend({
    el: '#content',
    template: template('userManager'),
    collection: new Users(),
    events: {
        'click .user-refresh': 'refresh'
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
    }
});
