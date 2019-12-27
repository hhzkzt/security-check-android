//
// Created by 陈颂颂 on 2019/12/27.
//

#include <dirent.h>
#include <string.h>
#include "include/emulator-check.h"
#include "include/utils.h"
#include "include/log.h"

/**
 * 检测模拟器不存在的相关文件
 * @param env
 * @return
 */
jboolean specialFilesEmulatorcheck(JNIEnv *env) {

    if (exists("/system/lib/libdroid4x.so") // 文卓爷
        || exists("/system/bin/windroyed") // 文卓爷
        || exists("/system/bin/microvirtd") // 逍遥
        || exists("/system/bin/nox-prop") // 夜神
        || exists("/system/bin/ttVM-prop") // 天天
        || exists("/system/lib/libc_malloc_debug_qemu.so")) {

        return JNI_TRUE;
    }
    return JNI_FALSE;

}

/**
 * 检测特殊目录，/sys/class/thermal/thermal_zoneX/temp(温度挂载文件)
 * @return 大于 0 为真机，等于 0 为模拟器
 */
jboolean thermalCheck() {

    //当前手机的温度检测，手机下均有thermal_zone文件
    DIR *dirptr = NULL;
    int i = 0;
    struct dirent *entry;

    if ((dirptr = opendir("/sys/class/thermal/")) != NULL) {
        while (entry = readdir(dirptr)) {
            LOGE("%s  \n", entry->d_name);
            if (!strcmp(entry->d_name, ".") || !strcmp(entry->d_name, "..")) {
                continue;
            }
            char *tmp = entry->d_name;
            if (strstr(tmp, "thermal_zone") != NULL) {
                i++;
            }
        }
        closedir(dirptr);
    } else {
        LOGE("open thermal fail");
    }
    return i > 0 ? JNI_FALSE : JNI_TRUE;
}

/**
 * build 文件检测示例
 * @return
 */
jboolean buildCheck() {
    char name[64] = "";
    getProperty("ro.product.name", name);
    if (strcmp(name, "ChangWan") == 0
        || strcmp(name, "Droid4X") == 0
        || strcmp(name, "lgshouyou") == 0
        || strcmp(name, "nox") == 0
        || strcmp(name, "ttVM_Hdragon") == 0) {
        return JNI_TRUE;
    }
    return JNI_FALSE;
}

/**
 * 蓝牙文件检测
 * @return
 */
jboolean bluetoothCheck() {
    if (!exists("/system/lib/libbluetooth_jni.so")) {
        return JNI_TRUE;
    }
    return JNI_FALSE;
}
