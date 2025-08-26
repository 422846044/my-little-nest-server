package top.zhongyingjie.common.entity;

/**
* 
* @TableName article_info
*/
public class ArticleInfo extends BaseEntity {

    /**
    * 文章id
    */
    private Long id;
    /**
    * 文章分类
    */
    private Integer category;
    /**
    * 标题
    */
    private String title;
    /**
    * 内容
    */
    private String content;
    /**
    * 概要
    */
    private String summary;
    /**
    * 封面地址
    */
    private String cover;

    /**
    * 0删除,1正常,2草稿,3审批
    */
    private Integer status;

    /**
    * 文章id
    */
    public void setId(Long id){
    this.id = id;
    }

    /**
    * 文章分类
    */
    public void setCategory(Integer category){
    this.category = category;
    }

    /**
    * 标题
    */
    public void setTitle(String title){
    this.title = title;
    }

    /**
    * 内容
    */
    public void setContent(String content){
    this.content = content;
    }

    /**
    * 概要
    */
    public void setSummary(String summary){
    this.summary = summary;
    }

    /**
    * 封面地址
    */
    public void setCover(String cover){
    this.cover = cover;
    }



    /**
    * 0删除,1正常,2草稿,3审批
    */
    public void setStatus(Integer status){
    this.status = status;
    }






    /**
    * 文章id
    */
    public Long getId(){
    return this.id;
    }

    /**
    * 文章分类
    */
    public Integer getCategory(){
    return this.category;
    }

    /**
    * 标题
    */
    public String getTitle(){
    return this.title;
    }

    /**
    * 内容
    */
    public String getContent(){
    return this.content;
    }

    /**
    * 概要
    */
    public String getSummary(){
    return this.summary;
    }

    /**
    * 封面地址
    */
    public String getCover(){
    return this.cover;
    }



    /**
    * 0删除,1正常,2草稿,3审批
    */
    public Integer getStatus(){
    return this.status;
    }





}
