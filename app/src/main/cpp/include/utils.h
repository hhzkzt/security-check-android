//
// Created by 陈颂颂 on 2019/12/27.
//

#ifndef SECURITY_CHECK_ANDROID_UTILS_H
#define SECURITY_CHECK_ANDROID_UTILS_H

#include <zconf.h>

/**
 * path: 路径
 * 返回值 1:文件存在; 0:文件不存在
 */
int exists(const char *path);

int getProperty(const char *name, char *dest);

#endif //SECURITY_CHECK_ANDROID_UTILS_H
