package fun.dfwh.admin.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author atulan_zyj
 * @date 2024/11/6
 */
@Data
public class ArticleInfoVO implements Serializable {

    private static final long serialVersionUID = 544070305674823348L;

    private Long id;

    private String title;

    private String content;

    private String category;

    private List<String> tags;

}
