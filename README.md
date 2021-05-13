### NIO基础

### 1. 三大组件

##### 1.1 Channel 

Channel是一个**双向数据通信管道**，类似于`Stream`，可以将数据从Channel读取Buffer，也可以将Buffer中的数据写入Channel。Channel比Stream更为底层

常见的Channel类型有

1. FileChannel

   文件数据传输通道

2. DategramChannel

   UDP通信数据传输通道

3. SocketChannel

   客户端、服务器端TCP通信数据传输通道

4. ServerSocketChannel

   服务器端TCP通信数据传输通道

##### 1.2 Buffer

Buffer则用来缓冲读写数据

常见的Buffer类型有

1. ByteBuffer
   1. MappedByteBuffer
   2. DirectByteBuffer
   3. HeapByteBuffer
2. ShortBuffer
3. IntBuffer
4. LongBuffer
5. ...

##### 1.3 Selector

Selector单从字面意思不太好理解，需要结合服务器设计的演化来理解它的用途

1. 多线程版服务端设计

   ```mermaid
   graph TD
   subgraph 多线程版
   t1(thread) --> s1(socket1)
   t2(thread) --> s2(socket2)
   t3(thread) --> s3(socket3)
   end
   ```

   #### ⚠️ 多线程版缺点

   * 内存占用高
   * 线程上下文切换成本高
   * 只适合连接数少的场景

2. 线程池版服务端设计

   ```mermaid
   graph TD
   subgraph 线程池版
   t4(thread) --> s4(socket1)
   t5(thread) --> s5(socket2)
   t4(thread) -.-> s6(socket3)
   t5(thread) -.-> s7(socket4)
   end
   ```

   #### ⚠️ 线程池版缺点

   * 阻塞模式下，线程仅能处理一个 socket 连接
   * 仅适合短连接场景

3. Selector

   ```mermaid
   graph TD
   subgraph selector 版
   thread --> selector
   selector --> c1(channel)
   selector --> c2(channel)
   selector --> c3(channel)
   end
   ```

   Selector的作用是配合一个线程来管理多个Channel，获取这些Channel上发生的事件。这些Channel工作在非阻塞模式下，不会让一个线程吊死在一个Channel上。*适合连接数特别多，但是流量低的场景*

   调用selector的select会阻塞知道这些Channel发生了读写事件，这些事件发生后，selector就会返回事件

### 2. ByteBuffer

##### 2.1黏包&半包

> 网络上有多条数据发送给服务端，数据之间使用 \n 进行分隔
>          但由于某种原因这些数据在接收时，被进行了重新组合，例如原始数据有3条为
>              Hello,world\n
>              I'm zhangsan\n
>              How are you?\n
>          变成了下面的两个 byteBuffer (黏包，半包)
>              Hello,world\nI'm zhangsan\nHo
>              w are you?\n
>          现在要求你编写程序，将错乱的数据恢复成原始的按 \n 分隔的数据

1. 黏包

   服务器为了提高发送数据的效率，将多条数据合并在一起发送数据

2. 半包

   buffer缓冲区的容量大小的限制导致了半包情况的出现

#### 3. 文件编程

### 4. 网络编程

#### 4.1阻塞&非阻塞

##### 阻塞

> 阻塞的表现就是线程暂停了，线程处于闲置状态，暂停期间不会占用CPU

1. 在阻塞模式下相关的方法会导致线程暂停

   ```java
   // 在没有建立连接时让线程暂停
   SocketChannel socketChannel = serverSocketChannel.accept();
   ```

   ```java
   // 在没有数据可以读取时让线程暂停
   channel.read(byteBuffer);
   ```

2. 单线程环境下，阻塞方法之前相互影响，几乎不能正常工作，*需要多线程的支持*

3. 但是在多线程环境下，又会出现新的问题，具体体现在以下方面

   1. 32位的JVM，一个线程占用320k；64位JVM，一个线程占用1024K，如果连接数量过多，必然会导致OOM。并且如果线程数量过多的话，反而会因为频繁地进行上下文切换而导致性能降低
   2. 可以采用线程池技术来减少线程池数量和频繁地线程上下文切换。但是治标不治本，如果有很多连接的建立，但是长时间处于inactive状态，会阻塞线程池中的所有线程，因此不适合长链接，适合短连接。

### 5. NIO vs BIO





