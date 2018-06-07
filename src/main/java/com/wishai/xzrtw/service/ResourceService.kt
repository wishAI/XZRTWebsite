package com.wishai.xzrtw.service

import com.wishai.xzrtw.config.XzrtPropertiesFactory
import com.wishai.xzrtw.exception.FileNotStore
import com.wishai.xzrtw.model.Applicant
import com.wishai.xzrtw.model.Article
import com.wishai.xzrtw.model.ArticleContent

import java.nio.file.Path
import java.nio.file.Paths

interface ResourceService {

    /* FUNCTIONS FOR THE ARTICLES */
    fun makeArticleContent(data: Map<String, Any>): ArticleContent

    /* should add the src before create the article */
    fun findArticle(id: Int?): Article?

    fun saveArticle(article: Article): Int?

    fun saveApplicant(applicant: Applicant): Int?


    /* FUNCTIONS FOR ADDING AND GETTING SRC */
    fun loadSrc(id: Int, dType: String): Any?

    fun loadSrc(id: Int, dType: String, isCached: Boolean): Any?

    @Throws(FileNotStore::class)
    fun storeSrc(src: Any, dType: String): Int?


    /* FUNCTIONS USED TO TEST, SHOULD BE REMOVED IN THE FUTURE */

    fun removeArticle(id: Int?)

    companion object {

        /* STATIC RESOURCES GOES HERE*/

        val LANG_ZH = "zh"
        val LANG_EN = "en"

        val BLOCK_TYPE_PARAGRAPH = "paragraph"
        val BLOCK_TYPE_IMAGE = "image"

        val CATEGORY_RACE = "race"
        val CATEGORY_NEWS = "news"
        val CATEGORY_TECH = "tech"

        val FILE_TYPE_IMAGE = "image"
        val FILE_TYPE_MUSIC = "music"
        val FILE_TYPE_VIDEO = "video"
        val FILE_TYPE_ZIP = "zip"
        val FILE_TYPE_PDF = "pdf"
        val SRC_TYPE_TEXT = "text"

        val FILE_PATH_IMAGE = "images"
        val FILE_PATH_MUSIC = "musics"
        val FILE_PATH_VIDEO = "videos"
        val FILE_PATH_ZIP = "zips"
        val FILE_PATH_PDF = "pdfs"

//        val FILE_ROOT_PATH = Paths.get(XzrtPropertiesFactory.xzrtProperties!!.uploadRootPath)

        fun categoryType2GalleryType(category: String): String? {
            when (category) {
                ResourceService.CATEGORY_RACE -> return "race"
                ResourceService.CATEGORY_NEWS -> return "news"
                ResourceService.CATEGORY_TECH -> return "tech"
            }
            return null
        }

        /* get the sub path of file */
        fun file2Path(type: String, fileName: String): Path? {
            val FILE_ROOT_PATH = Paths.get(XzrtPropertiesFactory.xzrtProperties!!.uploadRootPath)
            when (type) {
                FILE_TYPE_IMAGE -> return FILE_ROOT_PATH.resolve(FILE_PATH_IMAGE).resolve(fileName)
                FILE_TYPE_MUSIC -> return FILE_ROOT_PATH.resolve(FILE_PATH_MUSIC).resolve(fileName)
                FILE_TYPE_VIDEO -> return FILE_ROOT_PATH.resolve(FILE_PATH_VIDEO).resolve(fileName)
                FILE_TYPE_PDF -> return FILE_ROOT_PATH.resolve(FILE_PATH_PDF).resolve(fileName)
                FILE_TYPE_ZIP -> return FILE_ROOT_PATH.resolve(FILE_PATH_ZIP).resolve(fileName)
            }
            return null
        }

        /* ?? SHOULD BUILD IN THE IMPL */
        fun makeArticle(lang: String, author: String, category: String, articleContent: ArticleContent): Article {
            val article = Article()

            // initial a article content, and bind to the article
            article.setArticleContentByLang(articleContent, lang)

            // set single non-json data to article
            article.author = author
            article.category = category

            // set the author
            return article
        }
    }


    /* THINGS ARE NOT REQUIRED ANYMORE */

}
