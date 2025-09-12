package top.zhongyingjie.admin.dto;

import top.zhongyingjie.common.validator.group.Add;
import top.zhongyingjie.common.validator.group.Draft;
import top.zhongyingjie.common.validator.group.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 文章信息数据传输对象
 *
 * @author Kong
 */
public class ArticleDTO implements Serializable {

    private static final long serialVersionUID = 1921018368625001032L;

    /**
     * 文章id
     */
    @NotNull(groups = Update.class, message = "文章id不能为空")
    private Long id;

    /**
     * 文章分类
     */
    @NotNull(groups = {Add.class, Update.class}, message = "分类编码不能为空")
    private Integer category;

    /**
     * 标题
     */
    @NotEmpty(groups = {Add.class, Update.class, Draft.class}, message = "标题不能为空")
    private String title;

    /**
     * 内容
     */
    @NotEmpty(groups = {Add.class, Update.class}, message = "内容不能为空")
    private String content;

    /**
     * 标签
     */
    @NotNull(groups = {Add.class, Update.class}, message = "缺少标签参数")
    private List<String> tags;

    /**
     * 概要
     */
    private String summary;

    /**
     * 封面地址
     */
    private String cover;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
