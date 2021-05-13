package com.geek.netty.char1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.geek.netty.ByteBufferUtil.debugAll;

/**
 * @author: 赵静超
 * @date: 2021/5/13 15:56
 * @description: String 转 ByteBuffer
 */
public class ByteBuffer04StringTest {
    public static void main(String[] args) {
        // 1.字符串直接转byteBuffer
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes());
        debugAll(buffer1);

        // 2.Charset
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        debugAll(buffer2);

        // 3.wrap
        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
        debugAll(buffer3);

        // ByteBuffer 2 String
        String string = StandardCharsets.UTF_8.decode(buffer3).toString();
        System.out.println(string);
    }
}
