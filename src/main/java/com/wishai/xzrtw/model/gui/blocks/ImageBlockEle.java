package com.wishai.xzrtw.model.gui.blocks;

import com.wishai.xzrtw.service.ResourceService;

public class ImageBlockEle extends BlockEle {
    private String info;
    private String imageUrl;

    public ImageBlockEle() {
        super.set_type(ResourceService.Companion.getBLOCK_TYPE_IMAGE());
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
