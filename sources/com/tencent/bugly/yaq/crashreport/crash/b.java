package com.tencent.bugly.yaq.crashreport.crash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import com.tencent.bugly.yaq.BuglyStrategy;
import com.tencent.bugly.yaq.crashreport.common.info.PlugInBean;
import com.tencent.bugly.yaq.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.yaq.crashreport.common.strategy.a;
import com.tencent.bugly.yaq.proguard.ah;
import com.tencent.bugly.yaq.proguard.aj;
import com.tencent.bugly.yaq.proguard.ak;
import com.tencent.bugly.yaq.proguard.al;
import com.tencent.bugly.yaq.proguard.am;
import com.tencent.bugly.yaq.proguard.k;
import com.tencent.bugly.yaq.proguard.o;
import com.tencent.bugly.yaq.proguard.p;
import com.tencent.bugly.yaq.proguard.r;
import com.tencent.bugly.yaq.proguard.t;
import com.tencent.bugly.yaq.proguard.u;
import com.tencent.bugly.yaq.proguard.x;
import com.tencent.bugly.yaq.proguard.z;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/* compiled from: BUGLY */
public final class b {
    private static int a = 0;
    private Context b;
    private u c;
    private p d;
    private a e;
    private o f;
    private BuglyStrategy.a g;

    public b(int i, Context context, u uVar, p pVar, a aVar, BuglyStrategy.a aVar2, o oVar) {
        a = i;
        this.b = context;
        this.c = uVar;
        this.d = pVar;
        this.e = aVar;
        this.g = aVar2;
        this.f = oVar;
    }

    private static List<a> a(List<a> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        for (a aVar : list) {
            if (aVar.d && aVar.b <= currentTimeMillis - 86400000) {
                arrayList.add(aVar);
            }
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00a3  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0144  */
    private CrashDetailBean a(List<a> list, CrashDetailBean crashDetailBean) {
        CrashDetailBean crashDetailBean2;
        CrashDetailBean crashDetailBean3;
        if (list == null || list.size() == 0) {
            return crashDetailBean;
        }
        CrashDetailBean crashDetailBean4 = null;
        ArrayList arrayList = new ArrayList(10);
        for (a aVar : list) {
            if (aVar.e) {
                arrayList.add(aVar);
            }
        }
        if (arrayList.size() > 0) {
            List b2 = b((List<a>) arrayList);
            if (b2 != null && b2.size() > 0) {
                Collections.sort(b2);
                int i = 0;
                while (i < b2.size()) {
                    CrashDetailBean crashDetailBean5 = (CrashDetailBean) b2.get(i);
                    if (i != 0) {
                        if (crashDetailBean5.s != null) {
                            String[] split = crashDetailBean5.s.split("\n");
                            if (split != null) {
                                int length = split.length;
                                for (int i2 = 0; i2 < length; i2++) {
                                    String str = split[i2];
                                    if (!crashDetailBean4.s.contains(str)) {
                                        crashDetailBean4.t++;
                                        crashDetailBean4.s += str + "\n";
                                    }
                                }
                            }
                        }
                        crashDetailBean5 = crashDetailBean4;
                    }
                    i++;
                    crashDetailBean4 = crashDetailBean5;
                }
                crashDetailBean2 = crashDetailBean4;
                if (crashDetailBean2 != null) {
                    crashDetailBean.j = true;
                    crashDetailBean.t = 0;
                    crashDetailBean.s = "";
                    crashDetailBean3 = crashDetailBean;
                } else {
                    crashDetailBean3 = crashDetailBean2;
                }
                for (a aVar2 : list) {
                    if (!aVar2.e && !aVar2.d && !crashDetailBean3.s.contains(aVar2.b)) {
                        crashDetailBean3.t++;
                        crashDetailBean3.s += aVar2.b + "\n";
                    }
                }
                if (crashDetailBean3.r == crashDetailBean.r && !crashDetailBean3.s.contains(crashDetailBean.r)) {
                    crashDetailBean3.t++;
                    crashDetailBean3.s += crashDetailBean.r + "\n";
                    return crashDetailBean3;
                }
            }
        }
        crashDetailBean2 = null;
        if (crashDetailBean2 != null) {
        }
        for (a aVar22 : list) {
        }
        return crashDetailBean3.r == crashDetailBean.r ? crashDetailBean3 : crashDetailBean3;
    }

    public final boolean a(CrashDetailBean crashDetailBean) {
        return a(crashDetailBean, -123456789);
    }

    public final boolean a(CrashDetailBean crashDetailBean, int i) {
        if (crashDetailBean == null) {
            return true;
        }
        if (c.n != null && !c.n.isEmpty()) {
            x.c("Crash filter for crash stack is: %s", c.n);
            if (crashDetailBean.q.contains(c.n)) {
                x.d("This crash contains the filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        if (c.o != null && !c.o.isEmpty()) {
            x.c("Crash regular filter for crash stack is: %s", c.o);
            if (Pattern.compile(c.o).matcher(crashDetailBean.q).find()) {
                x.d("This crash matches the regular filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        int i2 = crashDetailBean.b;
        String str = crashDetailBean.n;
        String str2 = crashDetailBean.o;
        String str3 = crashDetailBean.p;
        String str4 = crashDetailBean.q;
        long j = crashDetailBean.r;
        String str5 = crashDetailBean.m;
        String str6 = crashDetailBean.e;
        String str7 = crashDetailBean.c;
        String str8 = crashDetailBean.A;
        String str9 = crashDetailBean.B;
        if (this.f != null) {
            x.c("Calling 'onCrashSaving' of RQD crash listener.", new Object[0]);
            if (!this.f.c()) {
                x.d("Crash listener 'onCrashSaving' return 'false' thus will not handle this crash.", new Object[0]);
                return true;
            }
        }
        if (crashDetailBean.b != 2) {
            r rVar = new r();
            rVar.b = 1;
            rVar.c = crashDetailBean.A;
            rVar.d = crashDetailBean.B;
            rVar.e = crashDetailBean.r;
            this.d.b(1);
            this.d.a(rVar);
            x.b("[crash] a crash occur, handling...", new Object[0]);
        } else {
            x.b("[crash] a caught exception occur, handling...", new Object[0]);
        }
        List<a> b2 = b();
        List list = null;
        if (b2 != null && b2.size() > 0) {
            ArrayList arrayList = new ArrayList(10);
            ArrayList<a> arrayList2 = new ArrayList<>(10);
            arrayList.addAll(a(b2));
            b2.removeAll(arrayList);
            if (!com.tencent.bugly.yaq.b.c && c.d) {
                boolean z = false;
                for (a aVar : b2) {
                    if (crashDetailBean.u.equals(aVar.c)) {
                        if (aVar.e) {
                            z = true;
                        }
                        arrayList2.add(aVar);
                    }
                    z = z;
                }
                if (z || arrayList2.size() >= c.c) {
                    x.a("same crash occur too much do merged!", new Object[0]);
                    CrashDetailBean a2 = a((List<a>) arrayList2, crashDetailBean);
                    for (a aVar2 : arrayList2) {
                        if (aVar2.a != a2.a) {
                            arrayList.add(aVar2);
                        }
                    }
                    d(a2);
                    c((List<a>) arrayList);
                    x.b("[crash] save crash success. For this device crash many times, it will not upload crashes immediately", new Object[0]);
                    return true;
                }
            }
            list = arrayList;
        }
        d(crashDetailBean);
        if (list != null && !list.isEmpty()) {
            c(list);
        }
        x.b("[crash] save crash success", new Object[0]);
        return false;
    }

    public final List<CrashDetailBean> a() {
        StrategyBean c2 = a.a().c();
        if (c2 == null) {
            x.d("have not synced remote!", new Object[0]);
            return null;
        } else if (!c2.g) {
            x.d("Crashreport remote closed, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            x.b("[init] WARNING! Crashreport closed by server, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            return null;
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            long b2 = z.b();
            List b3 = b();
            x.c("Size of crash list loaded from DB: %s", Integer.valueOf(b3.size()));
            if (b3 == null || b3.size() <= 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(a(b3));
            b3.removeAll(arrayList);
            Iterator it = b3.iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                if (aVar.b < b2 - c.g) {
                    it.remove();
                    arrayList.add(aVar);
                } else if (aVar.d) {
                    if (aVar.b >= currentTimeMillis - 86400000) {
                        it.remove();
                    } else if (!aVar.e) {
                        it.remove();
                        arrayList.add(aVar);
                    }
                } else if (((long) aVar.f) >= 3 && aVar.b < currentTimeMillis - 86400000) {
                    it.remove();
                    arrayList.add(aVar);
                }
            }
            if (arrayList.size() > 0) {
                c((List<a>) arrayList);
            }
            ArrayList arrayList2 = new ArrayList();
            List b4 = b(b3);
            if (b4 != null && b4.size() > 0) {
                String str = com.tencent.bugly.yaq.crashreport.common.info.a.b().j;
                Iterator it2 = b4.iterator();
                while (it2.hasNext()) {
                    CrashDetailBean crashDetailBean = (CrashDetailBean) it2.next();
                    if (!str.equals(crashDetailBean.f)) {
                        it2.remove();
                        arrayList2.add(crashDetailBean);
                    }
                }
            }
            if (arrayList2.size() > 0) {
                d((List<CrashDetailBean>) arrayList2);
            }
            return b4;
        }
    }

    public final void b(CrashDetailBean crashDetailBean) {
        if (this.f != null) {
            x.c("Calling 'onCrashHandleEnd' of RQD crash listener.", new Object[0]);
            o oVar = this.f;
            int i = crashDetailBean.b;
        }
    }

    public final void a(CrashDetailBean crashDetailBean, long j, boolean z) {
        boolean z2 = false;
        if (c.l) {
            x.a("try to upload right now", new Object[0]);
            ArrayList arrayList = new ArrayList();
            arrayList.add(crashDetailBean);
            if (crashDetailBean.b == 7) {
                z2 = true;
            }
            a(arrayList, 3000, z, z2, z);
        }
    }

    public final void a(final List<CrashDetailBean> list, long j, boolean z, boolean z2, boolean z3) {
        al alVar;
        if (!com.tencent.bugly.yaq.crashreport.common.info.a.a(this.b).e || this.c == null) {
            return;
        }
        if (z3 || this.c.b(c.a)) {
            StrategyBean c2 = this.e.c();
            if (!c2.g) {
                x.d("remote report is disable!", new Object[0]);
                x.b("[crash] server closed bugly in this app. please check your appid if is correct, and re-install it", new Object[0]);
            } else if (list != null && list.size() != 0) {
                try {
                    String str = this.c.a ? c2.s : c2.t;
                    String str2 = this.c.a ? StrategyBean.c : StrategyBean.a;
                    int i = this.c.a ? 830 : 630;
                    Context context = this.b;
                    com.tencent.bugly.yaq.crashreport.common.info.a b2 = com.tencent.bugly.yaq.crashreport.common.info.a.b();
                    if (context == null || list == null || list.size() == 0 || b2 == null) {
                        x.d("enEXPPkg args == null!", new Object[0]);
                        alVar = null;
                    } else {
                        al alVar2 = new al();
                        alVar2.a = new ArrayList<>();
                        for (CrashDetailBean a2 : list) {
                            alVar2.a.add(a(context, a2, b2));
                        }
                        alVar = alVar2;
                    }
                    if (alVar == null) {
                        x.d("create eupPkg fail!", new Object[0]);
                        return;
                    }
                    byte[] a3 = com.tencent.bugly.yaq.proguard.a.a((k) alVar);
                    if (a3 == null) {
                        x.d("send encode fail!", new Object[0]);
                        return;
                    }
                    am a4 = com.tencent.bugly.yaq.proguard.a.a(this.b, i, a3);
                    if (a4 == null) {
                        x.d("request package is null.", new Object[0]);
                        return;
                    }
                    AnonymousClass1 r5 = new t() {
                        public final void a(boolean z) {
                            b bVar = b.this;
                            b.a(z, list);
                        }
                    };
                    if (z) {
                        this.c.a(a, a4, str, str2, r5, j, z2);
                    } else {
                        this.c.a(a, a4, str, str2, r5, false);
                    }
                } catch (Throwable th) {
                    x.e("req cr error %s", th.toString());
                    if (!x.b(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    public static void a(boolean z, List<CrashDetailBean> list) {
        if (list != null && list.size() > 0) {
            x.c("up finish update state %b", Boolean.valueOf(z));
            for (CrashDetailBean crashDetailBean : list) {
                x.c("pre uid:%s uc:%d re:%b me:%b", crashDetailBean.c, Integer.valueOf(crashDetailBean.l), Boolean.valueOf(crashDetailBean.d), Boolean.valueOf(crashDetailBean.j));
                crashDetailBean.l++;
                crashDetailBean.d = z;
                x.c("set uid:%s uc:%d re:%b me:%b", crashDetailBean.c, Integer.valueOf(crashDetailBean.l), Boolean.valueOf(crashDetailBean.d), Boolean.valueOf(crashDetailBean.j));
            }
            for (CrashDetailBean a2 : list) {
                c.a().a(a2);
            }
            x.c("update state size %d", Integer.valueOf(list.size()));
        }
        if (!z) {
            x.b("[crash] upload fail.", new Object[0]);
        }
    }

    public final void c(CrashDetailBean crashDetailBean) {
        int i;
        String str;
        if (crashDetailBean != null) {
            if (this.g != null || this.f != null) {
                try {
                    x.a("[crash callback] start user's callback:onCrashHandleStart()", new Object[0]);
                    switch (crashDetailBean.b) {
                        case BuglyStrategy.a.CRASHTYPE_JAVA_CRASH /*0*/:
                            i = 0;
                            break;
                        case BuglyStrategy.a.CRASHTYPE_JAVA_CATCH /*1*/:
                            i = 2;
                            break;
                        case BuglyStrategy.a.CRASHTYPE_NATIVE /*2*/:
                            i = 1;
                            break;
                        case BuglyStrategy.a.CRASHTYPE_U3D /*3*/:
                            i = 4;
                            break;
                        case BuglyStrategy.a.CRASHTYPE_ANR /*4*/:
                            i = 3;
                            break;
                        case BuglyStrategy.a.CRASHTYPE_COCOS2DX_JS /*5*/:
                            i = 5;
                            break;
                        case BuglyStrategy.a.CRASHTYPE_COCOS2DX_LUA /*6*/:
                            i = 6;
                            break;
                        case BuglyStrategy.a.CRASHTYPE_BLOCK /*7*/:
                            i = 7;
                            break;
                        default:
                            return;
                    }
                    int i2 = crashDetailBean.b;
                    String str2 = crashDetailBean.n;
                    String str3 = crashDetailBean.p;
                    String str4 = crashDetailBean.q;
                    long j = crashDetailBean.r;
                    Map map = null;
                    if (this.f != null) {
                        x.c("Calling 'onCrashHandleStart' of RQD crash listener.", new Object[0]);
                        o oVar = this.f;
                        x.c("Calling 'getCrashExtraMessage' of RQD crash listener.", new Object[0]);
                        String b2 = this.f.b();
                        if (b2 != null) {
                            map = new HashMap(1);
                            map.put("userData", b2);
                        }
                    } else if (this.g != null) {
                        x.c("Calling 'onCrashHandleStart' of Bugly crash listener.", new Object[0]);
                        map = this.g.onCrashHandleStart(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
                    }
                    if (map != null && map.size() > 0) {
                        crashDetailBean.O = new LinkedHashMap(map.size());
                        for (Entry entry : map.entrySet()) {
                            if (!z.a((String) entry.getKey())) {
                                String str5 = (String) entry.getKey();
                                if (str5.length() > 100) {
                                    str5 = str5.substring(0, 100);
                                    x.d("setted key length is over limit %d substring to %s", Integer.valueOf(100), str5);
                                }
                                String str6 = str5;
                                if (z.a((String) entry.getValue()) || ((String) entry.getValue()).length() <= 30000) {
                                    str = ((String) entry.getValue());
                                } else {
                                    str = ((String) entry.getValue()).substring(((String) entry.getValue()).length() - 30000);
                                    x.d("setted %s value length is over limit %d substring", str6, Integer.valueOf(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH));
                                }
                                crashDetailBean.O.put(str6, str);
                                x.a("add setted key %s value size:%d", str6, Integer.valueOf(str.length()));
                            }
                        }
                    }
                    x.a("[crash callback] start user's callback:onCrashHandleStart2GetExtraDatas()", new Object[0]);
                    byte[] bArr = null;
                    if (this.f != null) {
                        x.c("Calling 'getCrashExtraData' of RQD crash listener.", new Object[0]);
                        bArr = this.f.a();
                    } else if (this.g != null) {
                        x.c("Calling 'onCrashHandleStart2GetExtraDatas' of Bugly crash listener.", new Object[0]);
                        bArr = this.g.onCrashHandleStart2GetExtraDatas(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
                    }
                    crashDetailBean.T = bArr;
                    if (bArr != null) {
                        if (bArr.length > 30000) {
                            x.d("extra bytes size %d is over limit %d will drop over part", Integer.valueOf(bArr.length), Integer.valueOf(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH));
                            crashDetailBean.T = Arrays.copyOf(bArr, BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
                        }
                        x.a("add extra bytes %d ", Integer.valueOf(bArr.length));
                    }
                } catch (Throwable th) {
                    x.d("crash handle callback something wrong! %s", th.getClass().getName());
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    private static ContentValues e(CrashDetailBean crashDetailBean) {
        int i;
        int i2 = 1;
        if (crashDetailBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (crashDetailBean.a > 0) {
                contentValues.put("_id", Long.valueOf(crashDetailBean.a));
            }
            contentValues.put("_tm", Long.valueOf(crashDetailBean.r));
            contentValues.put("_s1", crashDetailBean.u);
            String str = "_up";
            if (crashDetailBean.d) {
                i = 1;
            } else {
                i = 0;
            }
            contentValues.put(str, Integer.valueOf(i));
            String str2 = "_me";
            if (!crashDetailBean.j) {
                i2 = 0;
            }
            contentValues.put(str2, Integer.valueOf(i2));
            contentValues.put("_uc", Integer.valueOf(crashDetailBean.l));
            contentValues.put("_dt", z.a((Parcelable) crashDetailBean));
            return contentValues;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private static CrashDetailBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            CrashDetailBean crashDetailBean = (CrashDetailBean) z.a(blob, CrashDetailBean.CREATOR);
            if (crashDetailBean == null) {
                return crashDetailBean;
            }
            crashDetailBean.a = j;
            return crashDetailBean;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public final void d(CrashDetailBean crashDetailBean) {
        if (crashDetailBean != null) {
            ContentValues e2 = e(crashDetailBean);
            if (e2 != null) {
                long a2 = p.a().a("t_cr", e2, (o) null, true);
                if (a2 >= 0) {
                    x.c("insert %s success!", "t_cr");
                    crashDetailBean.a = a2;
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0071, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0072, code lost:
        r1 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a7, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00aa, code lost:
        r8.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00a7 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:19:0x005c] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00aa  */
    private List<CrashDetailBean> b(List<a> list) {
        Cursor cursor;
        Cursor cursor2;
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (a aVar : list) {
            sb.append(" or _id").append(" = ").append(aVar.a);
        }
        String sb2 = sb.toString();
        if (sb2.length() > 0) {
            sb2 = sb2.substring(4);
        }
        sb.setLength(0);
        try {
            cursor2 = p.a().a("t_cr", null, sb2, null, null, true);
            if (cursor2 == null) {
                if (cursor2 != null) {
                    cursor2.close();
                }
                return null;
            }
            try {
                ArrayList arrayList = new ArrayList();
                while (cursor2.moveToNext()) {
                    CrashDetailBean a2 = a(cursor2);
                    if (a2 != null) {
                        arrayList.add(a2);
                    } else {
                        sb.append(" or _id").append(" = ").append(cursor2.getLong(cursor2.getColumnIndex("_id")));
                    }
                }
                String sb3 = sb.toString();
                if (sb3.length() > 0) {
                    x.d("deleted %s illegle data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", sb3.substring(4), (String[]) null, (o) null, true)));
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
            }
            throw th;
        }
    }

    private static a b(Cursor cursor) {
        boolean z = true;
        if (cursor == null) {
            return null;
        }
        try {
            a aVar = new a();
            aVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            aVar.b = cursor.getLong(cursor.getColumnIndex("_tm"));
            aVar.c = cursor.getString(cursor.getColumnIndex("_s1"));
            aVar.d = cursor.getInt(cursor.getColumnIndex("_up")) == 1;
            if (cursor.getInt(cursor.getColumnIndex("_me")) != 1) {
                z = false;
            }
            aVar.e = z;
            aVar.f = cursor.getInt(cursor.getColumnIndex("_uc"));
            return aVar;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0053, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0054, code lost:
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0089, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008c, code lost:
        r6.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0089 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:7:0x003e] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x008c  */
    private List<a> b() {
        Cursor cursor;
        Cursor cursor2 = null;
        ArrayList arrayList = new ArrayList();
        try {
            cursor = p.a().a("t_cr", new String[]{"_id", "_tm", "_s1", "_up", "_me", "_uc"}, null, null, null, true);
            if (cursor == null) {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
            try {
                StringBuilder sb = new StringBuilder();
                while (cursor.moveToNext()) {
                    a b2 = b(cursor);
                    if (b2 != null) {
                        arrayList.add(b2);
                    } else {
                        sb.append(" or _id").append(" = ").append(cursor.getLong(cursor.getColumnIndex("_id")));
                    }
                }
                String sb2 = sb.toString();
                if (sb2.length() > 0) {
                    x.d("deleted %s illegle data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", sb2.substring(4), (String[]) null, (o) null, true)));
                }
                if (cursor != null) {
                    cursor.close();
                }
                return arrayList;
            } catch (Throwable th) {
            }
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
            }
            throw th;
        }
        try {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            return arrayList;
        } catch (Throwable th3) {
            th = th3;
            cursor = cursor2;
            if (cursor != null) {
            }
            throw th;
        }
    }

    private static void c(List<a> list) {
        if (list != null && list.size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (a aVar : list) {
                sb.append(" or _id").append(" = ").append(aVar.a);
            }
            String sb2 = sb.toString();
            if (sb2.length() > 0) {
                sb2 = sb2.substring(4);
            }
            sb.setLength(0);
            try {
                x.c("deleted %s data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", sb2, (String[]) null, (o) null, true)));
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static void d(List<CrashDetailBean> list) {
        if (list != null) {
            try {
                if (list.size() != 0) {
                    StringBuilder sb = new StringBuilder();
                    for (CrashDetailBean crashDetailBean : list) {
                        sb.append(" or _id").append(" = ").append(crashDetailBean.a);
                    }
                    String sb2 = sb.toString();
                    if (sb2.length() > 0) {
                        sb2 = sb2.substring(4);
                    }
                    sb.setLength(0);
                    x.c("deleted %s data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", sb2, (String[]) null, (o) null, true)));
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static ak a(Context context, CrashDetailBean crashDetailBean, com.tencent.bugly.yaq.crashreport.common.info.a aVar) {
        aj ajVar;
        int i;
        boolean z = true;
        if (context == null || crashDetailBean == null || aVar == null) {
            x.d("enExp args == null", new Object[0]);
            return null;
        }
        ak akVar = new ak();
        switch (crashDetailBean.b) {
            case BuglyStrategy.a.CRASHTYPE_JAVA_CRASH /*0*/:
                akVar.a = crashDetailBean.j ? "200" : "100";
                break;
            case BuglyStrategy.a.CRASHTYPE_JAVA_CATCH /*1*/:
                akVar.a = crashDetailBean.j ? "201" : "101";
                break;
            case BuglyStrategy.a.CRASHTYPE_NATIVE /*2*/:
                akVar.a = crashDetailBean.j ? "202" : "102";
                break;
            case BuglyStrategy.a.CRASHTYPE_U3D /*3*/:
                akVar.a = crashDetailBean.j ? "203" : "103";
                break;
            case BuglyStrategy.a.CRASHTYPE_ANR /*4*/:
                akVar.a = crashDetailBean.j ? "204" : "104";
                break;
            case BuglyStrategy.a.CRASHTYPE_COCOS2DX_JS /*5*/:
                akVar.a = crashDetailBean.j ? "207" : "107";
                break;
            case BuglyStrategy.a.CRASHTYPE_COCOS2DX_LUA /*6*/:
                akVar.a = crashDetailBean.j ? "206" : "106";
                break;
            case BuglyStrategy.a.CRASHTYPE_BLOCK /*7*/:
                akVar.a = crashDetailBean.j ? "208" : "108";
                break;
            default:
                x.e("crash type error! %d", Integer.valueOf(crashDetailBean.b));
                break;
        }
        akVar.b = crashDetailBean.r;
        akVar.c = crashDetailBean.n;
        akVar.d = crashDetailBean.o;
        akVar.e = crashDetailBean.p;
        akVar.g = crashDetailBean.q;
        akVar.h = crashDetailBean.z;
        akVar.i = crashDetailBean.c;
        akVar.j = null;
        akVar.l = crashDetailBean.m;
        akVar.m = crashDetailBean.e;
        akVar.f = crashDetailBean.B;
        akVar.t = com.tencent.bugly.yaq.crashreport.common.info.a.b().i();
        akVar.n = null;
        if (crashDetailBean.i != null && crashDetailBean.i.size() > 0) {
            akVar.o = new ArrayList<>();
            for (Entry entry : crashDetailBean.i.entrySet()) {
                ah ahVar = new ah();
                ahVar.a = ((PlugInBean) entry.getValue()).a;
                ahVar.c = ((PlugInBean) entry.getValue()).c;
                ahVar.d = ((PlugInBean) entry.getValue()).b;
                ahVar.b = aVar.r();
                akVar.o.add(ahVar);
            }
        }
        if (crashDetailBean.h != null && crashDetailBean.h.size() > 0) {
            akVar.p = new ArrayList<>();
            for (Entry entry2 : crashDetailBean.h.entrySet()) {
                ah ahVar2 = new ah();
                ahVar2.a = ((PlugInBean) entry2.getValue()).a;
                ahVar2.c = ((PlugInBean) entry2.getValue()).c;
                ahVar2.d = ((PlugInBean) entry2.getValue()).b;
                akVar.p.add(ahVar2);
            }
        }
        if (crashDetailBean.j) {
            akVar.k = crashDetailBean.t;
            if (crashDetailBean.s != null && crashDetailBean.s.length() > 0) {
                if (akVar.q == null) {
                    akVar.q = new ArrayList<>();
                }
                try {
                    akVar.q.add(new aj(1, "alltimes.txt", crashDetailBean.s.getBytes("utf-8")));
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                    akVar.q = null;
                }
            }
            String str = "crashcount:%d sz:%d";
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(akVar.k);
            if (akVar.q != null) {
                i = akVar.q.size();
            } else {
                i = 0;
            }
            objArr[1] = Integer.valueOf(i);
            x.c(str, objArr);
        }
        if (crashDetailBean.w != null) {
            if (akVar.q == null) {
                akVar.q = new ArrayList<>();
            }
            try {
                akVar.q.add(new aj(1, "log.txt", crashDetailBean.w.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e3) {
                e3.printStackTrace();
                akVar.q = null;
            }
        }
        if (crashDetailBean.x != null) {
            if (akVar.q == null) {
                akVar.q = new ArrayList<>();
            }
            try {
                akVar.q.add(new aj(1, "jniLog.txt", crashDetailBean.x.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e4) {
                e4.printStackTrace();
                akVar.q = null;
            }
        }
        if (!z.a(crashDetailBean.U)) {
            if (akVar.q == null) {
                akVar.q = new ArrayList<>();
            }
            try {
                ajVar = new aj(1, "crashInfos.txt", crashDetailBean.U.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e5) {
                e5.printStackTrace();
                ajVar = null;
            }
            if (ajVar != null) {
                x.c("attach crash infos", new Object[0]);
                akVar.q.add(ajVar);
            }
        }
        if (crashDetailBean.V != null) {
            if (akVar.q == null) {
                akVar.q = new ArrayList<>();
            }
            aj a2 = a("backupRecord.zip", context, crashDetailBean.V);
            if (a2 != null) {
                x.c("attach backup record", new Object[0]);
                akVar.q.add(a2);
            }
        }
        if (crashDetailBean.y != null && crashDetailBean.y.length > 0) {
            aj ajVar2 = new aj(2, "buglylog.zip", crashDetailBean.y);
            x.c("attach user log", new Object[0]);
            if (akVar.q == null) {
                akVar.q = new ArrayList<>();
            }
            akVar.q.add(ajVar2);
        }
        if (crashDetailBean.b == 3) {
            if (akVar.q == null) {
                akVar.q = new ArrayList<>();
            }
            if (crashDetailBean.O != null && crashDetailBean.O.containsKey("BUGLY_CR_01")) {
                try {
                    akVar.q.add(new aj(1, "anrMessage.txt", ((String) crashDetailBean.O.get("BUGLY_CR_01")).getBytes("utf-8")));
                    x.c("attach anr message", new Object[0]);
                } catch (UnsupportedEncodingException e6) {
                    e6.printStackTrace();
                    akVar.q = null;
                }
                crashDetailBean.O.remove("BUGLY_CR_01");
            }
            if (crashDetailBean.v != null) {
                aj a3 = a("trace.zip", context, crashDetailBean.v);
                if (a3 != null) {
                    x.c("attach traces", new Object[0]);
                    akVar.q.add(a3);
                }
            }
        }
        if (crashDetailBean.b == 1) {
            if (akVar.q == null) {
                akVar.q = new ArrayList<>();
            }
            if (crashDetailBean.v != null) {
                aj a4 = a("tomb.zip", context, crashDetailBean.v);
                if (a4 != null) {
                    x.c("attach tombs", new Object[0]);
                    akVar.q.add(a4);
                }
            }
        }
        if (aVar.C != null && !aVar.C.isEmpty()) {
            if (akVar.q == null) {
                akVar.q = new ArrayList<>();
            }
            StringBuilder sb = new StringBuilder();
            for (String append : aVar.C) {
                sb.append(append);
            }
            try {
                akVar.q.add(new aj(1, "martianlog.txt", sb.toString().getBytes("utf-8")));
                x.c("attach pageTracingList", new Object[0]);
            } catch (UnsupportedEncodingException e7) {
                e7.printStackTrace();
            }
        }
        if (crashDetailBean.T != null && crashDetailBean.T.length > 0) {
            if (akVar.q == null) {
                akVar.q = new ArrayList<>();
            }
            akVar.q.add(new aj(1, "userExtraByteData", crashDetailBean.T));
            x.c("attach extraData", new Object[0]);
        }
        akVar.r = new HashMap();
        akVar.r.put("A9", crashDetailBean.C);
        akVar.r.put("A11", crashDetailBean.D);
        akVar.r.put("A10", crashDetailBean.E);
        akVar.r.put("A23", crashDetailBean.f);
        akVar.r.put("A7", aVar.f);
        akVar.r.put("A6", aVar.s());
        akVar.r.put("A5", aVar.r());
        akVar.r.put("A22", aVar.h());
        akVar.r.put("A2", crashDetailBean.G);
        akVar.r.put("A1", crashDetailBean.F);
        akVar.r.put("A24", aVar.h);
        akVar.r.put("A17", crashDetailBean.H);
        akVar.r.put("A3", aVar.k());
        akVar.r.put("A16", aVar.m());
        akVar.r.put("A25", aVar.n());
        akVar.r.put("A14", aVar.l());
        akVar.r.put("A15", aVar.w());
        akVar.r.put("A13", aVar.x());
        akVar.r.put("A34", crashDetailBean.A);
        if (aVar.x != null) {
            akVar.r.put("productIdentify", aVar.x);
        }
        try {
            akVar.r.put("A26", URLEncoder.encode(crashDetailBean.I, "utf-8"));
        } catch (UnsupportedEncodingException e8) {
            e8.printStackTrace();
        }
        if (crashDetailBean.b == 1) {
            akVar.r.put("A27", crashDetailBean.K);
            akVar.r.put("A28", crashDetailBean.J);
            akVar.r.put("A29", crashDetailBean.k);
        }
        akVar.r.put("A30", crashDetailBean.L);
        akVar.r.put("A18", crashDetailBean.M);
        akVar.r.put("A36", (!crashDetailBean.N));
        akVar.r.put("F02", aVar.q);
        akVar.r.put("F03", aVar.r);
        akVar.r.put("F04", aVar.e());
        akVar.r.put("F05", aVar.s);
        akVar.r.put("F06", aVar.p);
        akVar.r.put("F08", aVar.v);
        akVar.r.put("F09", aVar.w);
        akVar.r.put("F10", aVar.t);
        if (crashDetailBean.P >= 0) {
            akVar.r.put("C01", crashDetailBean.P);
        }
        if (crashDetailBean.Q >= 0) {
            akVar.r.put("C02", crashDetailBean.Q);
        }
        if (crashDetailBean.R != null && crashDetailBean.R.size() > 0) {
            for (Entry entry3 : crashDetailBean.R.entrySet()) {
                akVar.r.put("C03_" + ((String) entry3.getKey()), entry3.getValue());
            }
        }
        if (crashDetailBean.S != null && crashDetailBean.S.size() > 0) {
            for (Entry entry4 : crashDetailBean.S.entrySet()) {
                akVar.r.put("C04_" + ((String) entry4.getKey()), entry4.getValue());
            }
        }
        akVar.s = null;
        if (crashDetailBean.O != null && crashDetailBean.O.size() > 0) {
            akVar.s = crashDetailBean.O;
            x.a("setted message size %d", Integer.valueOf(akVar.s.size()));
        }
        String str2 = "%s rid:%s sess:%s ls:%ds isR:%b isF:%b isM:%b isN:%b mc:%d ,%s ,isUp:%b ,vm:%d";
        Object[] objArr2 = new Object[12];
        objArr2[0] = crashDetailBean.n;
        objArr2[1] = crashDetailBean.c;
        objArr2[2] = aVar.e();
        objArr2[3] = Long.valueOf((crashDetailBean.r - crashDetailBean.M) / 1000);
        objArr2[4] = Boolean.valueOf(crashDetailBean.k);
        objArr2[5] = Boolean.valueOf(crashDetailBean.N);
        objArr2[6] = Boolean.valueOf(crashDetailBean.j);
        if (crashDetailBean.b != 1) {
            z = false;
        }
        objArr2[7] = Boolean.valueOf(z);
        objArr2[8] = Integer.valueOf(crashDetailBean.t);
        objArr2[9] = crashDetailBean.s;
        objArr2[10] = Boolean.valueOf(crashDetailBean.d);
        objArr2[11] = Integer.valueOf(akVar.r.size());
        x.c(str2, objArr2);
        return akVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0059 A[Catch:{ all -> 0x00df }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x005e A[SYNTHETIC, Splitter:B:22:0x005e] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00c0 A[SYNTHETIC, Splitter:B:46:0x00c0] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00c9  */
    /* JADX WARNING: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    private static aj a(String str, Context context, String str2) {
        FileInputStream fileInputStream;
        if (str2 == null || context == null) {
            x.d("rqdp{  createZipAttachment sourcePath == null || context == null ,pls check}", new Object[0]);
            return null;
        }
        x.c("zip %s", str2);
        File file = new File(str2);
        File file2 = new File(context.getCacheDir(), str);
        if (!z.a(file, file2, 5000)) {
            x.d("zip fail!", new Object[0]);
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            fileInputStream = new FileInputStream(file2);
            try {
                byte[] bArr = new byte[4096];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                    byteArrayOutputStream.flush();
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                x.c("read bytes :%d", Integer.valueOf(byteArray.length));
                aj ajVar = new aj(2, file2.getName(), byteArray);
                try {
                    fileInputStream.close();
                } catch (IOException e2) {
                    if (!x.a(e2)) {
                        e2.printStackTrace();
                    }
                }
                if (file2.exists()) {
                    x.c("del tmp", new Object[0]);
                    file2.delete();
                }
                return ajVar;
            } catch (Throwable th) {
                th = th;
                try {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e3) {
                            if (!x.a(e3)) {
                                e3.printStackTrace();
                            }
                        }
                    }
                    if (file2.exists()) {
                        return null;
                    }
                    x.c("del tmp", new Object[0]);
                    file2.delete();
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (fileInputStream != null) {
                    }
                    if (file2.exists()) {
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e4) {
                    if (!x.a(e4)) {
                        e4.printStackTrace();
                    }
                }
            }
            if (file2.exists()) {
                x.c("del tmp", new Object[0]);
                file2.delete();
            }
            throw th;
        }
    }

    public static void a(String str, String str2, String str3, String str4, String str5, CrashDetailBean crashDetailBean) {
        com.tencent.bugly.yaq.crashreport.common.info.a b2 = com.tencent.bugly.yaq.crashreport.common.info.a.b();
        if (b2 != null) {
            x.e("#++++++++++Record By Bugly++++++++++#", new Object[0]);
            x.e("# You can use Bugly(http:\\\\bugly.qq.com) to get more Crash Detail!", new Object[0]);
            x.e("# PKG NAME: %s", b2.c);
            x.e("# APP VER: %s", b2.j);
            x.e("# LAUNCH TIME: %s", z.a(new Date(com.tencent.bugly.yaq.crashreport.common.info.a.b().a)));
            x.e("# CRASH TYPE: %s", str);
            x.e("# CRASH TIME: %s", str2);
            x.e("# CRASH PROCESS: %s", str3);
            x.e("# CRASH THREAD: %s", str4);
            if (crashDetailBean != null) {
                x.e("# REPORT ID: %s", crashDetailBean.c);
                String str6 = "# CRASH DEVICE: %s %s";
                Object[] objArr = new Object[2];
                objArr[0] = b2.g;
                objArr[1] = b2.x().booleanValue() ? "ROOTED" : "UNROOT";
                x.e(str6, objArr);
                x.e("# RUNTIME AVAIL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.C), Long.valueOf(crashDetailBean.D), Long.valueOf(crashDetailBean.E));
                x.e("# RUNTIME TOTAL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.F), Long.valueOf(crashDetailBean.G), Long.valueOf(crashDetailBean.H));
                if (!z.a(crashDetailBean.K)) {
                    x.e("# EXCEPTION FIRED BY %s %s", crashDetailBean.K, crashDetailBean.J);
                } else if (crashDetailBean.b == 3) {
                    String str7 = "# EXCEPTION ANR MESSAGE:\n %s";
                    Object[] objArr2 = new Object[1];
                    objArr2[0] = crashDetailBean.O == null ? "null" : ((String) crashDetailBean.O.get("BUGLY_CR_01"));
                    x.e(str7, objArr2);
                }
            }
            if (!z.a(str5)) {
                x.e("# CRASH STACK: ", new Object[0]);
                x.e(str5, new Object[0]);
            }
            x.e("#++++++++++++++++++++++++++++++++++++++++++#", new Object[0]);
        }
    }
}
