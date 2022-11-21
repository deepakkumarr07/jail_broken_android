# Root Detector

A library to check devices using root access or simply root/jailbreak checking.

These are the current checks/tricks we are using to give an indication of root.

And there are some parameters when checking root:

| Params      | Description |
| ----------- | ----------- |
| ignoreSimulator   | By default `ignoreSimulator` is **false** , And when the value is true then it checks root with busyBox which is usually used on some android devices |


## Setup

- Android

No config needed on Android

- iOS

Add this code in the Info.plist file whose position is in the /ios/Runner/ folder

```xml
<key>LSApplicationQueriesSchemes</key>
	<array>
	<string>cydia</string>
</array>
```

### checkIsRoot

```dart
try {
 final result = await RootDetector.isRooted(
   ignoreSimulator: true, // by default is false
 ); // type data is bool
 return result;
} on PlatformException catch(e){
  // TODO: handling your error, whenever have error from native code
}
```

More example in [github](https://github.com/wisnuwiry/root_detector/tree/main/example).

## Credit

- [RootBeer](https://github.com/scottyab/rootbeer)
