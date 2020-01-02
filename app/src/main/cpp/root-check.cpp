//
// Created by 陈颂颂 on 2020/1/2.
//

#include "include/root-check.h"
#include "include/utils.h"
#include "include/app-utils.h"
#include "include/log.h"

#include <stdio.h>
#include <string.h>
#include <errno.h>

/**
 * root 检测
 * @return 0: false 1: true 2: not support
 */
int rootCheck(char *dest) {

    FILE *f = NULL;
    f = popen("su -v", "r");
    if (f != NULL) {
        if (fgets(dest, BUF_SIZE_256, f) == NULL) {
            LOGD("read popen error: %s", strerror(errno));
            pclose(f);
            return 0;
        }
        LOGD("su -v: %s", dest);
        if (strlen(dest) == 0) {
            pclose(f);
            LOGD("this not is root.");
            return 0;
        } else {
            pclose(f);
            LOGD("this is rooted.");
            return 1;
        }
    } else {
        LOGD("file pointer is null.");
        return 2;
    }

}