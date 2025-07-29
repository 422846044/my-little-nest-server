package top.dfwx.common.function;

/**
 * 抛异常接口
 *
 * @author atulan_zyj
 * @date 2024/8/21
 */
@FunctionalInterface
public interface ThrowExceptionFunction {

    /**
     * 抛出异常信息
     *
     * @param message 异常信息
     **/
    void throwMessage(String message);

}
