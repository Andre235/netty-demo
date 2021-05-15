package com.geek.netty.chart4;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.geek.netty.ByteBufferUtil.debugRead;

/**
 * @author: 赵静超
 * @date: 2021/5/13 21:38
 * @description: 使用selector来理解非阻塞模式(单线程)
 */
@Slf4j
public class Server1 {

    @SneakyThrows(value = Exception.class)
    public static void main(String[] args) {
        /**
         *  1.创建selector，管理多个channel
         */
        Selector selector = Selector.open();

        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        /**
         * 2.建立selector和channel之间的联系
         * selectionKey 就是将来事件发生以后，可以通过它来知道哪个channel产生的事件
         * 常见的事件类型有：
         * accept：在有连接请求时触发该事件
         * connect：是客户端建立连接后触发该事件
         * read：可读事件
         * write：可写事件
         */
        SelectionKey selectionKey = serverSocketChannel.register(selector, 0, null);
        log.info("register key: {}", selectionKey.toString());
        /**
         * 该selectionKey只关注accept事件
         */
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // select方法没有事件发生过则线程阻塞，有事件发生则线程继续运行
            selector.select();
            // 处理事件，selectedKeys包含了所有发生的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                log.info("handle key: {}", key.toString());
                ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                // 建立连接
                SocketChannel socketChannel = channel.accept();
                log.info("socketChannel: {}", socketChannel.toString());
            }
        }
    }
}
