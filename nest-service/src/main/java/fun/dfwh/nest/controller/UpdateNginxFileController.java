package fun.dfwh.nest.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import fun.dfwh.common.domain.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

/**
 * @author atulan_zyj
 * @date 2025/7/24
 */
@RestController
@RequestMapping("/nginx/update/file")
public class UpdateNginxFileController {


    @PutMapping("/replaceAll/{type}")
    public Result<Object> replaceAll(@PathVariable("type") Integer type,
                                     @RequestPart MultipartFile multipartFile) throws IOException {

        String fileName = "frontend.zip", originalDirName = "华雅生活家", targetDirName = "axure";
        if(type == 1){
            fileName = "backend.zip";
            originalDirName = "华雅后台";
            targetDirName = "hyht";
        }

        String uploadFilePath = "/usr/local/application/tmpFile/" + fileName;

        //将上传的文件保存到本地
        File file = new File(uploadFilePath);
        InputStream inputStream = multipartFile.getInputStream();
        IoUtil.copy(inputStream, Files.newOutputStream(file.toPath()));

        List<List<String>> list = Arrays.asList(Arrays.asList("unzip", uploadFilePath, "-d", "/usr/local/application/tmpFile/frontend"), // 解压文件
                Arrays.asList("rm", "-f", "/usr/local/nginx/html/axure/*"),// 删除原文件
                Arrays.asList("mv", "/usr/local/application/tmpFile/frontend/" + originalDirName + "/*", "/usr/local/nginx/html/" + targetDirName)  // 移动文件
        );
        for (List<String> strings : list) {
            extracted(strings);
        }
        return Result.ok();
    }

    private static void extracted(List<String> cmd) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }


}
