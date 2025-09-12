package top.zhongyingjie.common.entity;

/**
 * 文章信息表
 *
 * @TableName article_info
 */
public class ArticleInfo extends BaseEntity {

    private static final long serialVersionUID = 8549537000412027970L;

    /**
     * 文章id
     */
    private Long id;
    /**
     * 文章分类
     */
    private Integer category;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 概要
     */
    private String summary;
    /**
     * 封面地址
     */
    private String cover;

    /**
     * 0删除,1正常,2草稿,3审批
     */
    private Integer status;

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public Integer getCategory() {
        return this.category;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getCover() {
        return this.cover;
    }

    public Integer getStatus() {
        return this.status;
    }

}
