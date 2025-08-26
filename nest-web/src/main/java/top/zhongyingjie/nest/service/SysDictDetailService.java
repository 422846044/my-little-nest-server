package top.zhongyingjie.nest.service;

import java.util.Map;

/**
 * 数据字典详情服务接口
 */
public interface SysDictDetailService {
    
    /**
     * 根据字典编码查询字典数据，返回code为key，name为value的Map集合
     * @param dictCode 字典编码
     * @return Map<String, String> code为key，name为value的Map集合
     */
    Map<String, String> getDictMapByCode(String dictCode);
    
    /**
     * 根据字典编码和状态查询字典数据，返回code为key，name为value的Map集合
     * @param dictCode 字典编码
     * @param status 状态（1--正常0--不使用）
     * @return Map<String, String> code为key，name为value的Map集合
     */
    Map<String, String> getDictMapByCodeAndStatus(String dictCode, Integer status);
} 