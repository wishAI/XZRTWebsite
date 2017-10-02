package com.wishai.xzrtw.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("xzrt")
public class XzrtProperties {
    private String forwardRootUrl;
    private Integer noneImageId;
    private String uploadRootPath;


    public String getForwardRootUrl() {
        return forwardRootUrl;
    }

    public void setForwardRootUrl(String forwardRootUrl) {
        this.forwardRootUrl = forwardRootUrl;
    }

    public Integer getNoneImageId() {
        return noneImageId;
    }

    public void setNoneImageId(Integer noneImageId) {
        this.noneImageId = noneImageId;
    }

    public String getUploadRootPath() {
        return uploadRootPath;
    }

    public void setUploadRootPath(String uploadRootPath) {
        this.uploadRootPath = uploadRootPath;
    }
}
