//
//  ZendriveDelegateManager.h
//  ZendriveWrapper
//
//  Created by Mauricio Banduk on 04/10/17.
//  Copyright © 2017 Facebook. All rights reserved.
//
#import <Foundation/Foundation.h>

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
#if __has_include("ZendriveDriveStartInfo.h")
  #import "ZendriveDriveStartInfo.h"
#else
  #import <ZendriveSDK/ZendriveDriveStartInfo.h>
#endif
#if __has_include("ZendriveDriveResumeInfo.h")
  #import "ZendriveDriveResumeInfo.h"
#else
  #import <ZendriveSDK/ZendriveDriveResumeInfo.h>
#endif
#if __has_include("ZendriveDriveInfo.h")
  #import "ZendriveDriveInfo.h"
#else
  #import <ZendriveSDK/ZendriveDriveInfo.h>
#endif

#import "ZendriveWrapper.h"

#ifndef ZendriveDelegateManager_h
#define ZendriveDelegateManager_h

@interface ZendriveDelegateManager : NSObject <ZendriveDelegateProtocol>

@end

#endif /* ZendriveDelegateManager_h */
