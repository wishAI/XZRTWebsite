package com.wishai.xzrtw.model.src;


import javax.persistence.*;

@Entity
@Table(name = "text_src")
public class TextSrc {

    @Id
    @GeneratedValue
    private Integer srcKey;

    private String src;

    public Integer getSrcKey() {
        return srcKey;
    }

    public void setSrcKey(Integer srcKey) {
        this.srcKey = srcKey;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

}
