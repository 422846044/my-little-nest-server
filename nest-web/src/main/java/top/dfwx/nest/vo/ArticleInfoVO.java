package top.dfwx.nest.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ArticleInfoVO implements Serializable {
    private static final long serialVersionUID = -5708668344898639308L;
    private Long id;
    private String title;
    private String content;
    private Integer category;
    private List<Integer> tags;
    private String author;
    private Date createTime;

}
