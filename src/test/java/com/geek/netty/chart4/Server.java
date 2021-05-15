package com.geek.netty.chart4;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.geek.netty.ByteBufferUtil.debugRead;

/**
 * @author: 赵静超
 * @date: 2021/5/13 21:38
 * @description: 使用nio来理解阻塞模式(单线程)
 */
@Slf4j
public class Server {

    @SneakyThrows(value = Exception.class)
    public static void main(String[] args) {
        // 0.创建全局缓冲区 Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);

        // 1.创建服务器
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 开启非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 2.绑定监听端口
        serverSocketChannel.bind(new InetSocketAddress(8080));
        // 3.连接集合(SocketChannel 用来与客户端进行通信)
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // 4.accept 建立与客户端的连接
            // 在没有建立连接时让线程暂停
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null) {
                // 开启非阻塞模式
                socketChannel.configureBlocking(false);
                log.info("connected...{}", socketChannel.toString());
                channels.add(socketChannel);
            }
            for (SocketChannel channel : channels) {
                // 5.接口客户端发送的数据
                // 在没有数据可以读取时让线程暂停
                int length = channel.read(byteBuffer);
                if(length > 0) {
                    log.info("after read...{}", channel.toString());
                    // 切换为读模式
                    byteBuffer.flip();
                    debugRead(byteBuffer);
                    // 切换为写模式
                    byteBuffer.clear();
                }
            }
        }
    }
}
