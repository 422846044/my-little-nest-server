package top.zhongyingjie.nest.event;

/**
 * 文章浏览事件
 *
 * @author Kong
 */
public class ArticleViewEvent extends ViewEvent {

    private final String ip;

    private final String title;

    private final Long timestamp = System.currentTimeMillis();

    public ArticleViewEvent(String ip, String title) {
        this.ip = ip;
        this.title = title;
    }


    public String getIp() {
        return ip;
    }

    public String getTitle() {
        return title;
    }

    public Long getTimestamp() {
        return timestamp;
    }

}
