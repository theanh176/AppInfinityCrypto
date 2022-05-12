package com.example.appinfinitycrypto.Model;

public class DataNews {
    private int id;
    private String guid;
    private String published_on;
    private String imageurl;
    private String title;
    private String url;
    private String source;
    private String body;
    private String tags;
    private String categories;
    private String upvotes;
    private String downvotes;
    private String lang;
    private Source_info source_info;

    public static class Source_info {
        private String name;
        private String lang;
        private String img;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPublished_on() {

        // Convert timestamp to date
        String date = "";
        try {
            long time = Long.parseLong(published_on);
            date = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(time * 1000));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public void setPublished_on(String published_on) {
        this.published_on = published_on;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(String upvotes) {
        this.upvotes = upvotes;
    }

    public String getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(String downvotes) {
        this.downvotes = downvotes;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Source_info getSource_info() {
        return source_info;
    }

    public void setSource_info(Source_info source_info) {
        this.source_info = source_info;
    }
}
