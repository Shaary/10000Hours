package com.shaary.a10000hours.contracts;

import java.util.List;

public interface TimeRepo<T> {

    //Uses generics to be useful with different types
    void add(T item);

    void add(Iterable<T> items);

    void update(T item);

    void remove(T item);

    String retrieveTime();
}
