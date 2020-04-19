package com.tencent.bugly.yaq.proguard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class ax extends j implements Cloneable {
    private static ArrayList<aw> f;
    private static Map<String, String> g;
    public byte a = 0;
    public String b = "";
    public String c = "";
    public ArrayList<aw> d = null;
    public Map<String, String> e = null;

    public final void a(i iVar) {
        iVar.a(this.a, 0);
        if (this.b != null) {
            iVar.a(this.b, 1);
        }
        if (this.c != null) {
            iVar.a(this.c, 2);
        }
        if (this.d != null) {
            iVar.a(this.d, 3);
        }
        if (this.e != null) {
            iVar.a(this.e, 4);
        }
    }

    public final void a(h hVar) {
        this.a = hVar.a(this.a, 0, true);
        this.b = hVar.b(1, false);
        this.c = hVar.b(2, false);
        if (f == null) {
            f = new ArrayList<>();
            f.add(new aw());
        }
        this.d = (ArrayList) hVar.a(f, 3, false);
        if (g == null) {
            g = new HashMap();
            g.put("", "");
        }
        this.e = (Map) hVar.a(g, 4, false);
    }

    public final void a(StringBuilder sb, int i) {
    }
}
