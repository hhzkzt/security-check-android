#include <jni.h>

#include "include/emulator-check.h"
#include "include/virtual-apk-check.h"
#include "include/root-check.h"
#include "include/hook-check.h"
#include "include/utils.h"
#include "include/log.h"

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_song_check_activity_EmulatorActivity_specialFilesEmulatorcheck(JNIEnv *env,
                                                                        jobject instance) {
    return specialFilesEmulatorcheck();
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_song_check_activity_EmulatorActivity_thermalCheck(JNIEnv *env, jobject instance) {
    return thermalCheck();
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_song_check_activity_EmulatorActivity_buildCheck(JNIEnv *env, jobject instance) {
    return buildCheck();
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_song_check_activity_EmulatorActivity_bluetoothCheck(JNIEnv *env, jobject instance) {
    return bluetoothCheck();
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_check_activity_MoreOpenActivity_moreOpenCheck(JNIEnv *env, jobject instance) {
    return moreOpenCheck();
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_song_check_activity_RootActivity_rootCheck(JNIEnv *env, jobject instance) {
    char dest[BUF_SIZE_256] = UNKNOWN;
    int result = rootCheck(dest);
    LOGE("check root result: %d", result);
    return env->NewStringUTF(dest);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_check_activity_HookActivity_packageCheck(JNIEnv *env, jobject instance) {
    return pkgCheck();
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_check_activity_HookActivity_fileCheck(JNIEnv *env, jobject instance) {
    return frameCheck();
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_check_activity_HookActivity_xhookCheck(JNIEnv *env, jobject instance) {
    return xhookCheck();
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_check_activity_HookActivity_substrateCheck(JNIEnv *env, jobject instance) {
    return substrateSoCheck();
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_check_activity_HookActivity_inlineCheck(JNIEnv *env, jobject instance) {
    return inlineHookCheck();
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_check_activity_HookActivity_pltCheck(JNIEnv *env, jobject instance) {
    return pltHookCheck();
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_check_activity_EmulatorActivity_getArch(JNIEnv *env, jobject instance) {
    return getArch();
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_song_check_activity_EmulatorActivity_getApiVersion(JNIEnv *env, jobject instance) {
    return getApiVersion();
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_song_check_activity_EmulatorActivity_getMapsArch(JNIEnv *env, jobject instance) {
    char dst[BUF_SIZE_64] = UNKNOWN;
    getMapsArch(dst);
    return env->NewStringUTF(dst);
}