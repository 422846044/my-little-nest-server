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


/**
 * 字典信息api
 *
 * @author Kong
 */
@RestController
@RequestMapping("/dict")
@Validated
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 根据字典code获取字典详情信息列表
     *
     * @param dictCode 字典code
     * @return 统一返回对象
     */
    @GetMapping("/getDictDetailsByDictCode")
    public Result<List<DictDataVO>> getDictDetailsByDictCode(@RequestParam("dictCode") String dictCode) {
        return Result.success(dictService.getDictDetailsByDictCode(dictCode));
    }

    /**
     * 根据字典code获取字典详情映射表
     *
     * @param dictCode 字典code
     * @return 统一返回对象
     */
    @GetMapping("/getDictMapByDictCode")
    public Result<Map<String, String>> getDictMapByDictCode(@RequestParam("dictCode") String dictCode) {
        return Result.success(dictService.getDictMapByDictCode(dictCode));
    }

    /**
     * 添加字段详情
     *
     * @param dictCode   目录code
     * @param detailName 详情名称
     * @return 统一返回对象
     */
    @PostMapping("/detail/info/{dictCode}/{detailName}")
    public Result<Object> addDictDetail(@PathVariable("dictCode") String dictCode,
                                        @Length(message = "名称长度不能超过30")
                                        @NotBlank(message = "名称不能为空")
                                        @PathVariable("detailName") String detailName) {
        dictService.addDictDetail(dictCode, detailName);
        return Result.success();
    }
}
