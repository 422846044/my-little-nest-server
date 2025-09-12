package top.zhongyingjie.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * ID生成器:雪花算法代码实现
 *
 * @author Kong
 */
@Component
public class IdWorker {

    /**
     * tw_epoch:  时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    private static final long TW_EPOCH = 1677235541275L;
    /**
     * 机器标识位数
     */
    private static final long WORKER_ID_BITS = 5L;

    /**
     * 数据中心标识位数
     */
    private static final long DATA_CENTER_ID_BITS = 5L;

    /**
     * 机器ID最大值
     */
    private static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);

    /**
     * 数据中心ID最大值
     */
    private static final long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);

    /**
     * 毫秒内自增位
     */
    private static final long SEQUENCE_BITS = 12L;

    /**
     * 机器ID偏左移12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 数据中心ID左移17位
     */
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * 时间毫秒左移22位
     */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

    private static final long FFFF = 0xffff;

    private static final long FF00 = 0x0000FF00;

    private static final int EIGHT = 8;

    private static final int SIX = 6;

    private static final Logger log = LoggerFactory.getLogger(IdWorker.class);
    /**
     * 上次生产id时间戳
     */
    private static long lastTimestamp = -1L;
    /**
     * 0，并发控制
     */
    private long sequence = 0L;

    /**
     * 机器id
     */
    private final long workerId;

    /**
     * 数据标识id部分
     */
    private final long datacenterId;

    public IdWorker() {
        this.datacenterId = getDataCenterId();
        this.workerId = getMaxWorkerId(datacenterId);
    }

    /**
     * @param workerId     工作机器ID
     * @param datacenterId 序列号
     */
    public IdWorker(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATA_CENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 获取下一个ID
     *
     * @return ID
     */
    public synchronized Long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                            lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        return ((timestamp - TW_EPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (datacenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 获取 maxWorkerId
     *
     * @param datacenterId 数据中心id
     * @return 最大机器id
     */
    protected static long getMaxWorkerId(long datacenterId) {
        StringBuffer mpId = new StringBuffer();
        mpId.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            /*
             * GET jvmPid
             */
            mpId.append(name.split("@")[0]);
        }
        /*
         * MAC + PID 的hashcode 获取16个低位
         */
        return (mpId.toString().hashCode() & FFFF) % (MAX_WORKER_ID + 1);
    }

    /**
     * 数据标识id部分
     *
     * @return 获取数据标识id
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    protected static long getDataCenterId() {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (FF00 & (((long) mac[mac.length - 2]) << EIGHT))) >> SIX;
                id = id % (MAX_DATA_CENTER_ID + 1);
            }
        } catch (Exception e) {
            log.info(" getDatacenterId: {}", e.getMessage(), e);
        }
        return id;
    }
}
