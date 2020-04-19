package com.tencent.bugly.yaq.proguard;

import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class as extends j {
    private static byte[] y;
    private static Map<String, String> z = new HashMap();
    public int a = 0;
    public String b = "";
    public String c = "";
    public String d = "";
    public String e = "";
    public String f = "";
    public int g = 0;
    public byte[] h = null;
    public String i = "";
    public String j = "";
    public Map<String, String> k = null;
    public String l = "";
    public long m = 0;
    public String n = "";
    public String o = "";
    public String p = "";
    public long q = 0;
    public String r = "";
    public String s = "";
    public String t = "";
    public String u = "";
    public String v = "";
    public String w = "";
    private String x = "";

    public final void a(i iVar) {
        iVar.a(this.a, 0);
        iVar.a(this.b, 1);
        iVar.a(this.c, 2);
        iVar.a(this.d, 3);
        if (this.e != null) {
            iVar.a(this.e, 4);
        }
        iVar.a(this.f, 5);
        iVar.a(this.g, 6);
        iVar.a(this.h, 7);
        if (this.i != null) {
            iVar.a(this.i, 8);
        }
        if (this.j != null) {
            iVar.a(this.j, 9);
        }
        if (this.k != null) {
            iVar.a(this.k, 10);
        }
        if (this.l != null) {
            iVar.a(this.l, 11);
        }
        iVar.a(this.m, 12);
        if (this.n != null) {
            iVar.a(this.n, 13);
        }
        if (this.o != null) {
            iVar.a(this.o, 14);
        }
        if (this.p != null) {
            iVar.a(this.p, 15);
        }
        iVar.a(this.q, 16);
        if (this.r != null) {
            iVar.a(this.r, 17);
        }
        if (this.s != null) {
            iVar.a(this.s, 18);
        }
        if (this.t != null) {
            iVar.a(this.t, 19);
        }
        if (this.u != null) {
            iVar.a(this.u, 20);
        }
        if (this.v != null) {
            iVar.a(this.v, 21);
        }
        if (this.w != null) {
            iVar.a(this.w, 22);
        }
        if (this.x != null) {
            iVar.a(this.x, 23);
        }
    }

    static {
        byte[] bArr = new byte[1];
        y = bArr;
        bArr[0] = 0;
        z.put("", "");
    }

    public final void a(h hVar) {
        this.a = hVar.a(this.a, 0, true);
        this.b = hVar.b(1, true);
        this.c = hVar.b(2, true);
        this.d = hVar.b(3, true);
        this.e = hVar.b(4, false);
        this.f = hVar.b(5, true);
        this.g = hVar.a(this.g, 6, true);
        byte[] bArr = y;
        this.h = hVar.c(7, true);
        this.i = hVar.b(8, false);
        this.j = hVar.b(9, false);
        this.k = (Map) hVar.a(z, 10, false);
        this.l = hVar.b(11, false);
        this.m = hVar.a(this.m, 12, false);
        this.n = hVar.b(13, false);
        this.o = hVar.b(14, false);
        this.p = hVar.b(15, false);
        this.q = hVar.a(this.q, 16, false);
        this.r = hVar.b(17, false);
        this.s = hVar.b(18, false);
        this.t = hVar.b(19, false);
        this.u = hVar.b(20, false);
        this.v = hVar.b(21, false);
        this.w = hVar.b(22, false);
        this.x = hVar.b(23, false);
    }
}
