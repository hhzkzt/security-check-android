package com.song.check.debug;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.provider.Settings;

/**
 * Created by chensongsong on 2020/1/4.
 */
public class DebugCheck {

    /**
     * 开启了调试模式
     *
     * @param context
     * @return
     */
    public static boolean isOpenDebug(Context context) {
        try {
            return (Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0);
        } catch (Exception e) {
            //忽略异常
        }
        return false;
    }

    /**
     * 判斷是debug版本
     *
     * @param context
     * @return
     */
    public static boolean debugVersionCheck(Context context) {
        try {
            return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            //忽略异常
        }
        return false;
    }

    /**
     * 是否正在调试
     *
     * @return
     */
    public static boolean connectedCheck() {
        try {
            return android.os.Debug.isDebuggerConnected();
        } catch (Exception e) {
            //忽略异常
        }
        return false;
    }
}
