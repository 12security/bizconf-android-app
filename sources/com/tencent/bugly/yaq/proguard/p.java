package com.tencent.bugly.yaq.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public final class p {
    private static p a = null;
    private static q b = null;
    private static boolean c = false;

    /* compiled from: BUGLY */
    class a extends Thread {
        private int a;
        private o b;
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

        public a(int i2, o oVar) {
            this.a = i2;
            this.b = oVar;
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
                    p.this.a(this.c, this.d, this.b);
                    return;
                case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_NATIVE /*2*/:
                    p.this.a(this.c, this.m, this.n, this.b);
                    return;
                case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_U3D /*3*/:
                    Cursor a2 = p.this.a(this.e, this.c, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.b);
                    if (a2 != null) {
                        a2.close();
                        return;
                    }
                    return;
                case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_ANR /*4*/:
                    p.this.a(this.o, this.p, this.q, this.b);
                    return;
                case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_COCOS2DX_JS /*5*/:
                    p.this.a(this.o, this.b);
                    return;
                case com.tencent.bugly.yaq.BuglyStrategy.a.CRASHTYPE_COCOS2DX_LUA /*6*/:
                    p.this.a(this.o, this.p, this.b);
                    return;
                default:
                    return;
            }
        }
    }

    private p(Context context, List<com.tencent.bugly.yaq.a> list) {
        b = new q(context, list);
    }

    public static synchronized p a(Context context, List<com.tencent.bugly.yaq.a> list) {
        p pVar;
        synchronized (p.class) {
            if (a == null) {
                a = new p(context, list);
            }
            pVar = a;
        }
        return pVar;
    }

    public static synchronized p a() {
        p pVar;
        synchronized (p.class) {
            pVar = a;
        }
        return pVar;
    }

    public final long a(String str, ContentValues contentValues, o oVar, boolean z) {
        return a(str, contentValues, (o) null);
    }

    public final Cursor a(String str, String[] strArr, String str2, String[] strArr2, o oVar, boolean z) {
        return a(false, str, strArr, str2, null, null, null, null, null, null);
    }

    public final int a(String str, String str2, String[] strArr, o oVar, boolean z) {
        return a(str, str2, (String[]) null, (o) null);
    }

    /* access modifiers changed from: private */
    public synchronized long a(String str, ContentValues contentValues, o oVar) {
        long j = 0;
        synchronized (this) {
            try {
                SQLiteDatabase writableDatabase = b.getWritableDatabase();
                if (!(writableDatabase == null || contentValues == null)) {
                    long replace = writableDatabase.replace(str, "_id", contentValues);
                    if (replace >= 0) {
                        x.c("[Database] insert %s success.", str);
                    } else {
                        x.d("[Database] replace %s error.", str);
                    }
                    j = replace;
                }
                if (oVar != null) {
                    Long.valueOf(j);
                }
            } catch (Throwable th) {
                if (oVar != null) {
                    Long.valueOf(0);
                }
                throw th;
            }
        }
        return j;
    }

    /* access modifiers changed from: private */
    public synchronized Cursor a(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, o oVar) {
        Cursor cursor;
        try {
            SQLiteDatabase writableDatabase = b.getWritableDatabase();
            if (writableDatabase != null) {
                cursor = writableDatabase.query(z, str, strArr, str2, strArr2, str3, str4, str5, str6);
            } else {
                cursor = null;
            }
            if (oVar != null) {
            }
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            if (oVar != null) {
                cursor = null;
            } else {
                cursor = null;
            }
        }
        return cursor;
    }

    /* access modifiers changed from: private */
    public synchronized int a(String str, String str2, String[] strArr, o oVar) {
        int i = 0;
        synchronized (this) {
            try {
                SQLiteDatabase writableDatabase = b.getWritableDatabase();
                if (writableDatabase != null) {
                    i = writableDatabase.delete(str, str2, strArr);
                }
                if (oVar != null) {
                    Integer.valueOf(i);
                }
            } catch (Throwable th) {
                if (oVar != null) {
                    Integer.valueOf(0);
                }
                throw th;
            }
        }
        return i;
    }

    public final boolean a(int i, String str, byte[] bArr, o oVar, boolean z) {
        if (z) {
            return a(i, str, bArr, (o) null);
        }
        a aVar = new a(4, null);
        aVar.a(i, str, bArr);
        w.a().a(aVar);
        return true;
    }

    public final Map<String, byte[]> a(int i, o oVar, boolean z) {
        return a(i, (o) null);
    }

    public final boolean a(int i, String str, o oVar, boolean z) {
        return a(555, str, (o) null);
    }

    /* access modifiers changed from: private */
    public boolean a(int i, String str, byte[] bArr, o oVar) {
        boolean z = false;
        try {
            r rVar = new r();
            rVar.a = (long) i;
            rVar.f = str;
            rVar.e = System.currentTimeMillis();
            rVar.g = bArr;
            z = b(rVar);
            if (oVar != null) {
                Boolean.valueOf(z);
            }
        } catch (Throwable th) {
            if (oVar != null) {
                Boolean.valueOf(z);
            }
            throw th;
        }
        return z;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x002e  */
    public Map<String, byte[]> a(int i, o oVar) {
        Map<String, byte[]> map = null;
        try {
            List<r> c2 = c(i);
            if (c2 != null) {
                HashMap hashMap = new HashMap();
                try {
                    for (r rVar : c2) {
                        byte[] bArr = rVar.g;
                        if (bArr != null) {
                            hashMap.put(rVar.f, bArr);
                        }
                    }
                    map = hashMap;
                } catch (Throwable th) {
                    th = th;
                    map = hashMap;
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                    if (oVar == null) {
                    }
                    return map;
                }
            }
            if (oVar != null) {
            }
        } catch (Throwable th2) {
            th = th2;
            if (!x.a(th)) {
            }
            if (oVar == null) {
            }
            return map;
        }
        return map;
    }

    public final synchronized boolean a(r rVar) {
        boolean z = false;
        synchronized (this) {
            if (rVar != null) {
                try {
                    SQLiteDatabase writableDatabase = b.getWritableDatabase();
                    if (writableDatabase != null) {
                        ContentValues c2 = c(rVar);
                        if (c2 != null) {
                            long replace = writableDatabase.replace("t_lr", "_id", c2);
                            if (replace >= 0) {
                                x.c("[Database] insert %s success.", "t_lr");
                                rVar.a = replace;
                                z = true;
                            }
                        }
                    }
                } catch (Throwable th) {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
        return z;
    }

    private synchronized boolean b(r rVar) {
        boolean z = false;
        synchronized (this) {
            if (rVar != null) {
                try {
                    SQLiteDatabase writableDatabase = b.getWritableDatabase();
                    if (writableDatabase != null) {
                        ContentValues d = d(rVar);
                        if (d != null) {
                            long replace = writableDatabase.replace("t_pf", "_id", d);
                            if (replace >= 0) {
                                x.c("[Database] insert %s success.", "t_pf");
                                rVar.a = replace;
                                z = true;
                            }
                        }
                    }
                } catch (Throwable th) {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004c, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004d, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0082, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0082 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:17:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0085 A[SYNTHETIC, Splitter:B:43:0x0085] */
    public final synchronized List<r> a(int i) {
        List<r> list;
        Cursor cursor;
        String str;
        SQLiteDatabase writableDatabase = b.getWritableDatabase();
        if (writableDatabase != null) {
            if (i >= 0) {
                try {
                    str = "_tp = " + i;
                } catch (Throwable th) {
                    th = th;
                    cursor = null;
                    if (cursor != null) {
                    }
                    throw th;
                }
            } else {
                str = null;
            }
            cursor = writableDatabase.query("t_lr", null, str, null, null, null, null);
            if (cursor == null) {
                if (cursor != null) {
                    cursor.close();
                }
                list = null;
            } else {
                try {
                    StringBuilder sb = new StringBuilder();
                    ArrayList arrayList = new ArrayList();
                    while (cursor.moveToNext()) {
                        r a2 = a(cursor);
                        if (a2 != null) {
                            arrayList.add(a2);
                        } else {
                            sb.append(" or _id").append(" = ").append(cursor.getLong(cursor.getColumnIndex("_id")));
                        }
                    }
                    String sb2 = sb.toString();
                    if (sb2.length() > 0) {
                        x.d("[Database] deleted %s illegal data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", sb2.substring(4), null)));
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    list = arrayList;
                } catch (Throwable th2) {
                }
            }
        }
        list = null;
        return list;
    }

    public final synchronized void a(List<r> list) {
        if (list != null) {
            if (list.size() != 0) {
                SQLiteDatabase writableDatabase = b.getWritableDatabase();
                if (writableDatabase != null) {
                    StringBuilder sb = new StringBuilder();
                    for (r rVar : list) {
                        sb.append(" or _id").append(" = ").append(rVar.a);
                    }
                    String sb2 = sb.toString();
                    if (sb2.length() > 0) {
                        sb2 = sb2.substring(4);
                    }
                    sb.setLength(0);
                    try {
                        x.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", sb2, null)));
                    } catch (Throwable th) {
                        if (!x.a(th)) {
                            th.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public final synchronized void b(int i) {
        String str = null;
        synchronized (this) {
            SQLiteDatabase writableDatabase = b.getWritableDatabase();
            if (writableDatabase != null) {
                if (i >= 0) {
                    try {
                        str = "_tp = " + i;
                    } catch (Throwable th) {
                        if (!x.a(th)) {
                            th.printStackTrace();
                        }
                    }
                }
                x.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", str, null)));
            }
        }
    }

    private static ContentValues c(r rVar) {
        if (rVar == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (rVar.a > 0) {
                contentValues.put("_id", Long.valueOf(rVar.a));
            }
            contentValues.put("_tp", Integer.valueOf(rVar.b));
            contentValues.put("_pc", rVar.c);
            contentValues.put("_th", rVar.d);
            contentValues.put("_tm", Long.valueOf(rVar.e));
            if (rVar.g != null) {
                contentValues.put("_dt", rVar.g);
            }
            return contentValues;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private static r a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            r rVar = new r();
            rVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            rVar.b = cursor.getInt(cursor.getColumnIndex("_tp"));
            rVar.c = cursor.getString(cursor.getColumnIndex("_pc"));
            rVar.d = cursor.getString(cursor.getColumnIndex("_th"));
            rVar.e = cursor.getLong(cursor.getColumnIndex("_tm"));
            rVar.g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return rVar;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0048, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0049, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x007e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x007e A[ExcHandler: all (th java.lang.Throwable), Splitter:B:13:0x002e] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0081 A[SYNTHETIC, Splitter:B:39:0x0081] */
    private synchronized List<r> c(int i) {
        Cursor cursor;
        List<r> list;
        try {
            SQLiteDatabase writableDatabase = b.getWritableDatabase();
            if (writableDatabase != null) {
                String str = "_id = " + i;
                cursor = writableDatabase.query("t_pf", null, str, null, null, null, null);
                if (cursor == null) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    list = null;
                } else {
                    try {
                        StringBuilder sb = new StringBuilder();
                        ArrayList arrayList = new ArrayList();
                        while (cursor.moveToNext()) {
                            r b2 = b(cursor);
                            if (b2 != null) {
                                arrayList.add(b2);
                            } else {
                                sb.append(" or _tp").append(" = ").append(cursor.getString(cursor.getColumnIndex("_tp")));
                            }
                        }
                        if (sb.length() > 0) {
                            sb.append(" and _id").append(" = ").append(i);
                            x.d("[Database] deleted %s illegal data %d.", "t_pf", Integer.valueOf(writableDatabase.delete("t_pf", str.substring(4), null)));
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                        list = arrayList;
                    } catch (Throwable th) {
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
            }
            throw th;
        }
        list = null;
        return list;
    }

    /* access modifiers changed from: private */
    public synchronized boolean a(int i, String str, o oVar) {
        String str2;
        boolean z = true;
        boolean z2 = false;
        synchronized (this) {
            try {
                SQLiteDatabase writableDatabase = b.getWritableDatabase();
                if (writableDatabase != null) {
                    if (z.a(str)) {
                        str2 = "_id = " + i;
                    } else {
                        str2 = "_id = " + i + " and _tp" + " = \"" + str + "\"";
                    }
                    int delete = writableDatabase.delete("t_pf", str2, null);
                    x.c("[Database] deleted %s data %d", "t_pf", Integer.valueOf(delete));
                    if (delete <= 0) {
                        z = false;
                    }
                    z2 = z;
                }
                if (oVar != null) {
                    Boolean.valueOf(z2);
                }
            } catch (Throwable th) {
                if (oVar != null) {
                    Boolean.valueOf(false);
                }
                throw th;
            }
        }
        return z2;
    }

    private static ContentValues d(r rVar) {
        if (rVar == null || z.a(rVar.f)) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (rVar.a > 0) {
                contentValues.put("_id", Long.valueOf(rVar.a));
            }
            contentValues.put("_tp", rVar.f);
            contentValues.put("_tm", Long.valueOf(rVar.e));
            if (rVar.g == null) {
                return contentValues;
            }
            contentValues.put("_dt", rVar.g);
            return contentValues;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static r b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            r rVar = new r();
            rVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            rVar.e = cursor.getLong(cursor.getColumnIndex("_tm"));
            rVar.f = cursor.getString(cursor.getColumnIndex("_tp"));
            rVar.g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return rVar;
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}
