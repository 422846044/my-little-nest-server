package top.zhongyingjie.nest.pojo.dto;

import org.hibernate.validator.constraints.Length;
import top.zhongyingjie.common.annotation.ValStrArrCheck;
import top.zhongyingjie.common.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章分页查询参数
 *
 * @author zyj
 */
@EqualsAndHashCode(callSuper = true)
@Data
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
    @ValStrArrCheck(values = {"asc","desc"})
    private String sort;
}
