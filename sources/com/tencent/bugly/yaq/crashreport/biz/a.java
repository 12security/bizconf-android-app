package com.tencent.bugly.yaq.crashreport.biz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import com.tencent.bugly.yaq.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.yaq.proguard.am;
import com.tencent.bugly.yaq.proguard.ar;
import com.tencent.bugly.yaq.proguard.k;
import com.tencent.bugly.yaq.proguard.o;
import com.tencent.bugly.yaq.proguard.p;
import com.tencent.bugly.yaq.proguard.t;
import com.tencent.bugly.yaq.proguard.u;
import com.tencent.bugly.yaq.proguard.w;
import com.tencent.bugly.yaq.proguard.x;
import com.tencent.bugly.yaq.proguard.z;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: BUGLY */
public final class a {
    private Context a;
    /* access modifiers changed from: private */
    public long b;
    private int c;
    private boolean d = true;

    /* renamed from: com.tencent.bugly.yaq.crashreport.biz.a$a reason: collision with other inner class name */
    /* compiled from: BUGLY */
    class C0000a implements Runnable {
        private boolean a;
        private UserInfoBean b;

        public C0000a(UserInfoBean userInfoBean, boolean z) {
            this.b = userInfoBean;
            this.a = z;
        }

        public final void run() {
            try {
                if (this.b != null) {
                    UserInfoBean userInfoBean = this.b;
                    if (userInfoBean != null) {
                        com.tencent.bugly.yaq.crashreport.common.info.a b2 = com.tencent.bugly.yaq.crashreport.common.info.a.b();
                        if (b2 != null) {
                            userInfoBean.j = b2.e();
                        }
                    }
                    x.c("[UserInfo] Record user info.", new Object[0]);
                    a.a(a.this, this.b, false);
                }
                if (this.a) {
                    a aVar = a.this;
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
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    /* compiled from: BUGLY */
    class b implements Runnable {
        b() {
        }

        public final void run() {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < a.this.b) {
                w.a().a(new b(), (a.this.b - currentTimeMillis) + 5000);
                return;
            }
            a.this.a(3, false, 0);
            a.this.a();
        }
    }

    /* compiled from: BUGLY */
    class c implements Runnable {
        private long a = 21600000;

        public c(long j) {
            this.a = j;
        }

        public final void run() {
            a aVar = a.this;
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
            a aVar2 = a.this;
            long j = this.a;
            w.a().a(new c(j), j);
        }
    }

    static /* synthetic */ void a(a aVar, UserInfoBean userInfoBean, boolean z) {
        if (userInfoBean != null) {
            if (!z && userInfoBean.b != 1) {
                List a2 = aVar.a(com.tencent.bugly.yaq.crashreport.common.info.a.a(aVar.a).d);
                if (a2 != null && a2.size() >= 20) {
                    x.a("[UserInfo] There are too many user info in local: %d", Integer.valueOf(a2.size()));
                    return;
                }
            }
            long a3 = p.a().a("t_ui", a(userInfoBean), (o) null, true);
            if (a3 >= 0) {
                x.c("[Database] insert %s success with ID: %d", "t_ui", Long.valueOf(a3));
                userInfoBean.a = a3;
            }
        }
    }

    public a(Context context, boolean z) {
        this.a = context;
        this.d = z;
    }

    public final void a(int i, boolean z, long j) {
        int i2 = 1;
        com.tencent.bugly.yaq.crashreport.common.strategy.a a2 = com.tencent.bugly.yaq.crashreport.common.strategy.a.a();
        if (a2 == null || a2.c().h || i == 1 || i == 3) {
            if (i == 1 || i == 3) {
                this.c++;
            }
            com.tencent.bugly.yaq.crashreport.common.info.a a3 = com.tencent.bugly.yaq.crashreport.common.info.a.a(this.a);
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.b = i;
            userInfoBean.c = a3.d;
            userInfoBean.d = a3.g();
            userInfoBean.e = System.currentTimeMillis();
            userInfoBean.f = -1;
            userInfoBean.n = a3.j;
            if (i != 1) {
                i2 = 0;
            }
            userInfoBean.o = i2;
            userInfoBean.l = a3.a();
            userInfoBean.m = a3.p;
            userInfoBean.g = a3.q;
            userInfoBean.h = a3.r;
            userInfoBean.i = a3.s;
            userInfoBean.k = a3.t;
            userInfoBean.r = a3.B();
            userInfoBean.s = a3.G();
            userInfoBean.p = a3.H();
            userInfoBean.q = a3.I();
            w.a().a(new C0000a(userInfoBean, z), 0);
            return;
        }
        x.e("UserInfo is disable", new Object[0]);
    }

    public final void a() {
        this.b = z.b() + 86400000;
        w.a().a(new b(), (this.b - System.currentTimeMillis()) + 5000);
    }

    /* access modifiers changed from: private */
    public synchronized void c() {
        final List arrayList;
        boolean z;
        boolean z2;
        int i;
        boolean z3 = false;
        synchronized (this) {
            if (this.d) {
                u a2 = u.a();
                if (a2 != null) {
                    com.tencent.bugly.yaq.crashreport.common.strategy.a a3 = com.tencent.bugly.yaq.crashreport.common.strategy.a.a();
                    if (a3 != null && (!a3.b() || a2.b(1001))) {
                        String str = com.tencent.bugly.yaq.crashreport.common.info.a.a(this.a).d;
                        ArrayList arrayList2 = new ArrayList();
                        List a4 = a(str);
                        if (a4 != null) {
                            int size = a4.size() - 20;
                            if (size > 0) {
                                for (int i2 = 0; i2 < a4.size() - 1; i2++) {
                                    for (int i3 = i2 + 1; i3 < a4.size(); i3++) {
                                        if (((UserInfoBean) a4.get(i2)).e > ((UserInfoBean) a4.get(i3)).e) {
                                            UserInfoBean userInfoBean = (UserInfoBean) a4.get(i2);
                                            a4.set(i2, a4.get(i3));
                                            a4.set(i3, userInfoBean);
                                        }
                                    }
                                }
                                for (int i4 = 0; i4 < size; i4++) {
                                    arrayList2.add(a4.get(i4));
                                }
                            }
                            Iterator it = a4.iterator();
                            int i5 = 0;
                            while (it.hasNext()) {
                                UserInfoBean userInfoBean2 = (UserInfoBean) it.next();
                                if (userInfoBean2.f != -1) {
                                    it.remove();
                                    if (userInfoBean2.e < z.b()) {
                                        arrayList2.add(userInfoBean2);
                                    }
                                }
                                if (userInfoBean2.e <= System.currentTimeMillis() - 600000 || !(userInfoBean2.b == 1 || userInfoBean2.b == 4 || userInfoBean2.b == 3)) {
                                    i = i5;
                                } else {
                                    i = i5 + 1;
                                }
                                i5 = i;
                            }
                            if (i5 > 15) {
                                x.d("[UserInfo] Upload user info too many times in 10 min: %d", Integer.valueOf(i5));
                                z2 = false;
                            } else {
                                z2 = true;
                            }
                            arrayList = a4;
                            z = z2;
                        } else {
                            arrayList = new ArrayList();
                            z = true;
                        }
                        if (arrayList2.size() > 0) {
                            a((List<UserInfoBean>) arrayList2);
                        }
                        if (!z || arrayList.size() == 0) {
                            x.c("[UserInfo] There is no user info in local database.", new Object[0]);
                        } else {
                            x.c("[UserInfo] Upload user info(size: %d)", Integer.valueOf(arrayList.size()));
                            ar a5 = com.tencent.bugly.yaq.proguard.a.a(arrayList, this.c == 1 ? 1 : 2);
                            if (a5 == null) {
                                x.d("[UserInfo] Failed to create UserInfoPackage.", new Object[0]);
                            } else {
                                byte[] a6 = com.tencent.bugly.yaq.proguard.a.a((k) a5);
                                if (a6 == null) {
                                    x.d("[UserInfo] Failed to encode data.", new Object[0]);
                                } else {
                                    am a7 = com.tencent.bugly.yaq.proguard.a.a(this.a, a2.a ? 840 : 640, a6);
                                    if (a7 == null) {
                                        x.d("[UserInfo] Request package is null.", new Object[0]);
                                    } else {
                                        AnonymousClass1 r5 = new t() {
                                            public final void a(boolean z) {
                                                if (z) {
                                                    x.c("[UserInfo] Successfully uploaded user info.", new Object[0]);
                                                    long currentTimeMillis = System.currentTimeMillis();
                                                    for (UserInfoBean userInfoBean : arrayList) {
                                                        userInfoBean.f = currentTimeMillis;
                                                        a.a(a.this, userInfoBean, true);
                                                    }
                                                }
                                            }
                                        };
                                        StrategyBean c2 = com.tencent.bugly.yaq.crashreport.common.strategy.a.a().c();
                                        String str2 = a2.a ? c2.r : c2.t;
                                        String str3 = a2.a ? StrategyBean.b : StrategyBean.a;
                                        u a8 = u.a();
                                        if (this.c == 1) {
                                            z3 = true;
                                        }
                                        a8.a(1001, a7, str2, str3, r5, z3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public final void b() {
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

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0050, code lost:
        r1 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0085, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0085 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:12:0x0035] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0088  */
    public final List<UserInfoBean> a(String str) {
        Cursor cursor;
        Cursor cursor2;
        try {
            cursor2 = p.a().a("t_ui", null, z.a(str) ? null : "_pc = '" + str + "'", null, null, true);
            if (cursor2 == null) {
                if (cursor2 != null) {
                    cursor2.close();
                }
                return null;
            }
            try {
                StringBuilder sb = new StringBuilder();
                ArrayList arrayList = new ArrayList();
                while (cursor2.moveToNext()) {
                    UserInfoBean a2 = a(cursor2);
                    if (a2 != null) {
                        arrayList.add(a2);
                    } else {
                        sb.append(" or _id").append(" = ").append(cursor2.getLong(cursor2.getColumnIndex("_id")));
                    }
                }
                String sb2 = sb.toString();
                if (sb2.length() > 0) {
                    x.d("[Database] deleted %s error data %d", "t_ui", Integer.valueOf(p.a().a("t_ui", sb2.substring(4), (String[]) null, (o) null, true)));
                }
                if (cursor2 != null) {
                    cursor2.close();
                }
                return arrayList;
            } catch (Throwable th) {
            }
        } catch (Throwable th2) {
            th = th2;
            cursor2 = null;
            if (cursor2 != null) {
            }
            throw th;
        }
        try {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            cursor2 = cursor;
            if (cursor2 != null) {
                cursor2.close();
            }
            throw th;
        }
    }

    private static void a(List<UserInfoBean> list) {
        if (list != null && list.size() != 0) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < list.size() && i < 50) {
                sb.append(" or _id").append(" = ").append(((UserInfoBean) list.get(i)).a);
                i++;
            }
            String sb2 = sb.toString();
            if (sb2.length() > 0) {
                sb2 = sb2.substring(4);
            }
            sb.setLength(0);
            try {
                x.c("[Database] deleted %s data %d", "t_ui", Integer.valueOf(p.a().a("t_ui", sb2, (String[]) null, (o) null, true)));
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static ContentValues a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (userInfoBean.a > 0) {
                contentValues.put("_id", Long.valueOf(userInfoBean.a));
            }
            contentValues.put("_tm", Long.valueOf(userInfoBean.e));
            contentValues.put("_ut", Long.valueOf(userInfoBean.f));
            contentValues.put("_tp", Integer.valueOf(userInfoBean.b));
            contentValues.put("_pc", userInfoBean.c);
            contentValues.put("_dt", z.a((Parcelable) userInfoBean));
            return contentValues;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private static UserInfoBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            UserInfoBean userInfoBean = (UserInfoBean) z.a(blob, UserInfoBean.CREATOR);
            if (userInfoBean == null) {
                return userInfoBean;
            }
            userInfoBean.a = j;
            return userInfoBean;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }
}
