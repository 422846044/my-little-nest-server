package top.zhongyingjie.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zhongyingjie.admin.mapper.UpdatesInfoMapper;
import top.zhongyingjie.common.enums.UpdatesType;
import top.zhongyingjie.common.exchandler.GlobalException;
import top.zhongyingjie.common.utils.IdWorker;
import top.zhongyingjie.entity.UpdatesInfo;

/**
 * 动态信息服务
 *
 * @author Kong
 */
@Service
public class UpdatesInfoService {

    private static final Logger log = LoggerFactory.getLogger(UpdatesInfoService.class);

    @Autowired
    private UpdatesInfoMapper updatesInfoMapper;

    @Autowired
    private IdWorker idWorker;

    /**
     * 添加动态信息
     *
     * @param title 标题
     * @param type 类型
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(String title, UpdatesType type) {
        UpdatesInfo updatesInfo = new UpdatesInfo();
        updatesInfo.setId(idWorker.nextId());
        updatesInfo.setType(type.getCode());
        updatesInfo.setTitle(String.format(type.getParam(), title));
        if (updatesInfoMapper.insert(updatesInfo) == 0) {
            log.error("插入动态信息表失败：插入条数为0");
            throw new GlobalException("操作失败");
        }
    }

}
