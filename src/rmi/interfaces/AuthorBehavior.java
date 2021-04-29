package rmi.interfaces;

import java.rmi.RemoteException;
import java.util.List;

public interface AuthorBehavior extends UserBehavior {
    List<? extends ArticleBehavior> getCurrentAuthorArticles() throws RemoteException;

}
