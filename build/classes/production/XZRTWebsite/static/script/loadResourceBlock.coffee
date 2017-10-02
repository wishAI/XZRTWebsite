# load all the resource blocks here

$(document).ready ->
  $(".resourceBlock").each ->
    requestUrl = $(this).attr("blockSrc")
    blockSrcData = null
    thisElement = this
    $.ajax
      type: "GET"
      url: requestUrl
      success: (data) ->
        blockSrcData = data
        thisElement.innerHTML = blockSrcData