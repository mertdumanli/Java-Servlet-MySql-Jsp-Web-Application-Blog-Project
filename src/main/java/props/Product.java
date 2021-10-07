package props;

import java.util.Date;

public class Product {
    private int pid;//Ürünün kendi Id si
    private String title;//başlık
    private String summary;//özet
    private String article;//makale
    private Date date;//Yükleme veya son düzenleme tarihi
    private int aid;//Ürünün üreticisinin Id si

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }
}
