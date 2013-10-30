var Resource = Backbone.Model.extend({});
var Resources = Backbone.Collection.extend({
    model: Resource,
    url: '/resource'
})
var ResourceView = Backbone.View.extend({
    tagName: 'tr',
    template: template('resource'),
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
var ResourcesView = Backbone.View.extend({
    el: '#content',
    template: template('resourceManager'),
    collection: new Resources(),
    events: {
       // 'click .resource-refresh': 'refresh'
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
    add: function (resource) {
        var resourceView = new ResourceView({model: resource});
        $('#resource tbody').append(resourceView.render().el);
    },
    addAll: function () {
        this.collection.each(this.add);
    },
    refresh: function () {
        this.collection.fetch();
    }
});
