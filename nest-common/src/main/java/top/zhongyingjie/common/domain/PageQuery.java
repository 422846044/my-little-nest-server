package top.zhongyingjie.common.domain;

import java.io.Serializable;

/**
 * 分页查询基类
 *
 * @author atulan_zyj
 * @date 2024/10/18
 */
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 5135110693188015645L;

    private Integer pageNum;

    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
