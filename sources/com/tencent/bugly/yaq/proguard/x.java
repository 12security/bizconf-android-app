package com.tencent.bugly.yaq.proguard;

import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

/* compiled from: BUGLY */
public final class x {
    public static String a = "CrashReport";
    public static boolean b = false;
    private static String c = "CrashReportInfo";

    /* compiled from: BUGLY */
    public static class a {
        /* access modifiers changed from: private */
        public boolean a;
        /* access modifiers changed from: private */
        public File b;
        private String c;
        private long d;
        /* access modifiers changed from: private */
        public long e = 30720;

        public a(String str) {
            if (str != null && !str.equals("")) {
                this.c = str;
                this.a = a();
            }
        }

        /* access modifiers changed from: private */
        public synchronized boolean a() {
            boolean z = false;
            synchronized (this) {
                try {
                    this.b = new File(this.c);
                    if (!this.b.exists() || this.b.delete()) {
                        if (!this.b.createNewFile()) {
                            this.a = false;
                        }
                        z = true;
                    } else {
                        this.a = false;
                    }
                } catch (Throwable th) {
                    this.a = false;
                }
            }
            return z;
        }

        /* JADX WARNING: Removed duplicated region for block: B:22:0x0034 A[SYNTHETIC, Splitter:B:22:0x0034] */
        /* JADX WARNING: Removed duplicated region for block: B:28:0x003e A[SYNTHETIC, Splitter:B:28:0x003e] */
        public final synchronized boolean a(String str) {
            FileOutputStream fileOutputStream;
            FileOutputStream fileOutputStream2;
            boolean z = false;
            synchronized (this) {
                if (this.a) {
                    try {
                        fileOutputStream = new FileOutputStream(this.b, true);
                        try {
                            byte[] bytes = str.getBytes("UTF-8");
                            fileOutputStream.write(bytes);
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            this.d += (long) bytes.length;
                            try {
                                fileOutputStream.close();
                            } catch (IOException e2) {
                            }
                            z = true;
                        } catch (Throwable th) {
                            th = th;
                            if (fileOutputStream != null) {
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = null;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e3) {
                            }
                        }
                        throw th;
                    }
                }
            }
            return z;
        }
    }

    private static boolean a(int i, String str, Object... objArr) {
        if (!b) {
            return false;
        }
        if (str == null) {
            str = "null";
        } else if (!(objArr == null || objArr.length == 0)) {
            str = String.format(Locale.US, str, objArr);
        }
        switch (i) {
            case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_JAVA_CRASH /*0*/:
                Log.i(a, str);
                return true;
            case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_JAVA_CATCH /*1*/:
                Log.d(a, str);
                return true;
            case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_NATIVE /*2*/:
                Log.w(a, str);
                return true;
            case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_U3D /*3*/:
                Log.e(a, str);
                return true;
            case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_COCOS2DX_JS /*5*/:
                Log.i(c, str);
                return true;
            default:
                return false;
        }
    }

    public static boolean a(String str, Object... objArr) {
        return a(0, str, objArr);
    }

    public static boolean a(Class cls, String str, Object... objArr) {
        return a(0, String.format(Locale.US, "[%s] %s", new Object[]{cls.getSimpleName(), str}), objArr);
    }

    public static boolean b(String str, Object... objArr) {
        return a(5, str, objArr);
    }

    public static boolean c(String str, Object... objArr) {
        return a(1, str, objArr);
    }

    public static boolean b(Class cls, String str, Object... objArr) {
        return a(1, String.format(Locale.US, "[%s] %s", new Object[]{cls.getSimpleName(), str}), objArr);
    }

    public static boolean d(String str, Object... objArr) {
        return a(2, str, objArr);
    }

    public static boolean a(Throwable th) {
        if (!b) {
            return false;
        }
        return a(2, z.a(th), new Object[0]);
    }

    public static boolean e(String str, Object... objArr) {
        return a(3, str, objArr);
    }

    public static boolean b(Throwable th) {
        if (!b) {
            return false;
        }
        return a(3, z.a(th), new Object[0]);
    }
}
