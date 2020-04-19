package com.tencent.bugly.yaq.crashreport.crash;

import android.content.Context;
import com.tencent.bugly.yaq.BuglyStrategy;
import com.tencent.bugly.yaq.crashreport.common.info.b;
import com.tencent.bugly.yaq.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.yaq.crashreport.common.strategy.a;
import com.tencent.bugly.yaq.proguard.w;
import com.tencent.bugly.yaq.proguard.x;
import com.tencent.bugly.yaq.proguard.y;
import com.tencent.bugly.yaq.proguard.z;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class d {
    /* access modifiers changed from: private */
    public static d a = null;
    private a b;
    private com.tencent.bugly.yaq.crashreport.common.info.a c;
    private b d;
    private Context e;

    static /* synthetic */ void a(d dVar) {
        x.c("[ExtraCrashManager] Trying to notify Bugly agents.", new Object[0]);
        try {
            Class cls = Class.forName("com.tencent.bugly.agent.GameAgent");
            String str = "com.tencent.bugly";
            dVar.c.getClass();
            String str2 = "yaq";
            if (!"".equals(str2)) {
                str = str + "." + str2;
            }
            z.a(cls, "sdkPackageName", (Object) str, (Object) null);
            x.c("[ExtraCrashManager] Bugly game agent has been notified.", new Object[0]);
        } catch (Throwable th) {
            x.a("[ExtraCrashManager] no game agent", new Object[0]);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:58:0x025c A[Catch:{ Throwable -> 0x0255, all -> 0x0268 }] */
    static /* synthetic */ void a(d dVar, Thread thread, int i, String str, String str2, String str3, Map map) {
        String str4;
        String str5;
        if (thread == null) {
            thread = Thread.currentThread();
        }
        switch (i) {
            case BuglyStrategy.a.CRASHTYPE_ANR /*4*/:
                str4 = "Unity";
                break;
            case BuglyStrategy.a.CRASHTYPE_COCOS2DX_JS /*5*/:
            case BuglyStrategy.a.CRASHTYPE_COCOS2DX_LUA /*6*/:
                str4 = "Cocos";
                break;
            case 8:
                str4 = "H5";
                break;
            default:
                x.d("[ExtraCrashManager] Unknown extra crash type: %d", Integer.valueOf(i));
                return;
        }
        x.e("[ExtraCrashManager] %s Crash Happen", str4);
        try {
            if (!dVar.b.b()) {
                x.d("[ExtraCrashManager] There is no remote strategy, but still store it.", new Object[0]);
            }
            StrategyBean c2 = dVar.b.c();
            if (c2.g || !dVar.b.b()) {
                switch (i) {
                    case BuglyStrategy.a.CRASHTYPE_COCOS2DX_JS /*5*/:
                    case BuglyStrategy.a.CRASHTYPE_COCOS2DX_LUA /*6*/:
                        if (!c2.l) {
                            x.e("[ExtraCrashManager] %s report is disabled.", str4);
                            x.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                            return;
                        }
                    case 8:
                        if (!c2.m) {
                            x.e("[ExtraCrashManager] %s report is disabled.", str4);
                            x.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                            return;
                        }
                    default:
                        if (i == 8) {
                            i = 5;
                        }
                        CrashDetailBean crashDetailBean = new CrashDetailBean();
                        crashDetailBean.C = b.k();
                        crashDetailBean.D = b.i();
                        crashDetailBean.E = b.m();
                        crashDetailBean.F = dVar.c.p();
                        crashDetailBean.G = dVar.c.o();
                        crashDetailBean.H = dVar.c.q();
                        crashDetailBean.w = z.a(dVar.e, c.e, (String) null);
                        crashDetailBean.b = i;
                        crashDetailBean.e = dVar.c.h();
                        crashDetailBean.f = dVar.c.j;
                        crashDetailBean.g = dVar.c.w();
                        crashDetailBean.m = dVar.c.g();
                        crashDetailBean.n = str;
                        crashDetailBean.o = str2;
                        String str6 = "";
                        if (str3 != null) {
                            String[] split = str3.split("\n");
                            if (split.length > 0) {
                                str6 = split[0];
                            }
                            str5 = str3;
                        } else {
                            str5 = "";
                        }
                        crashDetailBean.p = str6;
                        crashDetailBean.q = str5;
                        crashDetailBean.r = System.currentTimeMillis();
                        crashDetailBean.u = z.b(crashDetailBean.q.getBytes());
                        crashDetailBean.z = z.a(c.f, false);
                        crashDetailBean.A = dVar.c.d;
                        crashDetailBean.B = thread.getName() + "(" + thread.getId() + ")";
                        crashDetailBean.I = dVar.c.y();
                        crashDetailBean.h = dVar.c.v();
                        crashDetailBean.M = dVar.c.a;
                        crashDetailBean.N = dVar.c.a();
                        crashDetailBean.P = dVar.c.H();
                        crashDetailBean.Q = dVar.c.I();
                        crashDetailBean.R = dVar.c.B();
                        crashDetailBean.S = dVar.c.G();
                        dVar.d.c(crashDetailBean);
                        crashDetailBean.y = y.a();
                        if (crashDetailBean.O == null) {
                            crashDetailBean.O = new LinkedHashMap();
                        }
                        if (map != null) {
                            crashDetailBean.O.putAll(map);
                        }
                        if (crashDetailBean == null) {
                            x.e("[ExtraCrashManager] Failed to package crash data.", new Object[0]);
                            x.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                            return;
                        }
                        b.a(str4, z.a(), dVar.c.d, thread.getName(), str + "\n" + str2 + "\n" + str3, crashDetailBean);
                        if (!dVar.d.a(crashDetailBean)) {
                            dVar.d.a(crashDetailBean, 3000, false);
                        }
                        x.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                        return;
                }
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
            x.e("[ExtraCrashManager] Crash report was closed by remote , will not upload to Bugly , print local for helpful!", new Object[0]);
            b.a(str4, z.a(), dVar.c.d, thread.getName(), str + "\n" + str2 + "\n" + str3, null);
        } catch (Throwable th) {
            if (!x.a(th)) {
            }
        } finally {
            x.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
        }
    }

    private d(Context context) {
        c a2 = c.a();
        if (a2 != null) {
            this.b = a.a();
            this.c = com.tencent.bugly.yaq.crashreport.common.info.a.a(context);
            this.d = a2.p;
            this.e = context;
            w.a().a(new Runnable() {
                public final void run() {
                    d.a(d.this);
                }
            });
        }
    }

    public static d a(Context context) {
        if (a == null) {
            a = new d(context);
        }
        return a;
    }

    public static void a(Thread thread, int i, String str, String str2, String str3, Map<String, String> map) {
        final Thread thread2 = thread;
        final int i2 = i;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final Map<String, String> map2 = map;
        w.a().a(new Runnable() {
            public final void run() {
                try {
                    if (d.a == null) {
                        x.e("[ExtraCrashManager] Extra crash manager has not been initialized.", new Object[0]);
                    } else {
                        d.a(d.a, thread2, i2, str4, str5, str6, map2);
                    }
                } catch (Throwable th) {
                    if (!x.b(th)) {
                        th.printStackTrace();
                    }
                    x.e("[ExtraCrashManager] Crash error %s %s %s", str4, str5, str6);
                }
            }
        });
    }
}
