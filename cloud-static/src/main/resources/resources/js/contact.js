var feedback = Backbone.Model.extend({
    urlRoot: '/feedback'
});

var ContactView = Backbone.View.extend({
    el: '#content',
    template: template('contact'),
    events: {
        //TODO:sth
    },
    initialize: function () {
        
    },
    render: function () {
        this.$el.html(this.template.render());
        return this;
    }
});