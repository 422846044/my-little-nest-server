package top.zhongyingjie.common.constant;

/**
 * @author atulan_zyj
 * @date 2024/10/11
 */
public enum RedisKey {

    ARTICLE_TAGS;

    public String getPrefix(){
        return "MY_LITTLE_NEST_" + this.name() + "_";
    }

}
