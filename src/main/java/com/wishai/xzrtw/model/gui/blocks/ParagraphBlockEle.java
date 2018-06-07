package com.wishai.xzrtw.model.gui.blocks;

import com.wishai.xzrtw.service.ResourceService;

public class ParagraphBlockEle extends BlockEle {
    private String textUrl;

    public ParagraphBlockEle() {
        super.set_type(ResourceService.Companion.getBLOCK_TYPE_PARAGRAPH());
    }

    public String getTextUrl() {
        return textUrl;
    }

    public void setTextUrl(String textUrl) {
        this.textUrl = textUrl;
    }
}
