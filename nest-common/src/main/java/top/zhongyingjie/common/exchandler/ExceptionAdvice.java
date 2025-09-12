package top.zhongyingjie.common.exchandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zhongyingjie.common.domain.Result;
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

/**
 * 异常通知器
 *
 * @author Kong
 */
@RestControllerAdvice//让系统知道这是处理异常的
//步骤1、将异常转化为对应的类型 2、调用里面的获取消息模块
public class ExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * 异常处理
     *
     * @param e 异常
     * @return 统一返回对象
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)//处理哪种异常
    public Result<Object> exceptionHandler(Exception e) {
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
            message = "参数校验异常:" + bindException.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if (e instanceof GlobalException) {
            // 自定义异常
            GlobalException exception = (GlobalException) e;
            message = exception.getMsg();
        } else {
            log.error("执行异常", e);
            message = "执行异常";
        }
        return Result.serverError(message);
    }
}
