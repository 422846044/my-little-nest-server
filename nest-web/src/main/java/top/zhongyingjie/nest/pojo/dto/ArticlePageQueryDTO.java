package top.zhongyingjie.nest.pojo.dto;

import org.hibernate.validator.constraints.Length;
import top.zhongyingjie.common.annotation.ValStrArrCheck;
import top.zhongyingjie.common.domain.PageQuery;

/**
 * 文章分页查询参数
 *
 * @author Kong
 */
public class ArticlePageQueryDTO extends PageQuery {

    private static final long serialVersionUID = -6477763564007502012L;

    /**
     * 关键字
     */
    @Length(max = 20, message = "超出关键字最大长度(20)")
    private String keyword;

    /**
     * 分类
     */
    private Integer category;

    /**
     * 标签
     */
    private String tag;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 月份
     */
    private Integer month;

    /**
     * 排序字段
     */
    @ValStrArrCheck(values = {"create_time"})
    private String order;

    /**
     * 排序方式
     */
    @ValStrArrCheck(values = {"asc", "desc"})
    private String sort;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
