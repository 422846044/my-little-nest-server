package fun.dfwh.admin.controller;

import fun.dfwh.admin.service.SystemService;
import fun.dfwh.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author atulan_zyj
 * @date 2025/2/17
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * 获取key对应的value
     *
     * @param key
     * @return
     */
    @GetMapping("/value/{key}")
    public Result getValue(@PathVariable("key") String key){
        return Result.ok().data(systemService.getValue(key));
    }


}
