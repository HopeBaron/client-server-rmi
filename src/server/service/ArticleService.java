package server.service;

import common.model.Article;
import server.dao.behaviors.ArticleDAO;
import server.dao.behaviors.UserDAO;
import server.publisher.Publisher;
import server.publisher.SetPublisher;

import java.rmi.RemoteException;
import java.rmi.ServerError;
import java.sql.SQLException;
import java.util.List;

public final class ArticleService {
    private Publisher<Article> publisher = new SetPublisher<>();
    private ArticleDAO articleDAO;
    private UserDAO userDAO;

    public ArticleService(ArticleDAO articleDAO, UserDAO userDAO, Publisher<Article> publisher) {
        this.publisher = publisher;
        this.articleDAO = articleDAO;
        this.userDAO = userDAO;
    }

    public Article get(long invoker, long id) throws RemoteException {
        try {
            return articleDAO.get(id);
        } catch (SQLException e) {
            throw new ServerError("Internal server error", null);
        }
    }

    public Article delete(long id) throws RemoteException {
        try {
            Article target = articleDAO.get(id);
            articleDAO.delete(id);
            publisher.notifySubscribers(target);
            return target;
        } catch (SQLException e) {
            throw new ServerError("Internal server error", null);
        }
    }

    public List<Article> getArticlesOf(long invoker, long target) throws RemoteException {
        try {
            return articleDAO.getArticlesOf(target);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServerError("Internal server error", null);
        }
    }

    public Publisher<Article> getPublisher() {
        return publisher;
    }
}
