package fun.dfwh.admin.entity;

import fun.dfwh.common.entity.BaseDomain;

import java.util.Date;

/**
* 
* @TableName user_role_info
*/
public class UserRoleInfo extends BaseDomain{

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
