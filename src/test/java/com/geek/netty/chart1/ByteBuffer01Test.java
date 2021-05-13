package com.geek.netty.chart1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: 赵静超
 * @date: 2021/5/13 13:44
 * @description:
 */
@Slf4j
public class ByteBuffer01Test {

    public static void main(String[] args) {
        // 获取fileChannel
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            // 准备缓冲区(给buffer分配10个字节的内存空间)
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                // channel从文件读取数据，同时向buffer写入数据
                int length = channel.read(buffer);
                log.info("向buffer写入的字节长度：{}", length);
                if(length == -1) {
                    break;
                }
                // 读取缓冲区内容
                buffer.flip(); // 缓冲区切换至读模式
                while (buffer.hasRemaining()) { // 是否还有剩余未读取的字节
                    log.info("缓冲区数据：{}", (char) buffer.get());
                }
                // 将buffer切换为写模式
                buffer.clear();
            }
        } catch (IOException e) {
        }
    }
}
