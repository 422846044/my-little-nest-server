package top.dfwx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author atulan_zyj
 * @date 2025/8/20
 */
@AllArgsConstructor
@Getter
public enum UpdatesType {

    ADD_ARTICLE(0, "发布文章", "发布了文章《%s》"),

    EDIT_ARTICLE(1, "编辑文章", "编辑了文章《%s》");

    private final int code;

    private final String desc;

    private final String param;

}
