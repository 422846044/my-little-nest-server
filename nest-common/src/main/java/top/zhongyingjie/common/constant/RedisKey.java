package top.zhongyingjie.common.constant;

/**
 * redis键枚举
 *
 * @author Kong
 */
public enum RedisKey {

    ARTICLE_TAGS;

    public String getPrefix() {
        return "MY_LITTLE_NEST_" + this.name() + "_";
    }

}
