package com.tencent.bugly.yaq.crashreport.crash;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.tencent.bugly.yaq.crashreport.common.info.b;
import com.tencent.bugly.yaq.crashreport.common.strategy.a;
import com.tencent.bugly.yaq.proguard.t;
import com.tencent.bugly.yaq.proguard.w;

/* compiled from: BUGLY */
public class BuglyBroadcastRecevier extends BroadcastReceiver {
    private static BuglyBroadcastRecevier d = null;
    private IntentFilter a = new IntentFilter();
    private Context b;
    private String c;
    private boolean e = true;

    public static synchronized BuglyBroadcastRecevier getInstance() {
        BuglyBroadcastRecevier buglyBroadcastRecevier;
        synchronized (BuglyBroadcastRecevier.class) {
            if (d == null) {
                d = new BuglyBroadcastRecevier();
            }
            buglyBroadcastRecevier = d;
        }
        return buglyBroadcastRecevier;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
        if (this.b != null) {
            this.b.unregisterReceiver(this);
        }
    }

    public synchronized void addFilter(String str) {
        if (!this.a.hasAction(str)) {
            this.a.addAction(str);
        }
        w.c("add action %s", new Object[]{str});
    }

    public synchronized void regist(Context context) {
        try {
            w.a("regis BC", new Object[0]);
            this.b = context;
            context.registerReceiver(this, this.a);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return;
    }

    public synchronized void unregist(Context context) {
        try {
            w.a("unregis BC", new Object[0]);
            context.unregisterReceiver(this);
            this.b = context;
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
        }
    }

    public final void onReceive(Context context, Intent intent) {
        try {
            a(context, intent);
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
        }
    }

    private synchronized boolean a(Context context, Intent intent) {
        boolean z = true;
        synchronized (this) {
            if (!(context == null || intent == null)) {
                if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    if (this.e) {
                        this.e = false;
                    } else {
                        String e2 = b.e(this.b);
                        w.c("is Connect BC " + e2, new Object[0]);
                        w.a("network %s changed to %s", new Object[]{this.c, e2});
                        if (e2 == null) {
                            this.c = null;
                        } else {
                            String str = this.c;
                            this.c = e2;
                            long currentTimeMillis = System.currentTimeMillis();
                            a a2 = a.a();
                            t a3 = t.a();
                            com.tencent.bugly.yaq.crashreport.common.info.a a4 = com.tencent.bugly.yaq.crashreport.common.info.a.a(context);
                            if (a2 == null || a3 == null || a4 == null) {
                                w.d("not inited BC not work", new Object[0]);
                            } else if (!e2.equals(str)) {
                                if (currentTimeMillis - a3.a(c.a) > 60000) {
                                    w.a("try to upload crash on network changed.", new Object[0]);
                                    c a5 = c.a();
                                    if (a5 != null) {
                                        a5.a(0);
                                    }
                                }
                                if (currentTimeMillis - a3.a(1001) > 60000) {
                                    w.a("try to upload userinfo on network changed.", new Object[0]);
                                    com.tencent.bugly.yaq.crashreport.biz.b.a.a(false);
                                }
                            }
                        }
                    }
                }
            }
            z = false;
        }
        return z;
    }
}
