//
//  ZendriveDelegateManager.h
//  ZendriveWrapper
//
//  Created by Mauricio Banduk on 04/10/17.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import <ZendriveSDK/Zendrive.h>
#import <React/RCTEventEmitter.h>

#ifndef ZendriveDelegateManager_h
#define ZendriveDelegateManager_h

@interface ZendriveDelegateManager : RCTEventEmitter <ZendriveDelegateProtocol, RCTBridgeModule>

@end

#endif /* ZendriveDelegateManager_h */
