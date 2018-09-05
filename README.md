
yarn add

react-native link react-native-zendrive

#  How to use:

## Android

import { DeviceEventEmitter } from 'react-native';

DeviceEventEmitter.addListener('accident', console.log);

DeviceEventEmitter.addListener('driveStart', console.log);

DeviceEventEmitter.addListener('driveResume', console.log);

DeviceEventEmitter.addListener('driveEnd', console.log);

DeviceEventEmitter.addListener('driveAnalyzed', console.log);

DeviceEventEmitter.addListener('locationSettingsChange', console.log),

DeviceEventEmitter.addListener('locationPermissionsChange', console.log),


// Than I used promisified functions:

import ZendriveWrapper from 'react-native-zendrive';

import Promise from 'bluebird';

const zd = Promise.promisifyAll(ZendriveWrapper);

const init = async (sdkKey, id, firstName, lastName, email) => {

try {

await zd.initAsync(sdkKey, id, firstName, lastName, email);

  } catch (e) {

console.warn(e);

throw e;

  }

};

const isSetup = async () => zd.isSetupAsync();

const getState = async () => zd.getStateAsync();

const startDrive = async tripId => zd.startDriveAsync(tripId);

const stopDrive = async tripId => zd.stopDriveAsync(tripId);

const triggerAccident = async () => zd.triggerAccidentAsync();

const setAutoDriveDetectionMode = async autoDriveDetectionMode => {
  await zd.setAutoDriveDetectionModeAsync(autoDriveDetectionMode);
};

## IOS
import ZendriveWrapper from 'react-native-zendrive';

import { NativeEventEmitter } from 'react-native';

const zdEmitter = new NativeEventEmitter(ZendriveWrapper);

zdEmitter.addListener('accident', console.log);

zdEmitter.addListener('driveStart', console.log);

zdEmitter.addListener('driveResume', console.log);

zdEmitter.addListener('driveEnd', console.log);

zdEmitter.addListener('driveAnalyzed', console.log);

zdEmitter.addListener('locationApproved', console.log);

zdEmitter.addListener('locationDenied', console.log);




