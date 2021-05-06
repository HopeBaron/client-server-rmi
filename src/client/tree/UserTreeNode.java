package client.tree;

import common.model.User;

public class UserTreeNode {
    private User user;
    public UserTreeNode(User user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return user.getUsername();
    }

    public User getUser() {
        return user;
    }
}
