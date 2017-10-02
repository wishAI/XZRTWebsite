package com.wishai.xzrtw.model;

import java.io.Serializable;

public class Progress implements Serializable {
    private long pBytesRead;
    private long pContentLength;
    private Integer pItems;

    public long getpBytesRead() {
        return pBytesRead;
    }

    public void setpBytesRead(long pBytesRead) {
        this.pBytesRead = pBytesRead;
    }

    public long getpContentLength() {
        return pContentLength;
    }

    public void setpContentLength(long pContentLength) {
        this.pContentLength = pContentLength;
    }

    public Integer getpItems() {
        return pItems;
    }

    public void setpItems(Integer pItems) {
        this.pItems = pItems;
    }

    @Override
    public String toString() {
        return "Progress{" +
                "pBytesRead=" + pBytesRead +
                ", pContentLength=" + pContentLength +
                ", pItems=" + pItems +
                '}';
    }

    public boolean isEmpty() {
        return pItems == null;
    }
}
