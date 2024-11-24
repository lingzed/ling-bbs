package com.ling.commons;

public class Test {
    private static boolean flag = true;

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (flag) {
                System.out.println("线程A在运行");
            }
            System.out.println(Thread.currentThread().getName() + "结束了");
        }, "线程A");
        thread.start();
        try {
            Thread.sleep(1000);
            flag = false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("主线程结束");
    }
}
