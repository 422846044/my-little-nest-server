package fun.dfwh.common.exchandler;

import fun.dfwh.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;


@Slf4j()//打印日志信息用
@RestControllerAdvice//让系统知道这是处理异常的类
//步骤：1、将异常转化为对应的类型 2、调用里面的获取消息模块
public class ExceptionAdvice {
    @ResponseBody//将异常的返回格式为json格式
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)//处理哪种异常
    public Result exceptionHandler(Exception e) {
        String message = "";
        //处理后端验证失败
        if (e instanceof MethodArgumentNotValidException) {
            //将异常强转为验证异常
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            //获取异常消息
            message = exception.getBindingResult().getFieldError().getDefaultMessage();
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exception = (ConstraintViolationException) e;
            Optional<ConstraintViolation<?>> aa = exception.getConstraintViolations().stream().findFirst();
            if (aa.isPresent()) {
                message = aa.get().getMessage();
            }
        } else if (e instanceof org.springframework.http.converter.HttpMessageNotReadableException) {
            message = "请求参数有误";
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            message = "参数校验异常：" + bindException.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        //自定义异常
        else if (e instanceof GlobalException) {
            GlobalException exception = (GlobalException) e;
            message = exception.getMsg();
        } else {
            log.error("执行异常", e);//将异常打印在控制台
            message = "执行异常";
        }
        return Result.error().message(message);
    }
}