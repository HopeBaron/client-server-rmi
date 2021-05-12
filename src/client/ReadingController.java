package client;

import client.tree.ArticleTreeNode;
import client.tree.UserTreeNode;
import common.exception.ErrorCode;
import common.exception.RemoteAuthenticationException;
import common.model.Article;
import common.model.Permission;
import common.model.User;
import common.rmi.Connection;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.rmi.RemoteException;

public class ReadingController extends JFrame {
    private JTree tree1;
    private JEditorPane editorPane1;
    private JTextField textField1;
    private JPanel root;
    private JButton refreshButton;
    private JButton deleteButton;
    private JButton saveButton;

    public ReadingController(Connection connection) {
        deleteButton.setEnabled(false);
        updateList(connection);
        refreshButton.addActionListener(e -> updateList(connection));
        saveButton.addActionListener(e -> {
            try {
                connection.addArticle(new Article(0, textField1.getText(), editorPane1.getText(), connection.getCurrentUser(), true));
                updateList(connection);
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });
        deleteButton.addActionListener(e -> {
            deleteSelection(connection);
            updateList(connection);
        });

        tree1.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1.getLastSelectedPathComponent();
            Object userObject = node.getUserObject();
            updateContent();
            try {
                updateButtons(connection, userObject);
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });
    }

    public JPanel getRoot() {
        return root;
    }

    private void updateList(Connection connection) {
        DefaultTreeModel model = (DefaultTreeModel) tree1.getModel();
        model.setRoot(new DefaultMutableTreeNode("Articles"));
        tree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);


        try {
            for (User user : connection.getUsers()) {

                DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(new UserTreeNode(user));
                for (Article article : connection.getArticlesOf(user.getId())) {
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(new ArticleTreeNode(article));
                    userNode.add(node);
                }
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree1.getModel().getRoot();
                root.add(userNode);
            }
            model.reload();
        } catch (RemoteException e) {
            Throwable ex = e.getCause();
            if (ex instanceof RemoteAuthenticationException) {
                RemoteAuthenticationException exx = (RemoteAuthenticationException) ex;
                ErrorCode code = exx.getErrorCode();
                if (code == ErrorCode.MISSING_ACCESS) {
                    dispose();
                }
            }
        }
    }

    private void deleteSelection(Connection connection) {
        try {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1.getLastSelectedPathComponent();
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
            e.printStackTrace();
        }
    }

    private void updateButtons(Connection connection, Object node) throws RemoteException {

        if (node instanceof ArticleTreeNode) {
            Article article = ((ArticleTreeNode) node).getArticle();
            deleteButton.setEnabled(article.getAuthor().getId() == connection.getCurrentUserId() ||
                    connection.getCurrentUser().getPermission().contains(Permission.MODIFY_OTHERS));
        }
        if (node instanceof UserTreeNode) {
            User user = ((UserTreeNode) node).getUser();
            deleteButton.setEnabled(user.getId() == connection.getCurrentUserId() ||
                    connection.getCurrentUser().getPermission().contains(Permission.MODIFY_OTHERS));

        }
    }

    private void updateContent() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1.getLastSelectedPathComponent();
        if (node == null) return;
        Object userObject = node.getUserObject();
        if (userObject instanceof ArticleTreeNode) {
            Article o = ((ArticleTreeNode) userObject).getArticle();
            textField1.setText(o.getTitle());
            editorPane1.setText(o.getContent());
        }
    }
}
