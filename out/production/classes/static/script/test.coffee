# def all the block structure here

class File
    constructor: (@type) ->
        @cacheId = -1

class Text
    constructor: () ->
        @cacheId = -1


class ImageBlock
    constructor: () ->
        @_type = 'image'
        @info = ''
        @file = null

class ParagraphBlock
    constructor: () ->
        @_type = 'paragraph'
        @text = null

class ArticleContent
    constructor: (@cover, @title, @info, @blocks) ->



$(document).ready ->

    $('#btnTest').on 'click', ->
        alert('The request has begun')
        formData = {}

        # make the form data
        formData['author'] =  'Hao Wang'
        formData['category'] =  'race'

        # the block json part
        imageBlock = new ImageBlock()
        imageBlock.info = 'Its only a test'
        file = new File('image')
        file.cacheId = 10
        imageBlock.file = file

        paragraphBlock = new ParagraphBlock()
        text = new Text()
        text.cacheId = 11
        paragraphBlock.text = text

        blocks = []
        blocks.push(imageBlock)
        blocks.push(paragraphBlock)

        # the cover json part
        cover = new File('image')
        cover.cacheId = 12

        articleContentZh = new ArticleContent(cover, 'nimeia', 'zhazha', blocks)

        formData['articleContentZh'] = articleContentZh

        console.log(formData)

        # use ajax to request
        $.ajax
            type: 'POST'
            url: '/doSaveArticle'
            data: JSON.stringify({'article': formData})
            contentType: 'application/json; charset=utf-8'
            processData: false
            success: (data)->
                alert('request successful')



