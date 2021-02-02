package cn.wildfire.chat.app.model;

public class Ads {
    private String txt;
    private String img;
    private String id;
    private String url;
    private String status;
    private String timestamp;

    public Ads(String txt, String url) {
        this.txt = txt;
        this.url = url;
    }

    public String getTxt() {
        return txt;
    }

    public String getTitle() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setTitle(String txt) {
        this.txt = txt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
