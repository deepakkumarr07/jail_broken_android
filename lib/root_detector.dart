import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class RootDetector {
  static const MethodChannel _channel = const MethodChannel("my_root_detector");

  static Future<bool> isRooted({bool ignoreSimulator = false}) async {
    return await _channel.invokeMethod('checkIsRooted', ignoreSimulator);
  }

  static Future<bool> get checkisemulator async {
    return await _channel.invokeMethod('check_emulator');
  }

  static Future<dynamic> get getPiratedApps async {
    return await _channel.invokeMethod('piratedcheck');
  }
}
