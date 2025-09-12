package top.zhongyingjie.admin.entity;

import top.zhongyingjie.common.entity.BaseEntity;

/**
 *
 * @TableName sys_dict
 */
public class SysDict extends BaseEntity {

    private static final long serialVersionUID = -2789959311075825224L;

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态（1--正常0--不使用）
     */
    private Integer status;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getStatus() {
        return this.status;
    }

}
