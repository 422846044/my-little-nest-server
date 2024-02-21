package fun.dfwh.nest.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleListVO {
    private String id;
    private String title;
    private String summary;
    private String cover;
    private Integer category;
    private List<Integer> tags;
    private Date createTime;
}