package fun.dfwh.admin.controller;

import fun.dfwh.admin.service.DictService;
import fun.dfwh.admin.vo.DictDataVO;
import fun.dfwh.common.domain.Result;
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
        return Result.ok().data(dictInfoVOList);
    }

    @GetMapping("/getDictMapByDictCode")
    public Result getDictMapByDictCode(@RequestParam("dictCode") String dictCode){
        Map data = dictService.getDictMapByDictCode(dictCode);
        return Result.ok().data(data);
    }
}
