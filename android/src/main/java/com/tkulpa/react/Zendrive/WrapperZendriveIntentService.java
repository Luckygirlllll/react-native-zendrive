package com.tkulpa.react.Zendrive;

import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.Arguments;
import com.zendrive.sdk.AccidentInfo;
import com.zendrive.sdk.DriveInfo;
import com.zendrive.sdk.DriveResumeInfo;
import com.zendrive.sdk.DriveStartInfo;
import com.zendrive.sdk.ZendriveIntentService;
import com.zendrive.sdk.ZendriveLocationSettingsResult;

import android.util.Log;

import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class WrapperZendriveIntentService extends ZendriveIntentService {

    private static final String ZENDRIVE_ACCIDENT_EVENT = "ZENDRIVE_ACCIDENT_EVENT";
    public static final String TAG = "Zendrive";

    public WrapperZendriveIntentService() {
        super("WrapperZendriveIntentService");
    }

    @Override
    public void onAccident(AccidentInfo accidentInfo) {
        WritableMap params = new WritableNativeMap();
        params.putString("sessionId", accidentInfo.sessionId);
        params.putString("trackingId", accidentInfo.trackingId);
        params.putString("driveId", accidentInfo.driveId);
        params.putString("accidentId", accidentInfo.accidentId);

        params.putDouble("timestampMillis", ((Number) accidentInfo.timestampMillis).doubleValue());
        params.putString("confidence", accidentInfo.confidence.toString());
        params.putDouble("locationLat", accidentInfo.location.latitude);
        params.putDouble("locationLon", accidentInfo.location.longitude);
        this.sendEvent("accident", params);
    }

    @Override
    public void onDriveStart(DriveStartInfo startInfo) {
        WritableMap params = new WritableNativeMap();
        params.putString("sessionId", startInfo.sessionId);
        params.putString("trackingId", startInfo.trackingId);
        params.putDouble("starTimeMillis", ((Number) startInfo.startTimeMillis).doubleValue());
        params.putDouble("startLocationLat", startInfo.startLocation.latitude);
        params.putDouble("startLocationLon", startInfo.startLocation.longitude);
        this.sendEvent("driveStart", params);
    }

    @Override
    public void onDriveResume(DriveResumeInfo resumeInfo) {
        WritableMap params = new WritableNativeMap();
        params.putString("sessionId", resumeInfo.sessionId);
        params.putString("trackingId", resumeInfo.trackingId);
        params.putDouble("driveGapStartTimestampMillis", resumeInfo.driveGapStartTimestampMillis);
        params.putDouble("driveGapEndTimestampMillis", resumeInfo.driveGapEndTimestampMillis);
        this.sendEvent("driveResume", params);
    }

    @Override
    public void onDriveEnd(DriveInfo driveInfo) {
        WritableMap params = new WritableNativeMap();
        params.putString("sessionId", driveInfo.sessionId);
        params.putString("trackingId", driveInfo.trackingId);
        params.putString("driveId", driveInfo.driveId);
        params.putString("driveType", driveInfo.driveType.toString());
        params.putString("userMode", driveInfo.userMode.toString());

        params.putInt("score", driveInfo.score.zendriveScore);
        params.putDouble("starTimeMillis", ((Number) driveInfo.startTimeMillis).doubleValue());
        params.putDouble("endTimeMillis", ((Number) driveInfo.endTimeMillis).doubleValue());
        params.putDouble("averageSpeed", driveInfo.averageSpeed);
        params.putDouble("distanceMeters", driveInfo.distanceMeters);
        params.putDouble("maxSpeed", driveInfo.maxSpeed);

        // TODO: Events and waipoints mapping
//        params.putMap("events", Arguments.fromArray());
//        params.putMap("waypoints", Arguments.fromArray());
        this.sendEvent("driveEnd", params);
    }

    @Override
    public void onLocationSettingsChange(ZendriveLocationSettingsResult locationSettingsResult) {
        WritableMap params = new WritableNativeMap();
        List<String> errorsList = new ArrayList<>();
        for (ZendriveLocationSettingsResult.Error error: locationSettingsResult.errors) {
            errorsList.add(error.toString());
        }
        params.putArray("locationSettingsErrors", Arguments.fromArray(errorsList));
        this.sendEvent("locationSettingsChange", params);
    }

    @Override
    public void onLocationPermissionsChange(boolean granted) {
        WritableMap params = new WritableNativeMap();
        params.putBoolean("granted", granted);
        this.sendEvent("locationPermissionsChange", params);
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        ReactApplicationContext reactContext = (ReactApplicationContext) getApplicationContext();
        if (reactContext.hasActiveCatalystInstance()) {
            try {
                reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit(eventName, params);
            } catch (Exception e) {
                Log.e(TAG, "sendEvent called before bundle loaded");
            }
        }
    }
}
