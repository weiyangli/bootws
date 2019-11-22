$.getData = function(options, callback) {
    $.ajax({
        url        : options.url,
        data       : options.data || null,
        async      : options.async || false,
        type       : options.type,
        dataType   : 'json', // 服务器的响应使用 JSON 格式
        contentType: options.contentType || 'application/json;charset=UTF-8',
        headers: {'X-Requested-With': 'XMLHttpRequest'}
    })
    .done(function(data, textStatus, jqXHR) {
        callback(data);
    })
    .fail(function(jqXHR, textStatus, failThrown) {
        // error
        console.log("请求失败");
    })
}