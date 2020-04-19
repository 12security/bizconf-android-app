package com.tencent.bugly.yaq.crashreport.common.info;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import com.tencent.bugly.yaq.proguard.x;
import com.tencent.bugly.yaq.proguard.z;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/* compiled from: BUGLY */
public class AppInfo {
    private static ActivityManager a;

    static {
        "@buglyAllChannel@".split(",");
        "@buglyAllChannelPriority@".split(",");
    }

    public static String a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return context.getPackageName();
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return "fail";
        }
    }

    public static PackageInfo b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(a(context), 0);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static boolean a(Context context, String str) {
        if (context == null || str == null || str.trim().length() <= 0) {
            return false;
        }
        try {
            String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
            if (strArr == null) {
                return false;
            }
            for (String equals : strArr) {
                if (str.equals(equals)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            if (x.a(th)) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0043 A[Catch:{ all -> 0x005e }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004c A[SYNTHETIC, Splitter:B:23:0x004c] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0056 A[SYNTHETIC, Splitter:B:29:0x0056] */
    public static String a(int i) {
        FileReader fileReader;
        String valueOf;
        int i2 = 0;
        try {
            fileReader = new FileReader("/proc/" + i + "/cmdline");
            try {
                char[] cArr = new char[512];
                fileReader.read(cArr);
                while (i2 < cArr.length && cArr[i2] != 0) {
                    i2++;
                }
                valueOf = new String(cArr).substring(0, i2);
                try {
                    fileReader.close();
                } catch (Throwable th) {
                }
            } catch (Throwable th2) {
                th = th2;
                try {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                    valueOf = String.valueOf(i);
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (Throwable th3) {
                        }
                    }
                    return valueOf;
                } catch (Throwable th4) {
                    th = th4;
                    if (fileReader != null) {
                    }
                    throw th;
                }
            }
        } catch (Throwable th5) {
            th = th5;
            fileReader = null;
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (Throwable th6) {
                }
            }
            throw th;
        }
        return valueOf;
    }

    public static String c(Context context) {
        if (context == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (packageManager == null || applicationInfo == null) {
                return null;
            }
            CharSequence applicationLabel = packageManager.getApplicationLabel(applicationInfo);
            if (applicationLabel != null) {
                return applicationLabel.toString();
            }
            return null;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> d(Context context) {
        HashMap hashMap;
        if (context == null) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                hashMap = new HashMap();
                Object obj = applicationInfo.metaData.get("BUGLY_DISABLE");
                if (obj != null) {
                    hashMap.put("BUGLY_DISABLE", obj.toString());
                }
                Object obj2 = applicationInfo.metaData.get("BUGLY_APPID");
                if (obj2 != null) {
                    hashMap.put("BUGLY_APPID", obj2.toString());
                }
                Object obj3 = applicationInfo.metaData.get("BUGLY_APP_CHANNEL");
                if (obj3 != null) {
                    hashMap.put("BUGLY_APP_CHANNEL", obj3.toString());
                }
                Object obj4 = applicationInfo.metaData.get("BUGLY_APP_VERSION");
                if (obj4 != null) {
                    hashMap.put("BUGLY_APP_VERSION", obj4.toString());
                }
                Object obj5 = applicationInfo.metaData.get("BUGLY_ENABLE_DEBUG");
                if (obj5 != null) {
                    hashMap.put("BUGLY_ENABLE_DEBUG", obj5.toString());
                }
                Object obj6 = applicationInfo.metaData.get("com.tencent.rdm.uuid");
                if (obj6 != null) {
                    hashMap.put("com.tencent.rdm.uuid", obj6.toString());
                }
            } else {
                hashMap = null;
            }
            return hashMap;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    public static List<String> a(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        try {
            String str = (String) map.get("BUGLY_DISABLE");
            if (str == null || str.length() == 0) {
                return null;
            }
            String[] split = str.split(",");
            for (int i = 0; i < split.length; i++) {
                split[i] = split[i].trim();
            }
            return Arrays.asList(split);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr != null && bArr.length > 0) {
            try {
                CertificateFactory instance = CertificateFactory.getInstance("X.509");
                if (instance == null) {
                    return null;
                }
                X509Certificate x509Certificate = (X509Certificate) instance.generateCertificate(new ByteArrayInputStream(bArr));
                if (x509Certificate == null) {
                    return null;
                }
                sb.append("Issuer|");
                Principal issuerDN = x509Certificate.getIssuerDN();
                if (issuerDN != null) {
                    sb.append(issuerDN.toString());
                } else {
                    sb.append("unknown");
                }
                sb.append("\n");
                sb.append("SerialNumber|");
                BigInteger serialNumber = x509Certificate.getSerialNumber();
                if (issuerDN != null) {
                    sb.append(serialNumber.toString(16));
                } else {
                    sb.append("unknown");
                }
                sb.append("\n");
                sb.append("NotBefore|");
                Date notBefore = x509Certificate.getNotBefore();
                if (issuerDN != null) {
                    sb.append(notBefore.toString());
                } else {
                    sb.append("unknown");
                }
                sb.append("\n");
                sb.append("NotAfter|");
                Date notAfter = x509Certificate.getNotAfter();
                if (issuerDN != null) {
                    sb.append(notAfter.toString());
                } else {
                    sb.append("unknown");
                }
                sb.append("\n");
                sb.append("SHA1|");
                String a2 = z.a(MessageDigest.getInstance("SHA1").digest(x509Certificate.getEncoded()));
                if (a2 == null || a2.length() <= 0) {
                    sb.append("unknown");
                } else {
                    sb.append(a2.toString());
                }
                sb.append("\n");
                sb.append("MD5|");
                String a3 = z.a(MessageDigest.getInstance("MD5").digest(x509Certificate.getEncoded()));
                if (a3 == null || a3.length() <= 0) {
                    sb.append("unknown");
                } else {
                    sb.append(a3.toString());
                }
            } catch (CertificateException e) {
                if (!x.a(e)) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        if (sb.length() == 0) {
            return "unknown";
        }
        return sb.toString();
    }

    public static String e(Context context) {
        String a2 = a(context);
        if (a2 == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a2, 64);
            if (packageInfo == null) {
                return null;
            }
            Signature[] signatureArr = packageInfo.signatures;
            if (signatureArr == null || signatureArr.length == 0) {
                return null;
            }
            return a(packageInfo.signatures[0].toByteArray());
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static boolean f(Context context) {
        if (context == null) {
            return false;
        }
        if (a == null) {
            a = (ActivityManager) context.getSystemService("activity");
        }
        try {
            MemoryInfo memoryInfo = new MemoryInfo();
            a.getMemoryInfo(memoryInfo);
            if (!memoryInfo.lowMemory) {
                return false;
            }
            x.c("Memory is low.", new Object[0]);
            return true;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    private static String h(Context context) {
        String str = "";
        boolean z = null;
        try {
            String string = z.a("DENGTA_META", context).getString("key_channelpath", "");
            z = z.a(string);
            if (z) {
                string = "channel.ini";
            }
            x.a("[AppInfo] Beacon channel file path: " + string, new Object[0]);
            if (!string.equals("")) {
                z = context.getAssets().open(string);
                Properties properties = new Properties();
                properties.load(z);
                str = properties.getProperty("CHANNEL", "");
                x.a("[AppInfo] Beacon channel read from assert: " + str, new Object[0]);
                if (!z.a(str)) {
                    if (z != null) {
                        try {
                            z.close();
                        } catch (IOException e) {
                            x.a(e);
                        }
                    }
                    return str;
                }
            }
            if (z != null) {
                try {
                    z.close();
                } catch (IOException e2) {
                    x.a(e2);
                }
            }
        } catch (Exception e3) {
            x.d("[AppInfo] Failed to get get beacon channel", new Object[0]);
            if (z != null) {
                try {
                    z.close();
                } catch (IOException e4) {
                    x.a(e4);
                }
            }
        } finally {
            if (z != null) {
                try {
                    z.close();
                } catch (IOException e5) {
                    x.a(e5);
                }
            }
        }
        return str;
    }

    private static String i(Context context) {
        String str = "";
        try {
            Object obj = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.get("CHANNEL_DENGTA");
            if (obj != null) {
                return obj.toString();
            }
            return str;
        } catch (Throwable th) {
            x.d("[AppInfo] Failed to read beacon channel from manifest.", new Object[0]);
            x.a(th);
            return str;
        }
    }

    public static String g(Context context) {
        if (context == null) {
            return "";
        }
        String h = h(context);
        if (z.a(h)) {
            return i(context);
        }
        return h;
    }
}
