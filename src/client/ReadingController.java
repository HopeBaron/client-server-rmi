package client;

import client.tree.ArticleTreeNode;
import client.tree.UserTreeNode;
import common.exception.ErrorCode;
import common.exception.RemoteAuthenticationException;
import common.model.Article;
import common.model.User;
import common.rmi.Connection;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.rmi.RemoteException;

public class ReadingController extends JFrame {
    private JTree tree;
    private JTextField titleField;
    private JPanel root;
    private JButton refreshButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton editButton;
    private JTextArea contentArea;

    public ReadingController(Connection connection) {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        contentArea.setLineWrap(true);
        reset();
        updateList(connection);
        refreshButton.addActionListener(e -> {
            updateList(connection);
            reset();
        });
        editButton.addActionListener(e -> {
            updateSelection(connection);
            updateList(connection);
            reset();
        });
        saveButton.addActionListener(e -> {
            try {
                connection.addArticle(new Article(0, titleField.getText(), contentArea.getText(), connection.getCurrentUser(), true));
            } catch (RemoteException remoteException) {
                handleException(remoteException);
            }
                updateList(connection);
                reset();

        });
        deleteButton.addActionListener(e -> {
            deleteSelection(connection);
            updateList(connection);
            reset();
        });

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) return;
            Object userObject = node.getUserObject();
            updateContent();
            updateButtons(connection, userObject);
        });
    }

    public JPanel getRoot() {
        return root;
    }

    private void updateList(Connection connection) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.setRoot(new DefaultMutableTreeNode("Articles"));
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        try {
            for (User user : connection.getUsers()) {

                DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(new UserTreeNode(user));
                for (Article article : connection.getArticlesOf(user.getId())) {
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(new ArticleTreeNode(article));
                    userNode.add(node);
                }
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
                root.add(userNode);
            }
            model.reload();
        } catch (RemoteException e) {
            handleException(e);
        }
    }


    private void deleteSelection(Connection connection) {
        try {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if(node == null) return;
            Object userObject = node.getUserObject();
            if (userObject instanceof UserTreeNode) {
                User o = ((UserTreeNode) userObject).getUser();
                connection.deleteUser(o.getId());
            }
            if (userObject instanceof ArticleTreeNode) {
                Article o = ((ArticleTreeNode) userObject).getArticle();
                connection.deleteArticle(o.getId());
            }
        } catch (RemoteException e) {
            handleException(e);
        }
    }

    private void updateSelection(Connection connection) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null) return;
        Object userObject = node.getUserObject();
        if (!(userObject instanceof ArticleTreeNode)) return;
        Article article = ((ArticleTreeNode) userObject).getArticle();
        article.setContent(contentArea.getText());
        article.setTitle(titleField.getText());
        try {
            connection.updateArticle(article);
        } catch (RemoteException exception) {
            handleException(exception);

        }
    }

    private void updateButtons(Connection connection, Object node) {
        try {
            if (node == null) reset();
            User currentUser = connection.getCurrentUser();
            if (node instanceof ArticleTreeNode) {
                Article article = ((ArticleTreeNode) node).getArticle();
                boolean canModify = currentUser.canModify(article);
                deleteButton.setEnabled(canModify);
                editButton.setEnabled(canModify);
            }
            if (node instanceof UserTreeNode) {
                User user = ((UserTreeNode) node).getUser();
                deleteButton.setEnabled(currentUser.canModify(user));
                editButton.setEnabled(false);

            }
        } catch (RemoteException exception) {
            handleException(exception);
        }
    }

    private void updateContent() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null || node.isRoot()) {
            reset();
            return;
        }
        Object userObject = node.getUserObject();
        if (userObject instanceof ArticleTreeNode) {
            Article o = ((ArticleTreeNode) userObject).getArticle();
            titleField.setText(o.getTitle());
            contentArea.setText(o.getContent());
        }
    }

    private void reset() {
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        contentArea.setText("");
        titleField.setText("");
    }

    private void handleException(RemoteException exception) {
        Throwable internalError = exception.getCause();
        if (internalError instanceof RemoteAuthenticationException) {
            RemoteAuthenticationException asAuthException = (RemoteAuthenticationException) internalError;
            if (asAuthException.getErrorCode() == ErrorCode.MISSING_ACCESS) {
                setVisible(false);

            }

            JOptionPane.showMessageDialog(null, asAuthException.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(null, "Remote error occurred.");
    }
}
