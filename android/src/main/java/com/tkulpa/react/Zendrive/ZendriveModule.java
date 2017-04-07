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
    public void init(String key, String driverId) {
		ZendriveDriverAttributes driverAttributes = new ZendriveDriverAttributes();
		driverAttributes.setFirstName("Antoine");
		driverAttributes.setLastName("Niyigena");
		driverAttributes.setEmail("homer@springfield.com");
		driverAttributes.setPhoneNumber("787488389");
		ZendriveConfiguration zendriveConfiguration = new ZendriveConfiguration(
			key, driverId);   // an unique id of the driver specific to your application
		zendriveConfiguration.setDriverAttributes(driverAttributes);

		Zendrive.setup(
			this.getReactApplicationContext(),
			zendriveConfiguration,
			null,        // can be null.
			new ZendriveOperationCallback() {
				@Override
				public void onCompletion(ZendriveOperationResult result) {
					if (result.isSuccess()) {
						Log.e(TAG, "Success");
					} else {
						Log.e(TAG, "Failure");
					}
				}
			}
		);
    }
	
	@ReactMethod
    public void startTrip(String id) {
	Zendrive.startDrive(id,
		new ZendriveOperationCallback() {
			@Override
			public void onCompletion(ZendriveOperationResult result) {
				if (result.isSuccess()) {
					Log.e(TAG, "Trip start: Success");
				} else { 
					Log.e(TAG, "Trip start: Failure");
				}
			}
		}
	);
	}
	
	@ReactMethod
    public void stopTrip(String id) {
	Zendrive.stopDrive(id,
		new ZendriveOperationCallback() {
			@Override
			public void onCompletion(ZendriveOperationResult result) {
				if (result.isSuccess()) {
					Log.e(TAG, "Trip stop: Success");
				} else { 
					Log.e(TAG, "Trip stop: Failure");
				}
			}
		}
	);
	}
	

}
