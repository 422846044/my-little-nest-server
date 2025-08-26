package top.zhongyingjie.nest.service.impl;

import cn.hutool.core.date.DateUtil;
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

@Service
public class UpdatesServiceImpl implements UpdatesService {

    @Autowired
    private UpdatesInfoMapper updatesInfoMapper;

    @Override
    public List<UpdatesVO> getNewUpdates() {
        List<UpdatesVO> dataList = new ArrayList<>();
        List<UpdatesInfo> updatesInfos = updatesInfoMapper.selectByLimitOrderByCreateTimeDesc(4);
        updatesInfos.forEach(updatesInfo -> {
            String time = "";
            UpdatesVO updatesVO = new UpdatesVO();
            BeanUtils.copyProperties(updatesInfo, updatesVO);
            Date createTime = updatesInfo.getCreateTime();
            long ms = DateUtil.betweenMs(new Date(), createTime);
            int minutes = (int) (ms / 60000);
            if(minutes == 0){
                time = (ms/1000) + "秒前";
            }else if(minutes < 60 ){
                time = minutes + "分钟前";
            }else{
                int hours = minutes / 60;
                if(hours < 24){
                    time = hours + "小时前";
                }else{
                    long day = DateUtil.betweenDay(new Date(), createTime, true);
                    if(day < 4){
                        time = day + "天前";
                    }else{
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
