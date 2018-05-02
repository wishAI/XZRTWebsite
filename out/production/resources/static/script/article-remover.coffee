$(document).ready ->
    document.getElementById('btnRemove').addEventListener('click', ->
        # get the article id
        id = parseInt(document.getElementById('iptArticleId').value)

        console.log(JSON.stringify({id: id}))

        # ajax post request
        $.ajax
            type: 'POST'
            url: '/doRemoveArticle'
            data: JSON.stringify({id: id})
            dataType: 'json'
            contentType: 'application/json; charset=utf8'
            success: (data) -> console.log(data)
            failure: (data) -> console.log(data)
    )