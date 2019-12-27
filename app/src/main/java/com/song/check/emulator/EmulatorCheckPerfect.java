package com.song.check.emulator;

import android.os.Build;
import android.text.TextUtils;

import com.song.check.utils.CommandUtils;
import com.song.check.utils.ReflectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 经过线上数据验证可靠方案
 * <p>
 * Created by chensongsong on 2019/12/26.
 */
public class EmulatorCheckPerfect {

    /**
     * Build 文件检测
     *
     * @return
     */
    public static boolean buildCheck() {

        if (Build.PRODUCT.contains("sdk") ||
                Build.PRODUCT.contains("sdk_x86") ||
                Build.PRODUCT.contains("sdk_google") ||
                Build.PRODUCT.contains("Andy") ||
                Build.PRODUCT.contains("Droid4X") ||
                Build.PRODUCT.contains("nox") ||
                Build.PRODUCT.contains("vbox86p") ||
                Build.PRODUCT.contains("aries")) {
            return true;
        }
        if (Build.MANUFACTURER.equals("Genymotion") ||
                Build.MANUFACTURER.contains("Andy") ||
                Build.MANUFACTURER.contains("nox") ||
                Build.MANUFACTURER.contains("TiantianVM")) {
            return true;
        }
        if (Build.BRAND.contains("Andy")) {
            return true;
        }
        if (Build.DEVICE.contains("Andy") ||
                Build.DEVICE.contains("Droid4X") ||
                Build.DEVICE.contains("nox") ||
                Build.DEVICE.contains("vbox86p") ||
                Build.DEVICE.contains("aries")) {
            return true;
        }
        if (Build.MODEL.contains("Emulator") ||
                Build.MODEL.equals("google_sdk") ||
                Build.MODEL.contains("Droid4X") ||
                Build.MODEL.contains("TiantianVM") ||
                Build.MODEL.contains("Andy") ||
                Build.MODEL.equals("Android SDK built for x86_64") ||
                Build.MODEL.equals("Android SDK built for x86")) {
            return true;
        }
        if (Build.HARDWARE.equals("vbox86") ||
                Build.HARDWARE.contains("nox") ||
                Build.HARDWARE.contains("ttVM_x86")) {
            return true;
        }
        if (Build.FINGERPRINT.contains("generic/sdk/generic") ||
                Build.FINGERPRINT.contains("generic_x86/sdk_x86/generic_x86") ||
                Build.FINGERPRINT.contains("Andy") ||
                Build.FINGERPRINT.contains("ttVM_Hdragon") ||
                Build.FINGERPRINT.contains("generic/google_sdk/generic") ||
                Build.FINGERPRINT.contains("vbox86p") ||
                Build.FINGERPRINT.contains("generic/vbox86p/vbox86p")) {
            return true;
        }

        return false;
    }

    /**
     * qemu 检测
     * @return
     */
    public static boolean qemuCheck() {
        if (checkQEmuDriverFile("/proc/tty/drivers")||checkQEmuDriverFile("/proc/cpuinfo")){
            return true;
        }
        return "1".equals(ReflectUtils.getProperty("ro.kernel.qemu"));
    }

    /**
     * qemu特有的驱动列表
     */
    private static final String[] KNOWN_QEMU_DRIVERS = {
            "goldfish"
    };


    /**
     * 驱动程序的列表
     *
     * @return true为模拟器
     */
    private static boolean checkQEmuDriverFile(String name) {
        File driver = new File(name);
        if (driver.exists() && driver.canRead()) {
            byte[] data = new byte[1024];
            try {
                InputStream inStream = new FileInputStream(driver);
                inStream.read(data);
                inStream.close();
            } catch (Exception e) {
            }
            String driverData = new String(data);
            for (String known_qemu_driver : KNOWN_QEMU_DRIVERS) {
                if (driverData.contains(known_qemu_driver)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检测系统属性，非常可靠
     * <p>
     * 1. data.unique.br_list.arch 为 i686
     * 2. data.unique.br_list.har 为 intel 或 vbox86 或包含 x86
     * 3. data.unique.br_list.a 为 x86
     * 4. data.unique.br_list.a_list 包含 x86
     * 5. sound 信息为 I82801AAICH
     * 6. su_v 为 16 com.android.settings
     *
     * @return
     */
    public static boolean checkSystemProperty() {

        String hardware = ReflectUtils.getProperty("ro.hardware");
        if (hardware.contains("intel")
                || hardware.contains("vbox86")
                || hardware.contains("vbox")
                || hardware.contains("ttvm")
                || hardware.contains("cancro")
                || hardware.contains("nox")
                || hardware.contains("x86")) {
            return true;
        }
        String abi = ReflectUtils.getProperty("ro.product.cpu.abi");
        if (abi.contains("x86")) {
            return true;
        }
        String abilist = ReflectUtils.getProperty("ro.product.cpu.abilist");
        if (abilist.contains("x86")) {
            return true;
        }
        String arch = CommandUtils.execute("uname -m");
        if (arch.contains("i686")) {
            return true;
        }
        String su = CommandUtils.execute("su -v");
        if (TextUtils.equals("16 com.android.settings", su)) {
            return true;
        }
        String sound = CommandUtils.execute("cat /proc/asound/card0/id");
        if (TextUtils.equals("I82801AAICH", sound)) {
            return true;
        }

        return false;
    }

    /**
     * 检查 cpuinfo 信息
     *
     * @return
     */
    public static boolean checkCpuInfo() {
        String cpuinfo = CommandUtils.execute("cat /proc/cpuinfo");
        if (cpuinfo.contains("intel") ||
                cpuinfo.contains("amd")) {
            return true;
        }
        return false;
    }

    /**
     * 设备通道文件，只兼容了qemu模拟器
     */
    private static final String[] KNOWN_PIPES = {
            "/dev/socket/qemud",
            "/dev/qemu_pipe"
    };


    /**
     * 检测“/dev/socket/qemud”，“/dev/qemu_pipe”这两个通道设备文件特征
     *
     * @return true为模拟器
     */
    public static boolean checkPipes() {
        for (String pipes : KNOWN_PIPES) {
            File qemu = new File(pipes);
            if (qemu.exists()) {
                return true;
            }
        }
        return false;
    }


    /**
     * @return all involved Build.* parameters and its values
     */
    public static String getDeviceInfo() {
        return "Build.PRODUCT: " + Build.PRODUCT + "\n" +
                "Build.MANUFACTURER: " + Build.MANUFACTURER + "\n" +
                "Build.BRAND: " + Build.BRAND + "\n" +
                "Build.DEVICE: " + Build.DEVICE + "\n" +
                "Build.MODEL: " + Build.MODEL + "\n" +
                "Build.HARDWARE: " + Build.HARDWARE + "\n" +
                "Build.FINGERPRINT: " + Build.FINGERPRINT + "\n" +
                "Build.TAGS: " + Build.TAGS + "\n" +
                "GL_RENDERER: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_RENDERER) + "\n" +
                "GL_VENDOR: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_VENDOR) + "\n" +
                "GL_VERSION: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_VERSION) + "\n" +
                "GL_EXTENSIONS: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_EXTENSIONS) + "\n";
    }

}
