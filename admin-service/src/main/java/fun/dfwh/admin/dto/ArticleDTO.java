package fun.dfwh.admin.dto;

import fun.dfwh.common.validator.group.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class ArticleDTO implements Serializable {

    private static final long serialVersionUID = 1921018368625001032L;

    /**
     * 文章id
     */
    @NotNull(groups = Update.class,message = "文章id不能为空")
    private Long id;

    /**
     * 文章分类
     */
    @NotNull(message = "分类编码不能为空")
    private Integer category;

    /**
     * 标题
     */
    @NotEmpty(message = "标题不能为空")
    private String title;

    /**
     * 内容
     */
    @NotEmpty(message = "内容不能为空")
    private String content;

    /**
     * 标签
     */
    @NotNull(message = "缺少标签参数")
    private List<String> tags;

    /**
     * 概要
     */
    private String summary;

    /**
     * 封面地址
     */
    private String cover;

}
