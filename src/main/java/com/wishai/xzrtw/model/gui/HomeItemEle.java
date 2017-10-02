package com.wishai.xzrtw.model.gui;

import com.wishai.xzrtw.model.query.ArticleQuery;

public class HomeItemEle {
    private String title = "not available";
    private ArticleQuery query;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArticleQuery getQuery() {
        return query;
    }

    public void setQuery(ArticleQuery query) {
        this.query = query;
    }

/* CUSTOMIZE FUNCTIONS */

    public static HomeItemEle makeEmptyInstance() {
        return new HomeItemEle();
    }

}
