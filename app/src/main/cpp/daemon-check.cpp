//
// Created by chensongsong on 2020/1/6.
//

#include <unistd.h>
#include <asm/fcntl.h>
#include <sys/ptrace.h>
#include <pthread.h>
#include <stdio.h>
#include <string.h>

#include "include/log.h"
#include "include/utils.h"

#define DELAY_TIME 10

int pip_ipc[2];
int child_pid;

/**
 * 主线程守护线程
 * @return
 */
static void *daemon_thread(void *) {

    /**
     * 和守护进程通信的状态码，非 -1 即为进程被调式
     */
    int statue = -1;

    close(pip_ipc[1]);
    while (read(pip_ipc[0], &statue, 4) > 0) {
        break;
    }

    sleep(1);

    // 设置非阻塞
    fcntl(pip_ipc[0], F_SETFL, O_NONBLOCK);
    LOGI("daemon_thread read child pip statue: %d", statue);

    while (true) {

        LOGI("daemon_thread current statue: %d", statue);
        read(pip_ipc[0], &statue, 4);
        LOGI("daemon_thread second read child pip statue: %d", statue);

        if (statue != 0 && statue != -1) {
            // 设置被 debug 标记
            kill(child_pid, SIGKILL);
//            kill(getpid(), SIGKILL);

            // 结束线程循环
            return NULL;
        }
        statue = -1;
        sleep(DELAY_TIME);
    }
}

/**
 * 守护子进程
 */
static void daemon_pip() {

    int main_pid, child_pip_temp;

    FILE *pf;
    char filename[64];
    char line[BUF_SIZE_512];

    // 获取主进程 pid
    main_pid = getpid();
    // 读取proc/pid/status中的TracerPid
    sprintf(filename, "/proc/%d/status", main_pid);

    child_pip_temp = fork();
    LOGI("%d", child_pip_temp);

    if (child_pip_temp == 0) {
        LOGI("daemon_child_pip has started.");
        // 关闭子进程的读管道
        close(pip_ipc[0]);
        //子进程反调试
        long ptraceId = ptrace(PTRACE_TRACEME, 0, 0, 0);
        LOGW("ptraceId: %ld", ptraceId);

        while (true) {

            pf = fopen(filename, "r");
            while (fgets(line, BUF_SIZE_512, pf)) {
                if (strstr(line, "TracerPid")) {

                    LOGI("TracerPid line: %s", line);
                    int statue = atoi(&line[10]);

                    LOGI("daemon_child_pip read main_pip statue: %d", statue);
                    //子进程向父进程写 statue 值
                    write(pip_ipc[1], &statue, 4);
                    fclose(pf);

                    if (statue != 0) {
                        LOGI("daemon_child_pip read main_pip has been debug: %d", statue);
                        return;
                    }
                    break;
                }
            }
            sleep(DELAY_TIME);

        }
    } else {
        LOGI("The parent starts the daemon.");
        child_pid = child_pip_temp;
    }
}

/**
 * 开启守护进程检测
 */
void startDaemon() {
    pthread_t self_thread;
    self_thread = pthread_self();

    int pipeResult = pipe(pip_ipc);
    LOGD("pipeResult: %d", pipeResult);
    pthread_create(&self_thread, NULL, daemon_thread, (void *) NULL);
    LOGI("daemon_thread be being running.");

    daemon_pip();
}
