//
// Created by 陈颂颂 on 2020/1/2.
//

#include <stdio.h>
#include <string.h>
#include <dlfcn.h>

#include "include/hook-check.h"
#include "include/log.h"
#include "include/utils.h"

/**
 * 检测主流 hook 框架: frida、Xposed、substrate
 * @return
 */
int frameCheck() {

    char path[BUF_SIZE_32];
    sprintf(path, "/proc/%d/maps", getpid());

    // 读取数据
    FILE *f = NULL;
    char buf[BUF_SIZE_512];
    f = fopen(path, "r");

    if (f != NULL) {
        while (fgets(buf, BUF_SIZE_512, f)) {
            // fgets 当读取 (n-1) 个字符时，或者读取到换行符时，或者到达文件末尾时，它会停止，具体视情况而定。
            LOGI("maps: %s", buf);
            if (strstr(buf, "frida")
                || strstr(buf, "com.saurik.substrate")
                || strstr(buf, "XposedBridge.jar")) {
                fclose(f);
                return 1;
            }
        }
    }

    fclose(f);
    return 0;

}

/**
 * 检查核心文件，若存在则为危险环境。不一定正在被 hook，但是有风险
 * @return
 */
int pkgCheck() {
    if (exists("/data/data/de.robv.android.xposed.installer")
        || exists("/data/data/com.saurik.substrate")
        || exists("/data/local/tmp/frida-server")) {
        return 1;
    }
    return 0;
}

/**
 * 检测已加载到内存的 Substrate 核心文件
 * @return
 */
int substrateSoCheck() {

    // 直接加载危险的so
    void *imagehandle = dlopen("libsubstrate-dvm.so", RTLD_GLOBAL | RTLD_NOW);
    if (imagehandle != NULL) {
        void *sym = dlsym(imagehandle, "MSJavaHookMethod");
        if (sym != NULL) {
            dlclose(imagehandle);
            LOGE("find Cydia Substrate");
            //发现Cydia Substrate相关模块
            return 1;
        }
    }

    LOGE("not find Cydia Substrate");
    return 0;

}

/**
 * 检测已加载到内存的 xhook 核心文件
 * @return
 */
int xhookCheck() {

    // 直接加载危险的so
    void *imagehandle = dlopen("libxhook.so", RTLD_GLOBAL | RTLD_NOW);
    if (imagehandle != NULL) {
        void *sym = dlsym(imagehandle, "xhook_register");
        if (sym != NULL) {
            dlclose(imagehandle);
            LOGE("find xhook");
            return 1;
        }
    }

    LOGE("not find xhook");
    return 0;

}

/**
 * inline hook check
 * TODO 等待后续完善
 * @return
 */
int inlineHookCheck(){
    return 0;
}

/**
 * PLT Hook check
 * TODO 等待后续完善
 * @return
 */
int pltHookCheck(){
    return 0;
}
