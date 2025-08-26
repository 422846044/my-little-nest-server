package top.zhongyingjie.nest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhongyingjie.nest.mapper.SysDictDetailMapper;
import top.zhongyingjie.nest.service.SysDictDetailService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据字典详情服务实现类
 */
@Service
public class SysDictDetailServiceImpl implements SysDictDetailService {
    
    @Autowired
    private SysDictDetailMapper sysDictDetailMapper;
    
    @Override
    public Map<String, String> getDictMapByCode(String dictCode) {
        List<HashMap> dictList = sysDictDetailMapper.selectByDictCode(dictCode);
        return convertToMap(dictList);
    }
    
    @Override
    public Map<String, String> getDictMapByCodeAndStatus(String dictCode, Integer status) {
        List<HashMap> dictList = sysDictDetailMapper.selectByDictCodeAndStatusOrderBySort(dictCode, status);
        return convertToMap(dictList);
    }
    
    /**
     * 将查询结果转换为Map
     * @param dictList 字典列表
     * @return Map<String, String> code为key，name为value的Map集合
     */
    private Map<String, String> convertToMap(List<HashMap> dictList) {
        if (dictList == null || dictList.isEmpty()) {
            return new HashMap<>();
        }
        
        return dictList.stream()
                .filter(dict -> dict.get("code") != null && dict.get("name") != null)
                .collect(Collectors.toMap(
                        dict -> dict.get("code").toString(),
                        dict -> dict.get("name").toString(),
                        (existing, replacement) -> existing // 如果有重复的key，保留第一个
                ));
    }
} 