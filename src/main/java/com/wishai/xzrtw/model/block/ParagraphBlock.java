package com.wishai.xzrtw.model.block;

import com.wishai.xzrtw.model.Text;
import com.wishai.xzrtw.model.gui.blocks.BlockEle;
import com.wishai.xzrtw.model.gui.blocks.ParagraphBlockEle;
import com.wishai.xzrtw.service.ResourceService;

import javax.persistence.*;

@Entity
@Table(name = "block_paragraph")
public class ParagraphBlock extends Block {



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "textId")
    private Text text;

    public ParagraphBlock() {
        super.setdType(ResourceService.BLOCK_TYPE_PARAGRAPH);
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    /* CUSTOMIZE FUNCTIONS */

    @Override
    public BlockEle toBlockEle() {
        ParagraphBlockEle paragraphBlockEle = new ParagraphBlockEle();
        paragraphBlockEle.setTextUrl(text.toUrl());

        return paragraphBlockEle;
    }
}
