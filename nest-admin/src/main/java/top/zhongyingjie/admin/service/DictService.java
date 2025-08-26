package top.zhongyingjie.admin.service;

import top.zhongyingjie.admin.vo.DictDataVO;

import java.util.List;
import java.util.Map;

public interface DictService {
    List<DictDataVO> getDictDetailsByDictCode(String dictCode);

    Map getDictMapByDictCode(String dictCode);

    void addDictDetail(String dictCode, String detailName);
}
