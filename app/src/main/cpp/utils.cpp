//
// Created by 陈颂颂 on 2019/12/27.
//

#include "include/utils.h"

/**
 * 判断文件是否存在
 * path: 路径
 * 返回值 1:文件存在; 0:文件不存在
 */
int exists(const char *path) {
    return access(path, F_OK) == 0 ? 1 : 0;
}

/**
 * 读取系统属性值
 * @param name
 * @return 返回属性长度，若为 0 则不存在当前属性
 */
int getProperty(const char *name, char *dest) {
    return __system_property_get(name, dest);
}
