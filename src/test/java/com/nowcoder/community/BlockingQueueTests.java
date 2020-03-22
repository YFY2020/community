package com.nowcoder.community;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTests {

    public static void main(String[] args) {

        BlockingQueue queue = new ArrayBlockingQueue(10);
        new Thread(new Producer(queue)).start();   //生产者线程
        new Thread(new Consumer(queue)).start();  //消费者线程  消费者不止一个
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }

}


class Producer implements Runnable {

    private BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
               Thread.sleep(20);  //20毫秒    //生产者每隔20毫秒生产一个数据
                queue.put(i);
                System.out.println(Thread.currentThread().getName()+"生产第"+(i+1)+"个数据"+" 队列中数据个数："+queue.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class Consumer implements Runnable{
    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try{
            while (true){
                Thread.sleep(new Random().nextInt(1000));   //消费者消费数据的事件是随机的，有时快，有时慢
                queue.take();
                System.out.println(Thread.currentThread().getName()+"消费数据 队列中数据个数："+queue.size());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
