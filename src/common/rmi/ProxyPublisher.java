package common.rmi;

import common.subscriber.Subscriber;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProxyPublisher<T> extends Remote {

    public boolean addSubscriber(Subscriber<T> subscriber) throws RemoteException;

    public boolean removeSubscriber(Subscriber<T> subscriber) throws RemoteException;
}
