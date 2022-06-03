package com.example.mvvmdemo.fps;

import android.util.Log;
import android.view.Choreographer;

public class ChoreographerFPS {
    private static long mLastFrameTime = 0;
    private static int mFrameCount = 0;

    public static void computeFps(){
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                if (mLastFrameTime == 0) {
                    mLastFrameTime = frameTimeNanos;
                }
                float diff = (frameTimeNanos - mLastFrameTime) / 1000000.0f;//得到毫秒，正常是 16.66 ms
                if (diff > 50000) {//500 每半分钟统计上报一次
                    double fps = (((double) (mFrameCount * 1000L)) / diff);
                    mFrameCount = 0;
                    mLastFrameTime = 0;
                    Log.d("xianyu", "doFrame: " + fps);
                } else {
                    ++mFrameCount;
                }
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }
}
