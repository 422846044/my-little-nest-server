package fun.dfwh.nest.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ArticleInfoVO implements Serializable {
    private Long id;
    private String title;
    private String content;
    private Integer category;
    private List<Integer> tags;
    private String author;
    private Date createTime;

}
