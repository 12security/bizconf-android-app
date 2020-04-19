package com.tencent.bugly.yaq.proguard;

import android.content.Context;
import android.os.Process;

/* compiled from: BUGLY */
public interface t {

    /* compiled from: BUGLY */
    class a implements Runnable {
        private final Context a;
        private final Runnable b;
        private final long c;

        public a(Context context) {
            this.a = context;
            this.b = null;
            this.c = 0;
        }

        public a(Context context, Runnable runnable, long j) {
            this.a = context;
            this.b = runnable;
            this.c = j;
        }

        public final void run() {
            if (!a.a(this.a, "security_info", 30000)) {
                w.c("[UploadManager] Sleep %d try to lock security file again (pid=%d | tid=%d)", new Object[]{Integer.valueOf(5000), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid())});
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (a.a(this, "BUGLY_ASYNC_UPLOAD") == null) {
                    w.d("[UploadManager] Failed to start a thread to execute task of initializing security context, try to post it into thread pool.", new Object[0]);
                    v a2 = v.a();
                    if (a2 != null) {
                        a2.a(this);
                    } else {
                        w.e("[UploadManager] Asynchronous thread pool is unavailable now, try next time.", new Object[0]);
                    }
                }
            } else {
                if (!t.c(t.this)) {
                    w.d("[UploadManager] Failed to load security info from database", new Object[0]);
                    t.this.b(false);
                }
                if (t.d(t.this) != null) {
                    if (t.this.b()) {
                        w.c("[UploadManager] Sucessfully got session ID, try to execute upload tasks now (pid=%d | tid=%d)", new Object[]{Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid())});
                        if (this.b != null) {
                            t.a(t.this, this.b, this.c);
                        }
                        t.a(t.this, 0);
                        a.b(this.a, "security_info");
                        synchronized (t.e(t.this)) {
                            t.a(t.this, false);
                        }
                        return;
                    }
                    w.a("[UploadManager] Session ID is expired, drop it.", new Object[0]);
                    t.this.b(true);
                }
                byte[] a3 = a.a(128);
                if (a3 == null || (a3.length << 3) != 128) {
                    w.d("[UploadManager] Failed to create AES key (pid=%d | tid=%d)", new Object[]{Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid())});
                    t.this.b(false);
                    a.b(this.a, "security_info");
                    synchronized (t.e(t.this)) {
                        t.a(t.this, false);
                    }
                    return;
                }
                t.a(t.this, a3);
                w.c("[UploadManager] Execute one upload task for requesting session ID (pid=%d | tid=%d)", new Object[]{Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid())});
                if (this.b != null) {
                    t.a(t.this, this.b, this.c);
                } else {
                    t.a(t.this, 1);
                }
            }
        }
    }

    void a(boolean z);
}
