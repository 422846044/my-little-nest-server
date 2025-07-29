package top.dfwx.admin.service.impl;

import top.dfwx.admin.mapper.SysDictDetailMapper;
import top.dfwx.admin.mapper.SysDictMapper;
import top.dfwx.admin.service.DictService;
import top.dfwx.admin.vo.DictDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DictServiceImpl implements DictService {
    @Autowired(required = false)
    private SysDictDetailMapper sysDictDetailMapper;
    @Autowired(required = false)
    private SysDictMapper sysDictMapper;
    @Override
    public List<DictDataVO> getDictDetailsByDictCode(String dictCode) {
        List<DictDataVO> list = new ArrayList<>();
        if(sysDictMapper.select1ByDictCodeAndStatus(dictCode,1)!=null){
            List<HashMap> sysDictDetailList =
                    sysDictDetailMapper.selectByDictCodeAndStatusOrderBySort(dictCode,1);
            for (HashMap<String,String> map : sysDictDetailList) {
                list.add(new DictDataVO(map.get("code"),map.get("name")));
            }
        }
        return list;
    }

    @Override
    public Map getDictMapByDictCode(String dictCode) {
        Map<String,String> data = new HashMap();
        List<HashMap> sysDictDetailList =
                sysDictDetailMapper.selectByDictCode(dictCode);
        for (HashMap hashMap : sysDictDetailList) {
            data.put(hashMap.get("code").toString(),hashMap.get("name").toString());
        }
        return data;
    }
}
