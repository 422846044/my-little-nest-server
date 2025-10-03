package top.zhongyingjie.admin.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.zhongyingjie.admin.event.AddArticleEvent;
import top.zhongyingjie.admin.event.ArticleEvent;
import top.zhongyingjie.admin.event.EditArticleEvent;
import top.zhongyingjie.admin.service.UpdatesInfoService;
import top.zhongyingjie.common.enums.UpdatesType;

/**
 * 文章事件监听器
 *
 * @author Kong
 */
@Component
public class ArticleEventListener {

    private static final Logger log = LoggerFactory.getLogger(ArticleEventListener.class);

    @Autowired
    private UpdatesInfoService updatesInfoService;

    /**
     * 异步添加文章的新增、编辑动态
     *
     * @param articleEvent 文章事件
     */
    @Async("viewCountAsyncService")
    @EventListener
    public void handlerArticleEvent(ArticleEvent articleEvent) {
        log.info("开始执行文章事件监听处理");
        if (articleEvent instanceof AddArticleEvent) {
            updatesInfoService.add(((AddArticleEvent) articleEvent).getTitle(), UpdatesType.ADD_ARTICLE);
        } else if (articleEvent instanceof EditArticleEvent) {
            updatesInfoService.add(((EditArticleEvent) articleEvent).getTitle(), UpdatesType.EDIT_ARTICLE);
        }
        log.info("文章事件监听处理结束");
    }

}
