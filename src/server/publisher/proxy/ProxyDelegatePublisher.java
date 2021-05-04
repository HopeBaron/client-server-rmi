package server.publisher.proxy;

import common.rmi.ProxyPublisher;
import common.subscriber.Subscriber;
import server.publisher.Publisher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public final class ProxyDelegatePublisher<T> extends UnicastRemoteObject implements ProxyPublisher<T> {
    private final Publisher<T> publisher;

    public ProxyDelegatePublisher(Publisher<T> publisher) throws RemoteException {
        super();
        this.publisher = publisher;
    }

    @Override
    public boolean addSubscriber(Subscriber<T> subscriber) throws RemoteException {
        return publisher.addSubscriber(subscriber);
    }

    @Override
    public boolean removeSubscriber(Subscriber<T> subscriber) throws RemoteException {
        return publisher.removeSubscriber(subscriber);
    }
}
