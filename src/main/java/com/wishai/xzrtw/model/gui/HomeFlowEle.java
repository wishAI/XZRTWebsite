package com.wishai.xzrtw.model.gui;


import com.wishai.xzrtw.config.XzrtPropertiesFactory;
import com.wishai.xzrtw.model.File;
import com.wishai.xzrtw.model.query.ArticleQuery;
import com.wishai.xzrtw.service.ResourceService;

public class HomeFlowEle {
    private String title = "not available";
    private String info = "not available";
    
    private String coverUrl;
    private ArticleQuery query;

    private static final Integer NONE_IMAGE_ID = XzrtPropertiesFactory.getXzrtProperties().getNoneImageId();

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ArticleQuery getQuery() {
        return query;
    }

    public void setQuery(ArticleQuery query) {
        this.query = query;
    }

    /* CUSTOMIZE FUNCTIONS GOES HERE */

    public static HomeFlowEle makeEmptyInstance() {
        HomeFlowEle homeFlowEle = new HomeFlowEle();
        File file = new File();
        file.setId(NONE_IMAGE_ID);
        file.setType(ResourceService.FILE_TYPE_IMAGE);
        homeFlowEle.setCoverUrl(file.toUrl());
        return homeFlowEle;
    }

}
