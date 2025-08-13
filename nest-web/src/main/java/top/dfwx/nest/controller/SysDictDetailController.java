package top.dfwx.nest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.dfwx.common.domain.Result;
import top.dfwx.nest.service.SysDictDetailService;

import java.util.Map;

/**
 * 数据字典详情控制器
 */
@RestController
@RequestMapping("/dataDict")
@CrossOrigin
public class SysDictDetailController {
    
    @Autowired
    private SysDictDetailService sysDictDetailService;
    
    /**
     * 根据字典编码查询字典数据
     * @param dictCode 字典编码
     * @return Result<Map<String, String>> 返回code为key，name为value的Map集合
     */
    @GetMapping("/map/{dictCode}")
    public Result<Map<String, String>> getDictMapByCode(@PathVariable("dictCode") String dictCode) {
        return Result.success(sysDictDetailService.getDictMapByCode(dictCode));
    }
    
    /**
     * 根据字典编码和状态查询字典数据
     * @param dictCode 字典编码
     * @param status 状态（1--正常0--不使用）
     * @return Result<Map<String, String>> 返回code为key，name为value的Map集合
     */
    @GetMapping("/map/{dictCode}/{status}")
    public Result<Map<String, String>> getDictMapByCodeAndStatus(
            @PathVariable String dictCode,
            @PathVariable Integer status) {
        return Result.success(sysDictDetailService.getDictMapByCodeAndStatus(dictCode, status));

    }
    
    /**
     * 根据字典编码查询字典数据（POST方式）
     * @param dictCode 字典编码
     * @return Result<Map<String, String>> 返回code为key，name为value的Map集合
     */
    @PostMapping("/map")
    public Result<Map<String, String>> getDictMapByCodePost(@RequestParam String dictCode) {
        return Result.success(sysDictDetailService.getDictMapByCode(dictCode));
    }
} 