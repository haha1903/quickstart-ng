$(function () {
    $('#btn_reg').click(function () {
        var tenant = {admin: $('input[name=admin]').val(), password: $('input[name=password]').val(), name: $('input[name=name]').val()};
        $.post(contextPath + '/tenant', JSON.stringify(tenant)).done(function (data) {
            alert(JSON.stringify(data));
        }).fail(function () {
                alert('fail');
            });
    });
});
