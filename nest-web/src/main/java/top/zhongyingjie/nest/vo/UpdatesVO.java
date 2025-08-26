package top.zhongyingjie.nest.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatesVO implements Serializable {

    private static final long serialVersionUID = -8298909370373700386L;

    private String title;

    private String time;

    private Integer type;

}
