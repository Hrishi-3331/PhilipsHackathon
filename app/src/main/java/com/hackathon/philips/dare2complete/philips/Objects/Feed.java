package com.hackathon.philips.dare2complete.philips.Objects;

public class Feed {
    private String title;
    private String image;
    private String category;
    private String content;

    public Feed() {
    }

    public Feed(String title, String image, String category, String content) {
        this.title = title;
        this.image = image;
        this.category = category;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
