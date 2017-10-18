
#import "ZendriveDelegateManager.h"

#if __has_include("RCTBridgeModule.h")
  #import "RCTBridgeModule.h"
#else
  #import <React/RCTBridgeModule.h>
#endif

#if __has_include("RCTEventEmitter.h")
  #import "RCTEventEmitter.h"
#else
  #import <React/RCTEventEmitter.h>
#endif

#if __has_include("RCTLog.h")
  #import "RCTLog.h"
#else
  #import <React/RCTLog.h>
#endif

#if __has_include("Zendrive.h")
  #import "Zendrive.h"
#else
  #import <ZendriveSDK/Zendrive.h>
#endif

#if __has_include("ZendriveTest.h")
  #import "ZendriveTest.h"
#else
  #import <ZendriveSDK/ZendriveTest.h>
#endif

@interface ZendriveWrapper : RCTEventEmitter <RCTBridgeModule>

@property(strong) id<ZendriveDelegateProtocol> zendriveDelegate;

@end
