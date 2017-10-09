//
//  ZendriveDelegateManager.m
//  ZendriveWrapper
//
//  Created by Mauricio Banduk on 04/10/17.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "ZendriveDelegateManager.h"
#import <Foundation/Foundation.h>

#import <ZendriveSDK/ZendriveDriveStartInfo.h>
#import <ZendriveSDK/ZendriveDriveResumeInfo.h>
#import <ZendriveSDK/ZendriveDriveInfo.h>

@implementation ZendriveDelegateManager

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"accident", @"driveStart", @"driveResume", @"driveEnd", @"locationPermissionsApproved", @"locationPermissionsDenied", @"driveAnalyzed"];
}



- (void)processStartOfDrive:(ZendriveDriveStartInfo *)startInfo {
    NSLog(@"Start of Drive invoked");
    [self sendEventWithName:@"driveStart" body:@{
                                                 @"sessionId": [startInfo sessionId],
                                                 @"trackingId": [startInfo trackingId],
                                                 @"starTimeMillis": [NSNumber numberWithLongLong:[startInfo startTimestamp]],
                                                }
     ];
}

- (void)processResumeOfDrive:(ZendriveDriveResumeInfo *)driveResumeInfo {
    NSLog(@"Resume of Drive invoked");
    [self sendEventWithName:@"driveResume" body:@{
                                                 @"sessionId": [driveResumeInfo sessionId],
                                                 @"trackingId": [driveResumeInfo trackingId],
                                                 @"driveGapStartTimestampMillis": [NSNumber numberWithLongLong:[driveResumeInfo driveGapStartTimestampMillis]],
                                                 @"driveGapEndTimestampMillis": [NSNumber numberWithLongLong:[driveResumeInfo driveGapEndTimestampMillis]],
                                                 }
     ];
}

- (void)processEndOfDrive:(ZendriveEstimatedDriveInfo *)driveEndInfo {
    NSLog(@"End of Drive invoked");
    
    [self sendEventWithName:@"driveEnd" body:@{
                                               @"sessionId": [driveEndInfo sessionId],
                                               @"trackingId": [driveEndInfo trackingId],
                                               @"driveId": [driveEndInfo driveId],
                                               @"driveType": [NSNumber numberWithInt: [driveEndInfo driveType]],
                                               @"zendriveScore": [NSNumber numberWithInt:[[driveEndInfo score] zendriveScore]],
                                               @"starTimeMillis": [NSNumber numberWithLongLong:[driveEndInfo startTimestamp]],
                                               @"endTimeMillis": [NSNumber numberWithLongLong:[driveEndInfo endTimestamp]],
                                               @"averageSpeed": [NSNumber numberWithDouble:[driveEndInfo averageSpeed]],
                                               @"distanceMeters": [NSNumber numberWithDouble:[driveEndInfo distance]],
                                               @"maxSpeed": [NSNumber numberWithDouble:[driveEndInfo maxSpeed]],
                                              }
     ];
}

- (void)processAnalysisOfDrive:(ZendriveAnalyzedDriveInfo *)analyzedDriverInfo {
    NSLog(@"Analysis of Drive invoked");
    
    [self sendEventWithName:@"driveAnalyzed" body:@{
                                                            @"sessionId": [analyzedDriverInfo sessionId],
                                                            @"trackingId": [analyzedDriverInfo trackingId],
                                                            @"driveId": [analyzedDriverInfo driveId],
                                                            @"driveType": [NSNumber numberWithInt: [analyzedDriverInfo driveType]],
                                                            @"zendriveScore": [NSNumber numberWithInt:[[analyzedDriverInfo score] zendriveScore]],
                                                            @"starTimeMillis": [NSNumber numberWithLongLong:[analyzedDriverInfo startTimestamp]],
                                                            @"endTimeMillis": [NSNumber numberWithLongLong:[analyzedDriverInfo endTimestamp]],
                                                            @"averageSpeed": [NSNumber numberWithDouble:[analyzedDriverInfo averageSpeed]],
                                                            @"distanceMeters": [NSNumber numberWithDouble:[analyzedDriverInfo distance]],
                                                            @"maxSpeed": [NSNumber numberWithDouble:[analyzedDriverInfo maxSpeed]],
                                                           }
     ];
}

- (void)processLocationDenied {
    NSLog(@"User denied Location to Zendrive SDK.");
    [self sendEventWithName:@"processLocationDenied" body:@{}];
}

- (void)processLocationApproved   {
    NSLog(@"User approved Location to Zendrive SDK.");
    [self sendEventWithName:@"processLocationApproved" body:@{}];
}

- (void)processAccidentDetected:(ZendriveAccidentInfo *)accidentInfo {
    NSLog(@"Accident detected by Zendrive SDK.");
    
    [self sendEventWithName:@"accident" body:@{
                                               @"sessionId": [accidentInfo sessionId],
                                               @"trackingId": [accidentInfo trackingId],
                                               @"driveId": [accidentInfo driveId],
                                               @"accidentId": [accidentInfo accidentId],
                                               @"timestamp  Millis": [NSNumber numberWithDouble:[accidentInfo timestamp]],
                                               @"confidence": [NSNumber numberWithInt:[accidentInfo confidence]],
                                               @"locationLat": [NSNumber numberWithDouble:[[accidentInfo accidentLocation] latitude]],
                                               @"locationLon": [NSNumber numberWithDouble:[[accidentInfo accidentLocation] longitude]],
                                               }
     ];
}


@end
















