package com.tkulpa.react.Zendrive;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.util.Log;

import com.zendrive.sdk.Zendrive;
import com.zendrive.sdk.ZendriveConfiguration;
import com.zendrive.sdk.ZendriveDriveDetectionMode;
import com.zendrive.sdk.ZendriveDriverAttributes;
import com.zendrive.sdk.ZendriveOperationResult;
import com.zendrive.sdk.ZendriveOperationCallback;

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
	public ZendriveOperationResult init(String key, String driverId, String driverFirstName, String driverLastName,
			String driverEmail, String driverPhoneNumber) {
		ZendriveDriverAttributes driverAttributes = new ZendriveDriverAttributes();
		driverAttributes.setFirstName(driverFirstName);
		driverAttributes.setLastName(driverLastName);
		driverAttributes.setEmail(driverEmail);
		driverAttributes.setPhoneNumber(driverPhoneNumber);
		ZendriveConfiguration zendriveConfiguration = new ZendriveConfiguration(key, driverId);
		zendriveConfiguration.setDriverAttributes(driverAttributes);

		Zendrive.setup(this.getReactApplicationContext(), zendriveConfiguration, WrapperZendriveIntentService.class, new ZendriveOperationCallback() {
			@Override
			public void onCompletion(ZendriveOperationResult result) {
				return result;
			}
		});
	}

	public ZendriveOperationResult setAutoDriveDetectionMode(Boolean enabled) {
		Integer driveDetectionMode;
		if (enabled) {
			driveDetectionMode = ZendriveDriveDetectionMode.AUTO_ON;
		} else {
			driveDetectionMode = ZendriveDriveDetectionMode.AUTO_OFF;
		}

		Zendrive.setDriveDetectionMode(driveDetectionMode, new ZendriveOperationCallback() {
			@Override
			public void onCompletion(ZendriveOperationResult result) {
				return result;
			}
		});
	}

	public ZendriveOperationResult shutdown() {
		Zendrive.teardown(new ZendriveOperationCallback() {
			@Override
			public void onCompletion(ZendriveOperationResult result) {
				return result;
			}
		});
	}

	@ReactMethod
	public ZendriveOperationResult startTrip(String id) {
		Zendrive.startDrive(id, new ZendriveOperationCallback() {
			@Override
			public void onCompletion(ZendriveOperationResult result) {
				return result;
			}
		});
	}

	@ReactMethod
	public ZendriveOperationResult stopTrip(String id) {
		Zendrive.stopDrive(id, new ZendriveOperationCallback() {
			@Override
			public void onCompletion(ZendriveOperationResult result) {
				return result;
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
	public ZendriveOperationResult triggerMockAccident() {
		Zendrive.triggerMockAccident(this.getReactApplicationContext(), ZendriveAccidentConfidence.HIGH,
				new ZendriveOperationCallback() {
					@Override
					public void onCompletion(ZendriveOperationResult result) {
						return result;
					}
				});
	}

	@ReactMethod
	public void startForeground(int notificationId) {
		android.app.Notification notification = createTrackingNotification(context.getApplicationContext());
		Zendrive.startForeground(notificationId, null);
	}

	@ReactMethod
	public void stopForeground() {
		Zendrive.stopForeground();
	}

}
