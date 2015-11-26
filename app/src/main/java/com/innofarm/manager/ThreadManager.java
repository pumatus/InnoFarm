package com.innofarm.manager;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的管理者
 * @author itcast
 *
 */
public class ThreadManager {
    private ThreadManager() {

    }

    private static ThreadManager instance = new ThreadManager();
    private ThreadPoolExecutor longExecutor; // 耗时比较长的线程池   用来请求网络
    private ThreadPoolExecutor shortExecutor; // 比较短的线程池    用来加载本地数据

    public static ThreadManager getInstance() {
        return instance;
    }
    /**
     * 执行异步任务
     * @param runnable
     */
    public void executeLongTask(Runnable runnable) {
        synchronized (instance) {
            // 开启线程池 执行异步任务
            // 参数1 线程池管理的线程数
            // 参数2 额外的开启的线程
            // 参数3 线程池存活的时间
            // 参数4 时间的单位
            // 参数5 队列
            if (longExecutor == null) {
                longExecutor = new ThreadPoolExecutor(3, 3, 1000,
                        TimeUnit.MICROSECONDS,
                        new LinkedBlockingQueue<Runnable>(10));
            }
        }
        longExecutor.execute(runnable);// 用线程池执行代码
    }
    public void executeShortTask(Runnable runnable) {
        synchronized (instance) {
            // 开启线程池 执行异步任务
            // 参数1 线程池管理的线程数
            // 参数2 额外的开启的线程
            // 参数3 线程池存活的时间
            // 参数4 时间的单位
            // 参数5 队列
            if (shortExecutor == null) {
                shortExecutor = new ThreadPoolExecutor(3, 3, 1000,
                        TimeUnit.MICROSECONDS,
                        new LinkedBlockingQueue<Runnable>(10));
            }
        }
        shortExecutor.execute(runnable);// 用线程池执行代码
    }
    public void cancelLong(Runnable runnable){
        if(longExecutor!=null&&!longExecutor.isShutdown()){
            longExecutor.remove(runnable);// 停止任务
        }
    }

}

