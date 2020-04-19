package com.tencent.bugly.yaq.crashreport.crash.anr;

import android.content.Context;
import android.os.FileObserver;
import com.tencent.bugly.yaq.crashreport.common.info.a;
import com.tencent.bugly.yaq.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.yaq.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.yaq.crashreport.crash.c;
import com.tencent.bugly.yaq.proguard.w;
import com.tencent.bugly.yaq.proguard.x;
import com.tencent.bugly.yaq.proguard.y;
import com.tencent.bugly.yaq.proguard.z;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: BUGLY */
public final class b {
    private AtomicInteger a = new AtomicInteger(0);
    private long b = -1;
    private final Context c;
    private final a d;
    private final w e;
    private final com.tencent.bugly.yaq.crashreport.common.strategy.a f;
    private final String g;
    private final com.tencent.bugly.yaq.crashreport.crash.b h;
    private FileObserver i;
    private boolean j = true;

    public b(Context context, com.tencent.bugly.yaq.crashreport.common.strategy.a aVar, a aVar2, w wVar, com.tencent.bugly.yaq.crashreport.crash.b bVar) {
        this.c = z.a(context);
        this.g = context.getDir("bugly", 0).getAbsolutePath();
        this.d = aVar2;
        this.e = wVar;
        this.f = aVar;
        this.h = bVar;
    }

    private CrashDetailBean a(a aVar) {
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        try {
            crashDetailBean.C = com.tencent.bugly.yaq.crashreport.common.info.b.k();
            crashDetailBean.D = com.tencent.bugly.yaq.crashreport.common.info.b.i();
            crashDetailBean.E = com.tencent.bugly.yaq.crashreport.common.info.b.m();
            crashDetailBean.F = this.d.p();
            crashDetailBean.G = this.d.o();
            crashDetailBean.H = this.d.q();
            crashDetailBean.w = z.a(this.c, c.e, (String) null);
            crashDetailBean.b = 3;
            crashDetailBean.e = this.d.h();
            crashDetailBean.f = this.d.j;
            crashDetailBean.g = this.d.w();
            crashDetailBean.m = this.d.g();
            crashDetailBean.n = "ANR_EXCEPTION";
            crashDetailBean.o = aVar.f;
            crashDetailBean.q = aVar.g;
            crashDetailBean.O = new HashMap();
            crashDetailBean.O.put("BUGLY_CR_01", aVar.e);
            int i2 = -1;
            if (crashDetailBean.q != null) {
                i2 = crashDetailBean.q.indexOf("\n");
            }
            crashDetailBean.p = i2 > 0 ? crashDetailBean.q.substring(0, i2) : "GET_FAIL";
            crashDetailBean.r = aVar.c;
            if (crashDetailBean.q != null) {
                crashDetailBean.u = z.b(crashDetailBean.q.getBytes());
            }
            crashDetailBean.z = aVar.b;
            crashDetailBean.A = aVar.a;
            crashDetailBean.B = "main(1)";
            crashDetailBean.I = this.d.y();
            crashDetailBean.h = this.d.v();
            crashDetailBean.i = this.d.J();
            crashDetailBean.v = aVar.d;
            crashDetailBean.L = this.d.n;
            crashDetailBean.M = this.d.a;
            crashDetailBean.N = this.d.a();
            crashDetailBean.P = this.d.H();
            crashDetailBean.Q = this.d.I();
            crashDetailBean.R = this.d.B();
            crashDetailBean.S = this.d.G();
            this.h.c(crashDetailBean);
            crashDetailBean.y = y.a();
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
        return crashDetailBean;
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x017d A[Catch:{ all -> 0x01e7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x01b0 A[SYNTHETIC, Splitter:B:52:0x01b0] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01d6 A[SYNTHETIC, Splitter:B:69:0x01d6] */
    private static boolean a(String str, String str2, String str3) {
        BufferedWriter bufferedWriter;
        TraceFileHelper.a readTargetDumpInfo = TraceFileHelper.readTargetDumpInfo(str3, str, true);
        if (readTargetDumpInfo == null || readTargetDumpInfo.d == null || readTargetDumpInfo.d.size() <= 0) {
            x.e("not found trace dump for %s", str3);
            return false;
        }
        File file = new File(str2);
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            if (!file.exists() || !file.canWrite()) {
                x.e("backup file create fail %s", str2);
                return false;
            }
            BufferedWriter bufferedWriter2 = null;
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(file, false));
                try {
                    String[] strArr = (String[]) readTargetDumpInfo.d.get("main");
                    if (strArr != null && strArr.length >= 3) {
                        String str4 = strArr[0];
                        bufferedWriter.write("\"main\" tid=" + strArr[2] + " :\n" + str4 + "\n" + strArr[1] + "\n\n");
                        bufferedWriter.flush();
                    }
                    for (Entry entry : readTargetDumpInfo.d.entrySet()) {
                        if (!((String) entry.getKey()).equals("main") && entry.getValue() != null && ((String[]) entry.getValue()).length >= 3) {
                            String str5 = ((String[]) entry.getValue())[0];
                            bufferedWriter.write("\"" + ((String) entry.getKey()) + "\" tid=" + ((String[]) entry.getValue())[2] + " :\n" + str5 + "\n" + ((String[]) entry.getValue())[1] + "\n\n");
                            bufferedWriter.flush();
                        }
                    }
                    try {
                        bufferedWriter.close();
                    } catch (IOException e2) {
                        if (!x.a(e2)) {
                            e2.printStackTrace();
                        }
                    }
                    return true;
                } catch (IOException e3) {
                    e = e3;
                    bufferedWriter2 = bufferedWriter;
                    try {
                        if (!x.a(e)) {
                        }
                        x.e("dump trace fail %s", e.getClass().getName() + ":" + e.getMessage());
                        if (bufferedWriter2 != null) {
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedWriter != null) {
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
                if (!x.a(e)) {
                    e.printStackTrace();
                }
                x.e("dump trace fail %s", e.getClass().getName() + ":" + e.getMessage());
                if (bufferedWriter2 != null) {
                    try {
                        bufferedWriter2.close();
                    } catch (IOException e5) {
                        if (!x.a(e5)) {
                            e5.printStackTrace();
                        }
                    }
                }
                return false;
            } catch (Throwable th3) {
                th = th3;
                bufferedWriter = null;
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e6) {
                        if (!x.a(e6)) {
                            e6.printStackTrace();
                        }
                    }
                }
                throw th;
            }
        } catch (Exception e7) {
            if (!x.a(e7)) {
                e7.printStackTrace();
            }
            x.e("backup file create error! %s  %s", e7.getClass().getName() + ":" + e7.getMessage(), str2);
            return false;
        }
    }

    public final boolean a() {
        return this.a.get() != 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0250, code lost:
        if (r5 != null) goto L_0x025b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0252, code lost:
        com.tencent.bugly.yaq.proguard.x.e("pack anr fail!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x025b, code lost:
        com.tencent.bugly.yaq.crashreport.crash.c.a().a(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0268, code lost:
        if (r5.a < 0) goto L_0x02bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x026a, code lost:
        com.tencent.bugly.yaq.proguard.x.a("backup anr record success!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0272, code lost:
        if (r13 == null) goto L_0x0297;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x027d, code lost:
        if (new java.io.File(r13).exists() == false) goto L_0x0297;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x027f, code lost:
        r12.a.set(3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x028d, code lost:
        if (a(r13, r7.d, r7.a) == false) goto L_0x0297;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        com.tencent.bugly.yaq.proguard.x.c("read trace first dump for create time!", new java.lang.Object[0]);
        r0 = -1;
        r2 = com.tencent.bugly.yaq.crashreport.crash.anr.TraceFileHelper.readFirstDumpInfo(r13, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x028f, code lost:
        com.tencent.bugly.yaq.proguard.x.a("backup trace success", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0297, code lost:
        com.tencent.bugly.yaq.crashreport.crash.b.a("ANR", com.tencent.bugly.yaq.proguard.z.a(), r7.a, "main", r7.e, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x02ac, code lost:
        if (r12.h.a(r5) != false) goto L_0x02b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x02ae, code lost:
        r12.h.a(r5, 3000, true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x02b6, code lost:
        r12.h.b(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x02bd, code lost:
        com.tencent.bugly.yaq.proguard.x.d("backup anr record fail!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x02c9, code lost:
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0029, code lost:
        if (r2 == null) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002b, code lost:
        r0 = r2.c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0031, code lost:
        if (r0 != -1) goto L_0x02c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0033, code lost:
        com.tencent.bugly.yaq.proguard.x.d("trace dump fail could not get time!", new java.lang.Object[0]);
        r4 = java.lang.System.currentTimeMillis();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004c, code lost:
        if (java.lang.Math.abs(r4 - r12.b) >= 10000) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004e, code lost:
        com.tencent.bugly.yaq.proguard.x.d("should not process ANR too Fre in %d", java.lang.Integer.valueOf(10000));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005f, code lost:
        r12.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r12.b = r4;
        r12.a.set(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        r6 = com.tencent.bugly.yaq.proguard.z.a(com.tencent.bugly.yaq.crashreport.crash.c.f, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0078, code lost:
        if (r6 == null) goto L_0x0080;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x007e, code lost:
        if (r6.size() > 0) goto L_0x00a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0080, code lost:
        com.tencent.bugly.yaq.proguard.x.d("can't get all thread skip this anr", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0088, code lost:
        r12.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x008f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        com.tencent.bugly.yaq.proguard.x.a(r0);
        com.tencent.bugly.yaq.proguard.x.e("get all thread stack fail!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009b, code lost:
        r12.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        r7 = r12.c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ab, code lost:
        if (10000 >= 0) goto L_0x0103;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ad, code lost:
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b0, code lost:
        com.tencent.bugly.yaq.proguard.x.c("to find!", new java.lang.Object[0]);
        r0 = (android.app.ActivityManager) r7.getSystemService("activity");
        r8 = r2 / 500;
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c6, code lost:
        r2 = r1;
        com.tencent.bugly.yaq.proguard.x.c("waiting!", new java.lang.Object[0]);
        r1 = r0.getProcessesInErrorState();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00d2, code lost:
        if (r1 == null) goto L_0x0107;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d4, code lost:
        r3 = r1.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00dc, code lost:
        if (r3.hasNext() == false) goto L_0x0107;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00de, code lost:
        r1 = (android.app.ActivityManager.ProcessErrorStateInfo) r3.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00e7, code lost:
        if (r1.condition != 2) goto L_0x00d8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00e9, code lost:
        com.tencent.bugly.yaq.proguard.x.c("found!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00f1, code lost:
        if (r1 != null) goto L_0x011d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00f3, code lost:
        com.tencent.bugly.yaq.proguard.x.c("proc state is unvisiable!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00fb, code lost:
        r12.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0103, code lost:
        r2 = 10000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        com.tencent.bugly.yaq.proguard.z.b(500);
        r1 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0111, code lost:
        if (((long) r2) < r8) goto L_0x02c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0113, code lost:
        com.tencent.bugly.yaq.proguard.x.c("end!", new java.lang.Object[0]);
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0123, code lost:
        if (r1.pid == android.os.Process.myPid()) goto L_0x013a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0125, code lost:
        com.tencent.bugly.yaq.proguard.x.c("not mind proc!", r1.processName);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0132, code lost:
        r12.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
        com.tencent.bugly.yaq.proguard.x.a("found visiable anr , start to process!", new java.lang.Object[0]);
        r2 = new java.io.File(r12.c.getFilesDir(), "bugly/bugly_trace_" + r4 + ".txt");
        r7 = new com.tencent.bugly.yaq.crashreport.crash.anr.a();
        r7.c = r4;
        r7.d = r2.getAbsolutePath();
        r7.a = r1.processName;
        r7.f = r1.shortMsg;
        r7.e = r1.longMsg;
        r7.b = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x017d, code lost:
        if (r6 == null) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x017f, code lost:
        r1 = r6.keySet().iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x018b, code lost:
        if (r1.hasNext() == false) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x018d, code lost:
        r0 = (java.lang.String) r1.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0199, code lost:
        if (r0.startsWith("main(") == false) goto L_0x0187;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x019b, code lost:
        r7.g = (java.lang.String) r6.get(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01a4, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01a9, code lost:
        if (com.tencent.bugly.yaq.proguard.x.a(r0) == false) goto L_0x01ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01ab, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01ae, code lost:
        com.tencent.bugly.yaq.proguard.x.e("handle anr error %s", r0.getClass().toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01c9, code lost:
        r1 = "anr tm:%d\ntr:%s\nproc:%s\nsMsg:%s\n lMsg:%s\n threads:%d";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:?, code lost:
        r2 = new java.lang.Object[6];
        r2[0] = java.lang.Long.valueOf(r7.c);
        r2[1] = r7.d;
        r2[2] = r7.a;
        r2[3] = r7.f;
        r2[4] = r7.e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01ee, code lost:
        if (r7.b != null) goto L_0x0222;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01f0, code lost:
        r0 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01f1, code lost:
        r2[5] = java.lang.Integer.valueOf(r0);
        com.tencent.bugly.yaq.proguard.x.c(r1, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0200, code lost:
        if (r12.f.b() != false) goto L_0x0229;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0202, code lost:
        com.tencent.bugly.yaq.proguard.x.e("crash report sync remote fail, will not upload to Bugly , print local for helpful!", new java.lang.Object[0]);
        com.tencent.bugly.yaq.crashreport.crash.b.a("ANR", com.tencent.bugly.yaq.proguard.z.a(), r7.a, "main", r7.e, null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x021a, code lost:
        r12.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:?, code lost:
        r0 = r7.b.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0231, code lost:
        if (r12.f.c().j != false) goto L_0x0244;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0233, code lost:
        com.tencent.bugly.yaq.proguard.x.d("ANR Report is closed!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x023c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x023d, code lost:
        r12.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0243, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:?, code lost:
        com.tencent.bugly.yaq.proguard.x.a("found visiable anr , start to upload!", new java.lang.Object[0]);
        r5 = a(r7);
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    public final void a(String str) {
        synchronized (this) {
            if (this.a.get() != 0) {
                x.c("trace started return ", new Object[0]);
                return;
            }
            this.a.set(1);
        }
    }

    private synchronized void c() {
        if (e()) {
            x.d("start when started!", new Object[0]);
        } else {
            this.i = new FileObserver("/data/anr/", 8) {
                public final void onEvent(int i, String str) {
                    if (str != null) {
                        String str2 = "/data/anr/" + str;
                        if (!str2.contains("trace")) {
                            x.d("not anr file %s", str2);
                            return;
                        }
                        b.this.a(str2);
                    }
                }
            };
            try {
                this.i.startWatching();
                x.a("start anr monitor!", new Object[0]);
                this.e.a(new Runnable() {
                    public final void run() {
                        b.this.b();
                    }
                });
            } catch (Throwable th) {
                this.i = null;
                x.d("start anr monitor failed!", new Object[0]);
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private synchronized void d() {
        if (!e()) {
            x.d("close when closed!", new Object[0]);
        } else {
            try {
                this.i.stopWatching();
                this.i = null;
                x.d("close anr monitor!", new Object[0]);
            } catch (Throwable th) {
                x.d("stop anr monitor failed!", new Object[0]);
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private synchronized boolean e() {
        return this.i != null;
    }

    private synchronized void b(boolean z) {
        if (z) {
            c();
        } else {
            d();
        }
    }

    private synchronized boolean f() {
        return this.j;
    }

    private synchronized void c(boolean z) {
        if (this.j != z) {
            x.a("user change anr %b", Boolean.valueOf(z));
            this.j = z;
        }
    }

    public final void a(boolean z) {
        c(z);
        boolean f2 = f();
        com.tencent.bugly.yaq.crashreport.common.strategy.a a2 = com.tencent.bugly.yaq.crashreport.common.strategy.a.a();
        if (a2 != null) {
            f2 = f2 && a2.c().g;
        }
        if (f2 != e()) {
            x.a("anr changed to %b", Boolean.valueOf(f2));
            b(f2);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    public final void b() {
        String name;
        long b2 = z.b() - c.g;
        File file = new File(this.g);
        if (file.exists() && file.isDirectory()) {
            try {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length != 0) {
                    String str = "bugly_trace_";
                    String str2 = ".txt";
                    int length = str.length();
                    int i2 = 0;
                    for (File file2 : listFiles) {
                        name = file2.getName();
                        if (name.startsWith(str)) {
                            int indexOf = name.indexOf(str2);
                            if (indexOf > 0 && Long.parseLong(name.substring(length, indexOf)) >= b2) {
                            }
                            if (file2.delete()) {
                                i2++;
                            }
                        }
                    }
                    x.c("Number of overdue trace files that has deleted: " + i2, new Object[0]);
                }
            } catch (Throwable th) {
                x.a(th);
            }
        }
    }

    public final synchronized void a(StrategyBean strategyBean) {
        boolean z = true;
        synchronized (this) {
            if (strategyBean != null) {
                if (strategyBean.j != e()) {
                    x.d("server anr changed to %b", Boolean.valueOf(strategyBean.j));
                }
                if (!strategyBean.j || !f()) {
                    z = false;
                }
                if (z != e()) {
                    x.a("anr changed to %b", Boolean.valueOf(z));
                    b(z);
                }
            }
        }
    }
}
