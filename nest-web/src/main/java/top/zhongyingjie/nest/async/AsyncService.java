package top.zhongyingjie.nest.async;


import top.zhongyingjie.nest.domain.FightLogInfo;
import top.zhongyingjie.nest.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步服务
 *
 * @author Kong
 */
@Service
public class AsyncService {

    @Autowired
    private LogService logService;

    /**
     * 异步添加乐斗日志信息
     *
     * @param fightLogInfo 乐斗日志信息
     */
    @Async(value = "logAsyncService")
    public void addLog(FightLogInfo fightLogInfo) {
        logService.addLog(fightLogInfo);
    }
}
