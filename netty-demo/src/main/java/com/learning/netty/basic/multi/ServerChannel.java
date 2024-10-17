package com.learning.netty.basic.multi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerChannel {
    public static void main(String[] args) {
        try (
                ServerSocketChannel ssc = ServerSocketChannel.open();
                Selector selector = Selector.open();
        ) {
            ssc.bind(new InetSocketAddress(8080));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT, null);

            // 使用另外的线程专门处理read事件
            Worker[] workers = new Worker[2];
            for (int i = 0; i < workers.length; i++) {
                workers[i] = new Worker("[Thread-" + i + "]");
            }

            AtomicInteger count = new AtomicInteger(0);
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        SocketChannel sc = ssc.accept();
                        System.out.println("Accepted connection from " + sc.getRemoteAddress());
                        sc.configureBlocking(false);
                        System.out.println("before register...");
                        // 轮询注册socket
                        workers[count.getAndIncrement() % workers.length].register(sc);
                        System.out.println("after register...");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class Worker implements Runnable {
        private final Selector selector;
        private final ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();
        private final String name;

        Worker(String name) throws IOException {
            // 启动选择器并监听事件中的read事件
            selector = Selector.open();
            this.name = name;
            new Thread(this).start();
        }

        void register(SocketChannel sc) throws IOException {
            // 注册事件需要使用线程通信通知该线程的 selector
            // 因为在构造函数的线程 start 后，selector 可能会阻塞在 selector.select(); 上
            // 因此无法执行 sc.register(selector, SelectionKey.OP_READ, null); 来注册新进来的 socket
            // 解决办法是使用线程通信，将注册改为事件传入队列，手动调用 selector.wakeup(); 让 selector 不阻塞
            // 然后在 selector.select(); 之后添加读取消息队列数据的代码逻辑，让线程执行传入注册逻辑，之后又可以正常回到 selector.select(); 的阻塞上
            queue.add(() -> {
                try {
                    sc.register(selector, SelectionKey.OP_READ, null);
                    System.out.println(name + "register success");
                } catch (ClosedChannelException e) {
                    throw new RuntimeException(e);
                }
            });
            selector.wakeup();
        }

        @Override
        public void run() {
            // 监听read事件
            try {
                while (true) {
                    System.out.println(name + "before select...");
                    selector.select();
                    System.out.println(name + "after select...");
                    // 可能通过wakeup让selector不阻塞，从而可以执行注册事件
                    Runnable registerAction = queue.poll();
                    if (registerAction != null) {
                        registerAction.run();
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel sc = (SocketChannel) key.channel();
                            System.out.println(name + "before read...");
                            int read = sc.read(buffer);
                            if (read > 0) {
                                buffer.flip();
                                while (buffer.hasRemaining()) {
                                    System.out.print((char) buffer.get());
                                }
                                System.out.println();
                            }
                            System.out.println(name + "after read...");
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
