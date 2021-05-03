package server.publisher;

import common.model.Article;
import common.subscriber.Subscriber;

import java.util.HashSet;
import java.util.Set;

public class SetPublisher<T> implements Publisher<T> {
    private HashSet<Subscriber<T>> subscribers = new HashSet<>();

    @Override
    public boolean addSubscriber(Subscriber<T> subscriber) {
        return subscribers.add(subscriber);
    }

    @Override
    public boolean removeSubscriber(Subscriber<T> subscriber) {
        return subscribers.remove(subscriber);
    }

    @Override
    public Set<Subscriber<T>> getSubscribers() {
        return subscribers;
    }

    @Override
    public void notifySubscribers(T data) {
        for(Subscriber<T> subscriber : subscribers) {
            subscriber.update(data);
        }
    }
}
