package top.zhongyingjie.nest.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhongyingjie.entity.UpdatesInfo;
import top.zhongyingjie.nest.mapper.UpdatesInfoMapper;
import top.zhongyingjie.nest.service.UpdatesService;
import top.zhongyingjie.nest.vo.UpdatesVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 动态信息服务实现
 *
 * @author Kong
 */
@Service
public class UpdatesServiceImpl implements UpdatesService {

    private static final int ONE_THOUSAND = 1000;

    private static final int HOUR = 24;

    private static final int MINUTE = 60;

    private static final int SECOND = 60;

    private static final int MIN_DAY = 4;

    private static final int LIMIT = 4;

    @Autowired
    private UpdatesInfoMapper updatesInfoMapper;

    @Override
    public List<UpdatesVO> getNewUpdates() {
        List<UpdatesVO> dataList = new ArrayList<>();
        List<UpdatesInfo> updatesInfos = updatesInfoMapper.selectByLimitOrderByCreateTimeDesc(LIMIT);
        updatesInfos.forEach(updatesInfo -> {
            String time = "";
            UpdatesVO updatesVO = new UpdatesVO();
            BeanUtils.copyProperties(updatesInfo, updatesVO);
            Date createTime = updatesInfo.getCreateTime();
            long ms = DateUtil.betweenMs(new Date(), createTime);
            int minutes = (int) (ms / (SECOND * ONE_THOUSAND));
            if (minutes == 0) {
                time = (ms / ONE_THOUSAND) + "秒前";
            } else if (minutes < SECOND) {
                time = minutes + "分钟前";
            } else {
                int hours = minutes / MINUTE;
                if (hours < HOUR) {
                    time = hours + "小时前";
                } else {
                    long day = DateUtil.betweenDay(new Date(), createTime, true);
                    if (day < MIN_DAY) {
                        time = day + "天前";
                    } else {
                        time = DateUtil.formatDateTime(createTime);
                    }
                }
            }
            updatesVO.setTime(time);
            dataList.add(updatesVO);
        });
        return dataList;
    }
}
