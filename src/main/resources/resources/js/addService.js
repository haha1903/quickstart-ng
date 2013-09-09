$(function () {
    $('#btn_addService').click(function () {
        var service = {name: $('input[name=name]').val()};
        $.post(contextPath + '/service', JSON.stringify(service)).done(function (data) {
            alert('add service success');
        }).fail(function () {
                alert('fail');
            });
    });
});
