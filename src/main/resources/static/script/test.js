// Generated by CoffeeScript 1.12.2
(function() {
  var ArticleContent, File, ImageBlock, ParagraphBlock, Text;

  File = (function() {
    function File(type) {
      this.type = type;
      this.cacheId = -1;
    }

    return File;

  })();

  Text = (function() {
    function Text() {
      this.cacheId = -1;
    }

    return Text;

  })();

  ImageBlock = (function() {
    function ImageBlock() {
      this._type = 'image';
      this.info = '';
      this.file = null;
    }

    return ImageBlock;

  })();

  ParagraphBlock = (function() {
    function ParagraphBlock() {
      this._type = 'paragraph';
      this.text = null;
    }

    return ParagraphBlock;

  })();

  ArticleContent = (function() {
    function ArticleContent(cover1, title, info, blocks1) {
      this.cover = cover1;
      this.title = title;
      this.info = info;
      this.blocks = blocks1;
    }

    return ArticleContent;

  })();

  $(document).ready(function() {
    return $('#btnTest').on('click', function() {
      var articleContentZh, blocks, cover, file, formData, imageBlock, paragraphBlock, text;
      alert('The request has begun');
      formData = {};
      formData['author'] = 'Hao Wang';
      formData['category'] = 'race';
      imageBlock = new ImageBlock();
      imageBlock.info = 'Its only a test';
      file = new File('image');
      file.cacheId = 10;
      imageBlock.file = file;
      paragraphBlock = new ParagraphBlock();
      text = new Text();
      text.cacheId = 11;
      paragraphBlock.text = text;
      blocks = [];
      blocks.push(imageBlock);
      blocks.push(paragraphBlock);
      cover = new File('image');
      cover.cacheId = 12;
      articleContentZh = new ArticleContent(cover, 'nimeia', 'zhazha', blocks);
      formData['articleContentZh'] = articleContentZh;
      console.log(formData);
      return $.ajax({
        type: 'POST',
        url: '/doSaveArticle',
        data: JSON.stringify({
          'article': formData
        }),
        contentType: 'application/json; charset=utf-8',
        processData: false,
        success: function(data) {
          return alert('request successful');
        }
      });
    });
  });

}).call(this);

//# sourceMappingURL=test.js.map
