package top.zhongyingjie.nest.vo;

import java.io.Serializable;

/**
 * 归档信息视图对象
 *
 * @author Kong
 */
public class HistoryListVO implements Serializable {

    private static final long serialVersionUID = -8828701508885096648L;

    private Long year;

    private Integer month;

    private Long number;

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
