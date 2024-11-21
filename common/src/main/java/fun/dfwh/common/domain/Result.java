package fun.dfwh.common.domain;

import fun.dfwh.common.constant.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -754791189633729778L;

    private Boolean success;


    private Integer code;

    private String message;

    private T data ;

    public Result(){
        super();
    }

    public Result (Boolean success,Integer code, String message,T data){
        this.success=success;
        this.code=code;
        this.message=message;
        this.data=data;
    }


    public static <T> Result<T> ok(){
        Result<T> Result = new Result<>();
        Result.setSuccess(true);
        Result.setCode(ErrorCode.SUCCCESS.getCode());
        Result.setMessage(ErrorCode.SUCCCESS.getMessage());
        return Result;
    }

    public static <T> Result<T> error(){
        Result<T> Result = new Result<>();
        Result.setSuccess(false);
        Result.setCode(ErrorCode.FAIL.getCode());
        Result.setMessage(ErrorCode.FAIL.getMessage());
        return Result;
    }

    public static <T> Result<T> build(Boolean success,Integer code, String message,T data) {
        return new Result<>(success,code, message, data);
    }
    public static <T> Result<T> build(Boolean success) {
        if(success){
            Result<T> Result = new Result<>();
            Result.setSuccess(true);
            Result.setCode(ErrorCode.SUCCCESS.getCode());
            Result.setMessage(ErrorCode.SUCCCESS.getMessage());
            return Result;
        }else {
            Result<T> Result = new Result<>();
            Result.setSuccess(false);
            Result.setCode(ErrorCode.FAIL.getCode());
            Result.setMessage(ErrorCode.FAIL.getMessage());
            return Result;
        }
    }
    public static <T> Result<T> build(Boolean success,String operation) {
        if(success){
            Result<T> Result = new Result<>();
            Result.setSuccess(true);
            Result.setCode(ErrorCode.SUCCCESS.getCode());
            Result.setMessage(operation.concat("成功"));
            return Result;
        }else {
            Result<T> Result = new Result<>();
            Result.setSuccess(false);
            Result.setCode(ErrorCode.FAIL.getCode());
            Result.setMessage(operation.concat("失败"));
            return Result;
        }
    }


    public Result<T> success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result<T> message(String message){
        this.setMessage(message);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result<T> data(T data){
        this.data=data;
        return this;
    }
}
