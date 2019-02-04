package com.lanbing.spring.xnolscan.helper;

import java.util.concurrent.atomic.AtomicBoolean;

public class StatusHelper {

    private static AtomicBoolean starting = new AtomicBoolean(false);

    public static boolean canStart() {
        return starting.compareAndSet(false, true);
    }

    public static boolean isStarting() {
        return starting.get();
    }

    public static void stop() {
        starting.set(Boolean.FALSE.booleanValue());
    }
}
