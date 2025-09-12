package top.zhongyingjie.nest.pojo;

import cn.hutool.json.JSONObject;

/**
 * 道具城请求结果
 *
 * @author Kong
 */
public class DjcResult {
    private Boolean djcResult = true;
    private String msg = "";
    private Object data;
    private JSONObject jsonObject;

    public DjcResult(Boolean djcResult, String msg) {
        this.djcResult = djcResult;
        this.msg = msg;
    }

    public DjcResult() {

    }

    public DjcResult(String msg) {

    }

    /**
     * 链式调用，设置消息
     *
     * @param msg 消息
     * @return 道具城请求结果
     */
    public static DjcResult msg(String msg) {
        return new DjcResult(msg);
    }

    /**
     * 链式调用，设置result
     *
     * @param djcResult 道具城请求结果
     * @return 道具城请求结果
     */
    public DjcResult djcResult(Boolean djcResult) {
        this.djcResult = djcResult;
        return this;
    }

    /**
     * 链式调用，设置jsonObject
     *
     * @param jsonObject 道具城请求结果
     * @return 道具城请求结果
     */
    public DjcResult jsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        return this;
    }

    /**
     * 请求失败
     *
     * @return 道具城请求结果
     */
    public static DjcResult requestFail() {
        return new DjcResult(false, "请求发送失败");
    }

    public Boolean getDjcResult() {
        return djcResult;
    }

    public void setDjcResult(Boolean djcResult) {
        this.djcResult = djcResult;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
