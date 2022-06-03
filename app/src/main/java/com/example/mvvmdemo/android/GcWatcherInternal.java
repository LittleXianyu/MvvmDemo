package com.example.mvvmdemo.android;
import android.os.SystemClock;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * gc 监听器
 * 参考自com.android.internal.os.BinderInternal
 */
public class GcWatcherInternal {

    private static WeakReference<GcWatcher> sGcWatcher;

    private static ArrayList<Runnable> sGcWatchers = new ArrayList<>();
    private static Object lock=new Object();
    private static long sLastGcTime;

    private static final class GcWatcher {
        @Override
        protected void finalize() throws Throwable {
            sLastGcTime = SystemClock.uptimeMillis();
            ArrayList<Runnable> sTmpWatchers;
            synchronized (lock) {
                sTmpWatchers = sGcWatchers;
                try{
                    for (int i = 0; i < sTmpWatchers.size(); i++) {
                        if (sTmpWatchers.get(i) != null) {
                            sTmpWatchers.get(i).run();
                        }
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
                sGcWatcher = new WeakReference<>(new GcWatcher());
            }

        }

    }

    public static void addGcWatcher(Runnable watcher) {
        synchronized (lock) {
            sGcWatchers.add(watcher);
            if(sGcWatcher==null)
                sGcWatcher = new WeakReference<>(new GcWatcher());
        }
    }
    public static void removeGcWatcher(Runnable watcher) {
        synchronized (lock) {
            sGcWatchers.remove(watcher);
            if(sGcWatchers.isEmpty())
                sGcWatcher=null;
        }
    }
}
