package com.wishai.xzrtw.model.block

import com.wishai.xzrtw.config.XzrtPropertiesFactory
import com.wishai.xzrtw.controller.MainController
import com.wishai.xzrtw.model.Text
import com.wishai.xzrtw.model.gui.blocks.BlockEle
import com.wishai.xzrtw.model.gui.blocks.ParagraphBlockEle
import com.wishai.xzrtw.service.ResourceService
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.DependsOn
import javax.persistence.*

@Entity
@Table(name = "block_paragraph")
@DependsOn(value = ["XzrtPropertiesFactory"])
@ComponentScan("com.wishai.xzrtw.config")
class ParagraphBlock : Block {

    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "textId")
    var text: Text? = null

    constructor() {
        super.setdType(ResourceService.BLOCK_TYPE_PARAGRAPH)
    }

    override fun toBlockEle(): BlockEle {
        val paragraphBlockEle = ParagraphBlockEle()
        paragraphBlockEle.textUrl = text!!.toUrl()

        return paragraphBlockEle
    }
}
