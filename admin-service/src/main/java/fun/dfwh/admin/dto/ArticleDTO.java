package fun.dfwh.admin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleDTO implements Serializable {
    /**
     * 文章id
     */
    private Long id;

    /**
     * 文章分类
     */
    private Integer gategory;

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

    private static final long serialVersionUID = 1L;
}
