package com.song.check.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * Created by chensongsong on 2020/1/2.
 */
public class CommandUtils {

    public static String execute(String command) {
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("sh");
            bufferedOutputStream = new BufferedOutputStream(process.getOutputStream());

            bufferedInputStream = new BufferedInputStream(process.getInputStream());
            bufferedOutputStream.write(command.getBytes());
            bufferedOutputStream.write('\n');
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            process.waitFor();
            return readFromInputStream(bufferedInputStream);
        } catch (Exception e) {
            return Constants.UNKNOWN;
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
    }

    public static String getUidStrFormat() {
        try {
            String filter = execute("cat /proc/self/cgroup");
            if (filter == null || filter.length() == 0) {
                return null;
            }

            int uidStartIndex = filter.lastIndexOf("uid");
            int uidEndIndex = filter.lastIndexOf("/pid");
            if (uidStartIndex < 0) {
                return null;
            }
            if (uidEndIndex <= 0) {
                uidEndIndex = filter.length();
            }

            filter = filter.substring(uidStartIndex + 4, uidEndIndex);
            String strUid = filter.replaceAll("\n", "");
            if (isNumber(strUid)) {
                int uid = Integer.valueOf(strUid);
                filter = String.format("u0_a%d", uid - 10000);
                return filter;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isNumber(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String readFromInputStream(BufferedInputStream bufferedInputStream) {
        if (null == bufferedInputStream) {
            return Constants.UNKNOWN;
        }
        int BUFFER_SIZE = 1024;
        byte[] buffer = new byte[BUFFER_SIZE];
        StringBuilder sb = new StringBuilder();
        try {
            int len = 0;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}