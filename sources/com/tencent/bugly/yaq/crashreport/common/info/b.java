package com.tencent.bugly.yaq.crashreport.common.info;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import com.tencent.bugly.yaq.BuglyStrategy.a;
import com.tencent.bugly.yaq.proguard.x;
import com.tencent.bugly.yaq.proguard.z;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: BUGLY */
public class b {
    private static final String[] a = {"/su", "/su/bin/su", "/sbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/data/local/su", "/system/xbin/su", "/system/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/system/bin/cufsdosck", "/system/xbin/cufsdosck", "/system/bin/cufsmgr", "/system/xbin/cufsmgr", "/system/bin/cufaevdd", "/system/xbin/cufaevdd", "/system/bin/conbb", "/system/xbin/conbb"};
    private static final String[] b = {"com.ami.duosupdater.ui", "com.ami.launchmetro", "com.ami.syncduosservices", "com.bluestacks.home", "com.bluestacks.windowsfilemanager", "com.bluestacks.settings", "com.bluestacks.bluestackslocationprovider", "com.bluestacks.appsettings", "com.bluestacks.bstfolder", "com.bluestacks.BstCommandProcessor", "com.bluestacks.s2p", "com.bluestacks.setup", "com.kaopu001.tiantianserver", "com.kpzs.helpercenter", "com.kaopu001.tiantianime", "com.android.development_settings", "com.android.development", "com.android.customlocale2", "com.genymotion.superuser", "com.genymotion.clipboardproxy", "com.uc.xxzs.keyboard", "com.uc.xxzs", "com.blue.huang17.agent", "com.blue.huang17.launcher", "com.blue.huang17.ime", "com.microvirt.guide", "com.microvirt.market", "com.microvirt.memuime", "cn.itools.vm.launcher", "cn.itools.vm.proxy", "cn.itools.vm.softkeyboard", "cn.itools.avdmarket", "com.syd.IME", "com.bignox.app.store.hd", "com.bignox.launcher", "com.bignox.app.phone", "com.bignox.app.noxservice", "com.android.noxpush", "com.haimawan.push", "me.haima.helpcenter", "com.windroy.launcher", "com.windroy.superuser", "com.windroy.launcher", "com.windroy.ime", "com.android.flysilkworm", "com.android.emu.inputservice", "com.tiantian.ime", "com.microvirt.launcher", "me.le8.androidassist", "com.vphone.helper", "com.vphone.launcher", "com.duoyi.giftcenter.giftcenter"};
    private static final String[] c = {"/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace", "/system/bin/qemu-props", "/dev/socket/qemud", "/dev/qemu_pipe", "/dev/socket/baseband_genyd", "/dev/socket/genyd"};
    private static String d = null;
    private static String e = null;

    public static String a() {
        try {
            return Build.MODEL;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return "fail";
        }
    }

    public static String b() {
        try {
            return VERSION.RELEASE;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return "fail";
        }
    }

    public static int c() {
        try {
            return VERSION.SDK_INT;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return -1;
        }
    }

    public static String d() {
        return "null";
    }

    public static String e() {
        return "null";
    }

    public static String a(Context context) {
        String str = "fail";
        if (context == null) {
            return str;
        }
        try {
            String string = Secure.getString(context.getContentResolver(), "android_id");
            if (string == null) {
                return "null";
            }
            return string.toLowerCase();
        } catch (Throwable th) {
            if (x.a(th)) {
                return str;
            }
            x.a("Failed to get Android ID.", new Object[0]);
            return str;
        }
    }

    public static String f() {
        return "null";
    }

    public static String b(Context context) {
        String str;
        String str2 = "fail";
        if (context == null) {
            return str2;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                str = telephonyManager.getSimSerialNumber();
                if (str == null) {
                    str = "null";
                }
            } else {
                str = str2;
            }
        } catch (Throwable th) {
            str = str2;
            x.a("Failed to get SIM serial number.", new Object[0]);
        }
        return str;
    }

    public static String g() {
        try {
            return Build.SERIAL;
        } catch (Throwable th) {
            x.a("Failed to get hardware serial number.", new Object[0]);
            return "fail";
        }
    }

    private static boolean t() {
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                return true;
            }
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
        return false;
    }

    public static String a(Context context, boolean z) {
        String str = null;
        if (z) {
            try {
                String a2 = z.a(context, "ro.product.cpu.abilist");
                if (z.a(a2) || a2.equals("fail")) {
                    a2 = z.a(context, "ro.product.cpu.abi");
                }
                if (!z.a(a2) && !a2.equals("fail")) {
                    x.b(b.class, "ABI list: " + a2, new Object[0]);
                    str = a2.split(",")[0];
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
                return "fail";
            }
        }
        if (str == null) {
            str = System.getProperty("os.arch");
        }
        return str;
    }

    public static long h() {
        long j = -1;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (x.a(th)) {
                return j;
            }
            th.printStackTrace();
            return j;
        }
    }

    public static long i() {
        long j = -1;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (x.a(th)) {
                return j;
            }
            th.printStackTrace();
            return j;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x007b A[Catch:{ all -> 0x00ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0080 A[SYNTHETIC, Splitter:B:44:0x0080] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0085 A[SYNTHETIC, Splitter:B:47:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00a6 A[SYNTHETIC, Splitter:B:61:0x00a6] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00ab A[SYNTHETIC, Splitter:B:64:0x00ab] */
    public static long j() {
        FileReader fileReader;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        try {
            fileReader = new FileReader("/proc/meminfo");
            try {
                bufferedReader = new BufferedReader(fileReader, 2048);
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e2) {
                            if (!x.a(e2)) {
                                e2.printStackTrace();
                            }
                        }
                        try {
                            fileReader.close();
                        } catch (IOException e3) {
                            if (!x.a(e3)) {
                                e3.printStackTrace();
                            }
                        }
                        return -1;
                    }
                    long parseLong = Long.parseLong(readLine.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim()) << 10;
                    try {
                        bufferedReader.close();
                    } catch (IOException e4) {
                        if (!x.a(e4)) {
                            e4.printStackTrace();
                        }
                    }
                    try {
                        fileReader.close();
                        return parseLong;
                    } catch (IOException e5) {
                        if (x.a(e5)) {
                            return parseLong;
                        }
                        e5.printStackTrace();
                        return parseLong;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader != null) {
                    }
                    if (fileReader != null) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
                if (bufferedReader != null) {
                }
                if (fileReader != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            fileReader = null;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e6) {
                    if (!x.a(e6)) {
                        e6.printStackTrace();
                    }
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e7) {
                    if (!x.a(e7)) {
                        e7.printStackTrace();
                    }
                }
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:73:0x010b A[Catch:{ all -> 0x015b }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0110 A[SYNTHETIC, Splitter:B:75:0x0110] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0115 A[SYNTHETIC, Splitter:B:78:0x0115] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0137 A[SYNTHETIC, Splitter:B:92:0x0137] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x013c A[SYNTHETIC, Splitter:B:95:0x013c] */
    public static long k() {
        FileReader fileReader;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        try {
            fileReader = new FileReader("/proc/meminfo");
            try {
                bufferedReader = new BufferedReader(fileReader, 2048);
                try {
                    bufferedReader.readLine();
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e2) {
                            if (!x.a(e2)) {
                                e2.printStackTrace();
                            }
                        }
                        try {
                            fileReader.close();
                            return -1;
                        } catch (IOException e3) {
                            if (x.a(e3)) {
                                return -1;
                            }
                            e3.printStackTrace();
                            return -1;
                        }
                    } else {
                        long parseLong = 0 + (Long.parseLong(readLine.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim()) << 10);
                        String readLine2 = bufferedReader.readLine();
                        if (readLine2 == null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e4) {
                                if (!x.a(e4)) {
                                    e4.printStackTrace();
                                }
                            }
                            try {
                                fileReader.close();
                                return -1;
                            } catch (IOException e5) {
                                if (x.a(e5)) {
                                    return -1;
                                }
                                e5.printStackTrace();
                                return -1;
                            }
                        } else {
                            long parseLong2 = parseLong + (Long.parseLong(readLine2.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim()) << 10);
                            String readLine3 = bufferedReader.readLine();
                            if (readLine3 == null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e6) {
                                    if (!x.a(e6)) {
                                        e6.printStackTrace();
                                    }
                                }
                                try {
                                    fileReader.close();
                                    return -1;
                                } catch (IOException e7) {
                                    if (x.a(e7)) {
                                        return -1;
                                    }
                                    e7.printStackTrace();
                                    return -1;
                                }
                            } else {
                                long parseLong3 = (Long.parseLong(readLine3.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim()) << 10) + parseLong2;
                                try {
                                    bufferedReader.close();
                                } catch (IOException e8) {
                                    if (!x.a(e8)) {
                                        e8.printStackTrace();
                                    }
                                }
                                try {
                                    fileReader.close();
                                    return parseLong3;
                                } catch (IOException e9) {
                                    if (x.a(e9)) {
                                        return parseLong3;
                                    }
                                    e9.printStackTrace();
                                    return parseLong3;
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader != null) {
                    }
                    if (fileReader != null) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
                if (bufferedReader != null) {
                }
                if (fileReader != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            fileReader = null;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e10) {
                    if (!x.a(e10)) {
                        e10.printStackTrace();
                    }
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e11) {
                    if (!x.a(e11)) {
                        e11.printStackTrace();
                    }
                }
            }
            throw th;
        }
    }

    public static long l() {
        if (!t()) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) statFs.getBlockSize()) * ((long) statFs.getBlockCount());
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return -2;
        }
    }

    public static long m() {
        if (!t()) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return -2;
        }
    }

    public static String n() {
        String str = "fail";
        try {
            return Locale.getDefault().getCountry();
        } catch (Throwable th) {
            if (x.a(th)) {
                return str;
            }
            th.printStackTrace();
            return str;
        }
    }

    public static String o() {
        String str = "fail";
        try {
            return Build.BRAND;
        } catch (Throwable th) {
            if (x.a(th)) {
                return str;
            }
            th.printStackTrace();
            return str;
        }
    }

    public static String c(Context context) {
        String str;
        String str2 = "unknown";
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return null;
            }
            if (activeNetworkInfo.getType() == 1) {
                return "WIFI";
            }
            if (activeNetworkInfo.getType() == 0) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    int networkType = telephonyManager.getNetworkType();
                    switch (networkType) {
                        case a.CRASHTYPE_JAVA_CATCH /*1*/:
                            return "GPRS";
                        case a.CRASHTYPE_NATIVE /*2*/:
                            return "EDGE";
                        case a.CRASHTYPE_U3D /*3*/:
                            return "UMTS";
                        case a.CRASHTYPE_ANR /*4*/:
                            return "CDMA";
                        case a.CRASHTYPE_COCOS2DX_JS /*5*/:
                            return "EVDO_0";
                        case a.CRASHTYPE_COCOS2DX_LUA /*6*/:
                            return "EVDO_A";
                        case a.CRASHTYPE_BLOCK /*7*/:
                            return "1xRTT";
                        case 8:
                            return "HSDPA";
                        case 9:
                            return "HSUPA";
                        case 10:
                            return "HSPA";
                        case 11:
                            return "iDen";
                        case 12:
                            return "EVDO_B";
                        case 13:
                            return "LTE";
                        case 14:
                            return "eHRPD";
                        case 15:
                            return "HSPA+";
                        default:
                            str = "MOBILE(" + networkType + ")";
                            break;
                    }
                    return str;
                }
            }
            str = str2;
            return str;
        } catch (Exception e2) {
            if (x.a(e2)) {
                return str2;
            }
            e2.printStackTrace();
            return str2;
        }
    }

    public static String d(Context context) {
        String a2 = z.a(context, "ro.miui.ui.version.name");
        if (!z.a(a2) && !a2.equals("fail")) {
            return "XiaoMi/MIUI/" + a2;
        }
        String a3 = z.a(context, "ro.build.version.emui");
        if (!z.a(a3) && !a3.equals("fail")) {
            return "HuaWei/EMOTION/" + a3;
        }
        String a4 = z.a(context, "ro.lenovo.series");
        if (z.a(a4) || a4.equals("fail")) {
            String a5 = z.a(context, "ro.build.nubia.rom.name");
            if (!z.a(a5) && !a5.equals("fail")) {
                return "Zte/NUBIA/" + a5 + "_" + z.a(context, "ro.build.nubia.rom.code");
            }
            String a6 = z.a(context, "ro.meizu.product.model");
            if (!z.a(a6) && !a6.equals("fail")) {
                return "Meizu/FLYME/" + z.a(context, "ro.build.display.id");
            }
            String a7 = z.a(context, "ro.build.version.opporom");
            if (!z.a(a7) && !a7.equals("fail")) {
                return "Oppo/COLOROS/" + a7;
            }
            String a8 = z.a(context, "ro.vivo.os.build.display.id");
            if (!z.a(a8) && !a8.equals("fail")) {
                return "vivo/FUNTOUCH/" + a8;
            }
            String a9 = z.a(context, "ro.aa.romver");
            if (!z.a(a9) && !a9.equals("fail")) {
                return "htc/" + a9 + "/" + z.a(context, "ro.build.description");
            }
            String a10 = z.a(context, "ro.lewa.version");
            if (!z.a(a10) && !a10.equals("fail")) {
                return "tcl/" + a10 + "/" + z.a(context, "ro.build.display.id");
            }
            String a11 = z.a(context, "ro.gn.gnromvernumber");
            if (!z.a(a11) && !a11.equals("fail")) {
                return "amigo/" + a11 + "/" + z.a(context, "ro.build.display.id");
            }
            String a12 = z.a(context, "ro.build.tyd.kbstyle_version");
            if (z.a(a12) || a12.equals("fail")) {
                return z.a(context, "ro.build.fingerprint") + "/" + z.a(context, "ro.build.rom.id");
            }
            return "dido/" + a12;
        }
        return "Lenovo/VIBE/" + z.a(context, "ro.build.version.incremental");
    }

    public static String e(Context context) {
        return z.a(context, "ro.board.platform");
    }

    public static boolean p() {
        boolean z;
        boolean z2;
        String[] strArr = a;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            } else if (new File(strArr[i]).exists()) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (Build.TAGS == null || !Build.TAGS.contains("test-keys")) {
            z2 = false;
        } else {
            z2 = true;
        }
        return z2 || z;
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r0v3, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r1v15 */
    /* JADX WARNING: type inference failed for: r1v18 */
    /* JADX WARNING: type inference failed for: r1v19, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r0v7, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v21, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r1v22, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r1v23, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: type inference failed for: r1v24 */
    /* JADX WARNING: type inference failed for: r2v18 */
    /* JADX WARNING: type inference failed for: r2v19 */
    /* JADX WARNING: type inference failed for: r2v20 */
    /* JADX WARNING: type inference failed for: r1v25 */
    /* JADX WARNING: type inference failed for: r1v26 */
    /* JADX WARNING: type inference failed for: r1v27 */
    /* JADX WARNING: type inference failed for: r1v28 */
    /* JADX WARNING: type inference failed for: r1v29 */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00a8, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00a9, code lost:
        r3 = r0;
        r2 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00ac, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00ad, code lost:
        r3 = r0;
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00af, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00b0, code lost:
        r3 = r0;
        r2 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00b7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00b8, code lost:
        r3 = r0;
        r2 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00bb, code lost:
        r1 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00c1, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v7
  assigns: []
  uses: []
  mth insns count: 90
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0091 A[SYNTHETIC, Splitter:B:42:0x0091] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x009f A[SYNTHETIC, Splitter:B:49:0x009f] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ac A[ExcHandler: all (r0v5 'th' java.lang.Throwable A[CUSTOM_DECLARE]), PHI: r2 
  PHI: (r2v6 ?) = (r2v18 ?), (r2v20 ?) binds: [B:12:0x002e, B:23:0x0059] A[DONT_GENERATE, DONT_INLINE], Splitter:B:12:0x002e] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00bb A[ExcHandler: Throwable (th java.lang.Throwable), PHI: r1 
  PHI: (r1v7 ?) = (r1v25 ?), (r1v26 ?), (r1v27 ?), (r1v28 ?), (r1v29 ?) binds: [B:31:0x007e, B:32:?, B:27:0x0075, B:16:0x004a, B:5:0x001f] A[DONT_GENERATE, DONT_INLINE], Splitter:B:5:0x001f] */
    /* JADX WARNING: Unknown variable types count: 10 */
    public static String q() {
        Throwable th;
        ? r2;
        ? r1;
        ? r22;
        ? r12;
        ? r23;
        ? r13;
        ? r0 = 0;
        try {
            StringBuilder sb = new StringBuilder();
            if (new File("/sys/block/mmcblk0/device/type").exists()) {
                ? bufferedReader = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/type"));
                try {
                    r12 = bufferedReader;
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        sb.append(readLine);
                    }
                    bufferedReader.close();
                    r22 = bufferedReader;
                } catch (Throwable th2) {
                    th = th2;
                    r2 = r13;
                    if (r2 != 0) {
                    }
                    throw th;
                }
            } else {
                r22 = r0;
            }
            try {
                ? r24 = r22;
                sb.append(",");
                if (new File("/sys/block/mmcblk0/device/name").exists()) {
                    ? bufferedReader2 = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/name"));
                    r12 = bufferedReader2;
                    String readLine2 = bufferedReader2.readLine();
                    if (readLine2 != null) {
                        sb.append(readLine2);
                    }
                    bufferedReader2.close();
                    r22 = bufferedReader2;
                }
                r24 = r23;
                sb.append(",");
                if (new File("/sys/block/mmcblk0/device/cid").exists()) {
                    r13 = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/cid"));
                    r12 = r13;
                    String readLine3 = r13.readLine();
                    if (readLine3 != null) {
                        sb.append(readLine3);
                    }
                } else {
                    r13 = r23;
                }
                r12 = r13;
                r0 = sb.toString();
                r12 = r13;
                if (r13 != 0) {
                    try {
                        r13.close();
                    } catch (IOException e2) {
                        x.a(e2);
                    }
                }
            } catch (Throwable th3) {
            }
        } catch (Throwable th4) {
            th = th4;
            r2 = r0;
            if (r2 != 0) {
                try {
                    r2.close();
                } catch (IOException e3) {
                    x.a(e3);
                }
            }
            throw th;
        }
        return r0;
    }

    public static String f(Context context) {
        StringBuilder sb = new StringBuilder();
        String a2 = z.a(context, "ro.genymotion.version");
        if (a2 != null) {
            sb.append("ro.genymotion.version");
            sb.append("|");
            sb.append(a2);
            sb.append("\n");
        }
        String a3 = z.a(context, "androVM.vbox_dpi");
        if (a3 != null) {
            sb.append("androVM.vbox_dpi");
            sb.append("|");
            sb.append(a3);
            sb.append("\n");
        }
        String a4 = z.a(context, "qemu.sf.fake_camera");
        if (a4 != null) {
            sb.append("qemu.sf.fake_camera");
            sb.append("|");
            sb.append(a4);
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0095 A[SYNTHETIC, Splitter:B:32:0x0095] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a6 A[SYNTHETIC, Splitter:B:40:0x00a6] */
    public static String g(Context context) {
        BufferedReader bufferedReader;
        String readLine;
        StringBuilder sb = new StringBuilder();
        if (d == null) {
            d = z.a(context, "ro.secure");
        }
        if (d != null) {
            sb.append("ro.secure");
            sb.append("|");
            sb.append(d);
            sb.append("\n");
        }
        if (e == null) {
            e = z.a(context, "ro.debuggable");
        }
        if (e != null) {
            sb.append("ro.debuggable");
            sb.append("|");
            sb.append(e);
            sb.append("\n");
        }
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/self/status"));
            do {
                try {
                    readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                } catch (Throwable th) {
                    th = th;
                    try {
                        x.a(th);
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e2) {
                                x.a(e2);
                            }
                        }
                        return sb.toString();
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedReader != null) {
                        }
                        throw th;
                    }
                }
            } while (!readLine.startsWith("TracerPid:"));
            if (readLine != null) {
                String trim = readLine.substring(10).trim();
                sb.append("tracer_pid");
                sb.append("|");
                sb.append(trim);
            }
            String sb2 = sb.toString();
            try {
                bufferedReader.close();
                return sb2;
            } catch (IOException e3) {
                x.a(e3);
                return sb2;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    x.a(e4);
                }
            }
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00c9, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00ca, code lost:
        r2 = r1;
        r3 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00d9, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00da, code lost:
        r2 = r1;
        r3 = r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00b2 A[SYNTHETIC, Splitter:B:42:0x00b2] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00c0 A[SYNTHETIC, Splitter:B:49:0x00c0] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00dd A[ExcHandler: Throwable (th java.lang.Throwable), PHI: r0 
  PHI: (r0v9 java.io.BufferedReader) = (r0v25 java.io.BufferedReader), (r0v26 java.io.BufferedReader), (r0v27 java.io.BufferedReader) binds: [B:27:0x0089, B:16:0x0054, B:5:0x001f] A[DONT_GENERATE, DONT_INLINE], Splitter:B:5:0x001f] */
    public static String r() {
        BufferedReader bufferedReader;
        Throwable th;
        BufferedReader bufferedReader2;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader3 = null;
        try {
            if (new File("/sys/class/power_supply/ac/online").exists()) {
                bufferedReader2 = new BufferedReader(new FileReader("/sys/class/power_supply/ac/online"));
                try {
                    String readLine = bufferedReader2.readLine();
                    if (readLine != null) {
                        sb.append("ac_online");
                        sb.append("|");
                        sb.append(readLine);
                    }
                    bufferedReader2.close();
                    bufferedReader3 = bufferedReader2;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                    }
                    throw th;
                }
            }
            try {
                sb.append("\n");
                if (new File("/sys/class/power_supply/usb/online").exists()) {
                    bufferedReader2 = new BufferedReader(new FileReader("/sys/class/power_supply/usb/online"));
                    String readLine2 = bufferedReader2.readLine();
                    if (readLine2 != null) {
                        sb.append("usb_online");
                        sb.append("|");
                        sb.append(readLine2);
                    }
                    bufferedReader2.close();
                    bufferedReader3 = bufferedReader2;
                }
                try {
                    sb.append("\n");
                    if (new File("/sys/class/power_supply/battery/capacity").exists()) {
                        bufferedReader2 = new BufferedReader(new FileReader("/sys/class/power_supply/battery/capacity"));
                        String readLine3 = bufferedReader2.readLine();
                        if (readLine3 != null) {
                            sb.append("battery_capacity");
                            sb.append("|");
                            sb.append(readLine3);
                        }
                        bufferedReader2.close();
                    } else {
                        bufferedReader2 = bufferedReader3;
                    }
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (IOException e2) {
                            x.a(e2);
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader = bufferedReader3;
                    if (bufferedReader != null) {
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                bufferedReader = bufferedReader3;
                if (bufferedReader != null) {
                }
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
            bufferedReader = null;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e3) {
                    x.a(e3);
                }
            }
            throw th;
        }
        return sb.toString();
    }

    public static String h(Context context) {
        StringBuilder sb = new StringBuilder();
        String a2 = z.a(context, "gsm.sim.state");
        if (a2 != null) {
            sb.append("gsm.sim.state");
            sb.append("|");
            sb.append(a2);
        }
        sb.append("\n");
        String a3 = z.a(context, "gsm.sim.state2");
        if (a3 != null) {
            sb.append("gsm.sim.state2");
            sb.append("|");
            sb.append(a3);
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0041 A[SYNTHETIC, Splitter:B:20:0x0041] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x004e A[SYNTHETIC, Splitter:B:27:0x004e] */
    public static long s() {
        BufferedReader bufferedReader;
        float f = 0.0f;
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/uptime"));
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    f = ((float) (System.currentTimeMillis() / 1000)) - Float.parseFloat(readLine.split(" ")[0]);
                }
                try {
                    bufferedReader.close();
                } catch (IOException e2) {
                    x.a(e2);
                }
            } catch (Throwable th) {
                try {
                    x.a("Failed to get boot time of device.", new Object[0]);
                    if (bufferedReader != null) {
                    }
                    return (long) f;
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            x.a(e3);
                        }
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            if (bufferedReader != null) {
            }
            throw th;
        }
        return (long) f;
    }

    public static boolean i(Context context) {
        if (k(context) == null) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < c.length; i++) {
                if (i == 0) {
                    if (!new File(c[i]).exists()) {
                        arrayList.add(Integer.valueOf(i));
                    }
                } else if (new File(c[i]).exists()) {
                    arrayList.add(Integer.valueOf(i));
                }
            }
            if ((arrayList.isEmpty() ? null : arrayList.toString()) == null) {
                return false;
            }
        }
        return true;
    }

    private static String k(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < b.length; i++) {
            try {
                packageManager.getPackageInfo(b[i], 1);
                arrayList.add(Integer.valueOf(i));
            } catch (NameNotFoundException e2) {
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList.toString();
    }

    public static boolean j(Context context) {
        return (((l(context) | v()) | w()) | u()) > 0;
    }

    private static int u() {
        try {
            Method method = Class.forName("android.app.ActivityManagerNative").getMethod("getDefault", new Class[0]);
            method.setAccessible(true);
            if (method.invoke(null, new Object[0]).getClass().getName().startsWith("$Proxy")) {
                return 256;
            }
            return 0;
        } catch (Exception e2) {
            return 256;
        }
    }

    private static int l(Context context) {
        int i = 0;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getInstallerPackageName("de.robv.android.xposed.installer");
            i = 1;
        } catch (Exception e2) {
        }
        try {
            packageManager.getInstallerPackageName("com.saurik.substrate");
            return i | 2;
        } catch (Exception e3) {
            return i;
        }
    }

    private static int v() {
        StackTraceElement[] stackTrace;
        try {
            throw new Exception("detect hook");
        } catch (Exception e2) {
            int i = 0;
            int i2 = 0;
            for (StackTraceElement stackTraceElement : e2.getStackTrace()) {
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("main")) {
                    i |= 4;
                }
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("handleHookedMethod")) {
                    i |= 8;
                }
                if (stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2") && stackTraceElement.getMethodName().equals("invoked")) {
                    i |= 16;
                }
                if (stackTraceElement.getClassName().equals("com.android.internal.os.ZygoteInit")) {
                    i2++;
                    if (i2 == 2) {
                        i |= 32;
                    }
                }
            }
            return i;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x005e A[SYNTHETIC, Splitter:B:16:0x005e] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a9 A[SYNTHETIC, Splitter:B:43:0x00a9] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00ba A[SYNTHETIC, Splitter:B:52:0x00ba] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c7 A[SYNTHETIC, Splitter:B:59:0x00c7] */
    /* JADX WARNING: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:95:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:97:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:40:0x00a4=Splitter:B:40:0x00a4, B:49:0x00b5=Splitter:B:49:0x00b5, B:13:0x0059=Splitter:B:13:0x0059} */
    private static int w() {
        UnsupportedEncodingException unsupportedEncodingException;
        BufferedReader bufferedReader;
        IOException iOException;
        FileNotFoundException fileNotFoundException;
        int i;
        int i2;
        int i3 = 0;
        try {
            HashSet hashSet = new HashSet();
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + Process.myPid() + "/maps"), "utf-8"));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    } else if (readLine.endsWith(".so") || readLine.endsWith(".jar")) {
                        hashSet.add(readLine.substring(readLine.lastIndexOf(" ") + 1));
                    }
                } catch (UnsupportedEncodingException e2) {
                    unsupportedEncodingException = e2;
                } catch (FileNotFoundException e3) {
                    fileNotFoundException = e3;
                    fileNotFoundException.printStackTrace();
                    if (bufferedReader != null) {
                        return i3;
                    }
                    try {
                        bufferedReader.close();
                        return i3;
                    } catch (IOException e4) {
                        e4.printStackTrace();
                        return i3;
                    }
                } catch (IOException e5) {
                    iOException = e5;
                    iOException.printStackTrace();
                    if (bufferedReader != null) {
                        return i3;
                    }
                    try {
                        bufferedReader.close();
                        return i3;
                    } catch (IOException e6) {
                        e6.printStackTrace();
                        return i3;
                    }
                }
            }
            Iterator it = hashSet.iterator();
            while (true) {
                try {
                    i = i3;
                    if (it.hasNext()) {
                        Object next = it.next();
                        if (((String) next).toLowerCase().contains("xposed")) {
                            i2 = i | 64;
                        } else {
                            i2 = i;
                        }
                        try {
                            if (((String) next).contains("com.saurik.substrate")) {
                                i3 = i2 | 128;
                            } else {
                                i3 = i2;
                            }
                        } catch (UnsupportedEncodingException e7) {
                            unsupportedEncodingException = e7;
                            i3 = i2;
                        } catch (FileNotFoundException e8) {
                            fileNotFoundException = e8;
                            i3 = i2;
                            fileNotFoundException.printStackTrace();
                            if (bufferedReader != null) {
                            }
                        } catch (IOException e9) {
                            iOException = e9;
                            i3 = i2;
                            iOException.printStackTrace();
                            if (bufferedReader != null) {
                            }
                        }
                    } else {
                        try {
                            bufferedReader.close();
                            return i;
                        } catch (IOException e10) {
                            e10.printStackTrace();
                            return i;
                        }
                    }
                } catch (UnsupportedEncodingException e11) {
                    unsupportedEncodingException = e11;
                    i3 = i;
                    try {
                        unsupportedEncodingException.printStackTrace();
                        if (bufferedReader != null) {
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e12) {
                                e12.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e13) {
                    fileNotFoundException = e13;
                    i3 = i;
                    fileNotFoundException.printStackTrace();
                    if (bufferedReader != null) {
                    }
                } catch (IOException e14) {
                    iOException = e14;
                    i3 = i;
                    iOException.printStackTrace();
                    if (bufferedReader != null) {
                    }
                }
            }
        } catch (UnsupportedEncodingException e15) {
            unsupportedEncodingException = e15;
            bufferedReader = null;
            unsupportedEncodingException.printStackTrace();
            if (bufferedReader != null) {
                return i3;
            }
            try {
                bufferedReader.close();
                return i3;
            } catch (IOException e16) {
                e16.printStackTrace();
                return i3;
            }
        } catch (FileNotFoundException e17) {
            fileNotFoundException = e17;
            bufferedReader = null;
            fileNotFoundException.printStackTrace();
            if (bufferedReader != null) {
            }
        } catch (IOException e18) {
            iOException = e18;
            bufferedReader = null;
            iOException.printStackTrace();
            if (bufferedReader != null) {
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
            if (bufferedReader != null) {
            }
            throw th;
        }
    }
}
