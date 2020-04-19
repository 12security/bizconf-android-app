package com.tencent.bugly.yaq.proguard;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tencent.bugly.yaq.CrashModule;
import com.tencent.bugly.yaq.crashreport.common.info.a;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public final class n {
    public static final long a = System.currentTimeMillis();
    private static n b = null;
    private Context c;
    /* access modifiers changed from: private */
    public String d = a.b().d;
    /* access modifiers changed from: private */
    public Map<Integer, Map<String, m>> e = new HashMap();
    /* access modifiers changed from: private */
    public SharedPreferences f;

    private n(Context context) {
        this.c = context;
        this.f = context.getSharedPreferences("crashrecord", 0);
    }

    public static synchronized n a(Context context) {
        n nVar;
        synchronized (n.class) {
            if (b == null) {
                b = new n(context);
            }
            nVar = b;
        }
        return nVar;
    }

    public static synchronized n a() {
        n nVar;
        synchronized (n.class) {
            nVar = b;
        }
        return nVar;
    }

    /* access modifiers changed from: private */
    public synchronized boolean b(int i) {
        boolean z;
        try {
            List<m> c2 = c(i);
            if (c2 == null) {
                z = false;
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (m mVar : c2) {
                    if (mVar.b != null && mVar.b.equalsIgnoreCase(this.d) && mVar.d > 0) {
                        arrayList.add(mVar);
                    }
                    if (mVar.c + 86400000 < currentTimeMillis) {
                        arrayList2.add(mVar);
                    }
                }
                Collections.sort(arrayList);
                if (arrayList.size() < 2) {
                    c2.removeAll(arrayList2);
                    a(i, (T) c2);
                    z = false;
                } else if (arrayList.size() <= 0 || ((m) arrayList.get(arrayList.size() - 1)).c + 86400000 >= currentTimeMillis) {
                    z = true;
                } else {
                    c2.clear();
                    a(i, (T) c2);
                    z = false;
                }
            }
        } catch (Exception e2) {
            x.e("isFrequentCrash failed", new Object[0]);
            z = false;
        }
        return z;
    }

    public final void a(int i, final int i2) {
        w.a().a(new Runnable(CrashModule.MODULE_ID) {
            public final void run() {
                List<m> list;
                m mVar;
                try {
                    if (!TextUtils.isEmpty(n.this.d)) {
                        List a2 = n.this.c(CrashModule.MODULE_ID);
                        if (a2 == null) {
                            list = new ArrayList<>();
                        } else {
                            list = a2;
                        }
                        if (n.this.e.get(Integer.valueOf(CrashModule.MODULE_ID)) == null) {
                            n.this.e.put(Integer.valueOf(CrashModule.MODULE_ID), new HashMap());
                        }
                        if (((Map) n.this.e.get(Integer.valueOf(CrashModule.MODULE_ID))).get(n.this.d) == null) {
                            m mVar2 = new m();
                            mVar2.a = (long) CrashModule.MODULE_ID;
                            mVar2.g = n.a;
                            mVar2.b = n.this.d;
                            mVar2.f = a.b().j;
                            a.b().getClass();
                            mVar2.e = "2.8.8";
                            mVar2.c = System.currentTimeMillis();
                            mVar2.d = i2;
                            ((Map) n.this.e.get(Integer.valueOf(CrashModule.MODULE_ID))).put(n.this.d, mVar2);
                            mVar = mVar2;
                        } else {
                            m mVar3 = (m) ((Map) n.this.e.get(Integer.valueOf(CrashModule.MODULE_ID))).get(n.this.d);
                            mVar3.d = i2;
                            mVar = mVar3;
                        }
                        ArrayList arrayList = new ArrayList();
                        boolean z = false;
                        for (m mVar4 : list) {
                            if (mVar4.g == mVar.g && mVar4.b != null && mVar4.b.equalsIgnoreCase(mVar.b)) {
                                z = true;
                                mVar4.d = mVar.d;
                            }
                            if ((mVar4.e != null && !mVar4.e.equalsIgnoreCase(mVar.e)) || ((mVar4.f != null && !mVar4.f.equalsIgnoreCase(mVar.f)) || mVar4.d <= 0)) {
                                arrayList.add(mVar4);
                            }
                        }
                        list.removeAll(arrayList);
                        if (!z) {
                            list.add(mVar);
                        }
                        n.this.a(CrashModule.MODULE_ID, list);
                    }
                } catch (Exception e) {
                    x.e("saveCrashRecord failed", new Object[0]);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0051 A[SYNTHETIC, Splitter:B:25:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0064 A[SYNTHETIC, Splitter:B:36:0x0064] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x006d A[Catch:{ Exception -> 0x003a }] */
    public synchronized <T extends List<?>> T c(int i) {
        T t;
        ObjectInputStream objectInputStream;
        Throwable th;
        ObjectInputStream objectInputStream2;
        ObjectInputStream objectInputStream3;
        try {
            File file = new File(this.c.getDir("crashrecord", 0), i);
            if (!file.exists()) {
                t = null;
            } else {
                try {
                    objectInputStream2 = new ObjectInputStream(new FileInputStream(file));
                } catch (IOException e2) {
                    objectInputStream3 = null;
                    try {
                        x.a("open record file error", new Object[0]);
                        if (objectInputStream3 != null) {
                        }
                        t = null;
                        return t;
                    } catch (Throwable th2) {
                        th = th2;
                        objectInputStream = objectInputStream3;
                        if (objectInputStream != null) {
                        }
                        throw th;
                    }
                } catch (ClassNotFoundException e3) {
                    objectInputStream2 = null;
                    try {
                        x.a("get object error", new Object[0]);
                        if (objectInputStream2 != null) {
                        }
                        t = null;
                        return t;
                    } catch (Throwable th3) {
                        th = th3;
                        objectInputStream = objectInputStream2;
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    objectInputStream = null;
                    if (objectInputStream != null) {
                    }
                    throw th;
                }
                try {
                    t = (List) objectInputStream2.readObject();
                    objectInputStream2.close();
                } catch (IOException e4) {
                    objectInputStream3 = objectInputStream2;
                    x.a("open record file error", new Object[0]);
                    if (objectInputStream3 != null) {
                        objectInputStream3.close();
                    }
                    t = null;
                    return t;
                } catch (ClassNotFoundException e5) {
                    x.a("get object error", new Object[0]);
                    if (objectInputStream2 != null) {
                        objectInputStream2.close();
                    }
                    t = null;
                    return t;
                }
            }
        } catch (Exception e6) {
            x.e("readCrashRecord error", new Object[0]);
        }
        return t;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004e A[SYNTHETIC, Splitter:B:25:0x004e] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0056 A[Catch:{ Exception -> 0x0032 }] */
    public synchronized <T extends List<?>> void a(int i, T t) {
        ObjectOutputStream objectOutputStream;
        if (t != null) {
            try {
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(this.c.getDir("crashrecord", 0), i)));
                    try {
                        objectOutputStream.writeObject(t);
                        objectOutputStream.close();
                    } catch (IOException e2) {
                        e = e2;
                        try {
                            e.printStackTrace();
                            x.a("open record file error", new Object[0]);
                            if (objectOutputStream != null) {
                                objectOutputStream.close();
                            }
                            return;
                        } catch (Throwable th) {
                            th = th;
                            if (objectOutputStream != null) {
                            }
                            throw th;
                        }
                    }
                } catch (IOException e3) {
                    e = e3;
                    objectOutputStream = null;
                    e.printStackTrace();
                    x.a("open record file error", new Object[0]);
                    if (objectOutputStream != null) {
                    }
                    return;
                } catch (Throwable th2) {
                    th = th2;
                    objectOutputStream = null;
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                x.e("writeCrashRecord error", new Object[0]);
            }
        }
        return;
    }

    public final synchronized boolean a(final int i) {
        boolean z = true;
        synchronized (this) {
            try {
                z = this.f.getBoolean(i + "_" + this.d, true);
                w.a().a(new Runnable() {
                    public final void run() {
                        n.this.f.edit().putBoolean(i + "_" + n.this.d, !n.this.b(i)).commit();
                    }
                });
            } catch (Exception e2) {
                x.e("canInit error", new Object[0]);
            }
        }
        return z;
    }
}
