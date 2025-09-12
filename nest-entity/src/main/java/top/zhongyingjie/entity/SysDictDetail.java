package top.zhongyingjie.entity;

import top.zhongyingjie.common.entity.BaseEntity;

/**
 *
 * @TableName sys_dict_detail
 */
public class SysDictDetail extends BaseEntity {

    private static final long serialVersionUID = 7851783347660141742L;

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态（1--正常0--不使用）
     */
    private Integer status;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return this.id;
    }

    public String getDictCode() {
        return this.dictCode;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public Integer getSort() {
        return this.sort;
    }

    public Integer getStatus() {
        return this.status;
    }
}
