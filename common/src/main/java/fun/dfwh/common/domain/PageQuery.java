package fun.dfwh.common.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询基类
 *
 * @author atulan_zyj
 * @date 2024/10/18
 */
@Data
public class PageQuery implements Serializable {
    private static final long serialVersionUID = 5135110693188015645L;

    private Integer pageNum;

    private Integer pageSize;


}
