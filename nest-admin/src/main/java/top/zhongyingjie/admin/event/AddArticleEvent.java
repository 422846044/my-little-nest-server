package top.zhongyingjie.admin.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author atulan_zyj
 * @date 2025/8/20
 */
@AllArgsConstructor
@Getter
public class AddArticleEvent extends ArticleEvent{

    private String title;

}
