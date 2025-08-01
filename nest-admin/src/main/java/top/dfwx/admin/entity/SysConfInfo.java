package top.dfwx.admin.entity;

import java.io.Serializable;

import top.dfwx.common.entity.BaseEntity;
import lombok.Data;

/**
 * @TableName sys_conf_info
 */
@Data
public class SysConfInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1225617980547246797L;

    private Integer id;

    private String key;

    private String value;

}
