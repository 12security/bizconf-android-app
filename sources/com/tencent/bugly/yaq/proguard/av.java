package com.tencent.bugly.yaq.proguard;

import com.tencent.bugly.yaq.crashreport.crash.jni.b;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class av extends j implements Cloneable {
    private static au m = new au();
    private static Map<String, String> n = new HashMap();
    private static /* synthetic */ boolean o;
    public boolean a = true;
    public boolean b = true;
    public boolean c = true;
    public String d = "";
    public String e = "";
    public au f = null;
    public Map<String, String> g = null;
    public long h = 0;
    public int i = 0;
    private String j = "";
    private String k = "";
    private int l = 0;

    static {
        boolean z;
        if (!av.class.desiredAssertionStatus()) {
            z = true;
        } else {
            z = false;
        }
        o = z;
        n.put("", "");
    }

    public final boolean equals(Object o2) {
        if (o2 == null) {
            return false;
        }
        av avVar = (av) o2;
        if (!k.a(this.a, avVar.a) || !k.a(this.b, avVar.b) || !k.a(this.c, avVar.c) || !k.a(this.d, avVar.d) || !k.a(this.e, avVar.e) || !k.a(this.f, avVar.f) || !k.a(this.g, avVar.g) || !k.a(this.h, avVar.h) || !k.a(this.j, avVar.j) || !k.a(this.k, avVar.k) || !k.a(this.l, avVar.l) || !k.a(this.i, avVar.i)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        try {
            throw new Exception("Need define key first!");
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public final Object clone() {
        boolean z = false;
        try {
            return super.clone();
        } catch (CloneNotSupportedException e2) {
            if (o) {
                return z;
            }
            throw new AssertionError();
        }
    }

    public final void a(i iVar) {
        iVar.a(this.a, 0);
        iVar.a(this.b, 1);
        iVar.a(this.c, 2);
        if (this.d != null) {
            iVar.a(this.d, 3);
        }
        if (this.e != null) {
            iVar.a(this.e, 4);
        }
        if (this.f != null) {
            iVar.a(this.f, 5);
        }
        if (this.g != null) {
            iVar.a(this.g, 6);
        }
        iVar.a(this.h, 7);
        if (this.j != null) {
            iVar.a(this.j, 8);
        }
        if (this.k != null) {
            iVar.a(this.k, 9);
        }
        iVar.a(this.l, 10);
        iVar.a(this.i, 11);
    }

    public final void a(h hVar) {
        boolean z = this.a;
        this.a = hVar.a(0, true);
        boolean z2 = this.b;
        this.b = hVar.a(1, true);
        boolean z3 = this.c;
        this.c = hVar.a(2, true);
        this.d = hVar.b(3, false);
        this.e = hVar.b(4, false);
        this.f = (au) hVar.a(m, 5, false);
        this.g = (Map) hVar.a(n, 6, false);
        this.h = hVar.a(this.h, 7, false);
        this.j = hVar.b(8, false);
        this.k = hVar.b(9, false);
        this.l = hVar.a(this.l, 10, false);
        this.i = hVar.a(this.i, 11, false);
    }

    public final void a(StringBuilder sb, int i2) {
        b bVar = new b(sb, i2);
        bVar.a(this.a, "enable");
        bVar.a(this.b, "enableUserInfo");
        bVar.a(this.c, "enableQuery");
        bVar.b(this.d, "url");
        bVar.b(this.e, "expUrl");
        bVar.a(this.f, "security");
        bVar.a(this.g, "valueMap");
        bVar.a(this.h, "strategylastUpdateTime");
        bVar.b(this.j, "httpsUrl");
        bVar.b(this.k, "httpsExpUrl");
        bVar.a(this.l, "eventRecordCount");
        bVar.a(this.i, "eventTimeInterval");
    }
}
