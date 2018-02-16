# load all the resource blocks here

$(document).ready ->
#  $(".resourceBlock").each ->
#    requestUrl = $(this).attr("blockSrc")
#    blockSrcData = null
#    thisElement = this
#    $.ajax
#      type: "GET"
#      url: requestUrl
#      success: (data) ->
#        blockSrcData = data
#        thisElement.innerHTML = blockSrcData
    # for each element with class src
    $('.src').each ->
        srcRequestId = $(this).attr('src-request-id')
        switch $(this).attr('src-type')
            when 'image'
                # make the url
                baseUrl = '/src/image?'
                paramObj = {id:srcRequestId}
                param = $.param(paramObj)
                url = baseUrl + param
                # put into the src attr of this ele
                $(this).attr('src', url)
            when 'text'
                # make the url
                baseUrl = '/src/text?'
                formData = new FormData()
                formData.append('id', srcRequestId)
                url = baseUrl + param
                # request the text src by ajax
                text = null
                thisEle = this
                $.ajax
                    type: 'GET'
                    url: '/src/text?id=1'
                    contentData: false
                    processData: false
                    success: (data)->
                        $(thisEle).append(data)
                # !! decode with the strong info
                # append the text into the ele (test only)


