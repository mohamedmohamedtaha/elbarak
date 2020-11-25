package com.muslimcharityapps.elbarak.utils;

import com.hwangjr.rxbus.Bus;
import com.hwangjr.rxbus.thread.ThreadEnforcer;

/**
 * Created by ssb on 18/6/17.
 */

public class RxBus {
    private static Bus bus;

    public static synchronized Bus get() {
        if (bus == null) {
            bus = new Bus(ThreadEnforcer.ANY);
        }

        return bus;
    }
}
