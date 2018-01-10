package com.shestakam.counterExperiments;

import com.shestakam.counterExperiments.Counter;

import java.util.concurrent.locks.ReentrantReadWriteLock;

class LockCounter implements Counter {
    private long counter = 0;
    private ReentrantReadWriteLock.WriteLock lock = new ReentrantReadWriteLock().writeLock();

    @Override
    public void increment() {
        lock.lock();
        counter++;
        lock.unlock();
    }

    @Override
    public long getCounter() {
        return counter;
    }
}