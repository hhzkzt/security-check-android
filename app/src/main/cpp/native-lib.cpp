#include <jni.h>
#include <string>

#include "include/emulator-check.h"
#include "include/virtual-apk-check.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_song_check_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

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