function active(view, e) {
    view.$el.find('.cur').removeClass('cur');
    $(e.target).addClass('cur');
}

var TopView = Backbone.View.extend({
    el: '#top',
    template: template('top'),
    events: {
        //'click #index': 'index',
        //'click #help': 'help'
    },
    initialize: function () {
    },
    render: function (data) {
        this.$el.append(this.template.render(data));
    },
    index: function (e) {
        active(this, e);
        app.navView.render();
    },
    help: function (e) {
        active(this, e);
        app.navView.clean();
        $('#content').html(template('help').render());
    }
});
var NavView = Backbone.View.extend({
    el: '#nav',
    template: template('nav'),
    events: {
        'click #service-list': 'serviceList',
        'click #user-manager': 'userManager',
        'click #resource-manager': 'resourceManager',
        'click #contact-us': 'contact'
    },
    initialize: function () {
        this.servicesView = new ServicesView();
        this.usersView = new UsersView();
        this.resourcesView = new ResourcesView();
        this.contactView = new ContactView();
    },
    serviceList: function (e) {
        active(this, e);
        this.servicesView.render();
        app.router.navigate('services');
        return false;
    },
    userManager: function (e) {
        active(this, e);
        this.usersView.render();
        app.router.navigate('users');
        return false;
    },
    resourceManager: function (e) {
        active(this, e);
        this.resourcesView.render();
        app.router.navigate('resources');
        return false;
    },
    contact: function(e){
    	active(this, e);
    	this.contactView.render();
    	app.router.navigate('contact');
    	return false;
    },
    render: function () {
        this.$el.html(this.template.render());
    },
    clean: function () {
        this.$el.empty();
    }
});
var AppRouter = Backbone.Router.extend({
    routes: {
        '': 'index',
        'services': 'services',
        'users': 'users',
        'resources': 'resources',
        'contact': 'contact',
        'help': 'help'
    },
    index: function () {
        $('#service-list').trigger('click');
    },
    services: function () {
        $('#service-list').trigger('click');
    },
    users: function () {
        $('#user-manager').trigger('click');
    },
    resources: function () {
        $('#resource-manager').trigger('click');
    },
    contact: function () {
        $('#contact-us').trigger('click');
    },
    help: function () {
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
    	
    	this.topView.render({name:"administrator@datayes.com"});
    	$.get("/platform/notification", function(result){
    	    $("#notification").html(result);
    	  });
        this.navView.render();
    },
    showModal: function (name, data) {
        return $(template(name).render(data)).on('hidden.bs.modal',function () {
            $(this).remove();
        }).modal({backdrop: 'static'});
    }
});
