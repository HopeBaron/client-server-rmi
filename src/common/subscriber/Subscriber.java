package common.subscriber;

import common.model.User;

public interface Subscriber<T> {
    void update(T data);
}
