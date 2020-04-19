package com.tencent.bugly.yaq.proguard;

import android.content.ContentValues;

/* compiled from: BUGLY */
public interface o {

    /* compiled from: BUGLY */
    class a extends Thread {
        private int a;
        private n b;
        private String c;
        private ContentValues d;
        private boolean e;
        private String[] f;
        private String g;
        private String[] h;
        private String i;
        private String j;
        private String k;
        private String l;
        private String m;
        private String[] n;
        private int o;
        private String p;
        private byte[] q;

        public a(int i2, n nVar) {
            this.a = i2;
            this.b = nVar;
        }

        public final void a(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
            this.e = z;
            this.c = str;
            this.f = strArr;
            this.g = str2;
            this.h = strArr2;
            this.i = str3;
            this.j = str4;
            this.k = str5;
            this.l = str6;
        }

        public final void a(int i2, String str, byte[] bArr) {
            this.o = i2;
            this.p = str;
            this.q = bArr;
        }

        public final void run() {
            switch (this.a) {
                case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_JAVA_CATCH /*1*/:
                    o.a(o.this, this.c, this.d, this.b);
                    return;
                case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_NATIVE /*2*/:
                    o.a(o.this, this.c, this.m, this.n, this.b);
                    return;
                case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_U3D /*3*/:
                    o.a(o.this, this.e, this.c, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.b);
                    return;
                case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_ANR /*4*/:
                    o.a(o.this, this.o, this.p, this.q, this.b);
                    return;
                case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_COCOS2DX_JS /*5*/:
                    o.a(o.this, this.o, this.b);
                    return;
                case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_COCOS2DX_LUA /*6*/:
                    o oVar = o.this;
                    o.a(this.o, this.p, this.b);
                    return;
                default:
                    return;
            }
        }
    }

    byte[] a();

    String b();

    boolean c();
}
