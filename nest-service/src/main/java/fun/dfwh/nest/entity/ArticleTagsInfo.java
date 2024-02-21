package fun.dfwh.nest.entity;

import fun.dfwh.common.entity.BaseDomain;

import java.util.Date;

/**
* 
* @TableName article_tags_info
*/
public class ArticleTagsInfo extends BaseDomain{

    /**
    * 
    */
    private Long articleId;
    /**
    * 
    */
    private Integer tagsCode;

    /**
    * 
    */
    public void setArticleId(Long articleId){
    this.articleId = articleId;
    }

    /**
    * 
    */
    public void setTagsCode(Integer tagsCode){
    this.tagsCode = tagsCode;
    }






    /**
    * 
    */
    public Long getArticleId(){
    return this.articleId;
    }

    /**
    * 
    */
    public Integer getTagsCode(){
    return this.tagsCode;
    }





}
