package com.tkulpa.react.Zendrive;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.zendrive.sdk.ZendriveNotificationContainer;
import com.zendrive.sdk.ZendriveNotificationProvider;


public class WrapperZendriveNotificationProvider implements ZendriveNotificationProvider {

    public WrapperZendriveNotificationProvider() {}

    @Override
    @RequiresApi(Build.VERSION_CODES.O)
    @NonNull
    public ZendriveNotificationContainer getMaybeInDriveNotificationContainer(@NonNull Context context) {
        return new ZendriveNotificationContainer(
                NotificationUtility.FOREGROUND_MODE_NOTIFICATION_ID,
                NotificationUtility.createMaybeInDriveNotification(context));
    }

    @Override
    @NonNull
    public ZendriveNotificationContainer getInDriveNotificationContainer(@NonNull Context context) {
        return new ZendriveNotificationContainer(
                NotificationUtility.FOREGROUND_MODE_NOTIFICATION_ID,
                NotificationUtility.createInDriveNotification(context));
    }

}
