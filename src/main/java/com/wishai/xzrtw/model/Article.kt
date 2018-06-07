package com.wishai.xzrtw.model


import com.wishai.xzrtw.model.gui.*
import com.wishai.xzrtw.model.query.ArticleQuery

import javax.persistence.*
import java.util.Date

import com.wishai.xzrtw.service.ResourceService

@Entity
@Table(name = "article")
class Article {

    /* GETTERS AND SETTERS GOES HERE */

    @Id
    @GeneratedValue
    var id: Int? = null
    var createDate = Date()
    var category: String? = null
    var author: String? = null


    @OneToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
    @JoinTable(
            name = "article__article_content",
            joinColumns = arrayOf(JoinColumn(name = "articleId")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "contentZhId"))
    )
    var articleContentZh: ArticleContent? = null

    /* CUSTOMIZES METHODS GOES HERE */

    fun setArticleContentByLang(articleContent: ArticleContent, lang: String) {
        when (lang) {
            ResourceService.LANG_ZH -> this.articleContentZh = articleContent
        }
    }

    fun getArticleContentByLang(lang: String): ArticleContent? {
        when (lang) {
            ResourceService.LANG_ZH -> return articleContentZh
        }
        return null
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

    private fun toArticleQuery(lang: String): ArticleQuery {
        val articleQuery = ArticleQuery()

        articleQuery.id = id
        articleQuery.lang = lang

        return articleQuery
    }

    fun toHomeItemEle(lang: String): HomeItemEle {
        // initialize a home item element
        val homeItemEle = HomeItemEle()

        // set the properties
        homeItemEle.title = getArticleContentByLang(lang)!!.title
        homeItemEle.query = toArticleQuery(lang)

        return homeItemEle
    }

    fun toHomeFlowEle(lang: String): HomeFlowEle {
        // initialize a home flow element
        val homeFlowEle = HomeFlowEle()

        // set the properties
        homeFlowEle.coverUrl = getArticleContentByLang(lang)!!.cover.toUrl()
        homeFlowEle.title = getArticleContentByLang(lang)!!.title
        homeFlowEle.info = getArticleContentByLang(lang)!!.info
        homeFlowEle.query = toArticleQuery(lang)

        return homeFlowEle
    }

    fun toArticleInfoEle(lang: String): ArticleInfoEle {
        // initialize a new article info element
        val articleInfoEle = ArticleInfoEle()

        // set the properties
        articleInfoEle.author = author
        articleInfoEle.category = category
        articleInfoEle.createDate = createDate
        articleInfoEle.title = getArticleContentByLang(lang)!!.title
        articleInfoEle.info = getArticleContentByLang(lang)!!.info

        return articleInfoEle
    }

    fun toMenuItemEle(lang: String): MenuItemEle {
        val menuItemEle = MenuItemEle()

        menuItemEle.createDate = createDate
        menuItemEle.query = toArticleQuery(lang)

        val articleContent = getArticleContentByLang(lang)
        menuItemEle.title = articleContent!!.title

        return menuItemEle
    }

    fun toMenuFlowEle(lang: String): MenuFlowEle {
        val menuFlowEle = MenuFlowEle()

        menuFlowEle.createDate = createDate
        menuFlowEle.query = toArticleQuery(lang)

        val articleContent = getArticleContentByLang(lang)
        menuFlowEle.title = articleContent!!.title
        menuFlowEle.coverUrl = articleContent.cover.toUrl()

        return menuFlowEle
    }


    override fun toString(): String {
        return "Article{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", category='" + category + '\''.toString() +
                ", articleContentZh=" + articleContentZh +
                '}'.toString()
    }
}
