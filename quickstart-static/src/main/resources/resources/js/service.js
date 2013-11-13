var Service = Backbone.Model.extend({});
var Services = Backbone.Collection.extend({
    model: Service,
    url: '/service'
})
var ServiceView = Backbone.View.extend({
    template: template('service'),
    initialize: function () {
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
var ServicesView = Backbone.View.extend({
    el: '#content',
    template: template('serviceList'),
    collection: new Services(),
    events: {
        //'click .service-refresh': 'refresh'
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
    add: function (service) {
        var serviceView = new ServiceView({model: service});
        $('#' + service.get('type')).append(serviceView.render().el);
    },
    addAll: function () {
        this.collection.each(this.add);
    },
    refresh: function () {
        this.collection.fetch();
    }
});