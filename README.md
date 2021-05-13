### NIO基础

#### 1. 三大组件

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

   ![](https://i.loli.net/2021/05/12/Iyowp7qVc1xNbnX.png)

2. 线程池版服务端设计

   ![image-20210512224646919](https://i.loli.net/2021/05/12/Iyowp7qVc1xNbnX.png)

3.  Selector

   Selector的作用是配合一个线程来管理多个Channel，获取这些Channel上发生的事件。这些Channel工作在非阻塞模式下，不会让一个线程吊死在一个Channel上。*适合连接数特别多，但是流量低的场景*

   调用selector的select会阻塞知道这些Channel发生了读写事件，这些事件发生后，selector就会返回事件

#### 2. ByteBuffer

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

#### 4. 网络编程

#### 5. NIO vs BIO





