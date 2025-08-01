package top.dfwx.admin.entity;

import top.dfwx.common.entity.BaseEntity;

import java.util.Date;

/**
* 
* @TableName user_role_info
*/
public class UserRoleInfo extends BaseEntity {

    /**
    * 
    */
    private Long userId;
    /**
    * 
    */
    private Integer roleId;
    /**
    * 
    */
    private Date createdTime;

    /**
    * 
    */
    public void setUserId(Long userId){
    this.userId = userId;
    }

    /**
    * 
    */
    public void setRoleId(Integer roleId){
    this.roleId = roleId;
    }


    /**
    * 
    */
    public void setCreatedTime(Date createdTime){
    this.createdTime = createdTime;
    }




    /**
    * 
    */
    public Long getUserId(){
    return this.userId;
    }

    /**
    * 
    */
    public Integer getRoleId(){
    return this.roleId;
    }


    /**
    * 
    */
    public Date getCreatedTime(){
    return this.createdTime;
    }



}
