package top.zhongyingjie.admin.dto;

import top.zhongyingjie.common.annotation.ValStrArrCheck;
import top.zhongyingjie.common.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章分页查询参数
 *
 * @author atulan_zyj
 * @date 2024/10/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticlePageQueryDTO extends PageQuery {

    private static final long serialVersionUID = -6477763564007502012L;

    /**
     * 关键字
     */
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
