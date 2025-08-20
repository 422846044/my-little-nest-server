package top.dfwx.admin.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.dfwx.admin.event.ArticleEvent;
import top.dfwx.admin.service.UpdatesInfoService;
import top.dfwx.common.enums.UpdatesType;

/**
 * @author atulan_zyj
 * @date 2025/8/20
 */
@Service
@Slf4j
public class ArticleEventListener {

    @Autowired
    private UpdatesInfoService updatesInfoService;

    @Async
    @EventListener
    public void handlerArticleEvent(ArticleEvent articleEvent){
        log.info("开始执行文章事件监听处理");
        /*switch (articleEvent){
            case AddArticleEvent:{
                break;
            }
        }*/
        updatesInfoService.add("", UpdatesType.ADD_ARTICLE);
        log.info("文章事件监听处理结束");
    }
}
