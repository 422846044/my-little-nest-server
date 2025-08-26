package top.zhongyingjie.admin.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author atulan_zyj
 * @date 2024/10/31
 */
public class Test {
    public static void main(String[] args) {
        FileUtil.copyContent(new File("D:\\证件" ), new File("D:\\test"), true);
    }
}
