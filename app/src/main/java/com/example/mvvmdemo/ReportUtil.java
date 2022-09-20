package com.example.mvvmdemo;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ReportUtil {
    public static final String TAG = "xianyu";
    public static void generateCoverageReport() {
        // 以下代码是获取app文件目录，这个需要root权限才可以访问。
        // String DEFAULT_COVERAGE_FILE_PATH = getFilesDir().getPath() + "/coverage.exec";
        // 以下代码是获取sd卡目录，普通权限就可以看到。
        String DEFAULT_COVERAGE_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/coverage.exec";
        Log.d(TAG,"DEFAULT_COVERAGE_FILE_PATH ==>  "+DEFAULT_COVERAGE_FILE_PATH);
        File file = new File(DEFAULT_COVERAGE_FILE_PATH);
        // 如果不存在就新建一个
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.d(TAG, "异常 : " + e);
                e.printStackTrace();
            }
        }
        Log.d(TAG, "generateCoverageReport():" + DEFAULT_COVERAGE_FILE_PATH);
        try {
            // 利用反射，执行getExecutionData方法拿到数据，写入到文件中
            OutputStream out = new FileOutputStream(DEFAULT_COVERAGE_FILE_PATH, false);
            Object agent = Class.forName("org.jacoco.agent.rt.RT")
                    .getMethod("getAgent")
                    .invoke(null);

            out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class)
                    .invoke(agent, false));
            out.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString(), e);
        }
    }

}
