package fun.dfwh.admin.service;

import fun.dfwh.admin.vo.DictDataVO;

import java.util.List;
import java.util.Map;

public interface DictService {
    List<DictDataVO> getDictDetailsByDictCode(String dictCode);

    Map getDictMapByDictCode(String dictCode);
}
