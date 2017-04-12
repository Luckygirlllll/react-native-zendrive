package com.tkulpa.react.Zendrive;

import com.facebook.react.bridge.Callback;

import com.zendrive.sdk.ZendriveOperationCallback;
import com.zendrive.sdk.ZendriveOperationResult;

public class CallbackWrapper implements ZendriveOperationCallback {
    
    private Callback callback;

    public CallbackWrapper(Callback jsCallback) {
        super();
        this.callback = jsCallback;
    }

    @Override
    public void onCompletion(ZendriveOperationResult result) {
        if (result.isSuccess()) {
            callback.invoke(true);
        } else {
            callback.invoke(false, result.getErrorCode());
        }
    }
}