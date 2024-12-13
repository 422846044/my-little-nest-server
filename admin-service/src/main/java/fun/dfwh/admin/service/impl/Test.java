package fun.dfwh.admin.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

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
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("1", "2"));
        System.out.println(strings.removeAll(Collections.emptyList()));
    }
}
