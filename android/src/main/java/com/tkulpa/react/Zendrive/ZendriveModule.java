package com.tkulpa.react.Zendrive;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.zendrive.sdk.Zendrive;
import com.zendrive.sdk.ZendriveConfiguration;
import com.zendrive.sdk.ZendriveDriveDetectionMode;
import com.zendrive.sdk.ZendriveDriverAttributes;
import com.zendrive.sdk.ZendriveAccidentConfidence;
import com.zendrive.sdk.ZendriveState;

public class ZendriveModule extends ReactContextBaseJavaModule {

	private static final String MODULE_NAME = "ZendriveWrapper";
	public static final String TAG = "Zendrive";
	private static ReactApplicationContext reactContext;

	public ZendriveModule(ReactApplicationContext reactContext) {
		super(reactContext);
		this.reactContext = reactContext;
	}

	public static void sendEvent(String eventName, @Nullable WritableMap params, Context context) {
		ReactContext rContext;
		if (reactContext == null) {
			Log.e(TAG, "no available ReactContext, trying to create from Context");
			rContext = new ReactApplicationContext(context);
		} else {
			rContext = reactContext;
		}

		if (rContext != null && rContext.hasActiveCatalystInstance()) {
			try {
				rContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
						.emit(eventName, params);
				Log.d(TAG, "sendEvent success (eventName: " + eventName + ")");
			} catch (Exception e) {
				Log.e(TAG, "sendEvent called before bundle loaded");
			}
		} else {
			Log.e(TAG, "could not emit " + eventName + ". No available ReactContext");
		}
	}

	@Override
	public String getName() {
		return MODULE_NAME;
	}

	@ReactMethod
  public void init(String key, String driverId, String firstName, String lastName, String email,
      final Callback callback) {
		ZendriveDriverAttributes zendriveDriverAttributes = new ZendriveDriverAttributes();
		ZendriveDriverAttributes driverAttributes = new ZendriveDriverAttributes();
		driverAttributes.setFirstName(firstName);
		driverAttributes.setLastName(lastName);
		driverAttributes.setEmail(email)
		// 	driverAttributes.setPhoneNumber(driver.getString("phoneNumber"));

		ZendriveConfiguration zendriveConfiguration = new ZendriveConfiguration(key, driverId);
		zendriveConfiguration.setDriverAttributes(driverAttributes);
		zendriveConfiguration.setDriveDetectionMode(ZendriveDriveDetectionMode.AUTO_ON);

		Zendrive.setup(
				this.getReactApplicationContext(),
				zendriveConfiguration,
				WrapperZendriveBroadcastReceiver.class,
				WrapperZendriveNotificationProvider.class,
				new CallbackWrapper(callback)
		);
	}

	@ReactMethod
	public void isSetup(final Callback callback) {
		callback.invoke(false, Zendrive.isSDKSetup());
	}

	@ReactMethod
	public void getState(final Callback callback) {
		ZendriveState zendriveState = Zendrive.getZendriveState();
		WritableMap params = new WritableNativeMap();
		params.putBoolean("isDriveInProgress", zendriveState.isDriveInProgress);
		params.putBoolean("isForegroundService", zendriveState.isForegroundService);

//		// TODO: Zendrive configuration serializer
//		params.putBoolean("zendriveConfiguration", zendriveState.zendriveConfiguration);

		callback.invoke(false, params);
	}

	@ReactMethod
	public void startDrive(String id, final Callback callback) {
		Zendrive.startDrive(id, new CallbackWrapper(callback));
	}

	@ReactMethod
	public void stopDrive(String id, final Callback callback) {
		Zendrive.stopDrive(id, new CallbackWrapper(callback));
	}

	@ReactMethod
	public void triggerAccident(final Callback callback) {
		if (!Zendrive.isAccidentDetectionSupported(this.getReactApplicationContext())) {
			callback.invoke(false, "Zendrive collision detection is not supported on this device");
		}
		try {
			Zendrive.triggerMockAccident(this.getReactApplicationContext(), ZendriveAccidentConfidence.HIGH, new CallbackWrapper(callback));
		} catch (Exception e) {
			callback.invoke(false, e.getMessage());
		}
	}

	@ReactMethod
	public void setAutoDriveDetectionMode(Boolean enabled, final Callback callback) {
		ZendriveDriveDetectionMode driveDetectionMode;
		if (enabled) {
			driveDetectionMode = ZendriveDriveDetectionMode.AUTO_ON;
		} else {
			driveDetectionMode = ZendriveDriveDetectionMode.AUTO_OFF;
		}
		Zendrive.setZendriveDriveDetectionMode(driveDetectionMode, new CallbackWrapper(callback));
	}

	@ReactMethod
	public void shutdown(final Callback callback) {
		Zendrive.teardown(new CallbackWrapper(callback));
	}

	@ReactMethod
	public void startSession(String id) {
		Zendrive.startSession(id);
	}

	@ReactMethod
	public void stopSession() {
		Zendrive.stopSession();
	}
}
