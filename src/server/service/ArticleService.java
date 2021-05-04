package server.service;

import common.model.Article;
import server.dao.behaviors.ArticleDAO;
import server.dao.behaviors.UserDAO;
import server.publisher.SetPublisher;

import java.rmi.RemoteException;
import java.rmi.ServerError;
import java.sql.SQLException;

public final class ArticleService {
    private SetPublisher<Article> publisher = new SetPublisher<>();
    private ArticleDAO articleDAO;
    private UserDAO userDAO;

    public Article get(long id) throws RemoteException {
        try {
            return articleDAO.get(id);
        } catch (SQLException e) {
            throw new ServerError("Internal server error", null);
        }
    }

    public Article delete(long invoker,long id) throws RemoteException {
        try {
            Article target = articleDAO.get(id);
            articleDAO.delete(id);
            publisher.notifySubscribers(target);
            return target;
        } catch (SQLException throwables) {
            throw new ServerError("Internal server error", null);
        }
    }


    public SetPublisher<Article> getPublisher() {
        return publisher;
    }
}
