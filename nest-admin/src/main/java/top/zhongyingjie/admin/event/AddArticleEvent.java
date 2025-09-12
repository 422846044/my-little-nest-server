package top.zhongyingjie.admin.event;

/**
 * 添加文章事件
 *
 * @author Kong
 */
public class AddArticleEvent extends ArticleEvent {

    private final String title;

    public AddArticleEvent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
