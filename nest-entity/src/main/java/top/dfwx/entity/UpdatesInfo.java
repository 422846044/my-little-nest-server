package top.dfwx.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.dfwx.common.entity.BaseEntity;

/**
 * @TableName updates_info
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdatesInfo extends BaseEntity {
    private static final long serialVersionUID = -4159296099285830795L;

    private Long id;

    private Integer type;

    private String title;


}