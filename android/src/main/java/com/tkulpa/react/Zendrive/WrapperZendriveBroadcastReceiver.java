package com.tkulpa.react.Zendrive;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableNativeArray;
import com.zendrive.sdk.AccidentInfo;
import com.zendrive.sdk.AnalyzedDriveInfo;
import com.zendrive.sdk.DriveResumeInfo;
import com.zendrive.sdk.DriveStartInfo;
import com.zendrive.sdk.EstimatedDriveInfo;
import com.zendrive.sdk.ZendriveBroadcastReceiver;
import com.zendrive.sdk.ZendriveLocationSettingsResult;

import android.content.Context;
import android.util.Log;

import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.WritableArray;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class WrapperZendriveBroadcastReceiver extends ZendriveBroadcastReceiver {
    public static final String TAG = "Zendrive";

    @Override
    public void onAccident(Context context, AccidentInfo accidentInfo) {
        Log.i(TAG, "Accident detected");
        WritableMap params = new WritableNativeMap();
        params.putString("sessionId", accidentInfo.sessionId);
        params.putString("trackingId", accidentInfo.trackingId);
        params.putString("driveId", accidentInfo.driveId);
        params.putString("accidentId", accidentInfo.accidentId);

        params.putDouble("timestampMillis", ((Number) accidentInfo.timestampMillis).doubleValue());
        params.putString("confidence", accidentInfo.confidence.toString());
        params.putDouble("locationLat", accidentInfo.location.latitude);
        params.putDouble("locationLon", accidentInfo.location.longitude);
        this.sendEvent(context, "accident", params);
    }

    @Override
    public void onDriveStart(Context context, DriveStartInfo startInfo) {
        Log.i(TAG, "DriveStart detected");
        WritableMap params = new WritableNativeMap();
        params.putString("sessionId", startInfo.sessionId);
        params.putString("trackingId", startInfo.trackingId);
        params.putDouble("starTimeMillis", ((Number) startInfo.startTimeMillis).doubleValue());
        params.putDouble("startLocationLat", startInfo.startLocation.latitude);
        params.putDouble("startLocationLon", startInfo.startLocation.longitude);
        this.sendEvent(context, "driveStart", params);
    }

    @Override
    public void onDriveResume(Context context, DriveResumeInfo resumeInfo) {
        Log.i(TAG, "DriveResume detected");
        WritableMap params = new WritableNativeMap();
        params.putString("sessionId", resumeInfo.sessionId);
        params.putString("trackingId", resumeInfo.trackingId);
        params.putDouble("driveGapStartTimestampMillis", resumeInfo.driveGapStartTimestampMillis);
        params.putDouble("driveGapEndTimestampMillis", resumeInfo.driveGapEndTimestampMillis);
        this.sendEvent(context, "driveResume", params);
    }

    @Override
    public void onDriveEnd(Context context, EstimatedDriveInfo estimatedDriveInfo) {
        Log.i(TAG, "DriveEnd detected");
        WritableMap params = new WritableNativeMap();
        params.putString("sessionId", estimatedDriveInfo.sessionId);
        params.putString("trackingId", estimatedDriveInfo.trackingId);
        params.putString("driveId", estimatedDriveInfo.driveId);
        params.putString("driveType", estimatedDriveInfo.driveType.toString());
        params.putString("userMode", estimatedDriveInfo.userMode.toString());

        params.putInt("score", estimatedDriveInfo.score.zendriveScore);
        params.putDouble("starTimeMillis", ((Number) estimatedDriveInfo.startTimeMillis).doubleValue());
        params.putDouble("endTimeMillis", ((Number) estimatedDriveInfo.endTimeMillis).doubleValue());
        params.putDouble("averageSpeed", estimatedDriveInfo.averageSpeed);
        params.putDouble("distanceMeters", estimatedDriveInfo.distanceMeters);
        params.putDouble("maxSpeed", estimatedDriveInfo.maxSpeed);

        // TODO: Events and waipoints mapping
//        params.putMap("events", Arguments.fromArray());
//        params.putMap("waypoints", Arguments.fromArray());
        this.sendEvent(context, "driveEnd", params);
    }

    @Override
    public void onLocationSettingsChange(Context context, ZendriveLocationSettingsResult locationSettingsResult) {
        Log.i(TAG, "LocationSettingsChange detected");
        WritableMap params = new WritableNativeMap();
        WritableArray errorsList = new WritableNativeArray();
        for (ZendriveLocationSettingsResult.Error error: locationSettingsResult.errors) {
            errorsList.pushString(error.toString());
        }
        params.putArray("locationSettingsErrors", errorsList);
        this.sendEvent(context, "locationSettingsChange", params);
    }

    @Override
    public void onLocationPermissionsChange(Context context, boolean granted) {
        Log.i(TAG, "LocationPermissionsChange detected");
        WritableMap params = new WritableNativeMap();
        params.putBoolean("granted", granted);
        this.sendEvent(context, "locationPermissionsChange", params);
    }

    @Override
    public void onDriveAnalyzed(Context context, AnalyzedDriveInfo analyzedDriverInfo) {
        Log.i(TAG, "DriveAnalyzed detected");
        WritableMap params = new WritableNativeMap();
        params.putString("sessionId", analyzedDriverInfo.sessionId);
        params.putString("trackingId", analyzedDriverInfo.trackingId);
        params.putString("driveId", analyzedDriverInfo.driveId);
        params.putString("driveType", analyzedDriverInfo.driveType.toString());
        params.putString("userMode", analyzedDriverInfo.userMode.toString());

        params.putInt("score", analyzedDriverInfo.score.zendriveScore);
        params.putDouble("starTimeMillis", ((Number) analyzedDriverInfo.startTimeMillis).doubleValue());
        params.putDouble("endTimeMillis", ((Number) analyzedDriverInfo.endTimeMillis).doubleValue());
        params.putDouble("averageSpeed", analyzedDriverInfo.averageSpeed);
        params.putDouble("distanceMeters", analyzedDriverInfo.distanceMeters);
        params.putDouble("maxSpeed", analyzedDriverInfo.maxSpeed);

        // TODO: Events and waipoints mapping
//        params.putMap("events", Arguments.fromArray());
//        params.putMap("waypoints", Arguments.fromArray());
        this.sendEvent(context, "driveAnalyzed", params);
    }

    private void sendEvent(Context context, String eventName, @Nullable WritableMap params) {
        ReactApplicationContext reactContext = new ReactApplicationContext(context);
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
