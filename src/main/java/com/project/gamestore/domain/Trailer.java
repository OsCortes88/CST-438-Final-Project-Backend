package com.project.gamestore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Trailer {

    @JsonProperty("name")
    private String name;
    @JsonProperty("preview")
    private String preview;
    private String uri;

    public Trailer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getUri() {
        return uri;
    }

    @JsonProperty("data")
    private void setUri(Map<String, Object> data) {
        this.uri = (String) data.get("480");
    }
}
