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
    })
});