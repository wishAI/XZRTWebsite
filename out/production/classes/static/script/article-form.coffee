$(document).ready ->
    document.getElementById('btnPost').addEventListener('click', ->
        json = JSON.parse(document.getElementById('iptJson').value)

        $.ajax
            type: 'POST'
            url: '/doSaveArticle'
            data: JSON.stringify(json)
            dataType: 'json'
            contentType: 'application/json; charset=utf8'
            success: (data) -> console.log(data)
            failure: (err) -> console.log(err)
    )