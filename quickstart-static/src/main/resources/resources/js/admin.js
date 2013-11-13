function active(view, e) {
    view.$el.find('.active').removeClass('active');
    $(e.target).parent().addClass('active');
}

var AdminView = Backbone.View.extend({
    el: 'body',
    template: template('admin'),
    initialize: function () {
    	this.$el.html(this.template.render());
    	//this.topView = new TopView();
        //this.navView = new NavView();
        //this.router = new AppRouter();
        this.tenantManagerView = new TenantManagerView();
    },
    render: function () {
        //this.topView.render();
        //this.navView.render();
        
        this.tenantManagerView.render();
    },
    showModal: function (name, data) {
        return $(template(name).render(data)).on('hidden.bs.modal',function () {
            $(this).remove();
        }).modal({backdrop: 'static'});
    }
});

var admin = new AdminView();
admin.render();
Backbone.history.start();