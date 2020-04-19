package com.tencent.bugly.yaq.proguard;

import android.os.Handler;
import android.os.HandlerThread;

/* compiled from: BUGLY */
public interface ab {

    /* compiled from: BUGLY */
    static class a {
        private Handler a = null;

        public a(String str) {
            HandlerThread handlerThread = new HandlerThread("BuglyBlockThread_" + str);
            handlerThread.start();
            this.a = new Handler(handlerThread.getLooper());
        }

        public final Handler a() {
            return this.a;
        }
    }

    byte[] a(byte[] bArr) throws Exception;

    byte[] b(byte[] bArr) throws Exception;
}
