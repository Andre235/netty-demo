package com.geek.netty.chart4;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author: 赵静超
 * @date: 2021/5/13 21:49
 * @description:
 */
public class Client {

    @SneakyThrows
    public static void main(String[] args) {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8080));
        socketChannel.write(Charset.defaultCharset().encode("hello"));
        System.out.println("waiting...");
    }
}
