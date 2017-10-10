//
//  ZendriveDelegateManager.m
//  ZendriveWrapper
//
//  Created by Mauricio Banduk on 04/10/17.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "ZendriveDelegateManager.h"

@implementation ZendriveDelegateManager

- (void)processStartOfDrive:(ZendriveDriveStartInfo *)startInfo {
    ZendriveWrapper *zendriveWrapper = [ZendriveWrapper allocWithZone: nil];
    RCTLogInfo(@"Start of Drive invoked");

    NSDictionary *body = [NSDictionary dictionaryWithObjectsAndKeys:
      [startInfo sessionId], @"sessionId",
      [startInfo trackingId], @"trackingId",
      [NSNumber numberWithLongLong:[startInfo startTimestamp]], @"starTimeMillis",
      nil
    ];

    [zendriveWrapper sendEventWithName:@"driveStart" body:body];
}

- (void)processResumeOfDrive:(ZendriveDriveResumeInfo *)driveResumeInfo {
    ZendriveWrapper *zendriveWrapper = [ZendriveWrapper allocWithZone: nil];
    RCTLogInfo(@"Resume of Drive invoked");

    NSDictionary *body = [NSDictionary dictionaryWithObjectsAndKeys:
      [driveResumeInfo sessionId], @"sessionId",
      [driveResumeInfo trackingId], @"trackingId",
      [NSNumber numberWithLongLong:[driveResumeInfo driveGapStartTimestampMillis]], @"driveGapStartTimestampMillis",
      [NSNumber numberWithLongLong:[driveResumeInfo driveGapEndTimestampMillis]], @"driveGapEndTimestampMillis",
      nil
    ];
    [zendriveWrapper sendEventWithName:@"driveResume" body:body];
}

- (void)processEndOfDrive:(ZendriveEstimatedDriveInfo *)driveEndInfo {
    ZendriveWrapper *zendriveWrapper = [ZendriveWrapper allocWithZone: nil];
    RCTLogInfo(@"End of Drive invoked");

    NSDictionary *body = [NSDictionary dictionaryWithObjectsAndKeys:
      [driveEndInfo sessionId], @"sessionId",
      [driveEndInfo trackingId], @"trackingId",
      [driveEndInfo driveId], @"driveId",
      [NSNumber numberWithInt: [driveEndInfo driveType]], @"driveType",
      [NSNumber numberWithInt:[[driveEndInfo score] zendriveScore]], @"zendriveScore",
      [NSNumber numberWithLongLong:[driveEndInfo startTimestamp]], @"starTimeMillis",
      [NSNumber numberWithLongLong:[driveEndInfo endTimestamp]], @"endTimeMillis",
      [NSNumber numberWithDouble:[driveEndInfo averageSpeed]], @"averageSpeed",
      [NSNumber numberWithDouble:[driveEndInfo distance]], @"distanceMeters",
      [NSNumber numberWithDouble:[driveEndInfo maxSpeed]], @"maxSpeed",
      nil
    ];
    [zendriveWrapper sendEventWithName:@"driveEnd" body:body];
}

- (void)processAnalysisOfDrive:(ZendriveAnalyzedDriveInfo *)analyzedDriverInfo {
    ZendriveWrapper *zendriveWrapper = [ZendriveWrapper allocWithZone: nil];
    RCTLogInfo(@"Analysis of Drive invoked");

    NSDictionary *body = [NSDictionary dictionaryWithObjectsAndKeys:
      [analyzedDriverInfo sessionId], @"sessionId",
      [analyzedDriverInfo trackingId], @"trackingId",
      [analyzedDriverInfo driveId], @"driveId",
      [NSNumber numberWithInt: [analyzedDriverInfo driveType]], @"driveType",
      [NSNumber numberWithInt:[[analyzedDriverInfo score] zendriveScore]], @"zendriveScore",
      [NSNumber numberWithLongLong:[analyzedDriverInfo startTimestamp]], @"starTimeMillis",
      [NSNumber numberWithLongLong:[analyzedDriverInfo endTimestamp]], @"endTimeMillis",
      [NSNumber numberWithDouble:[analyzedDriverInfo averageSpeed]], @"averageSpeed",
      [NSNumber numberWithDouble:[analyzedDriverInfo distance]], @"distanceMeters",
      [NSNumber numberWithDouble:[analyzedDriverInfo maxSpeed]], @"maxSpeed",
      nil
    ];
    [zendriveWrapper sendEventWithName:@"driveAnalyzed" body:body];
}

- (void)processLocationDenied {
    ZendriveWrapper *zendriveWrapper = [ZendriveWrapper allocWithZone: nil];
    RCTLogInfo(@"User denied Location to Zendrive SDK.");
    [zendriveWrapper sendEventWithName:@"locationDenied" body:@{}];
}

- (void)processLocationApproved   {
    ZendriveWrapper *zendriveWrapper = [ZendriveWrapper allocWithZone: nil];
    RCTLogInfo(@"User approved Location to Zendrive SDK.");
    [zendriveWrapper sendEventWithName:@"locationApproved" body:@{}];
}

- (void)processAccidentDetected:(ZendriveAccidentInfo *)accidentInfo {
    ZendriveWrapper *zendriveWrapper = [ZendriveWrapper allocWithZone: nil];
    RCTLogInfo(@"Accident detected by Zendrive SDK.");

    NSDictionary *body = [NSDictionary dictionaryWithObjectsAndKeys:
      [accidentInfo sessionId], @"sessionId",
      [accidentInfo trackingId], @"trackingId",
      [accidentInfo driveId], @"driveId",
      [accidentInfo accidentId], @"accidentId",
      [NSNumber numberWithDouble:[accidentInfo timestamp]], @"timestampMillis",
      [NSNumber numberWithInt:[accidentInfo confidence]], @"confidence",
      [NSNumber numberWithDouble:[[accidentInfo accidentLocation] latitude]], @"locationLat",
      [NSNumber numberWithDouble:[[accidentInfo accidentLocation] longitude]], @"locationLon",
      nil
    ];
    [zendriveWrapper sendEventWithName:@"accident" body:body];
}

@end
