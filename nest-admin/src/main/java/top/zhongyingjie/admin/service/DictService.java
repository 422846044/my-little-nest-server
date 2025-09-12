package top.zhongyingjie.admin.service;

import top.zhongyingjie.admin.vo.DictDataVO;

import java.util.List;
import java.util.Map;

/**
 * 字典服务接口
 *
 * @author Kong
 */
public interface DictService {

    /**
     * 通过字典code获取字典详情信息视图对象列表
     *
     * @param dictCode 字典code
     * @return 字典详情信息视图对象列表
     */
    List<DictDataVO> getDictDetailsByDictCode(String dictCode);

    /**
     * 通过字典code获取字典详情映射表
     *
     * @param dictCode 字典code
     * @return 字典详情映射表
     */
    Map<String, String> getDictMapByDictCode(String dictCode);

    /**
     * 添加字典详情
     *
     * @param dictCode 字典code
     * @param detailName 字典name
     */
    void addDictDetail(String dictCode, String detailName);
}
