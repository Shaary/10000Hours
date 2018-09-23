package com.shaary.a10000hours.contracts;

import java.util.List;

public interface TimerRepository {

    //Uses generics to be useful with different types
    boolean add(String ... items);

    void update(long item);

    void remove(long item);

}
