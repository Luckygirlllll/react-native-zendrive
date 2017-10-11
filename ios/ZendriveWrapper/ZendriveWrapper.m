
#import "ZendriveWrapper.h"

@implementation ZendriveWrapper

RCT_EXPORT_MODULE();

+ (id)allocWithZone:(NSZone *)zone {
    static ZendriveWrapper *sharedWrapper = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedWrapper = [super allocWithZone:zone];
    });
    return sharedWrapper;
}

- (NSArray<NSString *> *)supportedEvents {
  return @[@"accident", @"driveStart", @"driveResume", @"driveEnd", @"locationApproved", @"locationDenied", @"driveAnalyzed"];
}

RCT_EXPORT_METHOD(
                  init:(NSString *)key
                  driverId:(NSString *)driverId
                  firstName:(NSString *)firstName
                  lastName:(NSString *)lastName
                  email:(NSString *)email
                  callback:(RCTResponseSenderBlock)callback)
{
    id<ZendriveDelegateProtocol> zdDelegate = [[ZendriveDelegateManager alloc] init];
    self.zendriveDelegate = zdDelegate; // keep a strong reference to the delegate object

    ZendriveConfiguration *configuration = [[ZendriveConfiguration alloc] init];
    ZendriveDriverAttributes *driverAttributes = [[ZendriveDriverAttributes alloc] init];

    configuration.applicationKey = key;
    configuration.driverId = driverId;

    [driverAttributes setFirstName:firstName];
    [driverAttributes setLastName:lastName];
    [driverAttributes setEmail:email];

    configuration.driverAttributes = driverAttributes;
    configuration.driveDetectionMode = ZendriveDriveDetectionModeAutoON;

    [Zendrive setupWithConfiguration:configuration
                            delegate:zdDelegate // Can be nil
                   completionHandler:^(BOOL success, NSError *error) {
                       if(success) {
                          RCTLogInfo(@"Init success");
                           callback(@[[NSNull null]]);
                       } else {
                           RCTLogInfo(@"Init error: %@", [error localizedDescription]);
                           RCTLogInfo(@"Init error: %@", [error localizedFailureReason]);
                           RCTLogInfo(@"Init error: %@", [error localizedRecoveryOptions]);
                           RCTLogInfo(@"Init error: %@", [error localizedRecoverySuggestion]);
                           callback(@[[error description]]);
                       }
                   }];
}

RCT_EXPORT_METHOD(isSetup:(RCTResponseSenderBlock)callback)
{
    callback(@[[NSNull null], @([Zendrive isSDKSetup])]);
}


RCT_EXPORT_METHOD(getState:(RCTResponseSenderBlock)callback){
    ZendriveActiveDriveInfo *driveInfo = [Zendrive activeDriveInfo];
    NSObject *response = @{@"isDriveInProgress": @(driveInfo != Nil)};
    callback(@[[NSNull null], response]);
}

RCT_EXPORT_METHOD(startDrive:(NSString *)id callback:(RCTResponseSenderBlock)callback){
    if(![Zendrive isValidInputParameter:id]) {
        callback(@[@"tracking id is not valid"]);
    } else {
        [Zendrive startDrive:id];
        callback(@[[NSNull null]]);
    }
}

RCT_EXPORT_METHOD(stopDrive:(NSString *)id callback:(RCTResponseSenderBlock)callback){
    if(![Zendrive isValidInputParameter:id]) {
        callback(@[@"tracking id is not valid"]);
    } else {
        [Zendrive stopDrive:id];
        callback(@[[NSNull null]]);
    }
}

RCT_EXPORT_METHOD(triggerAccident:(RCTResponseSenderBlock)callback){
    if(![Zendrive isAccidentDetectionSupportedByDevice]) {
        callback(@[@"Zendrive collision detection is not supported on this device"]);
    } else {
        [ZendriveTest raiseMockAccident:ZendriveAccidentConfidenceHigh];
        callback(@[[NSNull null]]);
    }
}

RCT_EXPORT_METHOD(setAutoDriveDetectionMode:(BOOL)enabled callback:(RCTResponseSenderBlock)callback){
    ZendriveDriveDetectionMode driveDetectionMode;
    if(enabled) {
        driveDetectionMode = ZendriveDriveDetectionModeAutoON;
    } else {
        driveDetectionMode = ZendriveDriveDetectionModeAutoOFF;
    }
    [Zendrive setDriveDetectionMode:driveDetectionMode];
    callback(@[[NSNull null]]);
}


RCT_EXPORT_METHOD(shutdown:(RCTResponseSenderBlock)callback){
    [Zendrive teardownWithCompletionHandler:^() {
        callback(@[[NSNull null]]);
    }];
}

RCT_EXPORT_METHOD(startSession:(NSString *)id){
    [Zendrive startSession:id];
}


RCT_EXPORT_METHOD(stopSession){
    [Zendrive stopSession];
}
@end
