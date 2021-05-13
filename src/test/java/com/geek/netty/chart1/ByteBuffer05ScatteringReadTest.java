package com.geek.netty.chart1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static com.geek.netty.ByteBufferUtil.debugAll;

/**
 * @author: 赵静超
 * @date: 2021/5/13 16:08
 * @description: 分散分批次读取字节
 */
public class ByteBuffer05ScatteringReadTest {
    public static void main(String[] args) {
        try (FileChannel channel = new RandomAccessFile("words.txt", "r").getChannel()) {
            ByteBuffer buffer1 = ByteBuffer.allocate(3);
            ByteBuffer buffer2 = ByteBuffer.allocate(3);
            ByteBuffer buffer3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[] {buffer1, buffer2, buffer3});
            buffer1.flip();
            buffer2.flip();
            buffer3.flip();
            debugAll(buffer1);
            debugAll(buffer2);
            debugAll(buffer3);
        } catch (IOException e) {
        }
    }
}
