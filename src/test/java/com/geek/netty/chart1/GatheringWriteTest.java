package com.geek.netty.chart1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author: 赵静超
 * @date: 2021/5/13 16:38
 * @description: 分散读、集中写的目的是减少数据在buffer的拷贝次数，进而提高数据的读写效率
 */
public class GatheringWriteTest {
    public static void main(String[] args) {
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("word");
        ByteBuffer buffer3 = StandardCharsets.UTF_8.encode("你好");

        try (FileChannel channel = new RandomAccessFile("word2.txt", "rw").getChannel()) {
            channel.write((new ByteBuffer[]{buffer1, buffer2, buffer3}));
        } catch (IOException e) {
        }
    }
}
