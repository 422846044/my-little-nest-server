package top.zhongyingjie.nest.pojo;

import cn.hutool.json.JSONObject;
import lombok.Data;

@Data
public class DjcResult {
    private Boolean DjcResult = true;
    private String msg = "";
    private Object data ;

    private JSONObject jsonObject;


    public static DjcResult success(String msg){
        return new DjcResult(msg);
    }

    public static DjcResult success(){
        return new DjcResult();
    }

    public static DjcResult data(Object o){
        return new DjcResult(o);
    }

    public static DjcResult fail(String msg){
        return new DjcResult(false,msg);
    }

    public static DjcResult fail(){
        return new DjcResult(false);
    }

    public DjcResult(String msg){
        this.msg = msg;
    }

    public DjcResult(Boolean DjcResult){
        this.DjcResult = DjcResult;
    }

    public DjcResult(Boolean DjcResult,String msg){
        this.DjcResult = DjcResult;
        this.msg = msg;
    }

    public DjcResult(Object o){
        this.DjcResult = true;
        this.data = o;
    }

    public DjcResult(){

    }

    public static DjcResult msg(String msg) {
        return new DjcResult(msg);
    }

    public static DjcResult requestFail() {
        return new DjcResult(false,"请求发送失败");
    }

    public DjcResult DjcResult(boolean DjcResult){
        this.DjcResult = DjcResult;
        return this;
    }

    public DjcResult jsonObject(JSONObject jsonObject){
        this.jsonObject=jsonObject;
        return this;
    }
}
