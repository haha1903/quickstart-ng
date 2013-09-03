$(function () {
    $('#btn_login').click(function () {
        var user = {name: $('input[name=name]').val(), password: $('input[name=password]').val()};
        $.post(contextPath + '/login', JSON.stringify(user)).done(function (data) {
            alert(data.message);
        }).fail(function (jqxhr) {
                alert(jqxhr.responseJSON.message);
            });
    });
});
