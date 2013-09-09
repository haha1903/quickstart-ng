$(function () {
    $('#btn_addServer').click(function () {
        var server = {name: $('input[name=name]').val(),
            description: $('input[name=description]').val(),
            serverId: $('input[name=serverId]').val(),
            type: $('input[name=type]').val()
        };
        $.post(contextPath + '/server', JSON.stringify(server)).done(function (data) {
            alert('add server success');
        }).fail(function () {
                alert('fail');
            });
    });
});
