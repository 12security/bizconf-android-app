package com.wrapper.proxyapplication;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import com.tencent.bugly.yaq.crashreport.CrashReport;

public abstract class WrapperProxyApplication extends Application {
    static Context baseContext;
    static String className = "cn.bizconf.vcpro.common.app.BizConfApplication";
    static Application shellApp = null;
    static String tinkerApp = "tinker not support";

    /* access modifiers changed from: 0000 */
    public native void Ooo0ooO0oO();

    /* access modifiers changed from: protected */
    public abstract void initProxyApplication(Context context);

    static Context getWrapperProxyAppBaseContext() {
        return baseContext;
    }

    private synchronized boolean Fixappname() {
        if (className.startsWith(".")) {
            className = super.getPackageName() + className;
        } else if (className.indexOf(".") < 0) {
            className = super.getPackageName() + "." + className;
        }
        return true;
    }

    public static void fixAndroid(Context context, Application application) {
        if (VERSION.SDK_INT == 28) {
            try {
                AndroidNClassLoader.inject(context.getClassLoader(), application);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private static String getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "0";
        }
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        baseContext = getBaseContext();
        if (shellApp == null) {
            shellApp = this;
        }
        String appVersion = "4.1.0.12";
        CrashReport.setAppVersion(context, appVersion);
        CrashReport.setSdkExtraData(this, "900053609", appVersion);
        CrashReport.initCrashReport(this, "900053609", false);
        Fixappname();
        initProxyApplication(context);
    }

    public void onCreate() {
        super.onCreate();
        Ooo0ooO0oO();
    }
}
