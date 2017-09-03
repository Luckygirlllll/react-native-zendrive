package com.tkulpa.react.Zendrive;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import com.zendrive.sdk.Zendrive;
import com.zendrive.sdk.ZendriveConfiguration;
import com.zendrive.sdk.ZendriveDriveDetectionMode;
import com.zendrive.sdk.ZendriveDriverAttributes;
import com.zendrive.sdk.ZendriveOperationResult;
import com.zendrive.sdk.ZendriveOperationCallback;
import com.zendrive.sdk.ZendriveAccidentConfidence;

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
		ZendriveDriverAttributes zendriveDriverAttributes = new ZendriveDriverAttributes();

		ZendriveDriverAttributes driverAttributes = new ZendriveDriverAttributes();
 		driverAttributes.setFirstName(driver.getString("firstName"));
 		driverAttributes.setLastName(driver.getString("lastName"));
 		driverAttributes.setEmail(driver.getString("email"));
 		driverAttributes.setPhoneNumber(driver.getString("phoneNumber"));
 		ZendriveConfiguration zendriveConfiguration = new ZendriveConfiguration(
 			key, driverId);
 		zendriveConfiguration.setDriverAttributes(driverAttributes);

 		Zendrive.setup(
 			this.getReactApplicationContext(),
 			zendriveConfiguration,
 			WrapperZendriveIntentService.class,
 			new CallbackWrapper(callback)
 		);
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
	public void startTrip(String id, final Callback callback) {
		Zendrive.startDrive(id, new CallbackWrapper(callback));
	}

	@ReactMethod
	public void stopTrip(String id, final Callback callback) {
		Zendrive.stopDrive(id, new CallbackWrapper(callback));
	}

	@ReactMethod
	public void startSession(String id) {
		Zendrive.startSession(id);
	}

	@ReactMethod
	public void stopSession() {
		Zendrive.stopSession();
	}

	@ReactMethod
	public void triggerMockAccident(final Callback callback) {
		if(!Zendrive.isAccidentDetectionSupported(this.getReactApplicationContext())) {
			callback.invoke(false, "Zendrive collision detection is not supported on this device");
		}
		try {
			Zendrive.triggerMockAccident(this.getReactApplicationContext(), ZendriveAccidentConfidence.HIGH, new CallbackWrapper(callback));
		} catch (Exception e) {
			callback.invoke(false, e.getMessage());
		}
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
