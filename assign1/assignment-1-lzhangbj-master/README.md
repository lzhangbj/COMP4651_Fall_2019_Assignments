# COMP4651 Assignment-1 (2 questions, 6 marks)

### Deadline: Oct 4, 23:59 (Friday)
---

### Name: ZHANG Lin 
### Student Id: 20413320
---

## Question 1: Measure the EC2 CPU and Memory performance

1. (1 mark) Report the name of measurement tool used in your measurements (you are free to choose any open source measurement software as long as it can measure CPU and memory performance). Please describe your configuration of the measurement tool, and explain why you set such a value for each parameter. Explain what the values obtained from measurement results represent (e.g., the value of your measurement result can be the execution time for a scientific computing task, a score given by the measurement tools or something else).

    * Tool: [sysbench](https://github.com/akopytov/sysbench)

    * Config:  `CPU performance test`  Commands *sysbench --test=cpu --num-threads=4 --cpu-max-prime=10000 run*  
        
        We are using 4 thread for faster computation without affecting reletivity of the final results, meaning they are still comparable. The cpu task is to calculate prime numbers up to a value, which is 10000 in our case. Sysbench will verify prime numbers by doing standard division of the number by all numbers between 2 and the square root of the number. If any number gives a remainder of 0, the next number is calculated. This will put some stress on the CPU, helping us to examine the cpu.  performance.

        `Memory performance test`
        Commands *sysbench --test=memory --num-threads=4 --memory-total-size=10G --memory-oper=write --memory-scope=global run* 
        
        This command tests the performance of writing a total size of 10G contents. *memory-total-size* is the total size of meory to write, *memory-oper* specifies the operation, which is writing in our case, *memory-scope* is global, meaning each thread uses a globally allocated memory block.      

    * Measurement Values:   

    | `CPU events per second` |  `Writing Time/s` |
    |------------------------|--------------------|
    | number of events executed per second  | time cost of writing the data |


2. (1 mark) Run your measurement tool on general purpose `t3.medium`, `m5.large`, and `c5d.large` Linux instances, respectively, and find the performance differences among these instances. Launch all instances in the **US East (N. Virginia)** region. Does the performance of EC2 instances increase commensurate with the increase of the number of ECUs and memory resource?  

    In order to answer this question, you need to complete the following table by filling out blanks with the measurement results corresponding to each instance type.

    | Size      | CPU performance | Memory performance/s |
    |-----------|-----------------|--------------------|
    | `t3.medium` |     1681.34     |     1.7234        |
    | `m5.large`  |     1650.07     |     1.6802        |
    | `c5d.large` |     1816.92     |     1.4942        |
 
    > Region: US East (N. Virginia) 
     The performance of cpu is basically following the #ecus's order. To be more specifically, cpu performance c5d.large > m5.large ~ t3.medium, num of ecus c5d.large = 9 > m5.large = 8 while t3.medium's ecu number is a variable. 
     The performance of memory test, however, does not strictly follow the memory size order. m5.large is faster than t3.medium since it has 8G memory instead of 4 in t3.medium. As for c5d.large, although having only 4G memory, it performance is superior to the other two since c5d.large is equipped with ssd instead of EBS-Only in t3.medium and m5.large. 

## Question 2: Measure the EC2 Network performance

1. (1 mark) The metrics of network performance include **TCP bandwidth** and **round-trip time (RTT)**. Within the same region, what network performance is experienced between instances of the same type and different types? In order to answer this question, you need to complete the following table.  

    | Type          | TCP b/w (Gbps) | RTT (us) |
    |---------------|----------------|----------|
    | `t3.medium`-`t3.medium` |     4.8           |     292     |
    | `m5.large`-`m5.large`  |      4.97          |    280      |
    | `c5n.large`-`c5n.large` |     9.42           |    150      |
    | `t3.medium`-`c5n.large`   |     4.94           |   284       |
    | `m5.large`-`c5n.large`  |       4.97         |   273       |
    | `m5.large`-`t3.medium` |       4.97         |   274       |

    > Region: US East (N. Virginia)
        The network performance generally only differs in the instances of server and client instances, rather than whether the two instances' types are the same or not. Specifically, the network performance of c5n.large->c5n.large is much better, while all other combinations are worse. We know that the network performance is bounded by the worse one between the server and client. Since t3.medium and m5.large generally perform worse, the TCP bandwidth of network transfer using them as either one of server or client will be worse. While c5n.large's network performance is better, only combination of c5n.large->c5n.large has a larger bandwidth. This accounts for the reason.

2. (1 mark) What about the network performance for instances deployed in different regions? In order to answer this question, you need to complete the following table.

    | Connection | TCP b/w (Mbps)  | RTT (us) |
    |------------|-----------------|--------------------|
    | N. Virginia-Tokyo |     10.4            |      151815              |
    | N. Virginia-N. Virginia  |         4970        |       289             |
    | Tokyo-Tokyo |        9600         |      148              |
 
    > All instances are `c5.large`.
        network transfer between different regions needs to cross a much longer distance, thus performing much worse, as Virginia->Tokyo shows.
 
3. (1 mark) Is the network performance consistent over time? In order to answer this question, you need to complete the following table.

    | Time | TCP b/w (Gbps)  | RTT (us) |
    |------------|-----------------|--------------------|
    | Morning |    9.42             |       145             |
    | Afternoon  |      9.42           |     149               |
    | Evening |        9.42         |        163            |
 
    > Region: US East (N. Virginia); Connection: `c5n.large`-`c5n.large`
    the network performances are approximately the same over time.
 
4. (1 mark) *Open-end question:* In the above three sub-questions, you have measured network performance in different scenarios. Observe the values in each table, and try to explain why the network performance varies in different scenarios?

    > We can draw 3 conclusions from the above 3 sub questions. From question 1, we can see that TCP bandwidth is usually bounded by the worse one in server and client, thus only in the circumstances where both client and server have superior network performance will the TCP bandwidth be larger. Conclusion based on question 2 is that cross-region network communication is usually slower, showing that cross-region network communication, which usually has longer distance, is slower. The finally conclusion resulted from question 3 is that network performance usually does not vary over time. This is reasonable since machines do not sleep. 
