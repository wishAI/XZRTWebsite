package com.wishai.xzrtw.model.gui;

import com.wishai.xzrtw.model.query.ArticleQuery;

import java.util.Date;

public class MenuItemEle {
    private String title;
    private Date createDate;
    private ArticleQuery query;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public ArticleQuery getQuery() {
        return query;
    }

    public void setQuery(ArticleQuery query) {
        this.query = query;
    }
}
