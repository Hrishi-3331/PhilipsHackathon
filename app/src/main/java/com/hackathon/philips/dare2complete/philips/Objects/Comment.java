package com.hackathon.philips.dare2complete.philips.Objects;

public class Comment {
    private String writer;
    private String content;
    private String writer_id;

    public Comment() {
    }

    public Comment(String writer, String content, String writer_id) {
        this.writer = writer;
        this.content = content;
        this.writer_id = writer_id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter_id() {
        return writer_id;
    }

    public void setWriter_id(String writer_id) {
        this.writer_id = writer_id;
    }
}
