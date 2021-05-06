package server.service;

import common.exception.ErrorCode;
import common.exception.RemoteAuthenticationException;
import common.exception.RemoteInternalServerError;
import common.model.Article;
import common.model.Permission;
import common.model.Permissions;
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

    public Article getArticle(long invoker, long id) throws RemoteException {
        try {
            User invokerUser = userDAO.get(invoker);
            Article article = articleDAO.get(id);

            if (!invokerUser.isActive()) throw new RemoteAuthenticationException(ErrorCode.MISSING_ACCESS);
            else if (!invokerUser.getPermission().contains(Permission.MODIFY_OTHERS) && !article.isShown())
                return null;
            else return article;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }

    public List<Article> getAll(long invoker) throws RemoteAuthenticationException, RemoteInternalServerError {
        try {
            User invokerUser = userDAO.get(invoker);

            if (!invokerUser.isActive()) throw new RemoteAuthenticationException(ErrorCode.MISSING_ACCESS);
            else if (!invokerUser.getPermission().contains(Permission.MODIFY_OTHERS))
                return articleDAO.getStateArticles(true);

            else return articleDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }

    public Article addArticle(long invoker, Article article) throws RemoteAuthenticationException, RemoteInternalServerError {
        try {
            User invokerUser = userDAO.get(invoker);
            if (!invokerUser.isActive()) throw new RemoteAuthenticationException(ErrorCode.MISSING_ACCESS);
            else return articleDAO.create(article);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }

    }

    public boolean delete(long invoker, long id) throws RemoteAuthenticationException, RemoteInternalServerError {
        try {
            User invokerUser = userDAO.get(invoker);
            Article article = articleDAO.get(id);
            if (!invokerUser.isActive()) throw new RemoteAuthenticationException(ErrorCode.MISSING_ACCESS);

            Permissions perms = invokerUser.getPermission();
            if (!perms.contains(Permission.MODIFY_OTHERS) || article.getAuthor().getId() != invokerUser.getId())
                throw new RemoteAuthenticationException(ErrorCode.MISSING_PERMISSION);
            else return articleDAO.delete(article.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }

    public List<Article> getArticlesOf(long invoker, long target) throws RemoteAuthenticationException, RemoteInternalServerError {
        try {
            User invokerUser = userDAO.get(invoker);

            if (!invokerUser.isActive()) throw new RemoteAuthenticationException(ErrorCode.MISSING_ACCESS);
            else if (!invokerUser.getPermission().contains(Permission.MODIFY_OTHERS))
                return articleDAO.getStateArticlesOf(target, true);

            else return articleDAO.getArticlesOf(target);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }
}
