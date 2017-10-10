
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#import "RCTEventEmitter.h"
#import "RCTLog.h"
#else
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import <React/RCTLog.h>
#endif

#import <ZendriveSDK/Zendrive.h>
#import <ZendriveSDK/ZendriveTest.h>
#import "ZendriveDelegateManager.h"

@interface ZendriveWrapper : RCTEventEmitter <RCTBridgeModule>

@property(strong) id<ZendriveDelegateProtocol> zendriveDelegate;

@end
