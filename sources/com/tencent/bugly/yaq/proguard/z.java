package com.tencent.bugly.yaq.proguard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Process;
import com.tencent.bugly.yaq.crashreport.common.info.AppInfo;
import com.tencent.bugly.yaq.crashreport.common.info.PlugInBean;
import com.tencent.bugly.yaq.crashreport.common.info.a;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: BUGLY */
public class z {
    private static Map<String, String> a = null;
    private static boolean b = false;

    public static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        try {
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            return stringWriter.getBuffer().toString();
        } catch (Throwable th2) {
            if (!x.a(th2)) {
                th2.printStackTrace();
            }
            return "fail";
        }
    }

    public static String a() {
        return a(System.currentTimeMillis());
    }

    public static String a(long j) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(j));
        } catch (Exception e) {
            return new Date().toString();
        }
    }

    public static String a(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date);
        } catch (Exception e) {
            return new Date().toString();
        }
    }

    private static byte[] a(byte[] bArr, int i, String str) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        x.c("rqdp{  enD:} %d %d", Integer.valueOf(bArr.length), Integer.valueOf(i));
        try {
            ag a2 = a.a(i);
            if (a2 == null) {
                return null;
            }
            a2.a(str);
            return a2.b(bArr);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static byte[] b(byte[] bArr, int i, String str) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        try {
            ag a2 = a.a(i);
            if (a2 == null) {
                return null;
            }
            a2.a(str);
            return a2.a(bArr);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            x.d("encrytype %d %s", Integer.valueOf(i), str);
            return null;
        }
    }

    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.util.zip.ZipOutputStream] */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r2v5, types: [java.util.zip.ZipOutputStream] */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0080 A[SYNTHETIC, Splitter:B:35:0x0080] */
    /* JADX WARNING: Unknown variable types count: 2 */
    public static byte[] a(File file, String str, String str2) {
        ? r2;
        ? r22;
        byte[] bArr = 0;
        if (!(str == null || str.length() == 0)) {
            x.c("rqdp{  ZF start}", new Object[0]);
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
                try {
                    zipOutputStream.setMethod(8);
                    zipOutputStream.putNextEntry(new ZipEntry(str2));
                    byte[] bArr2 = new byte[1024];
                    while (true) {
                        int read = byteArrayInputStream.read(bArr2);
                        if (read <= 0) {
                            break;
                        }
                        zipOutputStream.write(bArr2, 0, read);
                    }
                    zipOutputStream.closeEntry();
                    zipOutputStream.flush();
                    zipOutputStream.finish();
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    try {
                        zipOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    x.c("rqdp{  ZF end}", new Object[0]);
                    bArr = byteArray;
                } catch (Throwable th) {
                    th = th;
                    r2 = zipOutputStream;
                }
            } catch (Throwable th2) {
                th = th2;
                r22 = bArr;
                if (r22 != 0) {
                }
                x.c("rqdp{  ZF end}", new Object[0]);
                throw th;
            }
        }
        return bArr;
    }

    public static byte[] a(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        String str = "[Util] Zip %d bytes data with type %s";
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(bArr.length);
        objArr[1] = i == 2 ? "Gzip" : "zip";
        x.c(str, objArr);
        try {
            ab a2 = aa.a(i);
            if (a2 == null) {
                return null;
            }
            return a2.a(bArr);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static byte[] b(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        String str = "[Util] Unzip %d bytes data with type %s";
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(bArr.length);
        objArr[1] = i == 2 ? "Gzip" : "zip";
        x.c(str, objArr);
        try {
            ab a2 = aa.a(i);
            if (a2 == null) {
                return null;
            }
            return a2.b(bArr);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static byte[] a(byte[] bArr, int i, int i2, String str) {
        byte[] bArr2 = null;
        if (bArr == null) {
            return bArr2;
        }
        try {
            return a(a(bArr, 2), 1, str);
        } catch (Throwable th) {
            if (x.a(th)) {
                return bArr2;
            }
            th.printStackTrace();
            return bArr2;
        }
    }

    public static byte[] b(byte[] bArr, int i, int i2, String str) {
        try {
            return b(b(bArr, 1, str), 2);
        } catch (Exception e) {
            if (!x.a(e)) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static long b() {
        try {
            return (((System.currentTimeMillis() + ((long) TimeZone.getDefault().getRawOffset())) / 86400000) * 86400000) - ((long) TimeZone.getDefault().getRawOffset());
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return -1;
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString().toUpperCase();
    }

    public static String b(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "NULL";
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(bArr);
            return a(instance.digest());
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0096 A[Catch:{ all -> 0x0103 }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x009b A[SYNTHETIC, Splitter:B:40:0x009b] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a0 A[SYNTHETIC, Splitter:B:43:0x00a0] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00e6 A[SYNTHETIC, Splitter:B:68:0x00e6] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00eb A[SYNTHETIC, Splitter:B:71:0x00eb] */
    public static boolean a(File file, File file2, int i) {
        ZipOutputStream zipOutputStream;
        FileInputStream fileInputStream;
        x.c("rqdp{  ZF start}", new Object[0]);
        if (file == null || file2 == null || file.equals(file2)) {
            x.d("rqdp{  err ZF 1R!}", new Object[0]);
            return false;
        } else if (!file.exists() || !file.canRead()) {
            x.d("rqdp{  !sFile.exists() || !sFile.canRead(),pls check ,return!}", new Object[0]);
            return false;
        } else {
            try {
                if (file2.getParentFile() != null && !file2.getParentFile().exists()) {
                    file2.getParentFile().mkdirs();
                }
                if (!file2.exists()) {
                    file2.createNewFile();
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
            if (!file2.exists() || !file2.canRead()) {
                return false;
            }
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file2)));
                    try {
                        zipOutputStream.setMethod(8);
                        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                        byte[] bArr = new byte[5000];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            zipOutputStream.write(bArr, 0, read);
                        }
                        zipOutputStream.flush();
                        zipOutputStream.closeEntry();
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            zipOutputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        x.c("rqdp{  ZF end}", new Object[0]);
                        return true;
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            if (!x.a(th)) {
                                th.printStackTrace();
                            }
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            if (zipOutputStream != null) {
                                try {
                                    zipOutputStream.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            x.c("rqdp{  ZF end}", new Object[0]);
                            return false;
                        } catch (Throwable th3) {
                            th = th3;
                            if (fileInputStream != null) {
                            }
                            if (zipOutputStream != null) {
                            }
                            x.c("rqdp{  ZF end}", new Object[0]);
                            throw th;
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    zipOutputStream = null;
                    if (fileInputStream != null) {
                    }
                    if (zipOutputStream != null) {
                    }
                    x.c("rqdp{  ZF end}", new Object[0]);
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                zipOutputStream = null;
                fileInputStream = null;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                if (zipOutputStream != null) {
                    try {
                        zipOutputStream.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                x.c("rqdp{  ZF end}", new Object[0]);
                throw th;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0080 A[Catch:{ all -> 0x00e4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0085 A[SYNTHETIC, Splitter:B:22:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x008a A[SYNTHETIC, Splitter:B:25:0x008a] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00cc A[SYNTHETIC, Splitter:B:52:0x00cc] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00d1 A[SYNTHETIC, Splitter:B:55:0x00d1] */
    private static ArrayList<String> c(Context context, String str) {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        BufferedReader bufferedReader3;
        if (AppInfo.f(context)) {
            return new ArrayList<>(Arrays.asList(new String[]{"unknown(low memory)"}));
        }
        ArrayList arrayList = new ArrayList();
        String str2 = "/system/bin/sh";
        try {
            if (!new File(str2).exists() || !new File(str2).canExecute()) {
                str2 = "sh";
            }
            ArrayList arrayList2 = new ArrayList(Arrays.asList(new String[]{str2, "-c"}));
            arrayList2.add(str);
            Process exec = Runtime.getRuntime().exec((String[]) arrayList2.toArray(new String[3]));
            bufferedReader2 = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            while (true) {
                try {
                    String readLine = bufferedReader2.readLine();
                    if (readLine == null) {
                        break;
                    }
                    arrayList.add(readLine);
                } catch (Throwable th) {
                    th = th;
                    bufferedReader3 = null;
                    if (bufferedReader2 != null) {
                    }
                    if (bufferedReader3 != null) {
                    }
                    throw th;
                }
            }
            bufferedReader3 = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
            while (true) {
                try {
                    String readLine2 = bufferedReader3.readLine();
                    if (readLine2 != null) {
                        arrayList.add(readLine2);
                    } else {
                        try {
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (bufferedReader3 != null) {
                        try {
                            bufferedReader3.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
            bufferedReader2.close();
            try {
                bufferedReader3.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            return arrayList;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader3 = null;
            bufferedReader2 = null;
            if (bufferedReader2 != null) {
            }
            if (bufferedReader3 != null) {
            }
            throw th;
        }
    }

    public static String a(Context context, String str) {
        if (str == null || str.trim().equals("")) {
            return "";
        }
        if (a == null) {
            a = new HashMap();
            ArrayList<String> c = c(context, "getprop");
            if (c != null && c.size() > 0) {
                x.b(z.class, "Successfully get 'getprop' list.", new Object[0]);
                Pattern compile = Pattern.compile("\\[(.+)\\]: \\[(.*)\\]");
                for (String matcher : c) {
                    Matcher matcher2 = compile.matcher(matcher);
                    if (matcher2.find()) {
                        a.put(matcher2.group(1), matcher2.group(2));
                    }
                }
                x.b(z.class, "System properties number: %d.", Integer.valueOf(a.size()));
            }
        }
        if (a.containsKey(str)) {
            return (String) a.get(str);
        }
        return "fail";
    }

    public static void b(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean a(String str) {
        if (str == null || str.trim().length() <= 0) {
            return true;
        }
        return false;
    }

    public static void b(String str) {
        if (str != null) {
            File file = new File(str);
            if (file.isFile() && file.exists() && file.canWrite()) {
                file.delete();
            }
        }
    }

    public static byte[] c(long j) {
        try {
            return (j).getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long c(byte[] bArr) {
        long j = -1;
        if (bArr == null) {
            return j;
        }
        try {
            return Long.parseLong(new String(bArr, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return j;
        }
    }

    public static Context a(Context context) {
        if (context == null) {
            return context;
        }
        Context applicationContext = context.getApplicationContext();
        return applicationContext != null ? applicationContext : context;
    }

    public static String b(Throwable th) {
        if (th == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    public static void a(Class<?> cls, String str, Object obj, Object obj2) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(null, obj);
        } catch (Exception e) {
        }
    }

    public static Object a(String str, String str2, Object obj, Class<?>[] clsArr, Object[] objArr) {
        boolean z = false;
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(null, objArr);
        } catch (Exception e) {
            return z;
        }
    }

    public static void a(Parcel parcel, Map<String, PlugInBean> map) {
        if (map == null || map.size() <= 0) {
            parcel.writeBundle(null);
            return;
        }
        int size = map.size();
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = new ArrayList(size);
        for (Entry entry : map.entrySet()) {
            arrayList.add(entry.getKey());
            arrayList2.add(entry.getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putInt("pluginNum", arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            bundle.putString("pluginKey" + i, (String) arrayList.get(i));
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            bundle.putString("pluginVal" + i2 + "plugInId", ((PlugInBean) arrayList2.get(i2)).a);
            bundle.putString("pluginVal" + i2 + "plugInUUID", ((PlugInBean) arrayList2.get(i2)).c);
            bundle.putString("pluginVal" + i2 + "plugInVersion", ((PlugInBean) arrayList2.get(i2)).b);
        }
        parcel.writeBundle(bundle);
    }

    public static Map<String, PlugInBean> a(Parcel parcel) {
        HashMap hashMap;
        Bundle readBundle = parcel.readBundle();
        if (readBundle == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int intValue = ((Integer) readBundle.get("pluginNum")).intValue();
        for (int i = 0; i < intValue; i++) {
            arrayList.add(readBundle.getString("pluginKey" + i));
        }
        for (int i2 = 0; i2 < intValue; i2++) {
            arrayList2.add(new PlugInBean(readBundle.getString("pluginVal" + i2 + "plugInId"), readBundle.getString("pluginVal" + i2 + "plugInVersion"), readBundle.getString("pluginVal" + i2 + "plugInUUID")));
        }
        if (arrayList.size() == arrayList2.size()) {
            HashMap hashMap2 = new HashMap(arrayList.size());
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                hashMap2.put(arrayList.get(i3), PlugInBean.class.cast(arrayList2.get(i3)));
            }
            hashMap = hashMap2;
        } else {
            x.e("map plugin parcel error!", new Object[0]);
            hashMap = null;
        }
        return hashMap;
    }

    public static void b(Parcel parcel, Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            parcel.writeBundle(null);
            return;
        }
        int size = map.size();
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = new ArrayList(size);
        for (Entry entry : map.entrySet()) {
            arrayList.add(entry.getKey());
            arrayList2.add(entry.getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("keys", arrayList);
        bundle.putStringArrayList("values", arrayList2);
        parcel.writeBundle(bundle);
    }

    public static Map<String, String> b(Parcel parcel) {
        HashMap hashMap;
        Bundle readBundle = parcel.readBundle();
        if (readBundle == null) {
            return null;
        }
        ArrayList stringArrayList = readBundle.getStringArrayList("keys");
        ArrayList stringArrayList2 = readBundle.getStringArrayList("values");
        if (stringArrayList == null || stringArrayList2 == null || stringArrayList.size() != stringArrayList2.size()) {
            x.e("map parcel error!", new Object[0]);
            hashMap = null;
        } else {
            HashMap hashMap2 = new HashMap(stringArrayList.size());
            for (int i = 0; i < stringArrayList.size(); i++) {
                hashMap2.put(stringArrayList.get(i), stringArrayList2.get(i));
            }
            hashMap = hashMap2;
        }
        return hashMap;
    }

    public static byte[] a(Parcelable parcelable) {
        Parcel obtain = Parcel.obtain();
        parcelable.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }

    public static <T> T a(byte[] bArr, Creator<T> creator) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        try {
            T createFromParcel = creator.createFromParcel(obtain);
            if (obtain == null) {
                return createFromParcel;
            }
            obtain.recycle();
            return createFromParcel;
        } catch (Throwable th) {
            if (obtain != null) {
                obtain.recycle();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x0108 A[SYNTHETIC, Splitter:B:55:0x0108] */
    public static String a(Context context, int i, String str) {
        Process process;
        StringBuilder sb;
        String sb2;
        if (!AppInfo.a(context, "android.permission.READ_LOGS")) {
            x.d("no read_log permission!", new Object[0]);
            return null;
        }
        String[] strArr = str == null ? new String[]{"logcat", "-d", "-v", "threadtime"} : new String[]{"logcat", "-d", "-v", "threadtime", "-s", str};
        process = null;
        sb = new StringBuilder();
        try {
            Process exec = Runtime.getRuntime().exec(strArr);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine).append("\n");
                    if (i > 0 && sb.length() > i) {
                        sb.delete(0, sb.length() - i);
                    }
                }
                String sb3 = sb.toString();
                if (exec == null) {
                    return sb3;
                }
                try {
                    exec.getOutputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    exec.getInputStream().close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                try {
                    exec.getErrorStream().close();
                    return sb3;
                } catch (IOException e3) {
                    e3.printStackTrace();
                    return sb3;
                }
            } catch (Throwable th) {
                th = th;
                process = exec;
                if (process != null) {
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            process.getErrorStream().close();
            return sb2;
        } catch (IOException e4) {
            e4.printStackTrace();
            return sb2;
        }
        try {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            sb2 = sb.append("\n[error:" + th.toString() + "]").toString();
            if (process == null) {
                return sb2;
            }
            try {
                process.getOutputStream().close();
            } catch (IOException e5) {
                e5.printStackTrace();
            }
            try {
                process.getInputStream().close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            process.getErrorStream().close();
            return sb2;
        } catch (Throwable th3) {
            th = th3;
            if (process != null) {
                try {
                    process.getOutputStream().close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
                try {
                    process.getInputStream().close();
                } catch (IOException e8) {
                    e8.printStackTrace();
                }
                try {
                    process.getErrorStream().close();
                } catch (IOException e9) {
                    e9.printStackTrace();
                }
            }
            throw th;
        }
        process.getInputStream().close();
        process.getErrorStream().close();
        return sb2;
    }

    public static Map<String, String> a(int i, boolean z) {
        HashMap hashMap = new HashMap(12);
        Map allStackTraces = Thread.getAllStackTraces();
        if (allStackTraces == null) {
            return null;
        }
        Thread thread = Looper.getMainLooper().getThread();
        if (!allStackTraces.containsKey(thread)) {
            allStackTraces.put(thread, thread.getStackTrace());
        }
        Thread.currentThread().getId();
        StringBuilder sb = new StringBuilder();
        for (Entry entry : allStackTraces.entrySet()) {
            sb.setLength(0);
            if (!(entry.getValue() == null || ((StackTraceElement[]) entry.getValue()).length == 0)) {
                StackTraceElement[] stackTraceElementArr = (StackTraceElement[]) entry.getValue();
                int length = stackTraceElementArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    StackTraceElement stackTraceElement = stackTraceElementArr[i2];
                    if (i > 0 && sb.length() >= i) {
                        sb.append("\n[Stack over limit size :" + i + " , has been cut!]");
                        break;
                    }
                    sb.append(stackTraceElement.toString()).append("\n");
                    i2++;
                }
                hashMap.put(((Thread) entry.getKey()).getName() + "(" + ((Thread) entry.getKey()).getId() + ")", sb.toString());
            }
        }
        return hashMap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0030 A[SYNTHETIC, Splitter:B:17:0x0030] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0050 A[Catch:{ Exception -> 0x0054 }] */
    public static synchronized byte[] a(int i) {
        byte[] bArr;
        DataInputStream dataInputStream;
        synchronized (z.class) {
            try {
                bArr = new byte[16];
                dataInputStream = new DataInputStream(new FileInputStream(new File("/dev/urandom")));
                try {
                    dataInputStream.readFully(bArr);
                    try {
                        dataInputStream.close();
                    } catch (Exception e) {
                        if (!x.b(e)) {
                            e.printStackTrace();
                        }
                        bArr = null;
                    }
                } catch (Exception e2) {
                    e = e2;
                    try {
                        x.e("Failed to read from /dev/urandom : %s", e);
                        if (dataInputStream != null) {
                        }
                        KeyGenerator instance = KeyGenerator.getInstance("AES");
                        instance.init(128, new SecureRandom());
                        bArr = instance.generateKey().getEncoded();
                        return bArr;
                    } catch (Throwable th) {
                        th = th;
                        if (dataInputStream != null) {
                            dataInputStream.close();
                        }
                        throw th;
                    }
                }
            } catch (Exception e3) {
                e = e3;
                dataInputStream = null;
                x.e("Failed to read from /dev/urandom : %s", e);
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                KeyGenerator instance2 = KeyGenerator.getInstance("AES");
                instance2.init(128, new SecureRandom());
                bArr = instance2.generateKey().getEncoded();
                return bArr;
            } catch (Throwable th2) {
                th = th2;
                dataInputStream = null;
                if (dataInputStream != null) {
                }
                throw th;
            }
        }
        return bArr;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    @TargetApi(19)
    public static byte[] a(int i, byte[] bArr, byte[] bArr2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
            Cipher instance = Cipher.getInstance("AES/GCM/NoPadding");
            if (VERSION.SDK_INT < 21 || b) {
                instance.init(i, secretKeySpec, new IvParameterSpec(bArr2));
            } else {
                instance.init(i, secretKeySpec, new GCMParameterSpec(instance.getBlockSize() << 3, bArr2));
            }
            return instance.doFinal(bArr);
        } catch (InvalidAlgorithmParameterException e) {
            b = true;
            throw e;
        } catch (Exception e2) {
            if (!x.b(e2)) {
                e2.printStackTrace();
            }
            return null;
        }
    }

    public static byte[] b(int i, byte[] bArr, byte[] bArr2) {
        try {
            PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bArr2));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(1, generatePublic);
            return instance.doFinal(bArr);
        } catch (Exception e) {
            if (!x.b(e)) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static boolean a(Context context, String str, long j) {
        x.c("[Util] Try to lock file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            File file = new File(context.getFilesDir() + File.separator + str);
            if (file.exists()) {
                if (System.currentTimeMillis() - file.lastModified() < j) {
                    return false;
                }
                x.c("[Util] Lock file (%s) is expired, unlock it.", str);
                b(context, str);
            }
            if (file.createNewFile()) {
                x.c("[Util] Successfully locked file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                return true;
            }
            x.c("[Util] Failed to locked file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return false;
        } catch (Throwable th) {
            x.a(th);
            return false;
        }
    }

    public static boolean b(Context context, String str) {
        x.c("[Util] Try to unlock file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            File file = new File(context.getFilesDir() + File.separator + str);
            if (!file.exists()) {
                return true;
            }
            if (!file.delete()) {
                return false;
            }
            x.c("[Util] Successfully unlocked file: %s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return true;
        } catch (Throwable th) {
            x.a(th);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0070 A[SYNTHETIC, Splitter:B:37:0x0070] */
    public static String a(File file, int i, boolean z) {
        BufferedReader bufferedReader;
        String str = null;
        if (file != null && file.exists() && file.canRead()) {
            try {
                StringBuilder sb = new StringBuilder();
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        sb.append(readLine).append("\n");
                        if (i > 0 && sb.length() > i) {
                            if (z) {
                                sb.delete(i, sb.length());
                                break;
                            }
                            sb.delete(0, sb.length() - i);
                        }
                    } catch (Throwable th) {
                        th = th;
                    }
                }
                str = sb.toString();
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    x.a(e);
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e2) {
                        x.a(e2);
                    }
                }
                throw th;
            }
        }
        return str;
    }

    private static BufferedReader a(File file) {
        if (file == null || !file.exists() || !file.canRead()) {
            return null;
        }
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        } catch (Throwable th) {
            x.a(th);
            return null;
        }
    }

    public static BufferedReader a(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str, str2);
            if (!file.exists() || !file.canRead()) {
                return null;
            }
            return a(file);
        } catch (NullPointerException e) {
            x.a(e);
            return null;
        }
    }

    public static Thread a(Runnable runnable, String str) {
        try {
            Thread thread = new Thread(runnable);
            thread.setName(str);
            thread.start();
            return thread;
        } catch (Throwable th) {
            x.e("[Util] Failed to start a thread to execute task with message: %s", th.getMessage());
            return null;
        }
    }

    public static boolean a(Runnable runnable) {
        if (runnable != null) {
            w a2 = w.a();
            if (a2 != null) {
                return a2.a(runnable);
            }
            String[] split = runnable.getClass().getName().split("\\.");
            if (a(runnable, split[split.length - 1]) != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean c(String str) {
        boolean z;
        if (str == null || str.trim().length() <= 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return false;
        }
        if (str.length() > 255) {
            x.a("URL(%s)'s length is larger than 255.", str);
            return false;
        } else if (str.toLowerCase().startsWith("http")) {
            return true;
        } else {
            x.a("URL(%s) is not start with \"http\".", str);
            return false;
        }
    }

    public static SharedPreferences a(String str, Context context) {
        if (context != null) {
            return context.getSharedPreferences(str, 0);
        }
        return null;
    }

    public static String b(String str, String str2) {
        if (a.b() == null || a.b().E == null) {
            return "";
        }
        return a.b().E.getString(str, str2);
    }
}
