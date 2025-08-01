package top.dfwx.admin.service;

import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QiniuService {

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Autowired
    @Qualifier("qiniuCache")
    private Cache<String,String> qiniuUploadToken;
    public String getUploadToken(){
        String token = qiniuUploadToken.asMap().get("token");
        if(StrUtil.isEmpty(token)){
            Auth auth = Auth.create(accessKey, secretKey);
            token = auth.uploadToken(bucket);
            qiniuUploadToken.put("token",token);
        }
        return token;
    }
}
