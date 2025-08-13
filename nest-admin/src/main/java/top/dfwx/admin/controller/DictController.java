package top.dfwx.admin.controller;

import top.dfwx.admin.service.DictService;
import top.dfwx.admin.vo.DictDataVO;
import top.dfwx.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @GetMapping("/getDictDetailsByDictCode")
    public Result getDictDetailsByDictCode(@RequestParam("dictCode") String dictCode){
        List<DictDataVO> dictInfoVOList = dictService.getDictDetailsByDictCode(dictCode);
        return Result.success(dictInfoVOList);
    }

    @GetMapping("/getDictMapByDictCode")
    public Result getDictMapByDictCode(@RequestParam("dictCode") String dictCode){
        Map data = dictService.getDictMapByDictCode(dictCode);
        return Result.success(data);
    }
}
