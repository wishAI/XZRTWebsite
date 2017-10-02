package com.wishai.xzrtw.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.wishai.xzrtw.component.UrlBuilder;

import javax.persistence.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue
    private Integer id;
    private String type;
    private String srcKey;

    @JsonInclude()
    @Transient
    private int cacheId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrcKey() {
        return srcKey;
    }

    public void setSrcKey(String srcKey) {
        this.srcKey = srcKey;
    }

    public int getCacheId() {
        return cacheId;
    }

    public void setCacheId(int cacheId) {
        this.cacheId = cacheId;
    }
    /* CUSTOMIZE FUNCTIONS GOES HERE*/

    public String toUrl() {
        // make the urlBuilder
        UrlBuilder urlBuilder = new UrlBuilder();
        urlBuilder.addSubfolder("src")
                .addSubfolder(getType())
                .addParameter("id", String.valueOf(getId()));

        try {
            return urlBuilder.getRelativeURL();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
