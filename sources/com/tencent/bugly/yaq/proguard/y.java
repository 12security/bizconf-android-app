package com.tencent.bugly.yaq.proguard;

import android.content.Context;
import android.os.Process;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: BUGLY */
public final class y {
    public static boolean a = true;
    private static SimpleDateFormat b;
    private static int c = 5120;
    private static StringBuilder d;
    /* access modifiers changed from: private */
    public static StringBuilder e;
    /* access modifiers changed from: private */
    public static boolean f;
    /* access modifiers changed from: private */
    public static a g;
    private static String h;
    private static String i;
    private static Context j;
    /* access modifiers changed from: private */
    public static String k;
    private static boolean l;
    private static boolean m = false;
    private static int n;
    /* access modifiers changed from: private */
    public static final Object o = new Object();

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
        public boolean a() {
            try {
                this.b = new File(this.c);
                if (this.b.exists() && !this.b.delete()) {
                    this.a = false;
                    return false;
                } else if (this.b.createNewFile()) {
                    return true;
                } else {
                    this.a = false;
                    return false;
                }
            } catch (Throwable th) {
                x.a(th);
                this.a = false;
                return false;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:16:0x0038 A[SYNTHETIC, Splitter:B:16:0x0038] */
        /* JADX WARNING: Removed duplicated region for block: B:22:0x0042 A[SYNTHETIC, Splitter:B:22:0x0042] */
        /* JADX WARNING: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
        public final boolean a(String str) {
            FileOutputStream fileOutputStream;
            if (!this.a) {
                return false;
            }
            try {
                fileOutputStream = new FileOutputStream(this.b, true);
                try {
                    byte[] bytes = str.getBytes("UTF-8");
                    fileOutputStream.write(bytes);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    this.d += (long) bytes.length;
                    this.a = true;
                    try {
                        fileOutputStream.close();
                    } catch (IOException e2) {
                    }
                    return true;
                } catch (Throwable th) {
                    th = th;
                    try {
                        x.a(th);
                        this.a = false;
                        if (fileOutputStream != null) {
                            return false;
                        }
                        try {
                            fileOutputStream.close();
                            return false;
                        } catch (IOException e3) {
                            return false;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileOutputStream != null) {
                        }
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e4) {
                    }
                }
                throw th;
            }
        }
    }

    static {
        b = null;
        try {
            b = new SimpleDateFormat("MM-dd HH:mm:ss");
        } catch (Throwable th) {
        }
    }

    private static boolean b(String str, String str2, String str3) {
        try {
            com.tencent.bugly.yaq.crashreport.common.info.a b2 = com.tencent.bugly.yaq.crashreport.common.info.a.b();
            if (!(b2 == null || b2.D == null)) {
                return b2.D.appendLogToNative(str, str2, str3);
            }
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
        return false;
    }

    private static String f() {
        try {
            com.tencent.bugly.yaq.crashreport.common.info.a b2 = com.tencent.bugly.yaq.crashreport.common.info.a.b();
            if (!(b2 == null || b2.D == null)) {
                return b2.D.getLogFromNative();
            }
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public static synchronized void a(Context context) {
        synchronized (y.class) {
            if (!l && context != null && a) {
                try {
                    e = new StringBuilder(0);
                    d = new StringBuilder(0);
                    j = context;
                    com.tencent.bugly.yaq.crashreport.common.info.a a2 = com.tencent.bugly.yaq.crashreport.common.info.a.a(context);
                    h = a2.d;
                    a2.getClass();
                    i = "yaq";
                    k = j.getFilesDir().getPath() + "/buglylog_" + h + "_" + i + ".txt";
                    n = Process.myPid();
                } catch (Throwable th) {
                }
                l = true;
            }
        }
    }

    public static void a(int i2) {
        synchronized (o) {
            c = i2;
            if (i2 < 0) {
                c = 0;
            } else if (i2 > 10240) {
                c = 10240;
            }
        }
    }

    public static void a(boolean z) {
        x.a("[LogUtil] Whether can record user log into native: " + z, new Object[0]);
        m = z;
    }

    public static void a(String str, String str2, Throwable th) {
        if (th != null) {
            String message = th.getMessage();
            if (message == null) {
                message = "";
            }
            a(str, str2, message + 10 + z.b(th));
        }
    }

    public static synchronized void a(String str, String str2, String str3) {
        synchronized (y.class) {
            if (l && a) {
                if (!m || !b(str, str2, str3)) {
                    long myTid = (long) Process.myTid();
                    d.setLength(0);
                    if (str3.length() > 30720) {
                        str3 = str3.substring(str3.length() - 30720, str3.length() - 1);
                    }
                    Date date = new Date();
                    d.append(b != null ? b.format(date) : date.toString()).append(" ").append(n).append(" ").append(myTid).append(" ").append(str).append(" ").append(str2).append(": ").append(str3).append("\u0001\r\n");
                    String sb = d.toString();
                    synchronized (o) {
                        e.append(sb);
                        if (e.length() > c) {
                            if (!f) {
                                f = true;
                                w.a().a(new Runnable() {
                                    public final void run() {
                                        synchronized (y.o) {
                                            try {
                                                if (y.g == null) {
                                                    y.g = new a(y.k);
                                                } else if (y.g.b == null || y.g.b.length() + ((long) y.e.length()) > y.g.e) {
                                                    y.g.a();
                                                }
                                                if (y.g.a(y.e.toString())) {
                                                    y.e.setLength(0);
                                                    y.f = false;
                                                }
                                            } catch (Throwable th) {
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }

    public static byte[] a() {
        if (!a) {
            return null;
        }
        if (m) {
            x.a("[LogUtil] Get user log from native.", new Object[0]);
            String f2 = f();
            if (f2 != null) {
                x.a("[LogUtil] Got user log from native: %d bytes", Integer.valueOf(f2.length()));
                return z.a((File) null, f2, "BuglyNativeLog.txt");
            }
        }
        StringBuilder sb = new StringBuilder();
        synchronized (o) {
            if (g != null && g.a && g.b != null && g.b.length() > 0) {
                sb.append(z.a(g.b, 30720, true));
            }
            if (e != null && e.length() > 0) {
                sb.append(e.toString());
            }
        }
        return z.a((File) null, sb.toString(), "BuglyLog.txt");
    }
}
