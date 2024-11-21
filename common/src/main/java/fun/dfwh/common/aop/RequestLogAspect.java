package fun.dfwh.common.aop;

import com.alibaba.fastjson2.JSON;
import fun.dfwh.common.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 请求日志切面
 *
 * @author atulan_zyj
 * @date 2024/4/4
 */
@Slf4j
@Component
@Aspect
public class RequestLogAspect {

    /**
     * 参数名发现器
     */
    private static final DefaultParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * 切入点
     */
    @Pointcut("execution(* fun.dfwh.*.controller..*(..)) ")
    public void entryPoint() {
        // 无需内容
    }

    @Before("entryPoint()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String[] parameterNames = PARAMETER_NAME_DISCOVERER.getParameterNames(signature.getMethod());
            //List<String> fieldsName = AspectUtils.getFieldsName(joinPoint);
            List<String> fieldsName = Arrays.asList(parameterNames);
            Object[] args = joinPoint.getArgs();
            Object[] myArgs = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof ServletRequest) {
                    myArgs[i] = "ServletRequest";
                } else if (args[i] instanceof ServletResponse) {
                    myArgs[i] = "ServletResponse";
                } else if (args[i] instanceof MultipartFile) {
                    myArgs[i] = "MultipartFile:" + ((MultipartFile) args[i]).getName();
                } else {
                    myArgs[i] = args[i];
                }
            }
            log.info("\n" + "[接口请求日志开始]" + "\n"
                            + "URL:{}" + "\n"
                            + "IP:{}" + "\n"
                            + "HTTP Method:{}" + "\n"
                            + "类名:{}" + "\n"
                            + "方法名:{}" + "\n"
                            + "请求参数名:{}" + "\n"
                            + "请求参数:{}" + "\n"
                            + "[接口请求日志结束]",
                    request.getRequestURL().toString(),
                    IpUtils.getIpAddress(request),
                    request.getMethod(),
                    joinPoint.getTarget().getClass().getName(),
                    joinPoint.getSignature().getName(),
                    fieldsName,
                    JSON.toJSONString(myArgs));
        } catch (Throwable e) {
            log.info("around " + joinPoint + " with exception : " + e.getMessage());
        }

    }

    @Around("entryPoint()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - startTime;

        log.info("\n" + "[接口返回日志开始]" + "\n"
                        + "类名:{}" + "\n"
                        + "方法名:{}" + "\n"
                        + "返回结果:{}" + "\n"
                        + "方法执行耗时:{}" + "ms" + "\n"
                        + "[接口返回日志结束]",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                JSON.toJSONString(result),
                time);

        return result;
    }

    @AfterThrowing(pointcut = "entryPoint()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        try {
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] parameters = joinPoint.getArgs();
            log.info("异常方法:" + className + "." + methodName + "();参数:" + JSON.toJSONString(parameters));
            log.info("异常信息:" + e.getMessage());
        } catch (Exception ex) {
            log.info("异常信息:" + e.getMessage());
        }
    }
}
