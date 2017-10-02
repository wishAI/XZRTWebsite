package com.wishai.xzrtw.component;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;


public class UrlBuilder {
    private StringBuilder folders, params;
    private String connType, host;

    public void setConnectionType(String conn) {
        connType = conn;
    }

    public UrlBuilder(){
        folders = new StringBuilder();
        params = new StringBuilder();
    }

    public UrlBuilder(String host) {
        this();
        this.host = host;
    }

    public UrlBuilder addSubfolder(String folder) {
        folders.append("/");
        folders.append(folder);

        return this;
    }

    public UrlBuilder addParameter(String parameter, String value) {
        if(params.toString().length() > 0){params.append("&");}
        params.append(parameter);
        params.append("=");
        params.append(value);

        return this;
    }

    public String getURL() throws URISyntaxException, MalformedURLException {
        URI uri = new URI(connType, host, folders.toString(),
                params.toString(), null);
        return uri.toURL().toString();
    }

    public String getRelativeURL() throws URISyntaxException, MalformedURLException{
        URI uri = new URI(null, null, folders.toString(), params.toString(), null);
        return uri.toString();
    }
}