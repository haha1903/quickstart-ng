$(function () {
    $('.service_status').change(function() {
        var service = {name: $(this).attr('name'), enabled: $(this).is(':checked')};
        $.put(contextPath + "/user?id=" + $(this).attr('user'), JSON.stringify(service)).done(function(data) {
//            alert('enable success')
        }).fail(function() {
                alert('fail');
            })
    });
});
