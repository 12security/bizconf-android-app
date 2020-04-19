package com.tencent.bugly.yaq.crashreport.biz;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.tencent.bugly.yaq.BuglyStrategy;
import com.tencent.bugly.yaq.crashreport.common.info.a;
import com.tencent.bugly.yaq.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.yaq.proguard.w;
import com.tencent.bugly.yaq.proguard.x;
import com.tencent.bugly.yaq.proguard.z;
import java.util.List;

/* compiled from: BUGLY */
public class b {
    public static a a;
    private static boolean b;
    /* access modifiers changed from: private */
    public static int c = 10;
    /* access modifiers changed from: private */
    public static long d = 300000;
    /* access modifiers changed from: private */
    public static long e = 30000;
    /* access modifiers changed from: private */
    public static long f = 0;
    /* access modifiers changed from: private */
    public static int g;
    /* access modifiers changed from: private */
    public static long h;
    /* access modifiers changed from: private */
    public static long i;
    /* access modifiers changed from: private */
    public static long j = 0;
    private static ActivityLifecycleCallbacks k = null;
    /* access modifiers changed from: private */
    public static Class<?> l = null;
    /* access modifiers changed from: private */
    public static boolean m = true;

    static /* synthetic */ String a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(z.a());
        sb.append("  ");
        sb.append(str);
        sb.append("  ");
        sb.append(str2);
        sb.append("\n");
        return sb.toString();
    }

    static /* synthetic */ int g() {
        int i2 = g;
        g = i2 + 1;
        return i2;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:72:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    public static void c(Context context, BuglyStrategy buglyStrategy) {
        StackTraceElement[] stackTrace;
        boolean z;
        boolean z2 = true;
        boolean z3 = false;
        if (buglyStrategy != null) {
            z3 = buglyStrategy.recordUserInfoOnceADay();
            z2 = buglyStrategy.isEnableUserInfo();
        }
        if (z3) {
            a a2 = a.a(context);
            List a3 = a.a(a2.d);
            if (a3 != null) {
                int i2 = 0;
                while (true) {
                    int i3 = i2;
                    if (i3 >= a3.size()) {
                        break;
                    }
                    UserInfoBean userInfoBean = (UserInfoBean) a3.get(i3);
                    if (userInfoBean.n.equals(a2.j) && userInfoBean.b == 1) {
                        long b2 = z.b();
                        if (b2 <= 0) {
                            break;
                        } else if (userInfoBean.e >= b2) {
                            if (userInfoBean.f <= 0) {
                                a aVar = a;
                                w a4 = w.a();
                                if (a4 != null) {
                                    a4.a(new Runnable() {
                                        public final void run() {
                                            try {
                                                a.this.c();
                                            } catch (Throwable th) {
                                                x.a(th);
                                            }
                                        }
                                    });
                                }
                            }
                            z = false;
                        }
                    }
                    i2 = i3 + 1;
                }
                if (!z) {
                    z2 = false;
                } else {
                    return;
                }
            }
            z = true;
            if (!z) {
            }
        }
        a b3 = a.b();
        if (b3 != null) {
            String str = null;
            boolean z4 = false;
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (stackTraceElement.getMethodName().equals("onCreate")) {
                    str = stackTraceElement.getClassName();
                }
                if (stackTraceElement.getClassName().equals("android.app.Activity")) {
                    z4 = true;
                }
            }
            if (str == null) {
                str = "unknown";
            } else if (z4) {
                b3.a(true);
            } else {
                str = "background";
            }
            b3.p = str;
        }
        if (z2) {
            Application application = null;
            if (VERSION.SDK_INT >= 14) {
                if (context.getApplicationContext() instanceof Application) {
                    application = (Application) context.getApplicationContext();
                }
                if (application != null) {
                    try {
                        if (k == null) {
                            k = new ActivityLifecycleCallbacks() {
                                public final void onActivityStopped(Activity activity) {
                                }

                                public final void onActivityStarted(Activity activity) {
                                }

                                public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                                }

                                public final void onActivityResumed(Activity activity) {
                                    String str = "unknown";
                                    if (activity != null) {
                                        str = activity.getClass().getName();
                                    }
                                    if (b.l == null || b.l.getName().equals(str)) {
                                        x.c(">>> %s onResumed <<<", str);
                                        a b = a.b();
                                        if (b != null) {
                                            b.C.add(b.a(str, "onResumed"));
                                            b.a(true);
                                            b.p = str;
                                            b.q = System.currentTimeMillis();
                                            b.t = b.q - b.i;
                                            long d = b.q - b.h;
                                            if (d > (b.f > 0 ? b.f : b.e)) {
                                                b.d();
                                                b.g();
                                                x.a("[session] launch app one times (app in background %d seconds and over %d seconds)", Long.valueOf(d / 1000), Long.valueOf(b.e / 1000));
                                                if (b.g % b.c == 0) {
                                                    b.a.a(4, b.m, 0);
                                                    return;
                                                }
                                                b.a.a(4, false, 0);
                                                long currentTimeMillis = System.currentTimeMillis();
                                                if (currentTimeMillis - b.j > b.d) {
                                                    b.j = currentTimeMillis;
                                                    x.a("add a timer to upload hot start user info", new Object[0]);
                                                    if (b.m) {
                                                        w.a().a(new C0000a(null, true), b.d);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                public final void onActivityPaused(Activity activity) {
                                    String str = "unknown";
                                    if (activity != null) {
                                        str = activity.getClass().getName();
                                    }
                                    if (b.l == null || b.l.getName().equals(str)) {
                                        x.c(">>> %s onPaused <<<", str);
                                        a b = a.b();
                                        if (b != null) {
                                            b.C.add(b.a(str, "onPaused"));
                                            b.a(false);
                                            b.r = System.currentTimeMillis();
                                            b.s = b.r - b.q;
                                            b.h = b.r;
                                            if (b.s < 0) {
                                                b.s = 0;
                                            }
                                            if (activity != null) {
                                                b.p = "background";
                                            } else {
                                                b.p = "unknown";
                                            }
                                        }
                                    }
                                }

                                public final void onActivityDestroyed(Activity activity) {
                                    String str = "unknown";
                                    if (activity != null) {
                                        str = activity.getClass().getName();
                                    }
                                    if (b.l == null || b.l.getName().equals(str)) {
                                        x.c(">>> %s onDestroyed <<<", str);
                                        a b = a.b();
                                        if (b != null) {
                                            b.C.add(b.a(str, "onDestroyed"));
                                        }
                                    }
                                }

                                public final void onActivityCreated(Activity activity, Bundle bundle) {
                                    String str = "unknown";
                                    if (activity != null) {
                                        str = activity.getClass().getName();
                                    }
                                    if (b.l == null || b.l.getName().equals(str)) {
                                        x.c(">>> %s onCreated <<<", str);
                                        a b = a.b();
                                        if (b != null) {
                                            b.C.add(b.a(str, "onCreated"));
                                        }
                                    }
                                }
                            };
                        }
                        application.registerActivityLifecycleCallbacks(k);
                    } catch (Exception e2) {
                        if (!x.a(e2)) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }
        if (m) {
            i = System.currentTimeMillis();
            a.a(1, false, 0);
            x.a("[session] launch app, new start", new Object[0]);
            a.a();
            w.a().a(new c(21600000), 21600000);
        }
    }

    public static void a(final Context context, final BuglyStrategy buglyStrategy) {
        long j2;
        if (!b) {
            m = a.a(context).e;
            a = new a(context, m);
            b = true;
            if (buglyStrategy != null) {
                l = buglyStrategy.getUserInfoActivity();
                j2 = buglyStrategy.getAppReportDelay();
            } else {
                j2 = 0;
            }
            if (j2 <= 0) {
                c(context, buglyStrategy);
            } else {
                w.a().a(new Runnable() {
                    public final void run() {
                        b.c(context, buglyStrategy);
                    }
                }, j2);
            }
        }
    }

    public static void a(long j2) {
        if (j2 < 0) {
            j2 = com.tencent.bugly.yaq.crashreport.common.strategy.a.a().c().q;
        }
        f = j2;
    }

    public static void a(StrategyBean strategyBean, boolean z) {
        if (a != null && !z) {
            a aVar = a;
            w a2 = w.a();
            if (a2 != null) {
                a2.a(new Runnable() {
                    public final void run() {
                        try {
                            a.this.c();
                        } catch (Throwable th) {
                            x.a(th);
                        }
                    }
                });
            }
        }
        if (strategyBean != null) {
            if (strategyBean.q > 0) {
                e = strategyBean.q;
            }
            if (strategyBean.w > 0) {
                c = strategyBean.w;
            }
            if (strategyBean.x > 0) {
                d = strategyBean.x;
            }
        }
    }

    public static void a() {
        if (a != null) {
            a.a(2, false, 0);
        }
    }

    public static void a(Context context) {
        if (b && context != null) {
            Application application = null;
            if (VERSION.SDK_INT >= 14) {
                if (context.getApplicationContext() instanceof Application) {
                    application = (Application) context.getApplicationContext();
                }
                if (application != null) {
                    try {
                        if (k != null) {
                            application.unregisterActivityLifecycleCallbacks(k);
                        }
                    } catch (Exception e2) {
                        if (!x.a(e2)) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
            b = false;
        }
    }
}
