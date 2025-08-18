package top.dfwx.admin.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import top.dfwx.admin.mapper.SysDictDetailMapper;
import top.dfwx.admin.mapper.SysDictMapper;
import top.dfwx.admin.service.DictService;
import top.dfwx.admin.vo.DictDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dfwx.common.exchandler.GlobalException;
import top.dfwx.entity.SysDictDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDictDetail(String dictCode, String detailName) {
        // 查找最大的code
        String code = sysDictDetailMapper.selectOneCodeByDictCodeOrderByCodeDesc(dictCode);
        if (code == null) {
            throw new GlobalException("无该字典代码信息");
        }
        if(!NumberUtil.isNumber(code)){
            throw new GlobalException("code值非数值类型无法自增，请指定code值");
        }
        int codeInt = Integer.parseInt(code);
        SysDictDetail sysDictDetail = new SysDictDetail();
        sysDictDetail.setDictCode(dictCode);
        sysDictDetail.setCode(String.valueOf(++codeInt));
        sysDictDetail.setName(detailName);
        sysDictDetail.setSort(0);
        sysDictDetail.setStatus(1);
        if(sysDictDetailMapper.insert(sysDictDetail) == 0){
            log.error("字典详情表插入失败：插入条数为0");
            throw new GlobalException("操作失败");
        }
    }
}
