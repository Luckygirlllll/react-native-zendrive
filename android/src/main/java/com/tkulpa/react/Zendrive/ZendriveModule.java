package com.tkulpa.react.Zendrive;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class ZendriveModule extends ReactContextBaseJavaModule {

    private static final String MODULE_NAME = "ZendriveWrapper";
    public static final String TAG = "Zendrive";

    public ZendriveModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

}
