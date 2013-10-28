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
$.views.helpers({s: function(url) {
    return staticPath + url;
}});
var debug = false;
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
                url: staticPath + 'debug' + fxhr.url,
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
	  errorContainer : ".alert",
	  errorLabelContainer: "#alert_ul",
	  errorElement: "li",
	  highlight: function(element, errorClass, validClass) {
		  $(element).parent().parent().addClass(errorClass).removeClass(validClass);
		  
	  },
	  unhighlight: function(element, errorClass, validClass) {
	    $(element).parent().parent().removeClass(errorClass).addClass(validClass);
	    
	  },
	  showErrors: function(errorMap, errorList) {
		  for ( i = 0; errorList[i]; i++ ) {
			 var error = errorList[i];
			 error.message =  $("label[for="+ error.element.name+"]").text()+" : "+error.message ;
		  };
		  
		  this.defaultShowErrors();
	  }
	});

jQuery.validator.addMethod("domain", function(value, element, param) {
	return this.optional(element) || /^(([\w-_\d]+\.)+)+(com|cn|net|org)$/gi.test(value);
}, jQuery.validator.messages.domain);

jQuery.validator.addMethod("phone", function(value, element, param) {
	return this.optional(element) || /^(\(\d+\))?(\d+\s*-?\s*)*\d+$/g.test(value);
}, jQuery.validator.messages.phone);

var BUTTON_OK=16;
var BUTTON_YES=8;
var BUTTON_NO=4;
var BUTTON_ABORT=2;
var BUTTON_CANCEL=1;