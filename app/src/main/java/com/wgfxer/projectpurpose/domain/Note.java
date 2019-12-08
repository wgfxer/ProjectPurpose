package com.wgfxer.projectpurpose.domain;

public class Note {
    private int titleResourceId;
    private String body;
    private int hintResourceId;

    public Note() {
    }

    public Note(int titleResourceId, int hintResourceId) {
        this.titleResourceId = titleResourceId;
        this.hintResourceId = hintResourceId;
    }

    public int getTitleResourceId() {
        return titleResourceId;
    }

    public void setTitleResourceId(int titleResourceId) {
        this.titleResourceId = titleResourceId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getHintResourceId() {
        return hintResourceId;
    }

    public void setHintResourceId(int hintResourceId) {
        this.hintResourceId = hintResourceId;
    }
}
