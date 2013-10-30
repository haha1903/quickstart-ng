var Service = Backbone.Model.extend({});
var Services = Backbone.Collection.extend({
    model: Service,
    url: '/service'
})

var ServicesView = Backbone.View.extend({
    el: '#content',
    template: template('serviceList'),
    collection: new Services(),
    events: {
        //'click .service-refresh': 'refresh'
    },
    initialize: function () {
        //this.collection.bind('add', this.add, this);
        //this.collection.bind('fetch', this.addAll, this);
    },
    render: function () {
        
        this.collection.reset();
        this.collection.fetch();
        this.$el.html(this.template.render(this.collection.toJSON()));
        return this;
    },
    add: function (service) {
        var serviceView = new ServiceView({model: service});
        $('.' + service.get('type') + '-service tbody').append(serviceView.render().el);
    },
    addAll: function () {
        this.collection.each(this.add);
    },
    refresh: function () {
        this.collection.fetch();
    }
});