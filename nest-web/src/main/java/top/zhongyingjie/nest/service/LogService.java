package top.zhongyingjie.nest.service;

import cn.hutool.core.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zhongyingjie.nest.domain.FightLogInfo;
import top.zhongyingjie.nest.mapper.FightLogInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 乐斗日志服务
 *
 * @author Kong
 */
@Service
public class LogService {

    private static final Logger log = LoggerFactory.getLogger(LogService.class);

    @Autowired(required = false)
    private FightLogInfoMapper fightLogInfoMapper;

    /**
     * 添加日志
     *
     * @param fightLogInfo 日志信息
     */
    public void addLog(FightLogInfo fightLogInfo) {
        try {
            fightLogInfoMapper.insertSelective(fightLogInfo);
        } catch (Exception e) {
            log.error("日志插入失败:[{}]", fightLogInfo.toString());
        }
    }

    /**
     * 通过QQ号获取日志信息
     *
     * @param qqNum QQ号
     * @return 日志信息列表
     */
    public List<FightLogInfo> getLogInfoByQQNum(Integer qqNum) {
        String sDate = DateUtil.today();
        String eDate = DateUtil.tomorrow().toDateStr();
        return fightLogInfoMapper.selectByQQNumAndCreateTime(qqNum, sDate, eDate);
    }
}
