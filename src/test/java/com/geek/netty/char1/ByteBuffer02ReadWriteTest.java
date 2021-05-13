package com.geek.netty.char1;

import com.geek.netty.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * @author: 赵静超
 * @date: 2021/5/13 15:21
 * @description:
 */
@Slf4j
public class ByteBuffer02ReadWriteTest {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // put a
        buffer.put((byte) 0x61);
        ByteBufferUtil.debugAll(buffer);
        // put b c d
        buffer.put(new byte[] {0x62, 0x63, 0x64});
        ByteBufferUtil.debugAll(buffer);

        // 切换至读模式
        buffer.flip();
        log.info("buffer data: {}", (char) buffer.get());
        ByteBufferUtil.debugAll(buffer);
        // 切换至写模式
        buffer.compact();
        ByteBufferUtil.debugAll(buffer);
    }
}
