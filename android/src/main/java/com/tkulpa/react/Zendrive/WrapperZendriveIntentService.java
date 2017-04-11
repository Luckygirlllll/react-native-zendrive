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

import javax.annotation.Nullable;

public class WrapperZendriveIntentService extends ZendriveIntentService {

    private static final String ZENDRIVE_ACCIDENT_EVENT = "ZENDRIVE_ACCIDENT_EVENT";
    public static final String TAG = "Zendrive";

    public WrapperZendriveIntentService() {
        super("WrapperZendriveIntentService");
    }

    @Override
    public void onAccident(AccidentInfo accidentInfo) {
        WritableMap params = Arguments.createMap();
        sendEvent(ZENDRIVE_ACCIDENT_EVENT, params);
    }

    @Override
    public void onDriveStart(DriveStartInfo startInfo) {

    }

    @Override
    public void onDriveResume(DriveResumeInfo resumeInfo) {

    }

    @Override
    public void onDriveEnd(DriveInfo driveInfo) {

    }

    @Override
    public void onLocationSettingsChange(ZendriveLocationSettingsResult locationSettingsResult) {

    }

    @Override
    public void onLocationPermissionsChange(boolean granted) {

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