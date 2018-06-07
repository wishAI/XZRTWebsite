package com.wishai.xzrtw.model.gui


import com.wishai.xzrtw.config.XzrtPropertiesFactory
import com.wishai.xzrtw.model.File
import com.wishai.xzrtw.model.query.ArticleQuery
import com.wishai.xzrtw.service.ResourceService

class HomeFlowEle {
    var title = "not available"
    var info = "not available"

    var coverUrl: String? = null
    var query: ArticleQuery? = null

    companion object {

        private val NONE_IMAGE_ID = XzrtPropertiesFactory.xzrtProperties!!.noneImageId

        /* CUSTOMIZE FUNCTIONS GOES HERE */

        fun makeEmptyInstance(): HomeFlowEle {
            val homeFlowEle = HomeFlowEle()
            val file = File()
            file.id = NONE_IMAGE_ID
            file.type = ResourceService.FILE_TYPE_IMAGE
            homeFlowEle.coverUrl = file.toUrl()
            return homeFlowEle
        }
    }

}
