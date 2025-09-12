package top.zhongyingjie.nest.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 文章信息视图对象
 *
 * @author Kong
 */
public class ArticleInfoVO implements Serializable {
    private static final long serialVersionUID = -5708668344898639308L;
    private Long id;
    private String title;
    private String content;
    private Integer category;
    private List<Integer> tags;
    private String author;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
