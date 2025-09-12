package top.zhongyingjie.nest.vo;

import java.io.Serializable;

/**
 * 动态信息视图对象
 *
 * @author Kong
 */
public class UpdatesVO implements Serializable {

    private static final long serialVersionUID = -8298909370373700386L;

    private String title;

    private String time;

    private Integer type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
