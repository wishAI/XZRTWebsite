$(document).ready ->
  setInterval(getUploadProgressInfo, 500)

  $("#btnUpload").on "click", ->
    formData = new FormData()
    file = $("#files")[0].files[0]
    formData.append("testFile", file)
    $.ajax
      type: "POST"
      url: "/file/doUpload"
      data: formData
      success: (data)->
        $("#result").text(data)
      contentType: false
      processData: false

  $("#btnTest").on "click", ->
    getUploadProgressInfo()


getUploadProgressInfo = ->
  $.ajax
    type: "GET"
    url: "/info/upload"
    success: (data)->
      $("#progress").text(data)
###
$.post "/file/doUpload", {testFile:file}, (data)->
  myObject = JSON.parse(data)
  $("#result").text(myObject["id"])
###

###
upload = ->
xhr = new XMLHttpRequest()
fd = new FormData()
file = document.getElementById("files").files[0]
fd.append("testFile", file)
xhr.upload.addEventListener("progress", onUploadProgress, false)
xhr.addEventListener("load", onUploadComplete, false)
xhr.addEventListener("error", onUploadFailed, false)
xhr.open("POST", "/file/doUpload")
xhr.send(fd)
return
###

onUploadProgress = (evt) ->
  if evt.lengthComputable
    progressInfo = document.getElementById("progress")
    progressInfo.innerHTML = evt.loaded


onUploadComplete = ->
  return

onUploadFailed = ->
  return
