package top.dfwx.nest.constant;

public enum DjcReqUrlEnum {
    COMM_AMS_AME_URL("https://comm.ams.game.qq.com/ams/ame/amesvr?"),
    LOGIN_URL("https://djcapp.game.qq.com/daoju/igw/main/?_service=app.login.user.first&"),

    DJC_MAIN("https://djcapp.game.qq.com/daoju/igw/main/?");
    private String urlStr;

    DjcReqUrlEnum(String urlStr){
        this.urlStr=urlStr;
    }

    public String getUrlStr() {
        return urlStr;
    }
}
