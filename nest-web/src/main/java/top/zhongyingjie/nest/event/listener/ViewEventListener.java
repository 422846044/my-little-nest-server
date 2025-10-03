package top.zhongyingjie.nest.event.listener;

import cn.hutool.core.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.zhongyingjie.nest.event.ArticleViewEvent;
import top.zhongyingjie.nest.event.DayViewEvent;
import top.zhongyingjie.nest.event.ViewEvent;

import java.util.Date;
import java.util.Objects;

/**
 * 浏览事件监听器
 *
 * @author Kong
 */
@Component
public class ViewEventListener {

    private static final Logger log = LoggerFactory.getLogger(ViewEventListener.class);

    public static final String ALL_VIEW_COUNT_KEY = "allViewCount";

    public static final String IP_VIEW_COUNT_KEY = "ipViewCount";

    public static final String DAY_VIEW_COUNT_KEY = "dayViewCount";

    @Autowired
    private RedisTemplate<String, Long> longRedisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 浏览事件监听处理
     *
     * @param event 浏览事件
     */
    @Async("viewCountAsyncService")
    @EventListener
    public void handlerViewEvent(ViewEvent event) {
        if (event instanceof DayViewEvent) {
            DayViewEvent dayViewEvent = (DayViewEvent) event;
            String ip = dayViewEvent.getIp();
            // 当指定的key不存在 ‌或 当key存在但有序集合中不包含目标元素时‌返回null
            if (Objects.isNull(stringRedisTemplate.opsForZSet().score(DAY_VIEW_COUNT_KEY, ip))) {
                stringRedisTemplate.opsForZSet().add(DAY_VIEW_COUNT_KEY, ip, dayViewEvent.getTimestamp());
                stringRedisTemplate.opsForZSet().incrementScore(IP_VIEW_COUNT_KEY, ip, 1);
                longRedisTemplate.opsForValue().increment(ALL_VIEW_COUNT_KEY, 1);
            }
        } else if (event instanceof ArticleViewEvent) {
            ArticleViewEvent articleViewEvent = (ArticleViewEvent) event;
            log.info("文章浏览记录->IP[{}],标题[{}],时间[{}]", articleViewEvent.getIp(),
                    articleViewEvent.getTitle(), DateUtil.formatDateTime(new Date(articleViewEvent.getTimestamp())));
        }
    }
}
