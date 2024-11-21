package fun.dfwh.common.config;


import fun.dfwh.common.entity.BaseEntity;
import fun.dfwh.common.pojo.UserHolder;
import fun.dfwh.common.pojo.UserLoginInfo;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * mybatis拦截器
 *
 * @author atulan_zyj
 * @date 2024/4/4
 */
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
        } else if (sqlCommandType == SqlCommandType.UPDATE) {
            update(parameter);
        }
        return invocation.proceed();
    }

    private void create(Object parameter) {
        if (parameter instanceof BaseEntity) {
            UserLoginInfo userLoginInfo = UserHolder.getUserLoginInfo();
            if (Objects.nonNull(userLoginInfo)) {
                ((BaseEntity) parameter).setCreateBy(Long.toString(userLoginInfo.getUserId()));
            }
            ((BaseEntity) parameter).setCreateTime(new Date());
            ((BaseEntity) parameter).setDeletedFlag(false);
        } else if (parameter instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap paramterMap = (MapperMethod.ParamMap) parameter;
            for (Object o : paramterMap.values()) {
                if (o instanceof BaseEntity) {
                    UserLoginInfo userLoginInfo = UserHolder.getUserLoginInfo();
                    if (Objects.nonNull(userLoginInfo)) {
                        ((BaseEntity) o).setCreateBy(Long.toString(userLoginInfo.getUserId()));
                    }
                    ((BaseEntity) o).setCreateTime(new Date());
                    ((BaseEntity) o).setDeletedFlag(false);
                } else if (o instanceof List) {
                    List list = (List) o;
                    for (Object object : list) {
                        if (object instanceof BaseEntity) {
                            UserLoginInfo userLoginInfo = UserHolder.getUserLoginInfo();
                            ((BaseEntity) object).setCreateBy(Objects.isNull(userLoginInfo) ? "" : Long.toString(userLoginInfo.getUserId()));
                            ((BaseEntity) object).setCreateTime(new Date());
                            ((BaseEntity) object).setDeletedFlag(false);
                        }
                    }
                }
            }
        }
    }

    private void update(Object parameter) {
        if (parameter instanceof BaseEntity) {
            UserLoginInfo userLoginInfo = UserHolder.getUserLoginInfo();
            if (Objects.isNull(((BaseEntity) parameter).getUpdateBy())) {
                ((BaseEntity) parameter).setUpdateBy(Objects.isNull(userLoginInfo) ? "" : Long.toString(userLoginInfo.getUserId()));
            }
            ((BaseEntity) parameter).setUpdateTime(new Date());
        } else if (parameter instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap paramterMap = (MapperMethod.ParamMap) parameter;
            for (Object o : paramterMap.values()) {
                if (o instanceof BaseEntity) {
                    UserLoginInfo userLoginInfo = UserHolder.getUserLoginInfo();
                    if (Objects.isNull(((BaseEntity) o).getUpdateBy())) {
                        ((BaseEntity) o).setUpdateBy(Objects.isNull(userLoginInfo) ? "" : Long.toString(userLoginInfo.getUserId()));
                    }
                    ((BaseEntity) o).setUpdateTime(new Date());
                } else if (o instanceof List) {
                    List list = (List) o;
                    for (Object object : list) {
                        if (object instanceof BaseEntity) {
                            UserLoginInfo userLoginInfo = UserHolder.getUserLoginInfo();
                            if (Objects.isNull(((BaseEntity) object).getUpdateBy())) {
                                ((BaseEntity) object).setUpdateBy(Objects.isNull(userLoginInfo) ? "" : Long.toString(userLoginInfo.getUserId()));
                            }
                            ((BaseEntity) object).setUpdateTime(new Date());
                        }
                    }
                }
            }
        }
    }

}
