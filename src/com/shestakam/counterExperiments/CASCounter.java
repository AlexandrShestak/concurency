package com.shestakam.counterExperiments;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

class CASCounter implements Counter {
    private volatile long counter = 0;
    private Unsafe unsafe;
    private long offset;

    CASCounter() throws Exception {
        unsafe = getUnsafe();
        offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
    }

    @Override
    public void increment() {
        long before = counter;
        while (!unsafe.compareAndSwapLong(this, offset, before, before + 1)) {
            before = counter;
        }
    }

    @Override
    public long getCounter() {
        return counter;
    }

    public static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        return unsafe;
    }
}
