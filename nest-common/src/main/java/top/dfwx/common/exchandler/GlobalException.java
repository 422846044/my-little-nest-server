package top.dfwx.common.exchandler;


import top.dfwx.common.constant.ErrorCode;
import lombok.Data;

@Data
public class GlobalException extends RuntimeException{
    private int code= ErrorCode.FAIL.getCode();
    private String msg = ErrorCode.FAIL.getMessage();

    public GlobalException(){
        super();
    }
    // 对该异常类的构造方法进行补充，不写的话会默认只有一个无参构造
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


}
