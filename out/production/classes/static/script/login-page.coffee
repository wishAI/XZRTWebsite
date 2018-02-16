$(document).ready ->
    document.getElementById('btnSubmit').addEventListener('click', ->
        name = document.getElementById('user-name').value
        password = document.getElementById('user-password').value

        console.log(name + '   ' + password)

        console.log(JSON.stringify({name: name, password: password, permission: ''}))

        $.ajax
            type: 'POST'
            url: '/user/doLogin'
            data: JSON.stringify({name: name, password: password, permission: ''})
            contentType: 'application/json; charset=UTF-8'
            dataType: 'text'
            success: (data) -> console.log(data)
            failure: (err) -> console.log(err)
            error: (err) -> console.log(err)

        console.log("complete")
    )