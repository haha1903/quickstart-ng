$(function () {
    $('.service_status').change(function() {
        var service = {name: $(this).attr(name)};
        $.put(contextPath + "/user", JSON.stringify(service)).done(function(data) {
            alert('enable success');
        }).fail(function() {
                alert('fail');
            })
    });
});
