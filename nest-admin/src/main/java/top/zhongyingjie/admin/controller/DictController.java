package top.zhongyingjie.admin.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.zhongyingjie.admin.service.DictService;
import top.zhongyingjie.admin.vo.DictDataVO;
import top.zhongyingjie.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dict")
@Validated
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

    @PostMapping("/detail/info/{dictCode}/{detailName}")
    public Result<Object> addDictDetail(@PathVariable("dictCode") String dictCode,
                                        @Length(message = "名称长度不能超过30")
                                        @NotBlank(message = "名称不能为空")
                                        @PathVariable("detailName") String detailName) {
        dictService.addDictDetail(dictCode, detailName);
        return Result.success();
    }
}
