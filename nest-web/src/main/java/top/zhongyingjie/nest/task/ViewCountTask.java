package top.zhongyingjie.nest.task;

import cn.hutool.core.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.zhongyingjie.nest.event.listener.ViewEventListener;

import java.util.Date;

/**
 * 浏览统计任务
 *
 * @author Kong
 */
@Component
public class ViewCountTask {

    private static final Logger log = LoggerFactory.getLogger(ViewCountTask.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 日浏览数据清算任务
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void countDayView() {
        log.info("日期[{}]统计浏览量为[{}]", DateUtil.formatDate(new Date()),
                redisTemplate.opsForZSet().count(ViewEventListener.DAY_VIEW_COUNT_KEY, 0, -1));
        // 清除当日统计
        redisTemplate.delete(ViewEventListener.DAY_VIEW_COUNT_KEY);
    }
}
