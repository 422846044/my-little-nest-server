package top.zhongyingjie.entity;

import top.zhongyingjie.common.entity.BaseEntity;

/**
 * 动态信息
 *
 * @TableName updates_info
 */
public class UpdatesInfo extends BaseEntity {
    private static final long serialVersionUID = -4159296099285830795L;

    private Long id;

    private Integer type;

    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
