$(function () {
    $.ajaxSetup({contentType: 'application/json', dataType: 'json'});
    jQuery.each([ "delete", "put" ], function (i, method) {
        jQuery[ method ] = function (url, data, callback, type) {
            if (jQuery.isFunction(data)) {
                type = type || callback;
                callback = data;
                data = undefined;
            }

            return jQuery.ajax({
                url: url,
                type: method,
                dataType: type,
                data: data,
                success: callback
            });
        };
    });
    var originalFetch = Backbone.Collection.prototype.fetch;
    Backbone.Collection.prototype.fetch = function(options){
        options = options ? _.clone(options) : {};
        var originalError = options.error;
        options.error = function(model, response, options){
            if (originalError) {
                originalError(model, response, options);
            } else {
                alert('fetch error: ' + response.statusText);
            }
        }
        originalFetch.apply(this, [options]);
    };
});
var staticPath = require.toUrl('');
function template(name) {
    if (!$.templates[name]) {
        $.ajax({url: staticPath + 'template/' + name + '.tpl',
            dataType: 'text',
            async: false}).done(function(html) {
                $.templates(name, html);
            });
    }
    return $.templates[name];
}
 function convertMap( object ) {
    var key, value,
        fieldsArray = [];
    for ( key in object ) {
        if ( object.hasOwnProperty( key )) {
            value = object[ key ];
            // For each property/field add an object to the array, with key and value
            fieldsArray.push({
                key: key,
                value: value
            });
        }
    }
    // Return the array, to be rendered using {{for ~fields(object)}}
    return fieldsArray;
 }
$.views.helpers({
	s: function(url) {
		return staticPath + url;
}
});


var debug = true;
if (debug) {
    var xhr = sinon.useFakeXMLHttpRequest();
    xhr.useFilters = true;
    xhr.addFilter(function (method, url, async, username, password) {
        return method != 'GET' || /\.(js|css|tpl)$/i.test(url);
    });
    xhr.onCreate = function (fxhr) {
        fxhr.onSend = function () {
            xhr.addFilter(function() {
                return true;
            });
            var done = this.onload;
            $.ajax({
                url: staticPath + 'debug' + fxhr.url+".data",
                async: fxhr.async,
                method: fxhr.method,
                crossDomain: true,
                dataType: 'text'
            }).done(function(data, status) {
                fxhr.responseText = data;
                done.apply(fxhr);
            });
            xhr.filters.pop();
        }
    };
}

jQuery.validator.setDefaults({
	  errorClass: "has-error",
	  //errorContainer : ".alert",
	  //errorLabelContainer: "#alert_ul",
	  //errorElement: "li",
	  highlight: function(element, errorClass, validClass) {
		  $(element).parent().addClass(errorClass).removeClass(validClass);
	  },
	  unhighlight: function(element, errorClass, validClass) {
	    $(element).parent().removeClass(errorClass).addClass(validClass);
	  },
	  errorPlacement: function(error, element) {
		  $(error).addClass('control-label');
		    error.appendTo( element.parent());
	  }/*
	  showErrors: function(errorMap, errorList) {
		  for ( i = 0; errorList[i]; i++ ) {
			 var error = errorList[i];
			 error.message =  $("label[for="+ error.element.name+"]").text()+" : "+error.message ;
		  };
		  
		  this.defaultShowErrors();
	  }*/
});

jQuery.validator.addMethod("domain", function(value, element, param) {
	return this.optional(element) || /^(([\w-_\d]+\.)+)+(com|cn|net|org)$/gi.test(value);
}, jQuery.validator.messages.domain);

jQuery.validator.addMethod("phone", function(value, element, param) {
	return this.optional(element) || /^(\(\d+\))?(\d+\s*-?\s*)*\d+$/g.test(value);
}, jQuery.validator.messages.phone);

jQuery.validator.addMethod("account", function(value, element, param) {
	return this.optional(element) || /^[\w\.]+$/gi.test(value);
}, jQuery.validator.messages.account);

var BUTTON_OK=16;
var BUTTON_YES=8;
var BUTTON_NO=4;
var BUTTON_ABORT=2;
var BUTTON_CANCEL=1;
