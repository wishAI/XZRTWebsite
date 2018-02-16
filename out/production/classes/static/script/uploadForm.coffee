$(document).ready ->
    $('#btnFile').on 'click', ->
        # for each file in the input element
        for file in $('#iptFiles')[0].files
            # setup form data
            formData = new FormData()

            # append the file and type
            formData.append('file', file)
            formData.append('type', $('#fileType')[0].value)

            # ajax request to upload the file
            console.log('Upload Starting!')
            $.ajax
                type: 'POST'
                url: '/src/doCacheFile'
                data: formData
                contentType: false
                processData: false
                success: (data) -> console.log(data)
            console.log('Upload finished!')


    $('#btnText').on 'click', ->
        # get the text in the text area
        text = $('#iptText').val()

        # make the form data
        formData = new FormData()
        formData.append('text', text)

        # ajax request to upload the text
        console.log('Upload Starting!')
        $.ajax
            type: 'POST'
            url: '/src/doCacheText'
            data: formData
            contentType: false
            processData: false
            success: (data) -> console.log(data)
        console.log('Upload finished!')

