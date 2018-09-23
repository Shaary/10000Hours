package com.shaary.a10000hours.presenter;

import com.shaary.a10000hours.contracts.TimerRepository;

import org.junit.Test;

public class TrackingActivityPresenterTest {

    @Test
    public void shouldAddDataToDatabase(){

    }

    private class MockDataBase implements TimerRepository {
        boolean added;

        @Override
        public boolean add(long... items) {
            added = true;
            return true;
        }

        @Override
        public void update(long item) {

        }

        @Override
        public void remove(long item) {

        }
    }

}