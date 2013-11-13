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
    	$(this.el).remove();
    }
});
var ResourcesView = Backbone.View.extend({
    el: '#content',
    template: template('resourceManager'),
    collection: new Resources(),
    total: {totalCpu:0,totalMem:0,totalDisk:0},
    events: {
    	'click #resource-add':'addResource'
       // 'click .resource-refresh': 'refresh'
    },
    initialize: function () {
        this.collection.bind('add', this.add, this);
        this.collection.on('change', this.countTotal, this);
        this.collection.on('remove', this.countTotal, this);
    },
    render: function () {
    	this.$el.html(this.template.render());
    	this.total= {totalCpu:0,totalMem:0,totalDisk:0};
        this.collection.reset();
        this.collection.fetch();
        return this;
    },
    add: function (resource) {
    	this.total.totalCpu+=resource.get("cpu");
		this.total.totalMem+=resource.get("mem");
		this.total.totalDisk+=resource.get("disk");
		
		var resourceView = new ResourceView({model: resource});
        $('#resource tbody').append(resourceView.render().el);
        
		this.showTotal();
    },
    countTotal: function(){
    	this.total= {totalCpu:0,totalMem:0,totalDisk:0};
    	var self = this;
    	this.collection.each(function(resource){
    		self.total.totalCpu+=resource.get("cpu");
    		self.total.totalMem+=resource.get("mem");
    		self.total.totalDisk+=resource.get("disk");
    	});
    	this.showTotal();
    },
    showTotal: function(){
    	$('#totalCpu').html(this.total.totalCpu);
		$('#totalMem').html(this.total.totalMem);
		$('#totalDisk').html((this.total.totalDisk/1000).toFixed(1));
    },
    refresh: function () {
    	this.collection.fetch();
    },
    addResource:function(){
    	this.collection.at(1).destroy();
    }
});
