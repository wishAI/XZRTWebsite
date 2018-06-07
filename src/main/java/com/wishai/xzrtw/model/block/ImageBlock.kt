package com.wishai.xzrtw.model.block


import com.wishai.xzrtw.model.File
import com.wishai.xzrtw.model.gui.blocks.BlockEle
import com.wishai.xzrtw.model.gui.blocks.ImageBlockEle
import com.wishai.xzrtw.service.ResourceService
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component

import javax.persistence.*

@Entity
@Table(name = "block_image")
@DependsOn(value = ["XzrtPropertiesFactory"])
@ComponentScan("com.wishai.xzrtw.config")
class ImageBlock : Block() {

    var info: String? = null

    /* link to file */
    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "fileId")
    var file: File? = null

    init {
        super.setdType(ResourceService.BLOCK_TYPE_IMAGE)
    }

    /* CUSTOMIZE FUNCTIONS */

    override fun toBlockEle(): BlockEle {
        val imageBlockEle = ImageBlockEle()
        imageBlockEle.info = info
        imageBlockEle.imageUrl = file!!.toUrl()

        return imageBlockEle
    }
}
