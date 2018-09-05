package com.tkulpa.react.Zendrive;

import com.facebook.react.bridge.WritableNativeArray;
import android.content.Intent;
import com.zendrive.sdk.AccidentInfo;
import com.zendrive.sdk.AnalyzedDriveInfo;
import com.zendrive.sdk.DriveResumeInfo;
import com.zendrive.sdk.DriveStartInfo;
import com.zendrive.sdk.EstimatedDriveInfo;
import com.zendrive.sdk.ZendriveBroadcastReceiver;

import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.WritableArray;


public class WrapperZendriveBroadcastReceiver extends ZendriveBroadcastReceiver {
    public static final String TAG = "Zendrive";

    @Override
    public void onAccident(Context context, AccidentInfo accidentInfo) {
        Log.i(TAG, "Accident detected: " + accidentInfo.toString());
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
        Log.i(TAG, "DriveStart detected: " + startInfo.toString());
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
        Log.i(TAG, "DriveResume detected: " + resumeInfo.toString());
        WritableMap params = new WritableNativeMap();
        params.putString("sessionId", resumeInfo.sessionId);
        params.putString("trackingId", resumeInfo.trackingId);
        params.putDouble("driveGapStartTimestampMillis", resumeInfo.driveGapStartTimestampMillis);
        params.putDouble("driveGapEndTimestampMillis", resumeInfo.driveGapEndTimestampMillis);
        ZendriveModule.sendEvent("driveResume", params, context);
    }

    @Override
    public void onDriveEnd(Context context, EstimatedDriveInfo estimatedDriveInfo) {
        Log.i(TAG, "DriveEnd detected: " + estimatedDriveInfo.toString());
        WritableMap params = new WritableNativeMap();
        params.putString("sessionId", estimatedDriveInfo.sessionId);
        params.putString("trackingId", estimatedDriveInfo.trackingId);
        params.putString("driveId", estimatedDriveInfo.driveId);
        params.putString("driveType", estimatedDriveInfo.driveType.toString());
//        params.putString("userMode", estimatedDriveInfo.userMode.toString());

        params.putInt("zendriveScore", estimatedDriveInfo.score.zendriveScore);
        params.putDouble("starTimeMillis", (double) estimatedDriveInfo.startTimeMillis);
        params.putDouble("endTimeMillis", (double) estimatedDriveInfo.endTimeMillis);
        params.putDouble("averageSpeed", estimatedDriveInfo.averageSpeed);
        params.putDouble("distanceMeters", estimatedDriveInfo.distanceMeters);
        params.putDouble("maxSpeed", estimatedDriveInfo.maxSpeed);

        // TODO: Events and waipoints mapping
//        params.putMap("events", Arguments.fromArray());
//        params.putMap("waypoints", Arguments.fromArray());
        ZendriveModule.sendEvent("driveEnd", params, context);
    }

    @Override
    public void onZendriveSettingsConfigChanged(Context context, boolean errorsFound,
                                                boolean warningsFound) {
        WritableMap params = new WritableNativeMap();
        params.putBoolean("errorsFound", errorsFound);
        params.putBoolean("warningsFound", warningsFound);
        ZendriveModule.sendEvent("locationPermissionsChange", params, context);
    }

    @Override
    public void onDriveAnalyzed(Context context, AnalyzedDriveInfo analyzedDriverInfo) {
        Log.i(TAG, "DriveAnalyzed detected: " + analyzedDriverInfo.toString());
        WritableMap params = new WritableNativeMap();
        params.putString("sessionId", analyzedDriverInfo.sessionId);
        params.putString("trackingId", analyzedDriverInfo.trackingId);
        params.putString("driveId", analyzedDriverInfo.driveId);
        params.putString("driveType", analyzedDriverInfo.driveType.toString());
//        params.putString("userMode", analyzedDriverInfo.userMode.toString());

        params.putInt("zendriveScore", analyzedDriverInfo.score.zendriveScore);
        params.putDouble("starTimeMillis", (double) analyzedDriverInfo.startTimeMillis);
        params.putDouble("endTimeMillis", (double) analyzedDriverInfo.endTimeMillis);
        params.putDouble("averageSpeed", analyzedDriverInfo.averageSpeed);
        params.putDouble("distanceMeters", analyzedDriverInfo.distanceMeters);
        params.putDouble("maxSpeed", analyzedDriverInfo.maxSpeed);

        // TODO: Events and waipoints mapping
//        params.putMap("events", Arguments.fromArray());
//        params.putMap("waypoints", Arguments.fromArray());
        ZendriveModule.sendEvent("driveAnalyzed", params, context);
    }




}