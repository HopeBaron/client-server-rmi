package rmi.interfaces;

import model.Article;
import model.Author;

import java.rmi.RemoteException;

public interface ArticleBehavior extends EntityBehavior {

    public String getTitle() throws RemoteException;

    public Author getAuthor() throws RemoteException;

    public String getContent() throws RemoteException;

    public ArticleBehavior setTitle(String title) throws RemoteException;

    public ArticleBehavior setContent(String content) throws RemoteException;
}
