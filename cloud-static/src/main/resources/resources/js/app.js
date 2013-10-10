function active(view, e) {
    view.$el.find('.active').removeClass('active');
    $(e.target).blur().parent().addClass('active');
}
var TopView = Backbone.View.extend({
    el: '#top',
    template: template('top'),
    events: {
        'click #index': 'index',
        'click #help': 'help'
    },
    initialize: function () {
    },
    render: function () {
        this.$el.append(this.template.render());
    },
    index: function(e) {
        active(this, e);
        app.navView.render();
    },
    help: function (e) {
        active(this, e);
        $('#content').html(template('help').render());
    }
});
var NavView = Backbone.View.extend({
    el: '#nav',
    template: template('nav'),
    events: {
        'click #service-list': 'serviceList',
        'click #user-manager': 'userManager',
        'click #resource-manager': 'resourceManager'
    },
    initialize: function () {
        this.servicesView = new ServicesView();
        this.usersView = new UsersView();
        this.resourcesView = new ResourcesView();
    },
    serviceList: function (e) {
        active(this, e);
        this.servicesView.render();
    },
    userManager: function (e) {
        active(this, e);
        this.usersView.render();
    },
    resourceManager: function (e) {
        active(this, e);
        this.resourcesView.render();
    },
    render: function () {
        this.$el.html(this.template.render());
    }
});
var AppRouter = Backbone.Router.extend({
    routes: {
        '': 'index',
        'services': 'services',
        'users': 'users',
        'resources': 'resources',
        'help': 'help'
    },
    index: function() {
        $('#service-list').trigger('click');
    },
    services: function() {
        $('#service-list').trigger('click');
    },
    users: function() {
        $('#user-manager').trigger('click');
    },
    resources: function() {
        $('#resource-manager').trigger('click');
    },
    help: function() {
        $('#help').trigger('click');
    }

});
var AppView = Backbone.View.extend({
    el: 'body',
    template: template('index'),
    initialize: function () {
        this.$el.html(this.template.render());
        this.topView = new TopView();
        this.navView = new NavView();
        this.router = new AppRouter();
    },
    render: function () {
        this.topView.render();
        this.navView.render();
    }
});
