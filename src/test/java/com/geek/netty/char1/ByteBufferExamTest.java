package com.geek.netty.char1;

import java.nio.ByteBuffer;

import static com.geek.netty.ByteBufferUtil.debugAll;

/**
 * @author: 赵静超
 * @date: 2021/5/13 16:47
 * @description: 黏包，半包导致的
 * 网络上有多条数据发送给服务端，数据之间使用 \n 进行分隔
 *          但由于某种原因这些数据在接收时，被进行了重新组合，例如原始数据有3条为
 *              Hello,world\n
 *              I'm zhangsan\n
 *              How are you?\n
 *          变成了下面的两个 byteBuffer (黏包，半包)
 *              Hello,world\nI'm zhangsan\nHo
 *              w are you?\n
 *          现在要求你编写程序，将错乱的数据恢复成原始的按 \n 分隔的数据
 */
public class ByteBufferExamTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(buffer);
        buffer.put("w are you?\n".getBytes());
        split(buffer);
    }

    private static void split(ByteBuffer source) {
        // 切换为读模式
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // get方法不会导致指针偏移
            // 找到一条完整消息
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                // 把消息存入完整的ByteBuffer中
                ByteBuffer targetBuffer = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    targetBuffer.put(source.get());
                }
                debugAll(targetBuffer);
            }
        }
        // 切换为写模式
        source.compact();
    }
}
