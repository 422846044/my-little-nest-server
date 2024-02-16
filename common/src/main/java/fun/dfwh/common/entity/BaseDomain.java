package fun.dfwh.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createTime;
    public String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date updateTime;
    public String updateBy;
}
