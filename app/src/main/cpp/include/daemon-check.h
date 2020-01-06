//
// Created by chensongsong on 2019/9/5.
//

#ifndef MOBJNI_GT_MOB_DAEMON_H
#define MOBJNI_GT_MOB_DAEMON_H

/**
 * 主线程守护线程
 * @return
 */
static void *daemon_thread(void *);

/**
 * 守护子进程
 */
static void daemon_pip();

/**
 * 开启守护进程检测
 */
void startDaemon();

#endif //MOBJNI_GT_MOB_DAEMON_H
