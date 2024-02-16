package fun.dfwh.admin.interceptor;

import fun.dfwh.admin.security.domain.SecurityUserDetails;
import fun.dfwh.common.entity.BaseDomain;
import fun.dfwh.common.pojo.UserHolder;
import fun.dfwh.common.pojo.UserLoginInfo;
import org.apache.catalina.security.SecurityConfig;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.Properties;

@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        if (args.length == 1) {
            return invocation.proceed();
        }
        // update方法有两个参数，参见Executor类中的update()方法。
            MappedStatement mappedStatement = (MappedStatement) args[0];
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = args[1];
        if (parameter == null) return invocation.proceed();
            if (sqlCommandType == SqlCommandType.INSERT) {
                create(parameter);
                update(parameter);
            }else if(sqlCommandType == SqlCommandType.UPDATE){
                update(parameter);
            }
        return invocation.proceed();
    }

    private void create(Object parameter){
        if(parameter instanceof BaseDomain){
            SecurityUserDetails ud = (SecurityUserDetails)
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ((BaseDomain) parameter).setCreateBy(Objects.isNull(ud)?"": ud.getUserId());
            ((BaseDomain) parameter).setCreateTime(new Date());
        }
    }

    private void update(Object parameter){
        if(parameter instanceof BaseDomain){
            SecurityUserDetails ud = (SecurityUserDetails)
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ((BaseDomain) parameter).setUpdateBy(Objects.isNull(ud)?"": ud.getUserId());
            ((BaseDomain) parameter).setUpdateTime(new Date());
        }
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
