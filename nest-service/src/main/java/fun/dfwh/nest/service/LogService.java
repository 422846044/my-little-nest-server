package fun.dfwh.nest.service;

import cn.hutool.core.date.DateUtil;
import fun.dfwh.nest.domain.FightLogInfo;
import fun.dfwh.nest.mapper.FightLogInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class LogService {

    @Autowired(required = false)
    private FightLogInfoMapper fightLogInfoMapper;

    public void addLog(FightLogInfo fightLogInfo){
        try {
            fightLogInfoMapper.insertSelective(fightLogInfo);
        } catch (Exception e) {
            log.error("日志插入失败:[{}]", fightLogInfo.toString());
        }
    }

    public List<FightLogInfo> getLogInfoByQQNum(Integer qqNum) {
        String sDate = DateUtil.today();
        String eDate = DateUtil.tomorrow().toDateStr();
        return fightLogInfoMapper.selectByQQNumAndCreateTime(qqNum,sDate,eDate);
    }
}
