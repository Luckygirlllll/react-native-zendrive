//
//  ZendriveDelegateManager.h
//  ZendriveWrapper
//
//  Created by Mauricio Banduk on 04/10/17.
//  Copyright © 2017 Facebook. All rights reserved.
//
#import <Foundation/Foundation.h>
#import <React/RCTLog.h>

#import <ZendriveSDK/Zendrive.h>
#import <ZendriveSDK/ZendriveDriveStartInfo.h>
#import <ZendriveSDK/ZendriveDriveResumeInfo.h>
#import <ZendriveSDK/ZendriveDriveInfo.h>

#import "ZendriveWrapper.h"

#ifndef ZendriveDelegateManager_h
#define ZendriveDelegateManager_h

@interface ZendriveDelegateManager : NSObject <ZendriveDelegateProtocol>

@end

#endif /* ZendriveDelegateManager_h */
