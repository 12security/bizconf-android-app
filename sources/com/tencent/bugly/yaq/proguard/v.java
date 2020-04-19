package com.tencent.bugly.yaq.proguard;

import android.content.Context;
import android.os.Process;
import com.tencent.bugly.yaq.BuglyStrategy;
import com.tencent.bugly.yaq.crashreport.common.info.a;
import com.tencent.bugly.yaq.crashreport.common.info.b;
import com.tencent.bugly.yaq.crashreport.common.strategy.StrategyBean;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/* compiled from: BUGLY */
public final class v implements Runnable {
    private int a;
    private int b;
    private final Context c;
    private final int d;
    private final byte[] e;
    private final a f;
    private final com.tencent.bugly.yaq.crashreport.common.strategy.a g;
    private final s h;
    private final u i;
    private final int j;
    private final t k;
    private final t l;
    private String m;
    private final String n;
    private final Map<String, String> o;
    private int p;
    private long q;
    private long r;
    private boolean s;
    private boolean t;

    public v(Context context, int i2, int i3, byte[] bArr, String str, String str2, t tVar, boolean z, boolean z2) {
        this(context, i2, i3, bArr, str, str2, tVar, z, 2, BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH, z2, null);
    }

    public v(Context context, int i2, int i3, byte[] bArr, String str, String str2, t tVar, boolean z, int i4, int i5, boolean z2, Map<String, String> map) {
        this.a = 2;
        this.b = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
        this.m = null;
        this.p = 0;
        this.q = 0;
        this.r = 0;
        this.s = true;
        this.t = false;
        this.c = context;
        this.f = a.a(context);
        this.e = bArr;
        this.g = com.tencent.bugly.yaq.crashreport.common.strategy.a.a();
        this.h = s.a(context);
        this.i = u.a();
        this.j = i2;
        this.m = str;
        this.n = str2;
        this.k = tVar;
        u uVar = this.i;
        this.l = null;
        this.s = z;
        this.d = i3;
        if (i4 > 0) {
            this.a = i4;
        }
        if (i5 > 0) {
            this.b = i5;
        }
        this.t = z2;
        this.o = map;
    }

    private void a(an anVar, boolean z, int i2, String str, int i3) {
        String str2;
        switch (this.d) {
            case 630:
            case 830:
                str2 = "crash";
                break;
            case 640:
            case 840:
                str2 = "userinfo";
                break;
            default:
                str2 = String.valueOf(this.d);
                break;
        }
        if (z) {
            x.a("[Upload] Success: %s", str2);
        } else {
            x.e("[Upload] Failed to upload(%d) %s: %s", Integer.valueOf(i2), str2, str);
            if (this.s) {
                this.i.a(i3, (an) null);
            }
        }
        if (this.q + this.r > 0) {
            this.i.a(this.i.a(this.t) + this.q + this.r, this.t);
        }
        if (this.k != null) {
            t tVar = this.k;
            int i4 = this.d;
            long j2 = this.q;
            long j3 = this.r;
            tVar.a(z);
        }
        if (this.l != null) {
            t tVar2 = this.l;
            int i5 = this.d;
            long j4 = this.q;
            long j5 = this.r;
            tVar2.a(z);
        }
    }

    private static boolean a(an anVar, a aVar, com.tencent.bugly.yaq.crashreport.common.strategy.a aVar2) {
        if (anVar == null) {
            x.d("resp == null!", new Object[0]);
            return false;
        } else if (anVar.a != 0) {
            x.e("resp result error %d", Byte.valueOf(anVar.a));
            return false;
        } else {
            try {
                if (!z.a(anVar.d) && !a.b().i().equals(anVar.d)) {
                    p.a().a(com.tencent.bugly.yaq.crashreport.common.strategy.a.a, "gateway", anVar.d.getBytes("UTF-8"), (o) null, true);
                    aVar.d(anVar.d);
                }
                if (!z.a(anVar.f) && !a.b().j().equals(anVar.f)) {
                    p.a().a(com.tencent.bugly.yaq.crashreport.common.strategy.a.a, "device", anVar.f.getBytes("UTF-8"), (o) null, true);
                    aVar.e(anVar.f);
                }
            } catch (Throwable th) {
                x.a(th);
            }
            aVar.i = anVar.e;
            if (anVar.b == 510) {
                if (anVar.c == null) {
                    x.e("[Upload] Strategy data is null. Response cmd: %d", Integer.valueOf(anVar.b));
                    return false;
                }
                ap apVar = (ap) a.a(anVar.c, ap.class);
                if (apVar == null) {
                    x.e("[Upload] Failed to decode strategy from server. Response cmd: %d", Integer.valueOf(anVar.b));
                    return false;
                }
                aVar2.a(apVar);
            }
            return true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:0x03bf, code lost:
        a(null, false, 1, "status of server is " + r5, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0330, code lost:
        if (r5 == 0) goto L_0x03d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0333, code lost:
        if (r5 != 2) goto L_0x03bf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x033e, code lost:
        if ((r11.q + r11.r) <= 0) goto L_0x0355;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0340, code lost:
        r11.i.a((r11.i.a(r11.t) + r11.q) + r11.r, r11.t);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0355, code lost:
        r11.i.a(r5, (com.tencent.bugly.yaq.proguard.an) null);
        com.tencent.bugly.yaq.proguard.x.a("[Upload] Session ID is invalid, will try again immediately (pid=%d | tid=%d).", java.lang.Integer.valueOf(android.os.Process.myPid()), java.lang.Integer.valueOf(android.os.Process.myTid()));
        r11.i.a(r11.j, r11.d, r11.e, r11.m, r11.n, r11.k, r11.a, r11.b, true, r11.o);
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    public final void run() {
        int i2;
        int i3;
        int i4;
        String str;
        int i5;
        String str2;
        byte[] a2;
        Map<String, String> map;
        byte[] bArr;
        boolean z;
        try {
            this.p = 0;
            this.q = 0;
            this.r = 0;
            byte[] bArr2 = this.e;
            if (b.c(this.c) == null) {
                a(null, false, 0, "network is not available", 0);
            } else if (bArr2 == null || bArr2.length == 0) {
                a(null, false, 0, "request package is empty!", 0);
            } else {
                long a3 = this.i.a(this.t);
                if (((long) bArr2.length) + a3 >= 2097152) {
                    x.e("[Upload] Upload too much data, try next time: %d/%d", Long.valueOf(a3), Long.valueOf(2097152));
                    a(null, false, 0, "over net consume: " + 2048 + "K", 0);
                    return;
                }
                x.c("[Upload] Run upload task with cmd: %d", Integer.valueOf(this.d));
                if (this.c == null || this.f == null || this.g == null || this.h == null) {
                    a(null, false, 0, "illegal access error", 0);
                    return;
                }
                StrategyBean c2 = this.g.c();
                if (c2 == null) {
                    a(null, false, 0, "illegal local strategy", 0);
                    return;
                }
                i2 = 0;
                HashMap hashMap = new HashMap();
                hashMap.put("prodId", this.f.f());
                hashMap.put("bundleId", this.f.c);
                hashMap.put("appVer", this.f.j);
                if (this.o != null) {
                    hashMap.putAll(this.o);
                }
                if (this.s) {
                    hashMap.put("cmd", Integer.toString(this.d));
                    hashMap.put("platformId", Byte.toString(1));
                    this.f.getClass();
                    hashMap.put("sdkVer", "2.8.8");
                    hashMap.put("strategylastUpdateTime", Long.toString(c2.p));
                    if (!this.i.a((Map<String, String>) hashMap)) {
                        a(null, false, 0, "failed to add security info to HTTP headers", 0);
                        return;
                    }
                    byte[] a4 = z.a(bArr2, 2);
                    if (a4 == null) {
                        a(null, false, 0, "failed to zip request body", 0);
                        return;
                    }
                    bArr2 = this.i.a(a4);
                    if (bArr2 == null) {
                        a(null, false, 0, "failed to encrypt request body", 0);
                        return;
                    }
                }
                byte[] bArr3 = bArr2;
                this.i.a(this.j, System.currentTimeMillis());
                if (this.k != null) {
                    t tVar = this.k;
                    int i6 = this.d;
                }
                if (this.l != null) {
                    t tVar2 = this.l;
                    int i7 = this.d;
                }
                i3 = -1;
                i4 = 0;
                str = this.m;
                while (true) {
                    i5 = i4 + 1;
                    if (i4 < this.a) {
                        if (i5 > 1) {
                            x.d("[Upload] Failed to upload last time, wait and try(%d) again.", Integer.valueOf(i5));
                            z.b((long) this.b);
                            if (i5 == this.a) {
                                x.d("[Upload] Use the back-up url at the last time: %s", this.n);
                                str = this.n;
                            }
                        }
                        x.c("[Upload] Send %d bytes", Integer.valueOf(bArr3.length));
                        if (this.s) {
                            str2 = a(str);
                        } else {
                            str2 = str;
                        }
                        x.c("[Upload] Upload to %s with cmd %d (pid=%d | tid=%d).", str2, Integer.valueOf(this.d), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                        a2 = this.h.a(str2, bArr3, this, (Map<String, String>) hashMap);
                        if (a2 != null) {
                            map = this.h.a;
                            if (this.s) {
                                if (map == null || map.size() == 0) {
                                    x.d("[Upload] Headers is empty.", new Object[0]);
                                    z = false;
                                } else if (!map.containsKey("status")) {
                                    x.d("[Upload] Headers does not contain %s", "status");
                                    z = false;
                                } else if (!map.containsKey("Bugly-Version")) {
                                    x.d("[Upload] Headers does not contain %s", "Bugly-Version");
                                    z = false;
                                } else {
                                    String str3 = (String) map.get("Bugly-Version");
                                    if (!str3.contains("bugly")) {
                                        x.d("[Upload] Bugly version is not valid: %s", str3);
                                        z = false;
                                    } else {
                                        x.c("[Upload] Bugly version from headers is: %s", str3);
                                        z = true;
                                    }
                                }
                                if (z) {
                                    i3 = Integer.parseInt((String) map.get("status"));
                                    x.c("[Upload] Status from server is %d (pid=%d | tid=%d).", Integer.valueOf(i3), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                                    break;
                                }
                                x.c("[Upload] Headers from server is not valid, just try again (pid=%d | tid=%d).", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                                x.e("[Upload] Failed to upload(%d): %s", Integer.valueOf(1), "[Upload] Failed to upload for no status header.");
                                if (map != null) {
                                    for (Entry entry : map.entrySet()) {
                                        x.c(String.format("[key]: %s, [value]: %s", new Object[]{entry.getKey(), entry.getValue()}), new Object[0]);
                                    }
                                }
                                x.c("[Upload] Failed to upload for no status header.", new Object[0]);
                                i2 = 1;
                                i4 = i5;
                                str = str2;
                            } else {
                                break;
                            }
                        } else {
                            x.e("[Upload] Failed to upload(%d): %s", Integer.valueOf(1), "Failed to upload for no response!");
                            i2 = 1;
                            i4 = i5;
                            str = str2;
                        }
                    } else {
                        a(null, false, i2, "failed after many attempts", 0);
                        return;
                    }
                }
                x.c("[Upload] Received %d bytes", Integer.valueOf(a2.length));
                if (!this.s) {
                    bArr = a2;
                } else if (a2.length == 0) {
                    for (Entry entry2 : map.entrySet()) {
                        x.c("[Upload] HTTP headers from server: key = %s, value = %s", entry2.getKey(), entry2.getValue());
                    }
                    a(null, false, 1, "response data from server is empty", 0);
                    return;
                } else {
                    byte[] b2 = this.i.b(a2);
                    if (b2 == null) {
                        a(null, false, 1, "failed to decrypt response from server", 0);
                        return;
                    }
                    bArr = z.b(b2, 2);
                    if (bArr == null) {
                        a(null, false, 1, "failed unzip(Gzip) response from server", 0);
                        return;
                    }
                }
                an a5 = a.a(bArr, this.s);
                if (a5 == null) {
                    a(null, false, 1, "failed to decode response package", 0);
                    return;
                }
                if (this.s) {
                    this.i.a(i3, a5);
                }
                String str4 = "[Upload] Response cmd is: %d, length of sBuffer is: %d";
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(a5.b);
                objArr[1] = Integer.valueOf(a5.c == null ? 0 : a5.c.length);
                x.c(str4, objArr);
                if (!a(a5, this.f, this.g)) {
                    a(a5, false, 2, "failed to process response package", 0);
                } else {
                    a(a5, true, 2, "successfully uploaded", 0);
                }
            }
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    public final void a(long j2) {
        this.p++;
        this.q += j2;
    }

    public final void b(long j2) {
        this.r += j2;
    }

    private static String a(String str) {
        if (z.a(str)) {
            return str;
        }
        try {
            return String.format("%s?aid=%s", new Object[]{str, UUID.randomUUID().toString()});
        } catch (Throwable th) {
            x.a(th);
            return str;
        }
    }
}
