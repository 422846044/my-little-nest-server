package top.dfwx.admin.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author atulan_zyj
 * @date 2024/10/23
 */
@Data
public class HomeDataCountVO implements Serializable {
    private static final long serialVersionUID = 7172062063532495943L;

    public Long allCount;

    public Long monthCount;

    public Long dayCount;

    public Long draftCount;

    public Integer monthCountThan;

    public Integer dayCountThan;



}
