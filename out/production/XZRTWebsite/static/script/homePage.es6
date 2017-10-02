var slideIndex = 0;

showSlides(slideIndex);
changeViewByClientSize();

function plusSlides(value) {
    var length = document.getElementsByClassName("slides").length;
    //make sure the index is in right range
    if (slideIndex + value < 0) {
        slideIndex = length - 1;
    } else if (slideIndex + value > length - 1) {
        slideIndex = 0;
    } else {
        slideIndex += value;
    }
    //refresh the slides
    showSlides(slideIndex);
}

function showSlides(which) {
    var slides = Array.prototype.slice.call(document.getElementsByClassName("slides"));
    for (var slide of slides) {
        slide.style.display = "none";
    }
    slides[which].style.display = "block";
}

function changeViewByClientSize() {
    //if the latest news is hidden the slideShow should be moved to center
    //determine if overflow
    var contentHeader = document.getElementById("contentHeader");
    contentHeader.style.overflow = "scroll";
    if (contentHeader.scrollHeight - contentHeader.clientHeight > 20) {
        contentHeader.style.textAlign = "center";
    } else {
        contentHeader.style.textAlign = "left";
    }
    contentHeader.style.overflow = "hidden";
    //make the gallery looks complete on any dpi
    //get the length and height of the client
    var clientWidth = document.body.offsetWidth;
    if (clientWidth > 1436) {
        document.getElementById("resourceGallery").style.backgroundSize = "100%";
    } else {
        document.getElementById("resourceGallery").style.backgroundSize = "auto 100%";
    }
    //determine the rows of resource flows
    var flowContainers = Array.prototype.slice.call(document.getElementsByClassName("homeFlowContainer"));
    if (clientWidth > 1050) {
        //set to default value 4in1
        for (var flowContainer of flowContainers) {
            var width = parseInt(clientWidth / 4 - 50);
            if (width > 400) {
                width = 400;
            }
            flowContainer.style.width = width + "px";
            flowContainer.style.height = flowContainer.style.width;
        }
    } else if (clientWidth > 547) {
        //set 2in1
        for (var flowContainer of flowContainers) {
            var width = parseInt(clientWidth / 2 - 70);
            flowContainer.style.width = width + "px";
            flowContainer.style.height = parseInt(width * 0.9) + "px";

        }
    } else {
        //set 1in1
        for (var flowContainer of flowContainers) {
            flowContainer.style.width = parseInt(clientWidth - 100) + "px";
            flowContainer.style.height = flowContainer.style.width;
        }
    }
}