// Generated by CoffeeScript 1.12.2
(function() {
  $(document).ready(function() {
    return document.getElementById('btnPost').addEventListener('click', function() {
      var json;
      json = JSON.parse(document.getElementById('iptJson').value);
      return $.ajax({
        type: 'POST',
        url: '/doSaveArticle',
        data: JSON.stringify(json),
        dataType: 'json',
        contentType: 'application/json; charset=utf8',
        success: function(data) {
          return console.log(data);
        },
        failure: function(err) {
          return console.log(err);
        }
      });
    });
  });

}).call(this);

//# sourceMappingURL=article-form.js.map
