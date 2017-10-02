package com.wishai.xzrtw.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.wishai.xzrtw.model.block.Block;
import com.wishai.xzrtw.model.gui.blocks.BlockEle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "article_content")
public class ArticleContent {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "coverFileId")
    @JsonProperty("cover")
    private File cover;
    private String info;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "article_content__block",
            joinColumns = {@JoinColumn(name = "articleContentId")},
            inverseJoinColumns = {@JoinColumn(name = "blockId")}
    )
    @OrderColumn(name = "sequence")
    private List<Block> blocks;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public File getCover() {
        return cover;
    }

    public void setCover(File cover) {
        this.cover = cover;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    /* CUSTOMIZE FUNCTIONS GOES HERE */

    public List<BlockEle> toBlockEles() {
        List<BlockEle> blockEles = new ArrayList<BlockEle>();

        for(Block block : blocks) {
            blockEles.add(block.toBlockEle());
        }

        return blockEles;
    }

}
