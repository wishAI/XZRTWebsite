package com.wishai.xzrtw.model;




import com.fasterxml.jackson.annotation.JsonInclude;
import com.wishai.xzrtw.component.UrlBuilder;

import javax.persistence.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Entity
@Table(name = "text")
public class Text {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer srcKey;

    @JsonInclude()
    @Transient
    private int cacheId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSrcKey() {
        return srcKey;
    }

    public void setSrcKey(Integer srcKey) {
        this.srcKey = srcKey;
    }

    public int getCacheId() {
        return cacheId;
    }

    public void setCacheId(int cacheId) {
        this.cacheId = cacheId;
    }

    /* CUSTOMIZE FUNCTIONS */

    public String toUrl() {
        // make url builder
        UrlBuilder urlBuilder = new UrlBuilder();
        urlBuilder.addSubfolder("src")
                .addSubfolder("text")
                .addParameter("id", String.valueOf(getId()));

        try {
            return urlBuilder.getRelativeURL();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
