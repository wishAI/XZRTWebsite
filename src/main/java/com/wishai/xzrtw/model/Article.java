package com.wishai.xzrtw.model;


import com.wishai.xzrtw.model.gui.*;
import com.wishai.xzrtw.model.query.ArticleQuery;

import javax.persistence.*;
import java.util.Date;

import static com.wishai.xzrtw.service.ResourceService.LANG_ZH;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue
    private Integer id;
    private Date createDate = new Date();
    private String category;
    private String author;


    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "article__article_content",
            joinColumns = @JoinColumn(name = "articleId"),
            inverseJoinColumns = @JoinColumn(name = "contentZhId")
    )
    private ArticleContent articleContentZh;


    /* GETTERS AND SETTERS GOES HERE */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArticleContent getArticleContentZh() {
        return articleContentZh;
    }

    public void setArticleContentZh(ArticleContent articleContentZh) {
        this.articleContentZh = articleContentZh;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /* CUSTOMIZES METHODS GOES HERE */

    public void setArticleContentByLang(ArticleContent articleContent, String lang) {
        switch (lang) {
            case LANG_ZH:
                this.articleContentZh = articleContent;
                break;
        }
    }

    public ArticleContent getArticleContentByLang(String lang) {
        switch (lang) {
            case LANG_ZH:
                return getArticleContentZh();
        }
        return null;
    }

//    private String toHrefUrl(String lang) {
//        UrlBuilder urlBuilder = new UrlBuilder();
//        urlBuilder.addSubfolder("article");
//        urlBuilder.addParameter("id", String.valueOf(this.id));
//        urlBuilder.addParameter("lang", lang);
//
//        try {
//            return urlBuilder.getRelativeURL();
//        } catch (URISyntaxException | MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private ArticleQuery toArticleQuery(String lang) {
        ArticleQuery articleQuery = new ArticleQuery();

        articleQuery.setId(id);
        articleQuery.setLang(lang);

        return articleQuery;
    }

    public HomeItemEle toHomeItemEle(String lang) {
        // initialize a home item element
        HomeItemEle homeItemEle = new HomeItemEle();

        // set the properties
        homeItemEle.setTitle(getArticleContentByLang(lang).getTitle());
        homeItemEle.setQuery(toArticleQuery(lang));

        return homeItemEle;
    }

    public HomeFlowEle toHomeFlowEle(String lang) {
        // initialize a home flow element
        HomeFlowEle homeFlowEle = new HomeFlowEle();

        // set the properties
        homeFlowEle.setCoverUrl(getArticleContentByLang(lang).getCover().toUrl());
        homeFlowEle.setTitle(getArticleContentByLang(lang).getTitle());
        homeFlowEle.setInfo(getArticleContentByLang(lang).getInfo());
        homeFlowEle.setQuery(toArticleQuery(lang));

        return homeFlowEle;
    }

    public ArticleInfoEle toArticleInfoEle(String lang) {
        // initialize a new article info element
        ArticleInfoEle articleInfoEle = new ArticleInfoEle();

        // set the properties
        articleInfoEle.setAuthor(author);
        articleInfoEle.setCategory(category);
        articleInfoEle.setCreateDate(createDate);
        articleInfoEle.setTitle(getArticleContentByLang(lang).getTitle());
        articleInfoEle.setInfo(getArticleContentByLang(lang).getInfo());

        return articleInfoEle;
    }

    public MenuItemEle toMenuItemEle(String lang) {
        MenuItemEle menuItemEle = new MenuItemEle();

        menuItemEle.setCreateDate(createDate);
        menuItemEle.setQuery(toArticleQuery(lang));

        ArticleContent articleContent = getArticleContentByLang(lang);
        menuItemEle.setTitle(articleContent.getTitle());

        return menuItemEle;
    }

    public MenuFlowEle toMenuFlowEle(String lang) {
        MenuFlowEle menuFlowEle = new MenuFlowEle();

        menuFlowEle.setCreateDate(createDate);
        menuFlowEle.setQuery(toArticleQuery(lang));

        ArticleContent articleContent = getArticleContentByLang(lang);
        menuFlowEle.setTitle(articleContent.getTitle());
        menuFlowEle.setCoverUrl(articleContent.getCover().toUrl());

        return menuFlowEle;
    }



    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", category='" + category + '\'' +
                ", articleContentZh=" + articleContentZh +
                '}';
    }
}
