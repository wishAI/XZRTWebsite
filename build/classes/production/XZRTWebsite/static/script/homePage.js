// Generated by CoffeeScript 1.11.1
(function() {
  var plusSlides, setProperFlowNum, showSlides, slideIndex;

  slideIndex = 0;

  $(document).ready(function() {
    $(window).resize(function() {
      return setProperFlowNum();
    });
    $(".homeFlowContainer").on("mouseenter mouseleave", function(evt) {
      if (evt.type === "mouseenter") {
        return $(this).find(".homeFlowImage").addClass("homeFlowImageHover");
      } else {
        return $(this).find(".homeFlowImage").removeClass("homeFlowImageHover");
      }
    });
    $("#btnSlidePrev").on("click", function() {
      return plusSlides(-1);
    });
    $("#btnSlideNext").on("click", function() {
      return plusSlides(1);
    });
    setProperFlowNum();
    showSlides(slideIndex);
    return setInterval(function() {
      return plusSlides(1);
    }, 4000);
  });

  plusSlides = function(value) {
    var length;
    length = $(".slides").length;
    if (slideIndex + value < 0) {
      slideIndex = length - 1;
    } else if (slideIndex + value > length - 1) {
      slideIndex = 0;
    } else {
      slideIndex += value;
    }
    return showSlides(slideIndex);
  };

  showSlides = function(index) {
    $(".slides").css("display", "none");
    return $(".slides").eq(index).css("display", "block");
  };

  setProperFlowNum = function() {
    var clientWidth, i, j, num, ref, results;
    clientWidth = $(document).width();
    if (clientWidth > 1250) {
      num = 4;
    } else if (clientWidth > 950) {
      num = 3;
    } else if (clientWidth > 650) {
      num = 2;
    } else {
      num = 1;
    }
    $(".homeFlowContainer").css("display", "none");
    results = [];
    for (i = j = 0, ref = num; 0 <= ref ? j < ref : j > ref; i = 0 <= ref ? ++j : --j) {
      $(".homeFlowContainer").eq(i).css("display", "block");
      results.push($(".homeFlowContainer").eq(i + 4).css("display", "block"));
    }
    return results;
  };

}).call(this);

//# sourceMappingURL=homePage.js.map