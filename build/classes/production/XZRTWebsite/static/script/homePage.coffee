slideIndex = 0

$(document).ready ->
  $(window).resize ->
    setProperFlowNum()
    #console.log($(document).width())

  $(".homeFlowContainer").on "mouseenter mouseleave", (evt) ->
    if(evt.type == "mouseenter")
      $(this).find(".homeFlowImage").addClass("homeFlowImageHover")
    else
      $(this).find(".homeFlowImage").removeClass("homeFlowImageHover")

  $("#btnSlidePrev").on "click", ->
    plusSlides(-1)

  $("#btnSlideNext").on "click", ->
    plusSlides(1)

  setProperFlowNum()
  showSlides(slideIndex)
  setInterval ->
    plusSlides(1)
  , 4000


plusSlides = (value) ->
  length = $(".slides").length
  #make sure the index is in right range
  if (slideIndex + value < 0)
    slideIndex = length - 1
  else if (slideIndex + value > length - 1)
    slideIndex = 0
  else
    slideIndex += value
  #refresh the slides
  showSlides(slideIndex)

showSlides = (index) ->
  $(".slides").css("display", "none")
  $(".slides").eq(index).css("display", "block")

setProperFlowNum = ->
  clientWidth = $(document).width()
  #console.log(clientWidth)
  if(clientWidth > 1250)
    num = 4
  else if(clientWidth > 950)
    num = 3
  else if(clientWidth > 650)
    num = 2
  else
    num = 1
  #set all flow container to none first
  $(".homeFlowContainer").css("display", "none")
  for i in [0...num]
    $(".homeFlowContainer").eq(i).css("display", "block")
    $(".homeFlowContainer").eq(i + 4).css("display", "block")