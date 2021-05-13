package com.geek.netty.char1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author: 赵静超
 * @date: 2021/5/13 21:06
 * @description: 两个channel之间进行通信
 */
public class FileChannelTransferToTest {
    public static void main(String[] args) {

        try {
            FileChannel fromChannel = new FileInputStream("data.txt").getChannel();
            FileChannel toChannel = new FileOutputStream("to.txt").getChannel();

            // transferTo方法效率高，因为底层会利用操作系统的零拷贝进行性能优化
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
