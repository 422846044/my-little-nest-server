package top.zhongyingjie.nest.code.example.signature.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import top.zhongyingjie.nest.code.example.signature.dto.BizRequest;
import top.zhongyingjie.nest.code.example.signature.dto.SignedRequest;
import top.zhongyingjie.nest.code.example.signature.service.ClientSignatureService;

import java.nio.charset.StandardCharsets;

/**
 * HTTP客户端调用示例
 *
 * @author Kong
 */
public class ApiClient {

    private final ClientSignatureService signatureService;
    private final String baseUrl;
    private final ObjectMapper objectMapper;

    public ApiClient(String appId, String appSecret, String baseUrl) {
        this.signatureService = new ClientSignatureService(appId, appSecret);
        this.baseUrl = baseUrl;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 调用API接口
     *
     * @param endpoint   路径
     * @param bizContent 业务内容
     * @return 请求结果
     */
    public String callApi(String endpoint, Object bizContent) throws Exception {
        // 构建带签名的请求
        SignedRequest signedRequest = signatureService.buildSignedRequest(bizContent);

        // 转换为JSON
        String requestBody = objectMapper.writeValueAsString(signedRequest);

        // 创建HTTP请求
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(baseUrl + endpoint);
            httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
            httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));

            // 执行请求
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }
        }
    }

    public static void main(String[] args) {
        // 创建客户端
        ApiClient client = new ApiClient(
                "appId01",
                "your_secret_key_123456",
                "http://localhost:8080"
        );

        // 构建业务参数
        BizRequest testRequest = new BizRequest("10001", "test");
        try {
            // 调用查询接口
            String queryResult = client.callApi("/test/signature/test", testRequest);
            System.out.println(queryResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}