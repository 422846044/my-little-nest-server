package top.zhongyingjie.admin.entity;

import java.io.Serializable;

import top.zhongyingjie.common.entity.BaseEntity;

/**
 * @TableName sys_conf_info
 */
public class SysConfInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1225617980547246797L;

    private Integer id;

    private String key;

    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
