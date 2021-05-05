package client;

import common.model.Article;
import common.model.User;
import common.rmi.Connection;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.rmi.RemoteException;

public class ReadingController {
    private JTree tree1;
    private JEditorPane editorPane1;
    private JTextField textField1;
    private JPanel root;

    public ReadingController(Connection connection) {
        DefaultTreeModel model = (DefaultTreeModel) tree1.getModel();
        model.setRoot(new DefaultMutableTreeNode("Articles"));
        try {
            for (User user : connection.getUsers()) {
                DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user.getUsername());
                for (Article article : connection.getArticlesOf(user.getId())) {
                   DefaultMutableTreeNode node =  new DefaultMutableTreeNode(article.getTitle());
                   userNode.add(node);
                }
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
                root.add(userNode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public JPanel getRoot() {
        return root;
    }
}
