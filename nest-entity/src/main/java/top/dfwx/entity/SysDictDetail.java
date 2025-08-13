package top.dfwx.entity;

import top.dfwx.common.entity.BaseEntity;

/**
* 
* @TableName sys_dict_detail
*/
public class SysDictDetail extends BaseEntity {

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

    /**
    * 主键id
    */
    public void setId(Integer id){
    this.id = id;
    }

    /**
    * 字典编码
    */
    public void setDictCode(String dictCode){
    this.dictCode = dictCode;
    }

    /**
    * 编码
    */
    public void setCode(String code){
    this.code = code;
    }

    /**
    * 名称
    */
    public void setName(String name){
    this.name = name;
    }

    /**
    * 排序
    */
    public void setSort(Integer sort){
    this.sort = sort;
    }

    /**
    * 状态（1--正常0--不使用）
    */
    public void setStatus(Integer status){
    this.status = status;
    }

    /**
    * 主键id
    */
    public Integer getId(){
    return this.id;
    }

    /**
    * 字典编码
    */
    public String getDictCode(){
    return this.dictCode;
    }

    /**
    * 编码
    */
    public String getCode(){
    return this.code;
    }

    /**
    * 名称
    */
    public String getName(){
    return this.name;
    }

    /**
    * 排序
    */
    public Integer getSort(){
    return this.sort;
    }

    /**
    * 状态（1--正常0--不使用）
    */
    public Integer getStatus(){
    return this.status;
    }
} 