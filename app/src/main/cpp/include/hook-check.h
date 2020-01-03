//
// Created by 陈颂颂 on 2020/1/2.
//

#ifndef SECURITY_CHECK_ANDROID_HOOK_CHECK_H
#define SECURITY_CHECK_ANDROID_HOOK_CHECK_H

/**
 * 检测主流 hook 框架: frida、Xposed、substrate
 * @return
 */
int frameCheck();

/**
 * 检查核心文件，若存在则为危险环境。不一定正在被 hook，但是有风险
 * @return
 */
int pkgCheck();

/**
 * 检测已加载到内存的 Substrate 核心文件
 * @return
 */
int substrateSoCheck();

/**
 * 检测已加载到内存的 xhook 核心文件
 * @return
 */
int xhookCheck();

/**
 * inline hook check
 * TODO 等待后续完善
 * @return
 */
int inlineHookCheck();

/**
 * PLT Hook check
 * TODO 等待后续完善
 * @return
 */
int pltHookCheck();

#endif //SECURITY_CHECK_ANDROID_HOOK_CHECK_H
