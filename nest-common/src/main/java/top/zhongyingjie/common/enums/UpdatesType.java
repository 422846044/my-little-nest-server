package top.zhongyingjie.common.enums;


/**
 * 动态类型枚举
 *
 * @author Kong
 */
public enum UpdatesType {

    ADD_ARTICLE(0, "发布文章", "发布了文章《%s》"),

    EDIT_ARTICLE(1, "编辑文章", "编辑了文章《%s》");

    private final int code;

    private final String desc;

    private final String param;

    UpdatesType(int code, String desc, String param) {
        this.code = code;
        this.desc = desc;
        this.param = param;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getParam() {
        return param;
    }
}
