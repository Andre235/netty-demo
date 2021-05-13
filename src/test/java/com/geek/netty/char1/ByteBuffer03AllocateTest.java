package com.geek.netty.char1;

import java.nio.ByteBuffer;

/**
 * @author: 赵静超
 * @date: 2021/5/13 15:37
 * @description:
 */
public class ByteBuffer03AllocateTest {
    public static void main(String[] args) {
        /**
         * class java.nio.HeapByteBuffer
         * 在堆内存上分配的内存空间，受GC影响,读写效率较低
         */
        System.out.println(ByteBuffer.allocate(16).getClass());
        /**
         * class java.nio.DirectByteBuffer
         * 在直接内存上分配的内存空间，不受GC影响，读写效率较高(少一次拷贝)
         * 分配内存时效率较低，如果使用不当可能产生内存泄漏
         */
        System.out.println(ByteBuffer.allocateDirect(16).getClass());
    }
}
