package space.wisnuwiry.root_detector

import android.content.Context
import android.os.Build
import androidx.annotation.NonNull
import com.andreacioccarelli.billingprotector.BillingProtector
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result


class RootDetectorPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    private lateinit var channel: MethodChannel
    private lateinit var context: Context
    private lateinit var bp: BillingProtector

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel =
            MethodChannel(flutterPluginBinding.binaryMessenger, "my_root_detector")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
        bp = BillingProtector(context)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        val ignoreSimulator = call.arguments == true
        when (call.method) {
            "checkIsRooted" -> {
                checkIsRooted(result, ignoreSimulator)
            }
            "check_emulator" -> {
                result.success(isEmulator())
            }
            "piratedcheck" -> {
                piratedappcheck(result)
            }
            "privacychecker" -> {
             privacychecker(result)
            }
            else -> result.notImplemented()
        }
    }

    private fun checkIsRooted(@NonNull result: Result, ignoreSimulator: Boolean) {
        try {
            if(ignoreSimulator){
                result.success(false)
            }else{
                result.success(bp.isRootInstalled())
            }
        } catch (e: IllegalArgumentException) {
           result.success(false)
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {}

    override fun onDetachedFromActivityForConfigChanges() {}

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {}

    override fun onDetachedFromActivity() {}

    private fun isEmulator(): Boolean {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator"))
    }

    private fun piratedappcheck(@NonNull result: Result) {
        val appList:MutableList<String> = ArrayList()
        for (app in bp.getPirateAppsList()) {
            appList.add(app.name)
        }
        result.success(appList)
    }

    private fun privacychecker(@NonNull result: Result) {
        val installer = context.packageManager.getInstallerPackageName(context.packageName)
        result.success("$installer")
    }

//    private fun checkLuckyPatcher(packagelist :Any?): Boolean {
//        Log.d("packagelist","$packagelist")
//        val pm = context.packageManager
//        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
//        if(packagelist!=null){
//            for (item in packagelist as List<String> ) {
//                Log.d("item","$item")
//                if(item.contains(".")){
//                   val res= isPackageInstalled(item,pm)
//                    if(res){
//                        return true;
//                    }
//                }else{
//                    for (packageInfo in packages) {
//                        var name= packageInfo.loadLabel(pm).toString();
//                        var check= name.toLowerCase().trim()
//                        check= check.replace("-","");
//                        check= check.replace(" ","");
//                        check= check.replace("_","");
//                        if(check.contains(item,true)){
//                            return true
//                        }
//                    }
//                }
//            }
//        }else {
//            for (packageInfo in packages) {
//                var name = packageInfo.loadLabel(pm).toString();
//                var check = name.toLowerCase().trim()
//                check = check.replace("-", "");
//                check = check.replace(" ", "");
//                check = check.replace("_", "");
//                if (check.contains("luсkypаtch")) {
//                    return true
//                }
//            }
//            val v1 = isPackageInstalled("com.dimonvideo.luckypatcher", pm)
//            val v2 = isPackageInstalled("com.chelpus.lackypatch", pm)
//            val v3 = isPackageInstalled("com.android.vending.billing.InAppBillingService.LACK", pm)
//            val v4 = isPackageInstalled("com.android.vending.billing.InAppBillingService.CLON", pm)
//            val v5 = isPackageInstalled("com.android.vending.billing.InAppBillingService.LACK", pm)
//            if (v1 || v2 || v3 || v4 || v5) {
//                return true
//            }
//        }
//        return false
//    }

//    private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
//        print(packageName)
//        return try {
//            packageManager.getPackageInfo(packageName, 0)
//            true
//        } catch (e: PackageManager.NameNotFoundException) {
//            false
//        }
//    }
}
