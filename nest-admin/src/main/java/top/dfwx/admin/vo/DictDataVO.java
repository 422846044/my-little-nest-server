package top.dfwx.admin.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DictDataVO implements Serializable {
    private String code;
    private String name;

    public DictDataVO() {
    }

    public DictDataVO(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
