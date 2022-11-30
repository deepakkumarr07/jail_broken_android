import 'dart:async';
import 'package:flutter/services.dart';

class RootDetector {
  static const MethodChannel _channel = const MethodChannel("rd");

  static Future<bool> isRooted({bool ignoreSimulator = false}) async {
    return await _channel.invokeMethod('cr', ignoreSimulator);
  }

  static Future<bool> get checkisemulator async {
    return await _channel.invokeMethod('ce');
  }

  static Future<dynamic> get getPiratedApps async {
    return await _channel.invokeMethod('pc');
  }

  static Future<dynamic> get privacyChecker async {
    return await _channel.invokeMethod('awif');
  }

  static Future<dynamic> get isdebuggerRunning async {
    return await _channel.invokeMethod('ac');
  }

  static Future<dynamic> get signkey async {
    return await _channel.invokeMethod('sk');
  }
}
