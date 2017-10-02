isScrolling = false

class Color
  constructor: (@R, @G, @B) ->

  toString: ->
    "#" + @R.toString(16) + @G.toString(16) + @B.toString(16)

  getMixedColor: (clr, dst) ->
    if(dst > 1 || dst < 0)
      return
    new Color(Math.round(@R * (1 - dst) + clr.R * dst), Math.round(@G * (1 - dst) + clr.G * dst), Math.round(@B * (1 - dst) + clr.B * dst))

onScroll = ->
  if(!isScrolling)
    requestAnimationFrame(changeViewByScrolling)

changeViewByScrolling = ->
  movingSpeed = 3
  footerHeight = 150
  headGalleryHeight = 470
  clrMain = new Color(82, 179, 255)
  clrNav = new Color(238, 238, 238)
  clrText = new Color(79, 79, 79)
  clrWhite = new Color(255, 255, 255)
  #console.log($(window).scrollTop()+"  "+$(document).height()+"  "+$(window).height())
  if($(window).scrollTop() + $(window).height() + 200 > $(document).height() - footerHeight * movingSpeed && $(document).height() > $(window).height())
    targetFooterTrans = Math.floor((footerHeight * movingSpeed - ($(document).height() - ($(window).scrollTop() + $(window).height()))) / movingSpeed)
    $("footer").css("transform", "translateY(" + (-targetFooterTrans) + "px")
  else if($("footer").css("transform") != "translateY(0px)")
    $("footer").css("transform", "translateY(0px)")

  if($(window).scrollTop() < headGalleryHeight * movingSpeed + 200)
    targetGalleryTrans = -$(window).scrollTop() / movingSpeed
    $("#headGalleryContainer").css("transform", "translateY(" + targetGalleryTrans + "px")
  else if($("#headGalleryContainer").css("transform") != "translateY(-500px)")
    $("#headGalleryContainer").css("transform", "translateY(-500px)")

  ###
  if($(window).scrollTop() > 900 && isScrolledDown == false)
    $("nav").css("background-color", clrMain.toString())
    $("nav a, nav span").css("color", clrWhite.toString())
    #$("#headGallery").css("opacity", 0)
    isScrolledDown = true
  else if($(window).scrollTop() <= 900 && isScrolledDown == true)
    $("nav").css("background-color", clrNav.toString())
    $("nav a, nav span").css("color", clrText.toString())
    #$("#headGallery").css("opacity", 1)
    isScrolledDown = false
  ###
  #$("nav").css("background-color", clrNav.getMixedColor(clrMain, 1 - targetOpacity).toString())
  #$("nav a, nav span").css("color", clrText.getMixedColor(clrWhite, 1 - targetOpacity).toString())
  #$("#headGallery").css("opacity", targetOpacity)
  #$("#headGalleryContainer").css("top", targetGalleryTrans)
  isScrolling = false


###
if(!isGalleryMoving)
  currentGalleryTrans = currentGalleryTrans
  isGalleryMoving = true
  movingInterval = setInterval ->
    targetGalleryTrans = targetGalleryTrans
    if(targetGalleryTrans == currentGalleryTrans)
      isGalleryMoving = false
      clearInterval(movingInterval)
    else if(Math.abs(targetGalleryTrans - currentGalleryTrans) < minMovingLength)
      currentGalleryTrans = targetGalleryTrans
    else if(currentGalleryTrans > targetGalleryTrans)
      currentGalleryTrans -= minMovingLength
    else if(currentGalleryTrans < targetGalleryTrans)
      currentGalleryTrans += minMovingLength
    $("#headGalleryContainer").css("top", currentGalleryTrans)
  , 2

###

$(document).ready ->
  $("footer").addClass("footerFixHidden")
  onScroll()
  window.addEventListener("scroll", onScroll)
  window.addEventListener("resize", onScroll)

  $("#btnPrint").on "click", ->
    window.print()


