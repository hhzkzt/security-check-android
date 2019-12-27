package com.song.check.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class CommandUtils {

    public static String getProperty(String propName) {
        String value = null;
        Object roSecureObj;
        try {
            roSecureObj = Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class)
                    .invoke(null, propName);
            if (roSecureObj != null) {
                value = (String) roSecureObj;
            }
        } catch (Exception e) {
            value = null;
        } finally {
            return value;
        }
    }

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