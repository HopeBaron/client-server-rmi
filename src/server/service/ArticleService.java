package server.service;

import common.exception.*;
import common.model.Article;
import common.model.User;
import server.dao.behaviors.ArticleDAO;
import server.dao.behaviors.UserDAO;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public final class ArticleService {
    private ArticleDAO articleDAO;
    private UserDAO userDAO;

    public ArticleService(ArticleDAO articleDAO, UserDAO userDAO) {
        this.articleDAO = articleDAO;
        this.userDAO = userDAO;
    }

    public Article getArticle(long invoker, long id) throws RemoteAuthenticationException {
        try {
            User invokerUser = userDAO.get(invoker);
            Article article = articleDAO.get(id);

            if (!invokerUser.isActive()) throw new MissingPermissionException();
            else if (!invokerUser.isAdmin() && !article.isShown())
                return null;
            else return article;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }

    public List<Article> getAll(long invoker) throws RemoteAuthenticationException {
        try {
            User invokerUser = userDAO.get(invoker);

            if (!invokerUser.isActive()) throw new MissingPermissionException();
            else if (!invokerUser.isAdmin()) return articleDAO.getStateArticles(true);
            else return articleDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }

    public Article addArticle(long invoker, Article article) throws RemoteAuthenticationException {
        try {
            User invokerUser = userDAO.get(invoker);
            if (!invokerUser.isActive()) throw new MissingPermissionException();
            if(article.getContent().isEmpty() || article.getTitle().isEmpty()) throw new InvalidArticleException();
            else return articleDAO.create(article);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }

    }

    public boolean delete(long invoker, long id) throws RemoteAuthenticationException {
        try {
            User invokerUser = userDAO.get(invoker);
            Article article = articleDAO.get(id);
            if (!invokerUser.isActive()) throw new MissingAccessException();

            if (!invokerUser.canModify(article))
                throw new MissingPermissionException();
            else return articleDAO.delete(article.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }

    public List<Article> getArticlesOf(long invoker, long target) throws RemoteAuthenticationException {
        try {
            User invokerUser = userDAO.get(invoker);

            if (!invokerUser.isActive()) throw new MissingAccessException();
            else if (!invokerUser.isAdmin()) return articleDAO.getStateArticlesOf(target, true);
            else return articleDAO.getArticlesOf(target);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }

    public Article updateArticle(long invoker, Article article) throws RemoteAuthenticationException {
        try {
            User invokerUser = userDAO.get(invoker);
            if (!invokerUser.isActive()) throw new MissingAccessException();
            else if (!invokerUser.canModify(articleDAO.get(article.getId()))) throw new MissingPermissionException();
            else if (articleDAO.update(article)) return articleDAO.get(article.getId());
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }
}
