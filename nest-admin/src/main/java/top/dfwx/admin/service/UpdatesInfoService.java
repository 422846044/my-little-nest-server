package top.dfwx.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dfwx.admin.mapper.UpdatesInfoMapper;
import top.dfwx.common.enums.UpdatesType;
import top.dfwx.common.exchandler.GlobalException;
import top.dfwx.common.utils.IdWorker;
import top.dfwx.entity.UpdatesInfo;

/**
 * @author atulan_zyj
 * @date 2025/8/20
 */
@Service
@Slf4j
public class UpdatesInfoService {

    @Autowired
    private UpdatesInfoMapper updatesInfoMapper;

    @Autowired
    private IdWorker idWorker;

    @Transactional(rollbackFor = Exception.class)
    public void add(String title, UpdatesType type){
        UpdatesInfo updatesInfo = new UpdatesInfo();
        updatesInfo.setId(idWorker.nextId());
        updatesInfo.setType(type.getCode());
        updatesInfo.setTitle(String.format(type.getParam(), title));
        if(updatesInfoMapper.insert(updatesInfo) == 0){
            log.error("插入动态信息表失败：插入条数为0");
            throw new GlobalException("操作失败");
        }
    }

}
