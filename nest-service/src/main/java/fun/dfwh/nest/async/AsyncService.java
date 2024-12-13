package fun.dfwh.nest.async;


import fun.dfwh.nest.domain.FightLogInfo;
import fun.dfwh.nest.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Autowired
    private LogService logService;

    @Async(value = "logAsyncService")
    public void addLog(FightLogInfo fightLogInfo){
        logService.addLog(fightLogInfo);
    }
}
