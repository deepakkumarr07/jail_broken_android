package space.wisnuwiry.root_detector

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Debug
import android.util.Base64
import android.util.Log
import androidx.annotation.NonNull
import com.andreacioccarelli.billingprotector.BillingProtector
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.security.MessageDigest


class RootDetectorPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    private lateinit var channel: MethodChannel
    private lateinit var context: Context
    private lateinit var bp: BillingProtector

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel =
            MethodChannel(flutterPluginBinding.binaryMessenger, "rd")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
        bp = BillingProtector(context)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        val igs = call.arguments == true
        when (call.method) {
            "cr" -> {
                cR(result, igs)
            }
            "ce" -> {
                try {
                    result.success(iE())
                }catch (exception: Exception) {
                    result.success(false)
                    Log.d("CE ERROR :", exception.stackTrace.toString())
                }
            }
            "pc" -> {
                pac(result)
            }
            "awif" -> {
                awif(result)
            }
            "ac" -> {
                ac(result)
            }
             "sk" -> {
                sk(result)
            }
            else -> result.notImplemented()
        }
    }

    private fun cR(@NonNull result: Result, igs: Boolean) {
        try {
            if(igs){
                result.success(false)
            }else{
                result.success(bp.isRootInstalled())
            }
        } catch (e: Exception) {
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

    private fun iE(): Boolean {
        return (Build.BRAND.startsWith("generic")
                && Build.DEVICE.startsWith("generic")
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
                || Build.PRODUCT.contains("simulator")
                )
    }

    private fun pac(@NonNull result: Result) {

        val aL:MutableList<String> = ArrayList()
        Thread(Runnable {
            try {
                for (app in bp.getPirateAppsList()) {
                    aL.add(app.name)
                }
                result.success(aL)
            } catch (exception: Exception) {
                Log.d("PAC ERROR :", exception.stackTrace.toString())
                result.success(aL)
            }
        }).start()
    }

    private fun awif(@NonNull result: Result) {
        try {
            val ins = context.packageManager.getInstallerPackageName(context.packageName)
            result.success(ins)
        } catch (exception: Exception) {
            Log.d("PAC ERROR :", exception.stackTrace.toString())
            result.success(null)
        }
    }


    private fun ac(@NonNull result: Result) {
        var res =false
        guardDebugger({
            res=false
        }) {
            res = true
        }
        result.success(res)
    }


    private fun guardDebugger(error: (() -> Unit) = {}, function: (() -> Unit)) {
        val isDebuggerAttached = Debug.isDebuggerConnected() || Debug.waitingForDebugger()
        if (!isDebuggerAttached) {
            function.invoke()
        } else {
            error.invoke()
        }
    }

    @SuppressLint("PackageManagerGetSignatures")
    @Suppress("DEPRECATION")
    private fun sk(@NonNull result: Result){
        try {
            val packageInfo: PackageInfo = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
            val res:MutableList<String> = ArrayList()
            if (packageInfo.signatures.isEmpty()) {
                result.success(res)
            }else{
                for (signature in packageInfo.signatures) {
                    val md: MessageDigest = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    res.add(Base64.encodeToString(md.digest(), Base64.DEFAULT).toString().trim())
                }
                result.success(res)
            }
        } catch (exception: Exception) {
            result.success("")
            Log.d("SD DEC ERROR :", exception.stackTrace.toString())
        }
    }
}
