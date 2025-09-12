package top.zhongyingjie.admin.event;

/**
 * 更新文章事件
 *
 * @author Kong
 */
public class EditArticleEvent extends ArticleEvent {

    private final String title;

    public EditArticleEvent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
