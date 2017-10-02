package com.wishai.xzrtw.model.src;

import org.springframework.core.io.Resource;

public class FileSrc {

    public FileSrc(Resource resource) {
        srcKey = resource.getFilename();
        src = resource;
    }

    private String srcKey;

    private Resource src;

    public String getSrcKey() {
        return srcKey;
    }

    public void setSrcKey(String srcKey) {
        this.srcKey = srcKey;
    }

    public Resource getSrc() {
        return src;
    }

    public void setSrc(Resource src) {
        this.src = src;
    }
}
