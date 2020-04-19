package com.tencent.bugly.yaq.proguard;

import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class aw extends j {
    private static Map<String, String> i = new HashMap();
    public long a = 0;
    public byte b = 0;
    public String c = "";
    public String d = "";
    public String e = "";
    public Map<String, String> f = null;
    public String g = "";
    public boolean h = true;

    public final void a(i iVar) {
        iVar.a(this.a, 0);
        iVar.a(this.b, 1);
        if (this.c != null) {
            iVar.a(this.c, 2);
        }
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
    }

    static {
        i.put("", "");
    }

    public final void a(h hVar) {
        this.a = hVar.a(this.a, 0, true);
        this.b = hVar.a(this.b, 1, true);
        this.c = hVar.b(2, false);
        this.d = hVar.b(3, false);
        this.e = hVar.b(4, false);
        this.f = (Map) hVar.a(i, 5, false);
        this.g = hVar.b(6, false);
        boolean z = this.h;
        this.h = hVar.a(7, false);
    }
}
