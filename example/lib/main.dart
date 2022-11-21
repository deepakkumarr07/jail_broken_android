import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:root_detector/root_detector.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _isRooted = 'Unknown';

  @override
  void initState() {
    a();
    super.initState();
    initPlatformState();
  }

  a() async {
    print('checkisemulator');
    print(await RootDetector.checkisemulator);
    print('checkluckypatcherAvailable');
    var res = await RootDetector.getPiratedApps;
    print(res);
    print('isRooted');
    var res1 = await RootDetector.isRooted();
    print(res1);
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      await RootDetector.isRooted(
        ignoreSimulator: false,
      ).then((value) {
        setState(() {
          _isRooted = value.toString();
        });
      });
    } on PlatformException {
      setState(() {
        _isRooted = 'Failed to get root status.';
      });
    }
  }

  @override
  void setState(VoidCallback fn) {
    if (mounted) {
      super.setState(fn);
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Root Detector'),
        ),
        body: Center(
          child: Text('Device is Rooted: $_isRooted\n'),
        ),
      ),
    );
  }
}
