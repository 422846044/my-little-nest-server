package top.zhongyingjie.common.entity;

/**
*
* @TableName article_tags_info
*/
public class ArticleTagsInfo extends BaseEntity {

    private static final long serialVersionUID = 3824907076983607827L;

    private Long id;

    /**
    * 文章id
    */
    private Long articleId;

    /**
    * 标签代码
    */
    private String tagsCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTagsCode() {
        return tagsCode;
    }

    public void setTagsCode(String tagsCode) {
        this.tagsCode = tagsCode;
    }
}
