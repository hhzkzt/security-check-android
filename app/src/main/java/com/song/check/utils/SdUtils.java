package com.song.check.utils;

import android.os.Environment;

/**
 * Created by chensongsong on 2020/1/2.
 */
public class SdUtils {

    public static boolean isMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static String getDirPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }


}
