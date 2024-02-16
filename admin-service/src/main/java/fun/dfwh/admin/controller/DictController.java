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

@RestController
@RequestMapping("/admin/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @GetMapping("/getDictDetailsByDictCode")
    public Result getDictDetailsByDictCode(@RequestParam("dictCode") String dictCode){
        List<DictDataVO> dictInfoVOList = dictService.getDictDetailsByDictCode(dictCode);
        return Result.ok().data(dictInfoVOList);
    }
}
