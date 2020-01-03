package com.song.check.hook;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.song.check.utils.LogUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HookCheck {

    /**
     * 检查包名是否存在
     *
     * @param context
     * @return
     */
    public static boolean packageCheck(Context context) {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        List<ApplicationInfo> appliacationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        if (appliacationInfoList == null) {
            return false;
        }
        for (ApplicationInfo item : appliacationInfoList) {
            if ("de.robv.android.xposed.installer".equals(item.packageName)) {
                LogUtils.i("Xposeded fonund on device");
                return true;
            }
            if ("com.saurik.substrate".equals(item.packageName)) {
                LogUtils.i("CydiaSubstrate fonund on device");
                return true;
            }
        }
        return false;
    }

    /**
     * 检测调用栈中的可疑方法
     */
    public static boolean exceptionCheck() {
        try {
            throw new Exception("Deteck hook");
        } catch (Exception e) {
            int zygoteInitCallCount = 0;
            for (StackTraceElement item : e.getStackTrace()) {
                // 检测"com.android.internal.os.ZygoteInit"是否出现两次，如果出现两次，则表明Substrate框架已经安装
                if ("com.android.internal.os.ZygoteInit".equals(item.getClassName())) {
                    zygoteInitCallCount++;
                    if (zygoteInitCallCount == 2) {
                        LogUtils.i("Substrate is active on the device.");
                        return true;
                    }
                }
                if ("com.saurik.substrate.MS$2".equals(item.getClassName()) && "invoke".equals(item.getMethodName())) {
                    LogUtils.i("A method on the stack trace has been hooked using Substrate.");
                    return true;
                }
                if ("de.robv.android.xposed.XposedBridge".equals(item.getClassName())
                        && "main".equals(item.getMethodName())) {
                    LogUtils.i("Xposed is active on the device.");
                    return true;
                }
                if ("de.robv.android.xposed.XposedBridge".equals(item.getClassName())
                        && "handleHookedMethod".equals(item.getMethodName())) {
                    LogUtils.i("A method on the stack trace has been hooked using Xposed.");
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 检测内存中可疑的jars
     */
    public static boolean mapsCheck() {
        Set<String> libraries = new HashSet<String>();
        String mapsFilename = "/proc/" + android.os.Process.myPid() + "/maps";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(mapsFilename));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains("frida")) {
                    LogUtils.i("frida found: " + line);
                    return true;
                }
                if (line.endsWith(".so") || line.endsWith(".jar")) {
                    int n = line.lastIndexOf(" ");
                    libraries.add(line.substring(n + 1));
                }
            }
            for (String library : libraries) {
                if (library.contains("com.saurik.substrate")) {
                    LogUtils.i("Substrate shared object found: " + library);
                    return true;
                }
                if (library.contains("XposedBridge.jar")) {
                    LogUtils.i("Xposed JAR found: " + library);
                    return true;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 尝试加载 Xposed 类
     *
     * @return
     */
    public static boolean classCheck() {
        try {
            Class.forName("de.robv.android.xposed.XC_MethodHook");
            return true;
        } catch (Exception e) {
        }
        try {
            Class.forName("de.robv.android.xposed.XposedHelpers");
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 关键字 只是表明hook了这些函数 并不能代表使用
     * getDeviceId 修改IMEI号
     * SERIAL  修改序列号
     * getSSID 修改WIFI名称
     * getMacAddress 修改 WIFI MAC地址
     * BluetoothAdapter#getAddress 修改蓝牙MAC地址
     * Secure#getString 修改androidId
     * getSubscriberId 修改IMSI
     * getLatitude 纬度
     * getLongitude 经度
     *
     * @param fieldName   属性名称
     * @param packageName 包名
     * @return
     */
    private static boolean checkKeyWordInFiled(String fieldName, String packageName) {
        Map map;
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            Object cls = classLoader.loadClass("de.robv.android.xposed.XposedHelpers").newInstance();
            Field field = cls.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            map = (Map) field.get(cls);
            if (!map.isEmpty()) {
                for (Object aKeySet : map.keySet()) {
                    if (aKeySet.toString().contains(packageName)) {
                    }
                    if (aKeySet.toString().toLowerCase().contains("getDeviceId".toLowerCase())) {
                    }
                    if (aKeySet.toString().toLowerCase().contains("SERIAL".toLowerCase())) {
                    }
                    if (aKeySet.toString().toLowerCase().contains("getSSID".toLowerCase())) {
                    }
                    if (aKeySet.toString().toLowerCase().contains("getMacAddress".toLowerCase())) {
                    }
                    if (aKeySet.toString().toLowerCase().contains("BluetoothAdapter#getAddress".toLowerCase())) {
                    }
                    if (aKeySet.toString().toLowerCase().contains("Secure#getString".toLowerCase())) {
                    }
                    if (aKeySet.toString().toLowerCase().contains("getSubscriberId".toLowerCase())) {
                    }
                    if (aKeySet.toString().toLowerCase().contains("getLatitude".toLowerCase())) {
                    }
                    if (aKeySet.toString().toLowerCase().contains("getLongitude".toLowerCase())) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
