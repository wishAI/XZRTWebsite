package com.wishai.xzrtw.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wishai.xzrtw.model.Applicant;
import com.wishai.xzrtw.model.Article;
import com.wishai.xzrtw.model.ArticleContent;
import com.wishai.xzrtw.model.gui.*;
import com.wishai.xzrtw.model.gui.blocks.BlockEle;
import com.wishai.xzrtw.service.GuiService;
import com.wishai.xzrtw.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MainController {

    private final ResourceService resourceService;

    private final GuiService guiService;

    @Autowired
    public MainController(
            ResourceService resourceService,
            GuiService guiService
    ) {
        this.resourceService = resourceService;
        this.guiService = guiService;
    }

    /* GET METHODS GOES HERE*/

    @GetMapping(value = "home", produces = MediaType.APPLICATION_JSON_VALUE)
    public HomePageJsonWrapper homePageJson(
            @RequestParam("lang") String lang
    ) {
        // receive the gui elements
        List<HomeFlowEle> homeFlowEles = guiService.makeHomeFlowEles(lang);
        List<HomeItemEle> homeItemEles = guiService.makeHomeItemEles(lang);

        // make the data wrapper
        HomePageJsonWrapper homePageJsonWrapper = new HomePageJsonWrapper();
        homePageJsonWrapper.setHomeFlowEles(homeFlowEles);
        homePageJsonWrapper.setHomeItemEles(homeItemEles);

        return homePageJsonWrapper;
    }

    @GetMapping(value = "menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuPageJsonWrapper menuPageJson(
            @RequestParam("category") String category,
            @RequestParam("lang") String lang
    ) {
        // receive the gui elements
        List<MenuFlowEle> menuFlowEles = guiService.makeMenuFlowEles(lang, category);
        List<MenuItemEle> menuItemEles = menuListEleJson(lang, category, 1);

        // make the data wrapper
        MenuPageJsonWrapper menuPageJsonWrapper = new MenuPageJsonWrapper();
        menuPageJsonWrapper.setMenuFlowEles(menuFlowEles);
        menuPageJsonWrapper.setMenuItemEles(menuItemEles);

        return menuPageJsonWrapper;
    }

    @GetMapping(value = "menu/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuItemEle> menuListEleJson(
            @RequestParam("lang") String lang,
            @RequestParam("category") String category,
            @RequestParam("page-num") int pageNum
    ) {
        return guiService.makeMenuItemEles(lang, category, pageNum);
    }

    @GetMapping("article")
    public ArticlePageJsonWrapper articlePageJson(
            @RequestParam("lang") String lang,
            @RequestParam("id") Integer id
    ) {
        // receive the article from service
        Article article = resourceService.findArticle(id);

        // make the article info element and get article content
        ArticleContent articleContent = article.getArticleContentByLang(lang);
        ArticleInfoEle articleInfoEle = article.toArticleInfoEle(lang);

        // make the data wrapper
        ArticlePageJsonWrapper articlePageJsonWrapper = new ArticlePageJsonWrapper();
        articlePageJsonWrapper.setBlockEles(articleContent.toBlockEles());
        articlePageJsonWrapper.setArticleInfoEle(articleInfoEle);

        return articlePageJsonWrapper;
    }

    /* POST METHOD GOES HERE */

    @PostMapping(
            value = "doSaveArticle",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @Transactional(rollbackFor = Exception.class)
    public String doSaveArticle(@RequestBody DoSaveArticleWrapper doSaveArticleWrapper) {
        // pass the article to service
        resourceService.saveArticle(doSaveArticleWrapper.getArticle());

        // return the status
        return "request successful";
    }


    @PostMapping(
            value = "doRemoveArticle",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @Transactional(rollbackFor = Exception.class)
    public String doRemoveArticle(@RequestBody DoRemoveArticleWrapper doRemoveArticleWrapper) {
        // remove the article by service
        resourceService.removeArticle(doRemoveArticleWrapper.getId());

        return "remove successful";
    }

    @PostMapping(
            value = "doSaveApplicant",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public String doSaveApplicant(@RequestBody Applicant applicant) {
        // save the applicant
        resourceService.saveApplicant(applicant);

        return "successful";
    }

    /* CLASS OF THE REQUEST AND RESPONSE WRAPPER */

    public static class HomePageJsonWrapper {
        private List<HomeFlowEle> homeFlowEles;
        private List<HomeItemEle> homeItemEles;

        public List<HomeFlowEle> getHomeFlowEles() {
            return homeFlowEles;
        }

        public void setHomeFlowEles(List<HomeFlowEle> homeFlowEles) {
            this.homeFlowEles = homeFlowEles;
        }

        public List<HomeItemEle> getHomeItemEles() {
            return homeItemEles;
        }

        public void setHomeItemEles(List<HomeItemEle> homeItemEles) {
            this.homeItemEles = homeItemEles;
        }
    }

    public static class ArticlePageJsonWrapper {

        private List<BlockEle> blockEles;
        private ArticleInfoEle articleInfoEle;

        public List<BlockEle> getBlockEles() {
            return blockEles;
        }

        public void setBlockEles(List<BlockEle> blockEles) {
            this.blockEles = blockEles;
        }

        public ArticleInfoEle getArticleInfoEle() {
            return articleInfoEle;
        }

        public void setArticleInfoEle(ArticleInfoEle articleInfoEle) {
            this.articleInfoEle = articleInfoEle;
        }
    }

    public static class MenuPageJsonWrapper {
        private List<MenuFlowEle> menuFlowEles;
        private List<MenuItemEle> menuItemEles;

        public List<MenuFlowEle> getMenuFlowEles() {
            return menuFlowEles;
        }

        public void setMenuFlowEles(List<MenuFlowEle> menuFlowEles) {
            this.menuFlowEles = menuFlowEles;
        }

        public List<MenuItemEle> getMenuItemEles() {
            return menuItemEles;
        }

        public void setMenuItemEles(List<MenuItemEle> menuItemEles) {
            this.menuItemEles = menuItemEles;
        }
    }

    public static class DoSaveArticleWrapper {
        private Article article;

        public DoSaveArticleWrapper(@JsonProperty("article") Article article) {
            this.article = article;
        }

        public Article getArticle() {
            return article;
        }

        public void setArticle(Article article) {
            this.article = article;
        }
    }

    public static class DoRemoveArticleWrapper {
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
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