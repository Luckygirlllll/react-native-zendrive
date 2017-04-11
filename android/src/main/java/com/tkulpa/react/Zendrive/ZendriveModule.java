package com.tkulpa.react.Zendrive;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Callback;

import android.util.Log;

import com.zendrive.sdk.Zendrive;
import com.zendrive.sdk.ZendriveConfiguration;
import com.zendrive.sdk.ZendriveDriveDetectionMode;
import com.zendrive.sdk.ZendriveDriverAttributes;
import com.zendrive.sdk.ZendriveOperationResult;
import com.zendrive.sdk.ZendriveOperationCallback;
import com.zendrive.sdk.ZendriveAccidentConfidence;

import javax.annotation.Nullable;

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

	@ReactMethod
	public void init(String key, String driverId, ReadableMap driver, final Callback callback) {
		ZendriveDriverAttributes driverAttributes = new ZendriveDriverAttributes();
		if (driver.hasKey("firstName")) {
			driverAttributes.setFirstName(driver.getString("firstName"));
		} else {
			driverAttributes.setFirstName("unknown");
		}
		if (driver.hasKey("lastName")) {
			driverAttributes.setLastName(driver.getString("lastName"));
		} else {
			driverAttributes.setLastName("unknown");
		}
		if (driver.hasKey("email")) {
			driverAttributes.setEmail(driver.getString("email"));
		} else {
			driverAttributes.setEmail("unknown");
		}
		if (driver.hasKey("phoneNumber")) {
			driverAttributes.setPhoneNumber(driver.getString("phoneNumber"));
		} else {
			driverAttributes.setPhoneNumber("unknown");
		}
		ZendriveConfiguration zendriveConfiguration = new ZendriveConfiguration(key, driverId);
		zendriveConfiguration.setDriverAttributes(driverAttributes);

		Zendrive.setup(this.getReactApplicationContext(), zendriveConfiguration, null, //WrapperZendriveIntentService.class
				new ZendriveOperationCallback() {
					@Override
					public void onCompletion(ZendriveOperationResult result) {
						callback.invoke(null, result);
					}
				});
	}

	public void setAutoDriveDetectionMode(Boolean enabled, final Callback callback) {
		ZendriveDriveDetectionMode driveDetectionMode;
		if (enabled) {
			driveDetectionMode = ZendriveDriveDetectionMode.AUTO_ON;
		} else {
			driveDetectionMode = ZendriveDriveDetectionMode.AUTO_OFF;
		}

		Zendrive.setZendriveDriveDetectionMode(driveDetectionMode, new ZendriveOperationCallback() {
			@Override
			public void onCompletion(ZendriveOperationResult result) {
				callback.invoke(null, result);
			}
		});
	}

	public void shutdown(final Callback callback) {
		Zendrive.teardown(new ZendriveOperationCallback() {
			@Override
			public void onCompletion(ZendriveOperationResult result) {
				callback.invoke(null, result);
			}
		});
	}

	@ReactMethod
	public void startTrip(String id, final Callback callback) {
		Zendrive.startDrive(id, new ZendriveOperationCallback() {
			@Override
			public void onCompletion(ZendriveOperationResult result) {
				callback.invoke(null, result);
			}
		});
	}

	@ReactMethod
	public void stopTrip(String id, final Callback callback) {
		Zendrive.stopDrive(id, new ZendriveOperationCallback() {
			@Override
			public void onCompletion(ZendriveOperationResult result) {
				callback.invoke(null, result);
			}
		});
	}

	@ReactMethod
	public void startSession(String id) {
		Zendrive.startSession(id);
	}

	@ReactMethod
	public void stopSession(String id) {
		Zendrive.stopSession();
	}

	@ReactMethod
	public void triggerMockAccident(final Callback callback) {
		Zendrive.triggerMockAccident(this.getReactApplicationContext(), ZendriveAccidentConfidence.HIGH,
				new ZendriveOperationCallback() {
					@Override
					public void onCompletion(ZendriveOperationResult result) {
						callback.invoke(null, result);
					}
				});
	}

	@ReactMethod
	public void startForeground(int notificationId) {
		Zendrive.startForeground(notificationId, null);
	}

	@ReactMethod
	public void stopForeground(Boolean removeNotification) {
		Zendrive.stopForeground(removeNotification);
	}

}
