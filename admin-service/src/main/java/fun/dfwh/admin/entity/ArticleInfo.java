package fun.dfwh.admin.entity;


import fun.dfwh.common.entity.BaseDomain;


/**
* 
* @TableName article_info
*/
public class ArticleInfo extends BaseDomain {

    /**
    * 文章id
    */
    private Long id;
    /**
    * 文章分类
    */
    private Integer gategory;
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
    * 目录
    */
    private String directory;
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
    public void setGategory(Integer gategory){
    this.gategory = gategory;
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
    * 目录
    */
    public void setDirectory(String directory){
    this.directory = directory;
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
    public Integer getGategory(){
    return this.gategory;
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
    * 目录
    */
    public String getDirectory(){
    return this.directory;
    }

    /**
    * 0删除,1正常,2草稿,3审批
    */
    public Integer getStatus(){
    return this.status;
    }





}
