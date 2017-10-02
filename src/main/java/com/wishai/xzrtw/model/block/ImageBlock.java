package com.wishai.xzrtw.model.block;


import com.wishai.xzrtw.model.File;
import com.wishai.xzrtw.model.gui.blocks.BlockEle;
import com.wishai.xzrtw.model.gui.blocks.ImageBlockEle;
import com.wishai.xzrtw.service.ResourceService;

import javax.persistence.*;

@Entity
@Table(name = "block_image")
public class ImageBlock extends Block {

    private String info;

    /* link to file */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fileId")
    private File file;

    public ImageBlock() {
        super.setdType(ResourceService.BLOCK_TYPE_IMAGE);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /* CUSTOMIZE FUNCTIONS */

    @Override
    public BlockEle toBlockEle() {
        ImageBlockEle imageBlockEle = new ImageBlockEle();
        imageBlockEle.setInfo(info);
        imageBlockEle.setImageUrl(file.toUrl());

        return imageBlockEle;
    }
}
