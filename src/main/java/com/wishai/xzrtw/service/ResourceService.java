package com.wishai.xzrtw.service;

import com.wishai.xzrtw.config.XzrtPropertiesFactory;
import com.wishai.xzrtw.exception.FileNotStore;
import com.wishai.xzrtw.model.Applicant;
import com.wishai.xzrtw.model.Article;
import com.wishai.xzrtw.model.ArticleContent;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public interface ResourceService {

    /* STATIC RESOURCES GOES HERE*/

    String LANG_ZH = "zh";
    String LANG_EN = "en";

    String BLOCK_TYPE_PARAGRAPH = "paragraph";
    String BLOCK_TYPE_IMAGE = "image";

    String CATEGORY_RACE = "race";
    String CATEGORY_NEWS = "news";
    String CATEGORY_TECH = "tech";

    String FILE_TYPE_IMAGE = "image";
    String FILE_TYPE_MUSIC = "music";
    String FILE_TYPE_VIDEO = "video";
    String FILE_TYPE_ZIP = "zip";
    String FILE_TYPE_PDF = "pdf";
    String SRC_TYPE_TEXT = "text";

    String FILE_PATH_IMAGE = "images";
    String FILE_PATH_MUSIC = "musics";
    String FILE_PATH_VIDEO = "videos";
    String FILE_PATH_ZIP = "zips";
    String FILE_PATH_PDF = "pdfs";

    Path FILE_ROOT_PATH = Paths.get(XzrtPropertiesFactory.getXzrtProperties().getUploadRootPath());
    
    static String categoryType2GalleryType(String category) {
        switch (category) {
            case ResourceService.CATEGORY_RACE:
                return "race";
            case ResourceService.CATEGORY_NEWS:
                return "news";
            case ResourceService.CATEGORY_TECH:
                return "tech";
        }
        return null;
    }

    /* get the sub path of file */
    static Path file2Path(String type, String fileName) {
        switch (type) {
            case FILE_TYPE_IMAGE:
                return FILE_ROOT_PATH.resolve(FILE_PATH_IMAGE).resolve(fileName);
            case FILE_TYPE_MUSIC:
                return FILE_ROOT_PATH.resolve(FILE_PATH_MUSIC).resolve(fileName);
            case FILE_TYPE_VIDEO:
                return FILE_ROOT_PATH.resolve(FILE_PATH_VIDEO).resolve(fileName);
            case FILE_TYPE_PDF:
                return FILE_ROOT_PATH.resolve(FILE_PATH_PDF).resolve(fileName);
            case FILE_TYPE_ZIP:
                return FILE_ROOT_PATH.resolve(FILE_PATH_ZIP).resolve(fileName);
        }
        return null;
    }

    /* ?? SHOULD BUILD IN THE IMPL */
    static Article makeArticle(String lang, String author, String category, ArticleContent articleContent) {
        Article article = new Article();

        // initial a article content, and bind to the article
        article.setArticleContentByLang(articleContent, lang);

        // set single non-json data to article
        article.setAuthor(author);
        article.setCategory(category);


        // set the author
        return article;
    }


    /* FUNCTIONS FOR THE ARTICLES */


    ArticleContent makeArticleContent(Map<String, Object> data);

    /* should add the src before create the article */
    Article findArticle(Integer id);

    Integer saveArticle(Article article);

    Integer saveApplicant(Applicant applicant);



    /* FUNCTIONS FOR ADDING AND GETTING SRC */
    Object loadSrc(Integer id, String dType);

    Object loadSrc(Integer id, String dType, boolean isCached);

    Integer storeSrc(Object src, String dType) throws FileNotStore;


    /* FUNCTIONS USED TO TEST, SHOULD BE REMOVED IN THE FUTURE */

    void removeArticle(Integer id);



    /* THINGS ARE NOT REQUIRED ANYMORE */

}
