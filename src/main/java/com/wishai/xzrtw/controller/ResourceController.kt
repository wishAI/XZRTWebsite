package com.wishai.xzrtw.controller

import com.wishai.xzrtw.exception.FileNotStore
import com.wishai.xzrtw.service.ResourceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.commons.CommonsMultipartFile

@Controller
@RequestMapping("src")
class ResourceController @Autowired
constructor(private val resourceService: ResourceService) {

    /* GET METHODS GOES HERE*/

    @GetMapping(value = "image", produces = arrayOf(MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE))
    fun imageFrame(
            @RequestParam("id") id: Int?,
            @RequestParam(value = "is-cached", defaultValue = "false") isCached: Boolean
    ): ResponseEntity<Resource> {
        val resource = resourceService.loadSrc(id!!, ResourceService.FILE_TYPE_IMAGE, isCached) as Resource
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + resource.filename)
                .body(resource)
    }

    @GetMapping(value = "text")
    @ResponseBody
    fun textFrame(
            @RequestParam("id") id: Int?,
            @RequestParam(value = "is-cached", defaultValue = "false") isCached: Boolean
    ): String {
        return resourceService.loadSrc(id!!, ResourceService.SRC_TYPE_TEXT, isCached) as String
    }

    @GetMapping("uploadPage")
    fun uploadPage(map: ModelMap): String {
        return "UploadPage"
    }

    /* POST METHOD GOES HERE */

    @PostMapping(value = "doCacheFile", consumes = arrayOf("multipart/form-data"))
    @ResponseBody
    @Transactional(rollbackFor = arrayOf(Exception::class))
    fun doCacheFile(
            @RequestParam("type") type: String,
            @RequestParam("file") file: CommonsMultipartFile
    ): DoCacheSrcStatusWrapper {
        // store the file and get the cache id
        var cacheId = 0
        try {
            cacheId = resourceService.storeSrc(file, type)!!
        } catch (fileNotStore: FileNotStore) {
            fileNotStore.printStackTrace()
            // return the failed status
            return DoCacheSrcStatusWrapper(-1, false)
        }

        // return the status data wrapper
        return DoCacheSrcStatusWrapper(cacheId, true)
    }

    @PostMapping("doCacheText")
    @ResponseBody
    @Transactional(rollbackFor = arrayOf(Exception::class))
    fun doCacheText(
            @RequestParam("text") text: String
    ): DoCacheSrcStatusWrapper {
        // store the text and get the cache id
        var cacheId = 0
        try {
            cacheId = resourceService.storeSrc(text, ResourceService.SRC_TYPE_TEXT)!!
        } catch (fileNotStore: FileNotStore) {
            fileNotStore.printStackTrace()
        }

        // return the status data wrapper
        return DoCacheSrcStatusWrapper(cacheId, true)
    }

    /* CLASS OF THE REQUEST AND RESPONSE WRAPPER */

    class DoCacheSrcStatusWrapper(var cacheId: Int, var isSuccessful: Boolean)

}
