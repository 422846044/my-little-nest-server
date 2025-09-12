package top.zhongyingjie.admin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import top.zhongyingjie.admin.mapper.SysDictDetailMapper;
import top.zhongyingjie.admin.mapper.SysDictMapper;
import top.zhongyingjie.admin.service.DictService;
import top.zhongyingjie.admin.vo.DictDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhongyingjie.common.exchandler.GlobalException;
import top.zhongyingjie.entity.SysDictDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典服务实现
 *
 * @author Kong
 */
@Service
public class DictServiceImpl implements DictService {

    private static final Logger log = LoggerFactory.getLogger(DictServiceImpl.class);

    @Autowired(required = false)
    private SysDictDetailMapper sysDictDetailMapper;

    @Autowired(required = false)
    private SysDictMapper sysDictMapper;

    @Override
    public List<DictDataVO> getDictDetailsByDictCode(String dictCode) {
        List<DictDataVO> list = new ArrayList<>();
        if (sysDictMapper.select1ByDictCodeAndStatus(dictCode, 1) != null) {
            List<HashMap> sysDictDetailList =
                    sysDictDetailMapper.selectByDictCodeAndStatusOrderBySort(dictCode, 1);
            for (HashMap<String, String> map : sysDictDetailList) {
                list.add(new DictDataVO(map.get("code"), map.get("name")));
            }
        }
        return list;
    }

    @Override
    public Map<String, String> getDictMapByDictCode(String dictCode) {
        Map<String, String> data = new HashMap();
        List<HashMap> sysDictDetailList = sysDictDetailMapper.selectByDictCode(dictCode);
        for (HashMap hashMap : sysDictDetailList) {
            data.put(hashMap.get("code").toString(), hashMap.get("name").toString());
        }
        return data;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDictDetail(String dictCode, String detailName) {
        // 查找最大的code 由于code是字符串 需要特殊处理
        Integer code = sysDictDetailMapper.selectOneCodeByDictCodeOrderByCodeDesc(dictCode);
        if (code == null) {
            throw new GlobalException("无该字典代码信息");
        }
        SysDictDetail sysDictDetail = new SysDictDetail();
        sysDictDetail.setDictCode(dictCode);
        sysDictDetail.setCode(String.valueOf(++code));
        sysDictDetail.setName(detailName);
        sysDictDetail.setSort(0);
        sysDictDetail.setStatus(1);
        if (sysDictDetailMapper.insert(sysDictDetail) == 0) {
            log.error("字典详情表插入失败：插入条数为0");
            throw new GlobalException("操作失败");
        }
    }
}
