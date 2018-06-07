package com.wishai.xzrtw.service;

import com.wishai.xzrtw.model.Article;
import com.wishai.xzrtw.model.gui.HomeFlowEle;
import com.wishai.xzrtw.model.gui.HomeItemEle;
import com.wishai.xzrtw.model.gui.MenuFlowEle;
import com.wishai.xzrtw.model.gui.MenuItemEle;
import com.wishai.xzrtw.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.wishai.xzrtw.service.ResourceService.*;

@Service
public class GuiServiceImpl implements GuiService {

    private final ArticleRepository articleRepository;

    @Autowired
    public GuiServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    @Override
    public List<HomeItemEle> makeHomeItemEles(String lang) {
        // get articles from database
        List<Article> newsArticles = articleRepository.findTop10ByCategoryOrderByCreateDateAsc(Companion.getCATEGORY_NEWS());

        // setup list of home item elements
        List<HomeItemEle> homeItemEles = new ArrayList<HomeItemEle>();

        // add elements into the list
        for(Article newsArticle : newsArticles) {
            homeItemEles.add(newsArticle.toHomeItemEle(lang));
        }

        return homeItemEles;
    }

    @Override
    public List<HomeFlowEle> makeHomeFlowEles(String lang) {
        final int FLOW_NUM = 4;

        // get articles from database
        List<Article> raceArticles = articleRepository.findTop4ByCategoryOrderByCreateDateAsc(Companion.getCATEGORY_RACE());
        List<Article> techArticles = articleRepository.findTop4ByCategoryOrderByCreateDateAsc(Companion.getCATEGORY_TECH());

        // setup list of home flow elements
        List<HomeFlowEle> homeFlowEles = new ArrayList<HomeFlowEle>();

        // add elements into the list
        for (Article raceArticle : raceArticles) {
            homeFlowEles.add(raceArticle.toHomeFlowEle(lang));
        }

        // append empty flows if not enough
        if (raceArticles.size() < FLOW_NUM) {
            for (int i = 0; i < FLOW_NUM - raceArticles.size(); i++) {
                homeFlowEles.add(HomeFlowEle.Companion.makeEmptyInstance());
            }
        }

        for (Article techArticle : techArticles) {
            homeFlowEles.add(techArticle.toHomeFlowEle(lang));
        }

        if (techArticles.size() < FLOW_NUM) {
            for (int i = 0; i < FLOW_NUM - techArticles.size(); i++) {
                homeFlowEles.add(HomeFlowEle.Companion.makeEmptyInstance());
            }
        }

        return homeFlowEles;
    }


    @Override
    public List<MenuItemEle> makeMenuItemEles(String lang, String category, int pageNum) {
        final int ITEM_NUM = 10;

        // get articles in the database
        Page<Article> articles = articleRepository.findByCategoryOrderByCreateDateAsc(category, new PageRequest(pageNum - 1, ITEM_NUM));

        // setup the list of menu item elements
        List<MenuItemEle> menuItemEles = new ArrayList<MenuItemEle>();

        // add elements to the list
        for(Article article : articles.getContent()) {
            menuItemEles.add(article.toMenuItemEle(lang));
        }

        return menuItemEles;
    }

    @Override
    public List<MenuFlowEle> makeMenuFlowEles(String lang, String categpry) {
        // get articles in the database
        List<Article> articles = articleRepository.findTop3ByCategoryOrderByCreateDateAsc(categpry);

        // setup the list of menu flow elements
        List<MenuFlowEle> menuFlowEles = new ArrayList<MenuFlowEle>();

        // add elements to the list
        for(Article article : articles) {
            menuFlowEles.add(article.toMenuFlowEle(lang));
        }

        return menuFlowEles;
    }


}
