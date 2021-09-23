package com.mythic.madjayq;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class InjectorProvider {

    private static Injector instance;

    public static void set(Injector injector) {
        instance = injector;
    }

    public static Injector get() {
        return instance;
    }
}
