package client.tree;

import common.model.Article;

public class ArticleTreeNode {
    Article article;
    public ArticleTreeNode(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    @Override
    public String toString() {
        return article.getTitle();
    }
}
