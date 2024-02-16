package fun.dfwh.admin.service.impl;

import fun.dfwh.admin.entity.SysDictDetail;
import fun.dfwh.admin.mapper.SysDictDetailMapper;
import fun.dfwh.admin.mapper.SysDictMapper;
import fun.dfwh.admin.service.DictService;
import fun.dfwh.admin.vo.DictDataVO;
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
}
