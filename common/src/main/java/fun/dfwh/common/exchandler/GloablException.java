package fun.dfwh.common.exchandler;


import fun.dfwh.common.constant.ErrorCode;
import lombok.Data;

@Data
public class GloablException extends RuntimeException{
    private int code= ErrorCode.FAIL.getCode();
    private String msg = ErrorCode.FAIL.getMessage();

    public GloablException(){
        super();
    }
    //对该异常类的构造方法进行补充，不写的化会默认只有一个无参构造
    public GloablException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public GloablException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public GloablException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public GloablException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }


}
