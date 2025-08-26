package top.zhongyingjie.common.utils;


import top.zhongyingjie.common.exchandler.GlobalException;
import top.zhongyingjie.common.function.ThrowExceptionFunction;

/**
 * 校验工具类
 *
 * @author atulan_zyj
 * @date 2024/8/21
 */
public class CheckUtils {

    /**
     * 如果参数为true抛出异常
     *
     * @param b 布尔值
     * @return ThrowExceptionFunction
     **/
    public static ThrowExceptionFunction isTure(boolean b) {
        return (errorMessage) -> {
            if (b) {
                throw new GlobalException(errorMessage);
            }
        };
    }


}
