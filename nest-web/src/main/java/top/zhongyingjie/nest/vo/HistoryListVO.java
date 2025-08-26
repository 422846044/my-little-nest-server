package top.zhongyingjie.nest.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author atulan_zyj
 * @date 2024/12/3
 */
@Data
public class HistoryListVO implements Serializable {

    private static final long serialVersionUID = -8828701508885096648L;

    private Long year;

    private Integer month;

    private Long number;

}
