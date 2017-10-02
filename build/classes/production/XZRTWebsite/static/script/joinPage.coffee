$(document).ready ->
  $("input[type = radio]").change ->
    value = this.value
    $("input[type = radio][name = " + this.name + "]").each ->
      if(this.value == value)
        $("label[for = " + this.id + "]").addClass("radioChecked")
      else
        $("label[for = " + this.id + "]").removeClass("radioChecked")