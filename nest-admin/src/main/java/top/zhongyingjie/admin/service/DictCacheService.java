package top.zhongyingjie.admin.service;

import top.zhongyingjie.common.cache.Cache;
import top.zhongyingjie.common.constant.RedisKey;
import top.zhongyingjie.common.exchandler.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author atulan_zyj
 * @date 2024/10/11
 */
@Service
public class DictCacheService {

    @Autowired
    private DictService dictService;

    @Autowired
    private Cache<Map<String, String>> cache;

    /**
     * 根据dictCode校验字典详情中code列表中是否存在无效值
     *
     * @param dictCode 字典代码
     * @param codes code列表
     */
    public void validCode(String dictCode, List<String> codes) {
        // 去重
        List<String> distinctCodes = codes.stream().distinct().collect(Collectors.toList());
        // 获取字典数据
        Map<String, String> map = getDictMapByDictCode(dictCode);
        // 校验是否存在
        distinctCodes.forEach(code -> {
            if (!map.containsKey(code)) {
                throw new GlobalException("存在无效参数");
            }
        });
    }

    /**
     * 通过字典code从缓存获取详情映射表
     *
     * @param dictCode 字典code
     * @return 映射表
     */
    public Map<String, String> getDictMapByDictCode(String dictCode) {
        // 查询缓存内容
        String redisKey = RedisKey.ARTICLE_TAGS.getPrefix();
        if (cache.hasKey(redisKey)) {
            return cache.get(redisKey);
        } else {
            return dictService.getDictMapByDictCode(dictCode);
        }
    }
}
