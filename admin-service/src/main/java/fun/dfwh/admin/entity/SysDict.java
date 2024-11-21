package fun.dfwh.admin.entity;

import fun.dfwh.common.entity.BaseEntity;

/**
* 
* @TableName sys_dict
*/
public class SysDict extends BaseEntity {

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

    /**
    * 主键id
    */
    public void setId(Integer id){
    this.id = id;
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
    * 描述
    */
    public void setDescription(String description){
    this.description = description;
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
    * 描述
    */
    public String getDescription(){
    return this.description;
    }

    /**
    * 状态（1--正常0--不使用）
    */
    public Integer getStatus(){
    return this.status;
    }





}
