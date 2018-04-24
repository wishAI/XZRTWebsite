package com.wishai.xzrtw.controller;

import com.wishai.xzrtw.exception.FileNotStore;
import com.wishai.xzrtw.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("src")
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /* GET METHODS GOES HERE*/

    @GetMapping(value = "image", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<Resource> imageFrame(
            @RequestParam("id") Integer id,
            @RequestParam(value ="is-cached", defaultValue = "false") boolean isCached
    ) {
        Resource resource = (Resource) resourceService.loadSrc(id, ResourceService.FILE_TYPE_IMAGE, isCached);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + resource.getFilename())
                .body(resource);
    }

    @GetMapping(value = "text")
    @ResponseBody
    public String textFrame(
            @RequestParam("id") Integer id,
            @RequestParam(value ="is-cached", defaultValue = "false") boolean isCached
            ) {
        return (String) resourceService.loadSrc(id, ResourceService.SRC_TYPE_TEXT, isCached);
    }

    @GetMapping("uploadPage")
    public String uploadPage(ModelMap map) {
        return "UploadPage";
    }

    /* POST METHOD GOES HERE */

    @PostMapping(value = "doCacheFile", consumes = "multipart/form-data")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public DoCacheSrcStatusWrapper doCacheFile(
            @RequestParam("type") String type,
            @RequestParam("file") CommonsMultipartFile file
    ) {
        // store the file and get the cache id
        int cacheId = 0;
        try {
            cacheId = resourceService.storeSrc(file, type);
        } catch (FileNotStore fileNotStore) {
            fileNotStore.printStackTrace();
            // return the failed status
            return new DoCacheSrcStatusWrapper(-1, false);
        }

        // return the status data wrapper
        return new DoCacheSrcStatusWrapper(cacheId, true);
    }

    @PostMapping("doCacheText")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public DoCacheSrcStatusWrapper doCacheText(
            @RequestParam("text") String text
    ) {
        // store the text and get the cache id
        int cacheId = 0;
        try {
            cacheId = resourceService.storeSrc(text, ResourceService.SRC_TYPE_TEXT);
        } catch (FileNotStore fileNotStore) {
            fileNotStore.printStackTrace();
        }

        // return the status data wrapper
        return new DoCacheSrcStatusWrapper(cacheId, true);
    }

    /* CLASS OF THE REQUEST AND RESPONSE WRAPPER */

    public static class DoCacheSrcStatusWrapper {
        private int cacheId;
        private boolean isSuccessful;

        public DoCacheSrcStatusWrapper(int cacheId, boolean isSuccessful) {
            this.cacheId = cacheId;
            this.isSuccessful = isSuccessful;
        }

        public int getCacheId() {
            return cacheId;
        }

        public void setCacheId(int cacheId) {
            this.cacheId = cacheId;
        }

        public boolean isSuccessful() {
            return isSuccessful;
        }

        public void setSuccessful(boolean successful) {
            isSuccessful = successful;
        }
    }

}
