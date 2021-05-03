package server.publisher;

import common.subscriber.Subscriber;

import java.util.Set;

public interface Publisher<T> {

    public boolean addSubscriber(Subscriber<T> subscriber);

    public boolean removeSubscriber(Subscriber<T> subscriber);

    public Set<Subscriber<T>> getSubscribers();

    public void notifySubscribers(T data);
}
