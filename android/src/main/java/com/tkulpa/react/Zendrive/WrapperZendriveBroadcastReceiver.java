package com.tkulpa.react.Zendrive;

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

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.WritableArray;


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
        ZendriveModule.sendEvent("accident", params, context);
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
        ZendriveModule.sendEvent("driveStart", params, context);
    }

    @Override
    public void onDriveResume(Context context, DriveResumeInfo resumeInfo) {
        Log.i(TAG, "DriveResume detected");
        WritableMap params = new WritableNativeMap();
        params.putString("sessionId", resumeInfo.sessionId);
        params.putString("trackingId", resumeInfo.trackingId);
        params.putDouble("driveGapStartTimestampMillis", resumeInfo.driveGapStartTimestampMillis);
        params.putDouble("driveGapEndTimestampMillis", resumeInfo.driveGapEndTimestampMillis);
        ZendriveModule.sendEvent("driveResume", params, context);
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
        ZendriveModule.sendEvent("driveEnd", params, context);
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
        ZendriveModule.sendEvent("locationSettingsChange", params, context);
    }

    @Override
    public void onLocationPermissionsChange(Context context, boolean granted) {
        Log.i(TAG, "LocationPermissionsChange detected");
        WritableMap params = new WritableNativeMap();
        params.putBoolean("granted", granted);
        ZendriveModule.sendEvent("locationPermissionsChange", params, context);
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
        ZendriveModule.sendEvent("driveAnalyzed", params, context);
    }

}
