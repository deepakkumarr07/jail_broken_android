import 'package:flutter/material.dart';
// ignore: depend_on_referenced_packages
import 'package:root_detector/root_detector.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  MyAppState createState() => MyAppState();
}

class MyAppState extends State<MyApp> {
  List<String> features = [];

  @override
  void initState() {
    super.initState();
    getVal();
  }

  getVal() async {
    features = [];
    features.add('isEmulator : ${await RootDetector.checkisemulator}');
    features.add('isRooted : ${await RootDetector.isRooted()}');
    features.add('patch app list : ${await RootDetector.getPiratedApps}');
    features.add('isEmulator : ${await RootDetector.checkisemulator}');
    features.add('installed From : ${await RootDetector.privacyChecker}');
    features.add('App Signature : ${await RootDetector.signkey}');
    features
        .add('is Debugger attached : ${await RootDetector.isdebuggerRunning}');
    print('FEATURES: ${features}');
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('App Security Check'),
        ),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            for (var item in features)
              Container(
                margin: const EdgeInsets.symmetric(vertical: 5),
                padding: const EdgeInsets.symmetric(vertical: 7),
                color: Colors.grey[200],
                child: Row(
                  children: [
                    Expanded(
                      flex: 40,
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.end,
                        children: [
                          Text(
                            '${item.split(':')[0]} :',
                          ),
                        ],
                      ),
                    ),
                    Expanded(
                      flex: 60,
                      child: Text(
                        item.split(':')[1],
                      ),
                    ),
                  ],
                ),
              ),
          ],
        ),
      ),
    );
  }
}
