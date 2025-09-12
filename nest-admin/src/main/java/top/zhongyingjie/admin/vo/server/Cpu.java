package top.zhongyingjie.admin.vo.server;


import top.zhongyingjie.common.utils.Arith;

/**
 * CPU相关信息
 *
 * @author Kong
 */
public class Cpu {

    private static final int MULTIPLIER = 100;

    private static final int SCALE = 2;

    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public int getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum) {
        this.cpuNum = cpuNum;
    }

    public double getTotal() {
        return Arith.round(Arith.mul(total, MULTIPLIER), SCALE);
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSys() {
        return Arith.round(Arith.mul(sys / total, MULTIPLIER), SCALE);
    }

    public void setSys(double sys) {
        this.sys = sys;
    }

    public double getUsed() {
        return Arith.round(Arith.mul(used / total, MULTIPLIER), SCALE);
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getWait() {
        return Arith.round(Arith.mul(wait / total, MULTIPLIER), SCALE);
    }

    public void setWait(double wait) {
        this.wait = wait;
    }

    public double getFree() {
        return Arith.round(Arith.mul(free / total, MULTIPLIER), SCALE);
    }

    public void setFree(double free) {
        this.free = free;
    }
}
