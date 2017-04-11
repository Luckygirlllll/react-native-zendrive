import com.zendrive.sdk.AccidentInfo;
import com.zendrive.sdk.DriveInfo;
import com.zendrive.sdk.DriveResumeInfo;
import com.zendrive.sdk.DriveStartInfo;
import com.zendrive.sdk.ZendriveIntentService;
import com.zendrive.sdk.ZendriveLocationSettingsResult;

public class WrapperZendriveIntentService extends ZendriveIntentService {

    private static final String ZENDRIVE_ACCIDENT_EVENT = "ZENDRIVE_ACCIDENT_EVENT";

    public WrapperZendriveIntentService() {
        super("WrapperZendriveIntentService");
    }

    @Override
    public void onAccident(AccidentInfo accidentInfo) {
        WritableMap params = Arguments.createMap();
        sendEvent(ZENDRIVE_ACCIDENT_EVENT, params);
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        if (this.getReactApplicationContext().hasActiveCatalystInstance()) {
            try {
                this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit(eventName, params);
            } catch (Exception e) {
                Log.e(TAG, "sendEvent called before bundle loaded");
            }
        }
    }
}