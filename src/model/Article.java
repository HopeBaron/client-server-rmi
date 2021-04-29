package model;

import rmi.interfaces.ArticleBehavior;

import java.rmi.RemoteException;

public final class Article implements ArticleBehavior {
    private final String id;
    private String title;
    private final Author author;
    private String content;

    public Article(String id, String title, Author author, String content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Author getAuthor() {
        return author;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Article setTitle(String title) {
        this.title = title;
    }

    @Override
    public Article setContent(String content) {
        this.content = content;
    }

    @Override
    public String getId() throws RemoteException {
        return null;
    }
}
