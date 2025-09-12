package top.zhongyingjie.admin.vo;

import java.io.Serializable;

/**
 * 首页数据统计视图对象
 *
 * @author Kong
 */
public class HomeDataCountVO implements Serializable {

    private static final long serialVersionUID = 7172062063532495943L;

    private Long allCount;

    private Long monthCount;

    private Long dayCount;

    private Long draftCount;

    private Integer monthCountThan;

    private Integer dayCountThan;

    public Long getAllCount() {
        return allCount;
    }

    public void setAllCount(Long allCount) {
        this.allCount = allCount;
    }

    public Long getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(Long monthCount) {
        this.monthCount = monthCount;
    }

    public Long getDayCount() {
        return dayCount;
    }

    public void setDayCount(Long dayCount) {
        this.dayCount = dayCount;
    }

    public Long getDraftCount() {
        return draftCount;
    }

    public void setDraftCount(Long draftCount) {
        this.draftCount = draftCount;
    }

    public Integer getMonthCountThan() {
        return monthCountThan;
    }

    public void setMonthCountThan(Integer monthCountThan) {
        this.monthCountThan = monthCountThan;
    }

    public Integer getDayCountThan() {
        return dayCountThan;
    }

    public void setDayCountThan(Integer dayCountThan) {
        this.dayCountThan = dayCountThan;
    }
}
