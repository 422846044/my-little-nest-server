package fun.dfwh.admin.entity;

import fun.dfwh.common.entity.BaseEntity;

/**
* 
* @TableName user_info
*/
public class UserInfo extends BaseEntity {

    /**
    * 用户id
    */
    private Long id;
    /**
    * 用户名
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 昵称
    */
    private String nickname;
    /**
    * 头像
    */
    private String avatar;
    /**
    * 用户状态(0不可用，1正常)
    */
    private Integer status;

    /**
    * 用户id
    */
    public void setId(Long id){
    this.id = id;
    }

    /**
    * 用户名
    */
    public void setUsername(String username){
    this.username = username;
    }

    /**
    * 密码
    */
    public void setPassword(String password){
    this.password = password;
    }

    /**
    * 昵称
    */
    public void setNickname(String nickname){
    this.nickname = nickname;
    }

    /**
    * 头像
    */
    public void setAvatar(String avatar){
    this.avatar = avatar;
    }

    /**
    * 用户状态(0不可用，1正常)
    */
    public void setStatus(Integer status){
    this.status = status;
    }






    /**
    * 用户id
    */
    public Long getId(){
    return this.id;
    }

    /**
    * 用户名
    */
    public String getUsername(){
    return this.username;
    }

    /**
    * 密码
    */
    public String getPassword(){
    return this.password;
    }

    /**
    * 昵称
    */
    public String getNickname(){
    return this.nickname;
    }

    /**
    * 头像
    */
    public String getAvatar(){
    return this.avatar;
    }

    /**
    * 用户状态(0不可用，1正常)
    */
    public Integer getStatus(){
    return this.status;
    }





}
