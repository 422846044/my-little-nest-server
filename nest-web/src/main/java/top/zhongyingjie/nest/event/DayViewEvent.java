package top.zhongyingjie.nest.event;

/**
 * 日浏览事件
 *
 * @author Kong
 */
public class DayViewEvent extends ViewEvent {

    private final String ip;

    private final Long timestamp = System.currentTimeMillis();

    public DayViewEvent(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
