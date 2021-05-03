package common.model;

public final class Article implements Entity {
    private long id;
    private String title;
    private String content;
    private User author;

    public Article(long id, String title, String content, User author) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

}
