footerHeight = 150

changeViewByWinSize = ->
# change footer to relative first to avoid document + footer > window
  $("footer").removeClass("footerFix")
#  console.log($(window).height()+"   "+$(document).height())
  if($(window).height() == $(document).height())
    $("footer").addClass("footerFix")
    if($(body).height() + footerHeight > $(window).height())
      $("footer").removeClass("footerFix")
  else
    $("footer").removeClass("footerFix")


$(document).ready ->
  changeViewByWinSize()
  metaViewport = "<meta name='viewport' content='width=711, user-scalable=no'/>"
  if(window.screen.width <= 450 || window.screen.height <= 450)
    $("head").append(metaViewport)
  $(window).resize ->
    changeViewByWinSize()