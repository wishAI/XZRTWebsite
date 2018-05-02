package com.wishai.xzrtw.controller

import com.fasterxml.jackson.annotation.JsonProperty
import com.wishai.xzrtw.model.Applicant
import com.wishai.xzrtw.model.Article
import com.wishai.xzrtw.model.ArticleContent
import com.wishai.xzrtw.model.gui.*
import com.wishai.xzrtw.model.gui.blocks.BlockEle
import com.wishai.xzrtw.service.GuiService
import com.wishai.xzrtw.service.ResourceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*


@RestController
class MainController @Autowired
    constructor(
        private val resourceService: ResourceService,
        private val guiService: GuiService
    ) {

    /* GET METHODS GOES HERE*/

    @GetMapping(value = "home", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun homePageJson(
            @RequestParam("lang") lang: String
    ): HomePageJsonWrapper {
        // receive the gui elements
        val homeFlowEles = guiService.makeHomeFlowEles(lang)
        val homeItemEles = guiService.makeHomeItemEles(lang)

        // make the data wrapper
        val homePageJsonWrapper = HomePageJsonWrapper()
        homePageJsonWrapper.homeFlowEles = homeFlowEles
        homePageJsonWrapper.homeItemEles = homeItemEles

        return homePageJsonWrapper
    }

    @GetMapping(value = "menu", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun menuPageJson(
            @RequestParam("category") category: String,
            @RequestParam("lang") lang: String
    ): MenuPageJsonWrapper {
        // receive the gui elements
        val menuFlowEles = guiService.makeMenuFlowEles(lang, category)
        val menuItemEles = menuListEleJson(lang, category, 1)

        // make the data wrapper
        val menuPageJsonWrapper = MenuPageJsonWrapper()
        menuPageJsonWrapper.menuFlowEles = menuFlowEles
        menuPageJsonWrapper.menuItemEles = menuItemEles

        return menuPageJsonWrapper
    }

    @GetMapping(value = "menu/items", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun menuListEleJson(
            @RequestParam("lang") lang: String,
            @RequestParam("category") category: String,
            @RequestParam("page-num") pageNum: Int
    ): List<MenuItemEle> {
        return guiService.makeMenuItemEles(lang, category, pageNum)
    }

    @GetMapping("article")
    fun articlePageJson(
            @RequestParam("lang") lang: String,
            @RequestParam("id") id: Int?
    ): ArticlePageJsonWrapper {
        // receive the article from service
        val article = resourceService.findArticle(id)

        // make the article info element and get article content
        val articleContent = article.getArticleContentByLang(lang)
        val articleInfoEle = article.toArticleInfoEle(lang)

        // make the data wrapper
        val articlePageJsonWrapper = ArticlePageJsonWrapper()
        articlePageJsonWrapper.blockEles = articleContent!!.toBlockEles()
        articlePageJsonWrapper.articleInfoEle = articleInfoEle

        return articlePageJsonWrapper
    }

    /* POST METHOD GOES HERE */

    @PostMapping(value = "doSaveArticle", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE), consumes = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    @Transactional(rollbackFor = arrayOf(Exception::class))
    open fun doSaveArticle(@RequestBody doSaveArticleWrapper: DoSaveArticleWrapper): String {
        // pass the article to service
        resourceService.saveArticle(doSaveArticleWrapper.article)

        // return the status
        return "request successful"
    }


    @PostMapping(value = "doRemoveArticle", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE), consumes = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    @Transactional(rollbackFor = arrayOf(Exception::class))
    open fun doRemoveArticle(@RequestBody doRemoveArticleWrapper: DoRemoveArticleWrapper): String {
        // remove the article by service
        resourceService.removeArticle(doRemoveArticleWrapper.id)

        return "remove successful"
    }

    @PostMapping(value = "doSaveApplicant", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE), consumes = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun doSaveApplicant(@RequestBody applicant: Applicant): String {
        // save the applicant
        resourceService.saveApplicant(applicant)

        return "successful"
    }

    /* CLASS OF THE REQUEST AND RESPONSE WRAPPER */

    class HomePageJsonWrapper {
        var homeFlowEles: List<HomeFlowEle>? = null
        var homeItemEles: List<HomeItemEle>? = null
    }

    class ArticlePageJsonWrapper {

        var blockEles: List<BlockEle>? = null
        var articleInfoEle: ArticleInfoEle? = null
    }

    class MenuPageJsonWrapper {
        var menuFlowEles: List<MenuFlowEle>? = null
        var menuItemEles: List<MenuItemEle>? = null
    }

    class DoSaveArticleWrapper(@param:JsonProperty("article") var article: Article?)

    class DoRemoveArticleWrapper {
        var id: Int? = null
    }

    /* THE FUNCTIONS FOR TEST !! SHOULD BE REMOVED IN THE FUTURE !! */

    //    @GetMapping("/test")
    //    @ResponseBody
    //    public String test(
    ////            @RequestParam("file") MultipartFile file
    //    ) {
    //        XzrtProperties xzrtProperties = XzrtPropertiesFactory.getXzrtProperties();
    //        return xzrtProperties.getNoneImageId();
    //    }
}