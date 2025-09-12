package top.zhongyingjie.common.exchandler;

import top.zhongyingjie.common.constant.ErrorCode;

/**
 * 自定义全局异常
 *
 * @author Kong
 */
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 5469152547364704628L;

    private int code = ErrorCode.FAIL.getCode();
    private String msg = ErrorCode.FAIL.getMessage();

    public GlobalException() {
        super();
    }

    public GlobalException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public GlobalException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public GlobalException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public GlobalException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
