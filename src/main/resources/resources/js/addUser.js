$(function () {
    $('#btn_addUser').click(function () {
        var user = {name: $('input[name=name]').val(), password: $('input[name=password]').val(), dept: $('input[name=dept]').val()};
        $.post(contextPath + '/user', JSON.stringify(user)).done(function (data) {
            alert('add user success');
        }).fail(function () {
                alert('fail');
            });
    });
});
