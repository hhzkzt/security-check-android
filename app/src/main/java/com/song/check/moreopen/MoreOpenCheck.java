package com.song.check.moreopen;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.song.check.utils.CommandUtils;
import com.song.check.utils.LogUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * 多开环境检测
 * Created by chensongsong on 2019/12/30.
 */
public class MoreOpenCheck {

    /**
     * 进程检测，若出现同一个 uid 下出现的进程名对应 /data/data/pkg 私有目录，超出 1 个则为多开
     * 需要排除当前进程名存在多个情况
     *
     * @return
     * @deprecated 当前方案在 6.0 以上机型不可用，因为只能获取当前 uid 进程列表
     */
    public static boolean processCheck() {

        String filter = CommandUtils.getUidStrFormat();

        String result = CommandUtils.execute("ps");
        if (result == null || result.isEmpty()) {
            return false;
        }

        LogUtils.d(result);

        String[] lines = result.split("\n");
        if (lines == null || lines.length <= 0) {
            return false;
        }

        int exitDirCount = 0;

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains(filter)) {
                int pkgStartIndex = lines[i].lastIndexOf(" ");
                String processName = lines[i].substring(pkgStartIndex <= 0
                        ? 0 : pkgStartIndex + 1, lines[i].length());
                File dataFile = new File(String.format("/data/data/%s",
                        processName, Locale.CHINA));
                if (dataFile.exists()) {
                    exitDirCount++;
                }
            }
        }

        return exitDirCount > 1;

    }

    /**
     * 判断当前私有路径是否是标准路径
     *
     * @param context
     * @return
     */
    public static boolean pathCheck(Context context) {

        String filesDir = context.getFilesDir().getAbsolutePath();
        String packageName = context.getPackageName();

        String normalPath_one = "/data/data/" + packageName + "/files";
        String normalPath_two = "/data/user/0/" + packageName + "/files";

        if (!normalPath_one.equals(filesDir) && !normalPath_two.equals(filesDir)) {
            return true;
        }

        return false;

    }

    /**
     * 多开App包名列表
     */
    private static String[] virtualPkgs = {
            "com.bly.dkplat",
            "com.lbe.parallel",
            "com.excelliance.dualaid",
            "com.lody.virtual",
            "com.qihoo.magic"
    };

    /**
     * maps检测, 若 maps 文件包含多开包名则为多开环境
     *
     * @return
     * @deprecated 无法普适所有多开软件, 且部分软件 maps 不依赖当前路径下 so
     */
    public static boolean mapsCheck() {
        BufferedReader bufr = null;
        try {
            bufr = new BufferedReader(new FileReader("/proc/self/maps"));
            String line;
            while ((line = bufr.readLine()) != null) {
                for (String pkg : virtualPkgs) {
                    if (line.contains(pkg)) {
                        return true;
                    }
                }
            }
        } catch (Exception ignore) {
            //忽略异常
        } finally {
            if (bufr != null) {
                try {
                    bufr.close();
                } catch (IOException e) {
                    //忽略异常
                }
            }
        }
        return false;
    }

    /**
     * 若 applist 存在两个当前包名则为多开
     *
     * @param context
     * @return
     * @deprecated 大部分多开软件已经绕过
     */
    public static boolean packageCheck(Context context) {

        try {
            if (context == null) {
                return false;
            }
            int count = 0;
            String packageName = context.getPackageName();
            PackageManager pm = context.getPackageManager();
            List<PackageInfo> pkgs = pm.getInstalledPackages(0);
            for (PackageInfo info : pkgs) {
                if (packageName.equals(info.packageName)) {
                    count++;
                }
            }
            return count > 1;
        } catch (Exception ignore) {
        }
        return false;
    }

    /**
     * 获取系统 binder 服务数量，若保持在 140 左右则为多开
     *
     * @return
     */
    public static boolean getSystemServer() {
        return false;
    }
}
