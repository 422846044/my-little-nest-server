package top.zhongyingjie.admin.vo;

import java.io.Serializable;

/**
 * 字典数据视图对象
 * 
 */
public class DictDataVO implements Serializable {

    private static final long serialVersionUID = -4394633362537062945L;
    
    private String code;
    
    private String name;

    public DictDataVO() {
    }

    public DictDataVO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
