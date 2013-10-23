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

var BUTTON_OK=16;
var BUTTON_YES=8;
var BUTTON_NO=4;
var BUTTON_ABORT=2;
var BUTTON_CANCEL=1;